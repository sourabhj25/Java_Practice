package com.agsft.authservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agsft.authservice.constant.HttpStatusCode;
import com.agsft.authservice.exception.AuthenticationServiceException;
import com.agsft.authservice.model.Client;
import com.agsft.authservice.model.conversion.ConvertRequestObjToRequest;
import com.agsft.authservice.model.conversion.ConvertRequestToDBModel;
import com.agsft.authservice.repository.ClientRepository;
import com.agsft.authservice.request.model.ClientRequestModel;
import com.agsft.authservice.request.model.LoginRequestModel;
import com.agsft.authservice.request.model.ServiceAccessRequestModel;
import com.agsft.authservice.response.model.ClientResponseModel;
import com.agsft.authservice.response.model.LoginResponseModel;
import com.agsft.authservice.service.ClientService;
import com.agsft.authservice.service.LoginService;
import com.agsft.authservice.service.UserService;
import com.agsft.authservice.utility.AuthenticationServiceUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;

@RestController
@Log
@Api
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@Autowired
	AuthenticationServiceUtility authenticationServiceUtility;
	
    @Autowired
    ClientService clientService;
    
    @Autowired
    ConvertRequestObjToRequest convertRequestObjToRequest;
	
    @Autowired
    ClientRepository clientRepo;
    
    @Autowired
    UserService userService;
    
    /**
     * This microservice api used to authenticate user using provided access key and secrete along with security algorithm
     * <li>Validate client request <li>
     * <li>Convert request object to expected login request object </li>
     * <li>Authenticate user with provide user name and password <li>
     * @param accessRequest - Access request expects login details and hashcode of login details generated basis on provided secret key
     * @param httpServletRequest - Headed expects accessKey client with keyWord : accessKey
     * @param bindingResult -Verify request validation
	 * @return Login details with authentication token
	 * @throws AuthenticationServiceException - Throw custom exception if any things failed .Handle by Advisor controller
     */
    @ApiOperation(value = "User Login", notes = " Expected request object: {\"hashCode\":\"hZBUXejyugL6rvUlZI8rVVuEoIWzmhZ9Xg+3RJTjQ+uKB0qG6HLsP1ylJp9pi6SA\",\"requestObject\":{\"username\":\"test\",\"password\":\"test\"} }")
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody @Valid ServiceAccessRequestModel accessRequest,
			HttpServletRequest httpServletRequest, BindingResult bindingResult) throws AuthenticationServiceException {		
		
		if (bindingResult.hasErrors())
		{
			log.info("Request has validation error");
			return ResponseEntity.ok(authenticationServiceUtility.createResponseEntityDTO(HttpStatusCode.VALIDATION_ERROR,
					authenticationServiceUtility.errorMsg(bindingResult), null));
		}
		else 
		{	//Verify provided access key with secret key generate data
			//If access key not matched throw Authentication Exception
			Client client=clientService.validateServiceAccess(accessRequest,httpServletRequest);
			//Convert requestObject to expected login request model
			LoginRequestModel loginRequest=convertRequestObjToRequest.convertObjRequestTologinRequest(accessRequest.getRequestObject());
			//Validate converted request model
			DataBinder binder = new DataBinder(loginRequest);
			bindingResult = binder.getBindingResult();
			authenticationServiceUtility.validateEntity(loginRequest, bindingResult);
			if (bindingResult.hasErrors())
			{
				return ResponseEntity.ok(authenticationServiceUtility.createResponseEntityDTO(HttpStatusCode.VALIDATION_ERROR,
						authenticationServiceUtility.errorMsg(bindingResult), null));
			}
			//Authenticate with provide username and password and client details
			LoginResponseModel loginResponse=loginService.userSignIn(loginRequest,client);
			return ResponseEntity.ok(authenticationServiceUtility.createResponseEntityDTO(HttpStatusCode.OK,
					authenticationServiceUtility.getMessage("login.success"), loginResponse));
		}
		
	}
	
	@RequestMapping(value = "authenticate/token", method = RequestMethod.POST)
	public ResponseEntity<?> expirationToken(HttpServletRequest httpServletRequest) throws AuthenticationServiceException  {
		
			return ResponseEntity.ok(authenticationServiceUtility.createResponseEntityDTO(HttpStatusCode.OK,
				authenticationServiceUtility.getMessage("token.authentication.success"), true));
		
	}
	
	
}
