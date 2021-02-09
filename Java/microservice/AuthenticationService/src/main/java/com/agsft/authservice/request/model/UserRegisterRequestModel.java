/**
 * 
 */
package com.agsft.authservice.request.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import lombok.Data;

/**
 * @author nilesh
 *
 */	
@Data
public class UserRegisterRequestModel {

	@NotNull
	String username;
	@NotNull
	String firstName;
	String lastName;
	@NotNull
	@Email
	String email;
	@NotNull
	String password;
    
	
	
	
}
