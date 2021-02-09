package com.agsft.ticketleap.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @author shekhar
 *
 */
@Document(collection = "userToken")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class UserToken {

	@Id
	String _id;
	
	String userId;

	String token;

	boolean isExpired;

}
