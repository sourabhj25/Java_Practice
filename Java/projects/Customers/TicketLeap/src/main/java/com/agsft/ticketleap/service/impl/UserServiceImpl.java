package com.agsft.ticketleap.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.agsft.ticketleap.constants.RolesConstants;
import com.agsft.ticketleap.model.Organisation;
import com.agsft.ticketleap.model.Roles;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.UserToken;
import com.agsft.ticketleap.model.req.CreateUserBuyTickets;
import com.agsft.ticketleap.model.req.RegisterDTO;
import com.agsft.ticketleap.model.req.UserDTO;
import com.agsft.ticketleap.model.res.LoginResponseDTO;
import com.agsft.ticketleap.model.res.UserResponseDTO;
import com.agsft.ticketleap.repo.OrganisationRepository;
import com.agsft.ticketleap.repo.UserRepository;
import com.agsft.ticketleap.repo.UserTokenRepository;
import com.agsft.ticketleap.services.UserService;
import com.agsft.ticketleap.util.ConversionUtility;
import com.agsft.ticketleap.util.EmailUtility;

/**
 * User Services for creation and updation
 * 
 * @author shekhar
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	UserTokenRepository userTokenRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	EmailUtility emailUtility;

	@Autowired
	OrganisationRepository orgRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.agsft.ticketleap.services.UserService#createUser(com.agsft.
	 * ticketleap. model.req.UserDTO)
	 */
	@Override
	public String createUser(UserDTO user) {
		User userObject = null;
		String token = null;
		userObject = userRepo.findUserByEmail(user.getEmail());
		if (userObject == null) {
			token = UUID.randomUUID().toString();
			Organisation org = orgRepo.findOrgById(user.getOrganisationIds());
			User newUser = ConversionUtility.convertUserDtoToUser(user);
			newUser.setPartnerId(org.getPartnetId());
			userRepo.save(newUser);
			User createdUserForToken = userRepo.findUserByEmail(newUser.getEmail());
			persistToken(createdUserForToken, token);
			emailUtility.sendMail(newUser.getEmail(), token, newUser.getRoles().getRoleType().toString());
			return "Email sent successfully check your account";

		} else {
			return "User already Exists";
		}

	}

	private void persistToken(User createdUserForToken, String token) {

		UserToken userToken = new UserToken();
		userToken.setExpired(false);
		userToken.setToken(token);
		userToken.setUserId(createdUserForToken.get_id());
		userTokenRepository.save(userToken);

	}

	@Override
	public User updateUser(String token, RegisterDTO registerDTO) {
		String responseMessage = "";

		User existingUser = userRepo.findUserByEmail(registerDTO.getEmail());

		String password = bCryptPasswordEncoder.encode(registerDTO.getPassword());
		UserToken userToken = userTokenRepository.findUserByToken(token);
		if (existingUser != null) {
			if (userToken != null) {
				if (!existingUser.getPassword().isEmpty())
					return existingUser;
				existingUser.setPassword(password);
				System.out.println(registerDTO.getPassword() + "Password :: " + existingUser.getPassword());
				userRepo.save(existingUser);

			} else {
				return null;
			}
		}
		return existingUser;
	}

	@Override
	public User findById(String uid) {
		return userRepo.findUserById(uid);
	}

	@Override
	public LoginResponseDTO getUserDetails(String username) {

		User user = userRepo.findUserByEmail(username);
		LoginResponseDTO loginResponseDTO = null;
		if (user != null) {
			loginResponseDTO = new LoginResponseDTO();
			loginResponseDTO.setEmail(user.getEmail());
			loginResponseDTO.setPartnerId(user.getPartnerId());
			loginResponseDTO.setRole(user.getRoles());
			loginResponseDTO.setUserId(user.get_id());
			return loginResponseDTO;
		} else {
			return loginResponseDTO;
		}

	}

	@Override
	public List<UserResponseDTO> getUserByOrganization(String orgId) {

		List<User> userList = userRepo.getUsersByOrganisation(orgId);
		List<UserResponseDTO> userResponseList = new ArrayList<>();
		for (User user : userList) {
			UserResponseDTO userDto = new UserResponseDTO();
			userDto.setEmail(user.getEmail());
			userDto.setUserType(user.getRoles().getRoleType());
			userDto.setFullName(user.getFullName());
			userResponseList.add(userDto);
		}
		return userResponseList;

	}

	@Override
	public User createUserForBuyTickets(CreateUserBuyTickets newUser) {

		User user = userRepo.findUserByEmail(newUser.getEmail());
		List<String> registerEventsIDs = new ArrayList<>();
		Roles roles = null;

		if (user != null) {
			registerEventsIDs = user.getRegisterEventsIDs();
			registerEventsIDs.add(newUser.getEventId());
			user.setRegisterEventsIDs(registerEventsIDs);
			return user;

		} else {
			user = new User();
			user.setEmail(newUser.getEmail());
			user.setFullName(newUser.getFullName());
			user.setRegistered(false);
			registerEventsIDs.add(newUser.getEventId());
			user.setRegisterEventsIDs(registerEventsIDs);
			user.setPassword("");
			roles = new Roles();
			roles.setRoleType(RolesConstants.END_USER.getRoleValue());
			user.setRoles(roles);
			userRepo.save(user);
			return user;
		}

	}

	@Override
	public User findByEmail(String email) {
		return userRepo.findUserByEmail(email);
	}

}
