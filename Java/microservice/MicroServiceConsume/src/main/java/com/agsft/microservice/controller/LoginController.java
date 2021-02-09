/**
 * 
 */
package com.agsft.microservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agsft.microservice.constant.HttpStatusCode;
import com.agsft.microservice.exception.MicroServiceException;
import com.agsft.microservice.request.model.LoginRequestModel;
import com.agsft.microservice.request.model.UserRegisterRequestModel;
import com.agsft.microservice.response.model.ResponseEntityDTO;
import com.agsft.microservice.service.LoginService;
import com.agsft.microservice.utility.JwtTokenUtility;
import com.agsft.microservice.utility.MicroServiceUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;

/**
 * @author nilesh
 *
 */
@RestController
@Log
@Api
public class LoginController {

	
	@Autowired
	LoginService loginService;
	
	@Autowired
	MicroServiceUtility microServiceUtility;
	
	@Autowired
	JwtTokenUtility tokenUtility;
	
	/**
	 * This api is used to consume login micro service api
	 * @param loginRequestDto
	 * @param httpServletRequest
	 * @param bindingResult
	 * @return
	 * @throws MicroServiceException
	 */
	@ApiOperation(value = "Login api ", notes = "Should send accessKey from header")
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequestModel loginRequestDto,
			HttpServletRequest httpServletRequest, BindingResult bindingResult) throws MicroServiceException   {		
		
		if (bindingResult.hasErrors()) {
			log.info("Login request input request is in valid"+bindingResult);
			return ResponseEntity.ok(microServiceUtility.createResponseEntityDTO(HttpStatusCode.VALIDATION_ERROR,
					microServiceUtility.errorMsg(bindingResult), null));
		} else 
		{	log.info("Authenticating requested user");
			ResponseEntityDTO loginResponse=loginService.login(loginRequestDto);
			return ResponseEntity.ok(microServiceUtility.createResponseEntityDTO(loginResponse.getResponse_code(),loginResponse.getResponse_message(),loginResponse.getResponse_body()));
		}
		
	}
	/**
	 * This service api is used to register user using microservice api
	 * @param registrationRequest
	 * @param httpServletRequest
	 * @param bindingResult
	 * @return
	 * @throws MicroServiceException
	 */
	@RequestMapping(value = "register/user", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegisterRequestModel registrationRequest,
			HttpServletRequest httpServletRequest, BindingResult bindingResult) throws MicroServiceException   {		
		
		log.info("Inside method register user using microservice");
		if (bindingResult.hasErrors()) {
			return ResponseEntity.ok(microServiceUtility.createResponseEntityDTO(HttpStatusCode.VALIDATION_ERROR,
					microServiceUtility.errorMsg(bindingResult), null));
		} else 
		{	
			ResponseEntityDTO registrationResponse=loginService.userRegistration(registrationRequest);
			return ResponseEntity.ok(microServiceUtility.createResponseEntityDTO(registrationResponse.getResponse_code(),registrationResponse.getResponse_message(),registrationResponse.getResponse_body()));
		}
		
	}
	
	
	@RequestMapping(value = "testExpiration", method = RequestMethod.POST)
	public ResponseEntity<?> test(
			HttpServletRequest httpServletRequest) throws MicroServiceException   {		
				
			boolean isExpired=tokenUtility.isTokenExpired(httpServletRequest.getHeader("Authorization"));
			log.info("Token expiration result:"+isExpired);
					if(isExpired)
					return ResponseEntity.ok(microServiceUtility.createResponseEntityDTO(HttpStatusCode.SERVICE_FAILED,
					"Token is expired",isExpired));
					else
						return ResponseEntity.ok(microServiceUtility.createResponseEntityDTO(HttpStatusCode.OK,
								"Token is valid.You can reused token",isExpired));	
	}
	
	
}
