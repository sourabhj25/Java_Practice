/*package com.agsft.ticketleap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.req.LoginDTO;
import com.agsft.ticketleap.repo.UserRepository;
import com.agsft.ticketleap.services.LoginService;

*//**
 * @author Vishal
 *
 *//*
@Component
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	public User authenticateLogin(LoginDTO loginDTO) {
		return userRepo.checkLogin(loginDTO.getEmail(), loginDTO.getPassword());
	}

	@Override
	public UserDetails userSignIn(String username, String password) {

		UserDetails userDetails = userDetailsService.loadUserByEmailId(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());
		Authentication token = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		if (token.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(token);
			System.out.println(String.format("Auto login %s successfully!", username));
		} else {
			System.out.println(String.format("authentication failed for name %s ", username));
		}
		Object userDetail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return (UserDetails) userDetail;
	}

}*/