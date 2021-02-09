package com.agsft.ticketleap.services;

import org.springframework.stereotype.Service;

/**
 * register the user to an existing event
 * 
 * @author Vishal
 *
 */
@Service
public interface UserEventRegisteration {

	public void registerUserToAnEvent(String userId, String eventId);
	
}
