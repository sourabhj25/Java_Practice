package com.agsft.ticketleap.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public enum HttpStatusCodes {

	OK(200, "OK"),

	VALIDATION_ERROR(201, "Validation Error"),
	
	UNAUTHORIZED(401, "Unauthorized"),

	NOT_FOUND(404, "Request object not found"),
	
	PAYMENT_FAILED(415, "Payment has failed");
	
	int value;
	String reasonPhrase;
	
}
