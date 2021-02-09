package com.agsft.ticketleap.services;

import java.util.List;

import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.req.CreateUserBuyTickets;
import com.agsft.ticketleap.model.req.RegisterDTO;
import com.agsft.ticketleap.model.req.UserDTO;
import com.agsft.ticketleap.model.res.LoginResponseDTO;
import com.agsft.ticketleap.model.res.UserResponseDTO;

/**
 * 
 * @author shekhar
 *
 */
public interface UserService {

	/**This function is to create new user for the system
	 * @param user
	 * @return message
	 */
	public String createUser(UserDTO user);

	public User updateUser(String token, RegisterDTO registerDTO);

	public User findById(String uid);
	
	public User findByEmail(String email);

	public LoginResponseDTO getUserDetails(String username);
	
	public List<UserResponseDTO> getUserByOrganization(String orgId);
	
	public User createUserForBuyTickets(CreateUserBuyTickets newUser);
	
}
