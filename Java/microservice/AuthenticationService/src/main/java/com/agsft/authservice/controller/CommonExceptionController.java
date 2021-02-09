package com.agsft.authservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.agsft.authservice.constant.HttpStatusCode;
import com.agsft.authservice.exception.AuthenticationServiceException;
import com.agsft.authservice.utility.AuthenticationServiceUtility;

import lombok.extern.java.Log;

/**
 * @author nilesh
 * 
 * The GenericException class used to handle exception occur anywhere in the application
 */
@ControllerAdvice
@Log
public class CommonExceptionController extends ResponseEntityExceptionHandler{

	@Autowired
	private AuthenticationServiceUtility authenticationServiceUtility;;

	/**
	 * This will handle custom exception send from application and send api response with error message and status code
	 * @param e
	 * @return
	 */
	@ExceptionHandler(AuthenticationServiceException.class)
	@ResponseBody
	public ResponseEntity<?> authServiceExeception(AuthenticationServiceException e) {
		// handles generic exception and returns "service failed" message
        log.info("Inside global user auth error handle :"+e.getMessage());
		return ResponseEntity.ok(authenticationServiceUtility.createResponseEntityDTO(e.getCode(), e.getMessage(), null));

	}
	/**
	 * This will handle exception throw from application and send api response with error message and status code
	 * @param request
	 *            Http Request
	 * @param 
	 * @return The Http Response with failure message and code
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<?> genericExeception(HttpServletRequest request, Exception e) {
		// handles generic exception and returns "service failed" message
		log.info("Inside global exception error handle");
		e.printStackTrace();
		return ResponseEntity
				.ok(authenticationServiceUtility.createResponseEntityDTO(HttpStatusCode.INTERNAL_SERVER_ERROR, e.getMessage(), null));

	}

	/**
	 * This will handle AccessDenied exception send from application and send api response with error message and status code
	 * @param request
	 * @param e
	 *            AccessDeniedException exception
	 * @return The Http Response with failure(access denied) message
	 */
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	public ResponseEntity<?> accessDeniedExeception(HttpServletRequest request, AccessDeniedException e) {
		// handles AccessDeniedException exception and returns "access denied " message
		return ResponseEntity
				.ok(authenticationServiceUtility.createResponseEntityDTO(HttpStatusCode.INTERNAL_SERVER_ERROR, e.getMessage(), null));
	}

	

}
