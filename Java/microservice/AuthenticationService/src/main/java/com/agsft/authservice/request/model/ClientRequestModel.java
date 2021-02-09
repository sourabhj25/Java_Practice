package com.agsft.authservice.request.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ClientRequestModel {

	String firstName;
	String lastName;
	@NotNull
	String userName;
	@NotNull
	String password;
	int securityType;
	@NotNull
	String email;
	boolean isExpirationEnabled;
	String timezone;
	Long expirationTimeout;
}
