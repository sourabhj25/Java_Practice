package com.agsft.ticketleap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.agsft.ticketleap.constants.PermissionsConstants;
import com.agsft.ticketleap.constants.RolesConstants;
import com.agsft.ticketleap.model.Roles;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.UserPermissions;
import com.agsft.ticketleap.model.req.RegisterDTO;
import com.agsft.ticketleap.repo.UserRepository;
import com.agsft.ticketleap.services.RegisterService;

/**
 * @author Vishal
 *
 */
@Component
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	/* (non-Javadoc)
	 * @see com.agsft.ticketleap.services.RegisterService#registerUser(com.agsft.ticketleap.model.req.RegisterDTO)
	 */
	@Override
	public String registerUser(RegisterDTO registerDTO) {
		String responseMessage = "";

		User duplicateUser = userRepo.findUserByEmail(registerDTO.getEmail());
		if (duplicateUser != null) {
			responseMessage = "Sorry ! This email has already been registered";
		} else if (registerDTO.getPassword().length() < 8) {
			responseMessage = "Sorry ! Please enter password of atleast 8 characters";
		} else {
			User user = new User();
			user.setEmail(registerDTO.getEmail());
			user.setFullName(registerDTO.getFullName());
			List<String> eventIds=new ArrayList<>();
			eventIds.add(registerDTO.getEventId());
			user.setRegisterEventsIDs(eventIds);
			String password= bCryptPasswordEncoder.encode(registerDTO.getPassword());
			user.setPassword(password);

			Roles roles = new Roles();
			roles.setRoleType(RolesConstants.END_USER.getRoleValue());
			List<UserPermissions> userPermissions = new ArrayList<>();
			userPermissions.add(new UserPermissions(PermissionsConstants.VIEW_EVENT.getPermissionValue(), true));
			roles.setPermissions(userPermissions);
			
			user.setRoles(roles);

			userRepo.save(user);
			responseMessage = "User has been registered successfully";
		}

		return responseMessage;
	}

}
