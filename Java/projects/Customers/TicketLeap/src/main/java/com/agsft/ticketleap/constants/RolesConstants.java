package com.agsft.ticketleap.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * Role Constants
 * 
 * @author Vishal
 *
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@AllArgsConstructor
public enum RolesConstants {

	PARTNER(0, "PARTNER"),
	OWNER(1, "OWNER"),
	ADMIN(2, "ADMIN"),
	END_USER(3, "END_USER");
	
	int id;
	String roleValue;
	
}
