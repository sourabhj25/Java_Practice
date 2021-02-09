package com.agsft.ticketleap.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agsft.ticketleap.constants.HttpStatusCodes;
import com.agsft.ticketleap.model.Event;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.security.JwtTokenUtil;
import com.agsft.ticketleap.services.EventService;
import com.agsft.ticketleap.services.UserEventRegisteration;
import com.agsft.ticketleap.services.UserService;
import com.agsft.ticketleap.util.TicketLeapUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * User Event Registration Controller
 * 
 * @author Vishal
 *
 */
@RestController
@Api(description = "User Event Registration Controller")
public class UserEventRegistrationController {

	@Value("${jwt.header}")
	private String header;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@Autowired
	EventService eventService;

	@Autowired
	UserEventRegisteration userEventReg;

	@Autowired
	TicketLeapUtils ticketLeapUtils;

	/**
	 * registers the user to a tournament
	 * 
	 * @param userId
	 * @param eventId
	 * @return
	 */
	@RequestMapping(value = "/registerUserToEvent", method = RequestMethod.POST)
	@ApiOperation(value = "Register User To An Event")
	public ResponseEntity<?> registerUserToAnEvent(@ApiParam("userId") @RequestParam("userId") String userId,
			@ApiParam("eventId") @RequestParam("eventId") String eventId, HttpServletRequest httpServletRequest) {
		String authToken = httpServletRequest.getHeader(this.header);
		String email = jwtTokenUtil.getUsernameFromToken(authToken);

		if (StringUtils.isEmpty(email)) {
			return ResponseEntity
					.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND, "User not found", null));
		} else {
			/**
			 * 1. Check if user exists
			 */
			User user = userService.findById(userId);
			if (user != null) {
				/**
				 * 2. check if event exists
				 */
				Event event = eventService.findEventById(eventId);
				if (event != null) {
					/**
					 * 3. register the user to an event 4. Mark the event as
					 * entered by user
					 */
					userEventReg.registerUserToAnEvent(userId, eventId);

					return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK,
							"User successfully registered to a tournament", null));
				} else {
					return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND,
							"Event does not exist", null));
				}
			} else {
				return ResponseEntity
						.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK, "User does not exist", null));
			}
		}
	}

}
