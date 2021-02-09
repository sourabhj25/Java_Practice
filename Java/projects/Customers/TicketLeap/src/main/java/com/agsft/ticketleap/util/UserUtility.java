package com.agsft.ticketleap.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.repo.UserRepository;


// TODO: Auto-generated Javadoc
/**
 * The Class UserUtility.
 */
@Component
public class UserUtility {

	/** The user repository. */
	@Autowired
	UserRepository userRepository;

	/**
	 * Gets the logged in user id.
	 *
	 * @return the logged in user id
	 */
	public User getLoggedInUser() {
		User createByUser = userRepository.findUserByEmail(findLoggedInUsername());
		return createByUser;
	}

	/**
	 * Find logged in username.
	 *
	 * @return the string
	 */
	public String findLoggedInUsername() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof UserDetails) 
		{
			return ((UserDetails) userDetails).getUsername();
		}
		return null;
	}

}
