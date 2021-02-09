package com.agsft.authservice.response.model;

import lombok.Data;

@Data
public class ActivationTemplateModel {

	String username;
	String firstName;
	String lastName;
	String email;
	String activationLink;
	
}
