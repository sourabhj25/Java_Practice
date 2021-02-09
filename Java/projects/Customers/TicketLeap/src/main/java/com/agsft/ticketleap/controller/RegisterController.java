package com.agsft.ticketleap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agsft.ticketleap.constants.HttpStatusCodes;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.req.RegisterDTO;
import com.agsft.ticketleap.model.res.LoginResponseDTO;
import com.agsft.ticketleap.services.RegisterService;
import com.agsft.ticketleap.services.UserService;
import com.agsft.ticketleap.util.TicketLeapUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Registration Controller
 * 
 * @author Vishal
 *
 */
@RestController
@Api(description = "Registration Controller")
@CrossOrigin
public class RegisterController {

	@Autowired
	RegisterService regService;

	@Autowired
	UserService userService;

	@Autowired
	TicketLeapUtils ticketLeapUtils;

	/**
	 * Register Controller
	 * 
	 * @param registerDTO
	 * @return
	 */
	@ApiOperation(value = "Register user with end user permissions")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@ApiParam("registerDTO") @RequestBody RegisterDTO registerDTO) {
		String message = regService.registerUser(registerDTO);

		if (message.contains("User has been registered")) {
			LoginResponseDTO user = userService.getUserDetails(registerDTO.getEmail());
			return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK, message, user));
		} else {
			return ResponseEntity
					.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR, message, null));
		}
	}

}
