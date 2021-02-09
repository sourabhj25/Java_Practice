package com.agsft.ticketleap.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * User Data Model
 * 
 * @author Vishal
 *
 */
@Data
@ToString(includeFieldNames = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@Document(collection = "user")
public class User {

	@Id
	String _id;

	String fullName;

	String email;

	String password;

	Roles roles;

	String partnerId;

	List<String> organisationIds;
	
	List<String> registerEventsIDs;

	boolean isRegistered;
	
	String cc_number;
	
}
