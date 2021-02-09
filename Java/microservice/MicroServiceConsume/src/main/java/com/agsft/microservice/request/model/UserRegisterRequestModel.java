/**
 * 
 */
package com.agsft.microservice.request.model;

import javax.validation.constraints.NotNull;

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
	String email;
	@NotNull
	String password;
    
	
	
	
}
