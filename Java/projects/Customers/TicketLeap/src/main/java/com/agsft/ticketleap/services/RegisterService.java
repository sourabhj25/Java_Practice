package com.agsft.ticketleap.services;

import org.springframework.stereotype.Component;

import com.agsft.ticketleap.model.req.RegisterDTO;

/**
 * @author Vishal
 *
 */
@Component
public interface RegisterService {

	/**
	 * registers the user with end user privilege
	 * 
	 * @param registerDTO
	 * @return
	 */
	String registerUser(RegisterDTO registerDTO);
	
	
}
