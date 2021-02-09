package com.agsft.authservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.agsft.authservice.model.conversion.ConvertRequestToDBModel;
import com.agsft.authservice.model.conversion.ConvertRequestObjToRequest;
import com.agsft.authservice.request.model.ServiceAccessRequestModel;
import com.agsft.authservice.request.model.UserRegisterRequestModel;
import com.agsft.authservice.request.model.UserUpdateModel;
import com.agsft.authservice.response.model.UserRegisterResponseModel;
import com.agsft.authservice.security.JwtAuthenticationTokenFilter;
import com.agsft.authservice.security.JwtTokenUtil;
import com.agsft.authservice.service.ClientService;
import com.agsft.authservice.service.UserService;
import com.agsft.authservice.utility.AuthenticationServiceUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;

@RestController
@Log
@Api
public class UserController {

	@Autowired
	AuthenticationServiceUtility authenticationServiceUtility;

	@Autowired
	UserService userService;

	@Autowired
	ClientService clientService;

	@Autowired
	ConvertRequestObjToRequest convertRequestObjToRequest;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;
    
	@ApiOperation(value = "User Registration API", notes = " Expected request object: {\n"
			+ "\"hashCode\":\"hZBUXejyugL6rvUlZI8rVVuEoIWzmhZ9Xg+3RJTjQ+uKB0qG6HLsP1ylJp9pi6SA\",\"requestObject\":{\"username\":\"test\",\"firstName\":\"test\",\"lastName\":\"test\",\"password\":\"test#\",\"email\":\"test@gmail.com\"}\n"
			+ "} }")
	@RequestMapping(value = "user/registration", method = RequestMethod.POST)
	public ResponseEntity<?> userRegistration(@RequestBody @Valid ServiceAccessRequestModel accessRequest,
			HttpServletRequest httpServletRequest, BindingResult bindingResult) throws AuthenticationServiceException {
		log.info("Inside user registration controller");
		if (bindingResult.hasErrors()) {
			return ResponseEntity.ok(authenticationServiceUtility.createResponseEntityDTO(
					HttpStatusCode.VALIDATION_ERROR, authenticationServiceUtility.errorMsg(bindingResult), null));
		} else {
			Client client = clientService.validateServiceAccess(accessRequest, httpServletRequest);
			UserRegisterRequestModel registrationRequest = convertRequestObjToRequest
					.convertObjRequestToRegistrationRequest(accessRequest.getRequestObject());
			DataBinder binder = new DataBinder(registrationRequest);
			bindingResult = binder.getBindingResult();
			authenticationServiceUtility.validateEntity(registrationRequest, bindingResult);
			if (bindingResult.hasErrors()) {
				return ResponseEntity.ok(authenticationServiceUtility.createResponseEntityDTO(
						HttpStatusCode.VALIDATION_ERROR, authenticationServiceUtility.errorMsg(bindingResult), null));
			}
			UserRegisterResponseModel userRegistrationResponse = userService.userRegister(registrationRequest, client);
			return ResponseEntity.ok(authenticationServiceUtility.createResponseEntityDTO(HttpStatusCode.OK,
					authenticationServiceUtility.getMessage("user.register.success"), userRegistrationResponse));
		}

	}

	@RequestMapping(value = "/activate/{userToken}/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> activate(@PathVariable("userToken") String userToken, @PathVariable("userId") int userId,
			HttpServletRequest httpServletRequest) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		return new ResponseEntity<String>(userService.userActivate(userToken, userId), responseHeaders,
				HttpStatus.NOT_FOUND);

	}

	@RequestMapping(value = "/update/user", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody @Valid UserUpdateModel userUpdateModel,
			HttpServletRequest httpServletRequest, BindingResult bindingResult) throws AuthenticationServiceException {
		log.info("Inside update user service");

		if (bindingResult.hasErrors()) {
			return ResponseEntity.ok(authenticationServiceUtility.createResponseEntityDTO(
					HttpStatusCode.VALIDATION_ERROR, authenticationServiceUtility.errorMsg(bindingResult), null));
		} else {
			String authToken = httpServletRequest.getHeader(this.tokenHeader); 
			String username = jwtTokenUtil.getUsernameFromToken(authToken);
			int clientId = jwtTokenUtil.getClientId(authToken);
			System.out.println("Client Id : " +clientId);
			
			Client client = clientService.getClientById(clientId);
			
			userService.updateUser(userUpdateModel, username, client);

			return ResponseEntity.ok(authenticationServiceUtility.createResponseEntityDTO(HttpStatusCode.OK, 
											authenticationServiceUtility.getMessage("user.update.success"), null));
		}
	}
	
	@RequestMapping(value = "/deactivate/user", method = RequestMethod.POST)
	public ResponseEntity<?> deactivateUser(HttpServletRequest httpServletRequest/*, BindingResult bindingResult*/) throws AuthenticationServiceException {
		log.info("Inside deactivate user service");

			String authToken = httpServletRequest.getHeader(this.tokenHeader); 
			String username = jwtTokenUtil.getUsernameFromToken(authToken);
			int clientId = jwtTokenUtil.getClientId(authToken);
			Client client = clientService.getClientById(clientId);
			
			userService.deactivateUser(username, client);

			return ResponseEntity.ok(authenticationServiceUtility.createResponseEntityDTO(HttpStatusCode.OK, 
					authenticationServiceUtility.getMessage("user.deactivate.success"), null));
	}
	
}
