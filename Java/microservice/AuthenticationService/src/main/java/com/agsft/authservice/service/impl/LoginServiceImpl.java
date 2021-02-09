package com.agsft.authservice.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.agsft.authservice.constant.HttpStatusCode;
import com.agsft.authservice.exception.AuthenticationServiceException;
import com.agsft.authservice.model.Client;
import com.agsft.authservice.model.User;
import com.agsft.authservice.model.conversion.ConvertDBModelToResponse;
import com.agsft.authservice.repository.UserRepository;
import com.agsft.authservice.request.model.LoginRequestModel;
import com.agsft.authservice.response.model.LoginResponseModel;
import com.agsft.authservice.security.CustomPasswordAuthenticationManager;
import com.agsft.authservice.security.JwtTokenUtil;
import com.agsft.authservice.security.JwtUserDetailsServiceImpl;
import com.agsft.authservice.service.LoginService;
import com.agsft.authservice.utility.AuthenticationServiceUtility;

import lombok.extern.java.Log;

@Service
@Log
public class LoginServiceImpl implements LoginService {

	    @Autowired
	    private JwtUserDetailsServiceImpl userDetailsService;
	    @Autowired
	    private UserRepository userRepository;	    
	    @Autowired
	    private AuthenticationServiceUtility authServiceUtility;
	    
	    @Autowired
	    private ConvertDBModelToResponse convertDbModelToResponse;
	    
	    @Autowired
		private JwtTokenUtil jwtTokenUtil;
		
	    @Autowired
	    CustomPasswordAuthenticationManager authenticationManager;
	    
	    /**
	     * This method used to authenticate user and sign in
	     * <li>Generate userdetails object to authenticate user</li>
	     * <li>If user authenticate set user details in context holder</li>
	     */
		public UserDetails userAuthentication(String username, String password,Client client)
		{
			UserDetails userDetails = userDetailsService.loadUserByUsername(username, client.getClientId());
			log.info("User details"+userDetails.toString());
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, password, userDetails.getAuthorities());
			log.info("username password token"+usernamePasswordAuthenticationToken);
			Authentication token = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			if (token.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(token);
				log.info(String.format("Auto login %s successfully!", username));
			} else {
				log.info(String.format("authentication failed for name %s ", username));
			}
			Object userDetail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return (UserDetails) userDetail;
		}

		/**
		 * <ul>
		 * This method is used to authenticate user with provided username and pasword.
		 * <li>Verify user exist in system of provided client</li>
		 * <li>Authenticate user</li>
		 * <li>Convert login user details to login response model</li>
		 * </ul>
		 */
		@Override
		public LoginResponseModel userSignIn(LoginRequestModel loginRequest,Client client) throws AuthenticationServiceException {
						
		    User user=userRepository.findByUsernameAndClient(loginRequest.getUsername(), client.getClientId());
			if(user==null)
			{
				throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(),authServiceUtility.getMessage("user.doesn't.exists")); 
			}
		    UserDetails userDetails=userAuthentication(loginRequest.getUsername(),loginRequest.getPassword(),client);
			if(userDetails==null)
			{
				throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(),authServiceUtility.getMessage("login.failed")); 
			}
			user.setLastLogin(new Date());
			userRepository.save(user);
			LoginResponseModel loginResponse=convertDbModelToResponse.convertUserToLoginResponse(user);
			loginResponse.setToken(jwtTokenUtil.generateToken(user,client));  	
		    return loginResponse;
		}


}
