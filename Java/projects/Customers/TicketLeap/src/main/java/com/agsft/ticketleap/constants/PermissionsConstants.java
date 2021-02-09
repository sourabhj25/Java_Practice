package com.agsft.ticketleap.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * Permission Constants
 * 
 * @author Vishal
 *
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public enum PermissionsConstants {

	CREATE_ORGANISATION(0, "CREATE_ORGANISATION"),
	CREATE_EVENT(1, "CREATE_EVENT"),
	VIEW_EVENT(2, "VIEW_EVENT");
	
	int id;
	String permissionValue;
	
}
