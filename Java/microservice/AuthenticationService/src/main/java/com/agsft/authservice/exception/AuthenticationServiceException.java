/**
 * 
 */
package com.agsft.authservice.exception;

import lombok.Data;

/**
 * @author nilesh
 *
 */
@Data
public class AuthenticationServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6231568802011859857L;

	int code;

	String message;

	public AuthenticationServiceException(int code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public AuthenticationServiceException() {
		
	}
}