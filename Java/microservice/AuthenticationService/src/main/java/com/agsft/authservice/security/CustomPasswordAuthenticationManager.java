package com.agsft.authservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.agsft.authservice.model.User;
import com.agsft.authservice.repository.UserRepository;
import com.agsft.authservice.service.UserService;

@Component
public class CustomPasswordAuthenticationManager implements AuthenticationProvider 
{
 
    @Autowired
    UserRepository userRepository;
    
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
    
	private static final Logger logger = LoggerFactory.getLogger(CustomPasswordAuthenticationManager.class);
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		 logger.info("Inside authenticate method");
		 JwtUser jwtUser=(JwtUser) authentication.getPrincipal();
		 User user=userRepository.findByUsernameAndClient(jwtUser.getUsername(), jwtUser.getClientId());
		 String password=authentication.getCredentials().toString();
		 if(bCryptPasswordEncoder.matches(password, user.getPassword()))
		 {
			 logger.info("User authenticated succesfully");
			 return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), authentication.getAuthorities());
		 }
		 else
		 {
			 logger.error("bad credentials");
			 throw new BadCredentialsException("bad credentials");
		 }
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		
		 return true;
	}
}