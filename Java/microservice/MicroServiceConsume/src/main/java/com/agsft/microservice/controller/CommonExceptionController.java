package com.agsft.microservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.agsft.microservice.constant.HttpStatusCode;
import com.agsft.microservice.exception.MicroServiceException;
import com.agsft.microservice.utility.MicroServiceUtility;

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
	private MicroServiceUtility userAuthUtils;

	@ExceptionHandler(MicroServiceException.class)
	@ResponseBody
	public ResponseEntity<?> microServiceException(MicroServiceException e) {
		// handles generic exception and returns "service failed" message
        log.info("Inside global user auth error handle :"+e.getMessage());
		return ResponseEntity.ok(userAuthUtils.createResponseEntityDTO(e.getCode(), e.getMessage(), null));

	}
	/**
	 * 
	 * @param request
	 *            Http Request
	 * @param e
	 *            The Exception
	 * @return The Http Response with failure message and code
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<?> genericException(HttpServletRequest request, Exception e) {
		// handles generic exception and returns "service failed" message
		log.info("Inside global exception error handle");
		e.printStackTrace();
		return ResponseEntity
				.ok(userAuthUtils.createResponseEntityDTO(HttpStatusCode.INTERNAL_SERVER_ERROR, e.getMessage(), null));

	}



	

}
