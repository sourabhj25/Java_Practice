package com.agsft.microservice.request.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginRequestModel {

	@NotNull
	String username;
	@NotNull
	String password;
}
