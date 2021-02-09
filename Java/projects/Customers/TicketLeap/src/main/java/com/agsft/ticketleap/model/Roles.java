package com.agsft.ticketleap.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * RolePermission Data Model
 * 
 * @author Vishal
 *
 */
@Data
@ToString(includeFieldNames = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class Roles {

	String roleType;

	List<UserPermissions> permissions;

}
