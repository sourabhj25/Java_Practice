package com.agsft.ticketleap.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @author Vishal
 *
 */
@Data
@ToString(includeFieldNames = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@Document(collection = "transaction")
public class Transaction {

	String _id;

	String email;

	String token_id;

	String taxation_id;

	String transaction_id;

	double amount;

	String currency;

	Date timestamp;

	boolean successful;
}
