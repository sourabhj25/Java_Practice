package com.agsft.authservice.request.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ServiceAccessRequestModel {

	@NotNull
	Object requestObject;
	@NotNull
	String hashCode;
	
}
