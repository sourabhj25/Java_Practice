package com.agsft.ticketleap.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * Partner Data Model
 * 
 * @author Vishal
 *
 */
@Data
@ToString(includeFieldNames = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@Document(collection = "partner")
public class Partner {

	@Id
	String _id;

	String partnerName;

	List<Organisation> organisations;

}
