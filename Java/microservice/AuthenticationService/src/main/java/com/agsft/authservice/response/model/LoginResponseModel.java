/**
 * 
 */
package com.agsft.authservice.response.model;


import lombok.Data;

/**
 * @author nilesh
 *
 */
@Data
public class LoginResponseModel {

	String username;
	String firstName;
	String lastName;
	String email;
	String token;
	
}
