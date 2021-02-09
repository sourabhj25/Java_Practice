package com.agsft.ticketleap.controller;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agsft.ticketleap.constants.HttpStatusCodes;
import com.agsft.ticketleap.constants.RolesConstants;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.req.CreateUserBuyTickets;
import com.agsft.ticketleap.model.req.RegisterDTO;
import com.agsft.ticketleap.model.req.UserDTO;
import com.agsft.ticketleap.security.JwtTokenUtil;
import com.agsft.ticketleap.services.UserService;
import com.agsft.ticketleap.util.TicketLeapUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * User functionality
 * 
 * @author shekhar
 *
 */
@RestController
@CrossOrigin
public class UserController {

	@Value("${jwt.header}")
	private String header;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@Autowired
	TicketLeapUtils ticketLeapUtils;

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ApiOperation(value = "Register User", notes = "Takes the mandatory parameters like username, email and full name, Also ask for organization Id and role of user")
	public ResponseEntity<?> addUser(
			@ApiParam("username, fullname, emailId, password") @Valid @RequestBody UserDTO user,
			HttpServletRequest httpServletRequest) throws AuthenticationException {
		String authToken = httpServletRequest.getHeader(this.header);
		String email = jwtTokenUtil.getUsernameFromToken(authToken);

		if (StringUtils.isEmpty(email)) {
			return ResponseEntity
					.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND, "User not found", null));
		} else {
			User loggedInUser = userService.findByEmail(email);

			if (RolesConstants.OWNER.getRoleValue().compareTo(loggedInUser.getRoles().getRoleType()) == 0) {
				String response = userService.createUser(user);
				return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK, response, null));
			} else {
				return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
						"Sorry! you are not permitted to Add user", null));
			}
		}

	}

	@ApiOperation(value = "Register user with Admin, sales permissions")
	@RequestMapping(value = "/registerAdminMembers/{token}", method = RequestMethod.POST)
	public ResponseEntity<?> registerStaff(
			@ApiParam("token which comes with email link") @PathVariable("token") String token,
			@ApiParam("registerDTO") @RequestBody RegisterDTO registerDTO, HttpServletRequest httpServletRequest) {

		String authToken = httpServletRequest.getHeader(this.header);
		String email = jwtTokenUtil.getUsernameFromToken(authToken);

		if (StringUtils.isEmpty(email)) {
			return ResponseEntity
					.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND, "User not found", null));
		} else {
			User user = userService.updateUser(token, registerDTO);
			String responseMessage = "";
			if (user != null) {
				responseMessage = "User has been registered successfully";
				return ResponseEntity
						.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK, responseMessage, user));
			} else {
				responseMessage = "User does not exists or token expired";
				return ResponseEntity
						.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK, responseMessage, user));
			}
		}
	}

	@RequestMapping(value = "/addBuyTicketUser", method = RequestMethod.POST)
	@ApiOperation(value = "Create User")
	public ResponseEntity<?> addUserBuyTickets(
			@ApiParam("fullname,emailId, eventId") @Valid @RequestBody CreateUserBuyTickets user,
			HttpServletRequest httpServletRequest) throws AuthenticationException {

		String authToken = httpServletRequest.getHeader(this.header);
		String email = jwtTokenUtil.getUsernameFromToken(authToken);

		if (StringUtils.isEmpty(email)) {
			return ResponseEntity
					.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND, "User not found", null));
		} else {
			User userResponse = userService.createUserForBuyTickets(user);
			if (userResponse != null)
				return ResponseEntity
						.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK, "User created", userResponse));
			else
				return ResponseEntity
						.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK, "Wrong data", userResponse));
		}
	}
}
