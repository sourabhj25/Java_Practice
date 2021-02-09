package com.agsft.microservice.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public enum HttpStatusCode {

	OK(200, "OK"),
	SERVICE_FAILED(202, "Service failed"),
	VALIDATION_ERROR(201, "Validation Error"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error");

	int statusCode;
	String status;
	
	public static HttpStatusCode value(int code) {
		
		return HttpStatusCode.value(code);
	}
	
}
