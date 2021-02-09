package com.agsft.ticketleap.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * Event Data Model
 * 
 * @author Vishal
 *
 */
@Data
@ToString(includeFieldNames = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@Document(collection = "event")
public class Event {

	@Id
	String _id;

	String eventName;

	String description;

	String eventUrl;
	
	String organisationId;
	
	String orgnisationName;
	

}
