/**
 * 
 */
package com.agsft.authservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agsft.authservice.constant.HttpStatusCode;
import com.agsft.authservice.exception.AuthenticationServiceException;
import com.agsft.authservice.model.Client;
import com.agsft.authservice.model.conversion.ConvertRequestToDBModel;
import com.agsft.authservice.request.model.ClientRequestModel;
import com.agsft.authservice.response.model.ClientResponseModel;
import com.agsft.authservice.service.ClientService;
import com.agsft.authservice.utility.AuthenticationServiceUtility;
import com.agsft.authservice.utility.EmailUtility;

import io.swagger.annotations.Api;

/**
 * @author nilesh
 * This controller used to implement service api related to client
 * <li>register/client : This api is used to register to get details of access key and secrete key for accessing microservice </li>
 */
@RestController
@Api
public class ClientController 
{
	
	@Autowired
	AuthenticationServiceUtility authenticationServiceUtility;
	
	@Autowired
	ConvertRequestToDBModel convertRequestToModelUtility;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	EmailUtility emailUtility;
	
	/**
	 * This api is used to register to application to get access of microservice
	 * @param clientRequestDto - Required details like fistName,lastName,email and security type
	 * @param httpServletRequest
	 * @param bindingResult -Verify request validation
	 * @return Generated client details such as access key and secrete key
	 * @throws AuthenticationServiceException - Throw custom exception if any things failed .Handle by Advisor controller
	 */
	@RequestMapping(value = "register/client", method = RequestMethod.POST)
	public ResponseEntity<?> registerClient(@RequestBody @Valid ClientRequestModel clientRequestDto,
			HttpServletRequest httpServletRequest, BindingResult bindingResult) throws AuthenticationServiceException  {
		
		if (bindingResult.hasErrors()) 
		{
			return ResponseEntity.ok(authenticationServiceUtility.createResponseEntityDTO(HttpStatusCode.VALIDATION_ERROR,
					authenticationServiceUtility.errorMsg(bindingResult), null));
		} else 
		{	
			Client client=convertRequestToModelUtility.convertClientRequestToClient(clientRequestDto);
			ClientResponseModel clientResponse=clientService.registerClient(client);
			return ResponseEntity.ok(authenticationServiceUtility.createResponseEntityDTO(HttpStatusCode.OK,
					authenticationServiceUtility.getMessage("client.register.success"), clientResponse));
		}
	}

}
