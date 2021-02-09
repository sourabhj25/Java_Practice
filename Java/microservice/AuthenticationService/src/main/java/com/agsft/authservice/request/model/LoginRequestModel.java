package com.agsft.authservice.request.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginRequestModel {

	@NotNull
	String username;
	@NotNull
	String password;
}
