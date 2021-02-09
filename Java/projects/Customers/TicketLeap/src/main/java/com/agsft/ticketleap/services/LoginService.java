package com.agsft.ticketleap.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.req.LoginDTO;

/**
 * @author Vishal
 *
 */
@Component
public interface LoginService {

	/**
	 * check if user exists or not
	 * 
	 * @param loginDTO
	 * @return
	 */
	public User authenticateLogin(LoginDTO loginDTO);
	
	
	public UserDetails userSignIn(String username, String password);
	
	
}
