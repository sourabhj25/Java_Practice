package com.agsft.authservice.request.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserUpdateModel {

	String firstName;
	String lastName;
	@NotNull
	String email;
}
