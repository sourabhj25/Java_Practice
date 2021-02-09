package com.agsft.ticketleap.util;

import java.util.ArrayList;
import java.util.List;

import com.agsft.ticketleap.constants.PermissionsConstants;
import com.agsft.ticketleap.model.Roles;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.UserPermissions;
import com.agsft.ticketleap.model.req.UserDTO;

import lombok.experimental.UtilityClass;

/**
 * Conversion Utility class
 * 
 * @author shekhar
 *
 */
@UtilityClass
public class ConversionUtility {

	public User convertUserDtoToUser(UserDTO userDto) {
		User user = new User();
		List<String> organisationIds = new ArrayList<String>();
		organisationIds.add(userDto.getOrganisationIds());
		user.setEmail(userDto.getEmail());
		user.setFullName(userDto.getFullName());
		user.setPassword(userDto.getPassword());
		user.setOrganisationIds(organisationIds);
		Roles role = new Roles();
		role.setRoleType(userDto.getRoleType());
		List<UserPermissions> userPermissions = new ArrayList<>();
		userPermissions.add(new UserPermissions(PermissionsConstants.CREATE_EVENT.getPermissionValue(), true));
		userPermissions.add(new UserPermissions(PermissionsConstants.CREATE_ORGANISATION.getPermissionValue(), true));
		userPermissions.add(new UserPermissions(PermissionsConstants.VIEW_EVENT.getPermissionValue(), true));

		role.setPermissions(userPermissions);
		user.setRoles(role);

		return user;

	}
}
