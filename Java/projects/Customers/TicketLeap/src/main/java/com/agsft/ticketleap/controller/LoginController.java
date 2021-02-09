package com.agsft.ticketleap.controller;

import java.util.logging.Level;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agsft.ticketleap.constants.HttpStatusCodes;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.security.JwtAuthRequest;
import com.agsft.ticketleap.security.JwtTokenUtil;
import com.agsft.ticketleap.security.JwtUserDetailsServiceImpl;
import com.agsft.ticketleap.services.LoginService;
import com.agsft.ticketleap.services.UserService;
import com.agsft.ticketleap.util.TicketLeapUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.java.Log;

/**
 * @author Vishal
 *
 */
@RestController
@Api(value = "Login Controller")
@CrossOrigin
@Log
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	TicketLeapUtils ticketLeapUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	/**
	 * Login Controller
	 * 
	 * @param loginDTO
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "login", notes = "Log in with proper credentials")
	public ResponseEntity<?> secureLogin(
			@ApiParam("email and password of the user") @Valid @RequestBody JwtAuthRequest authenticationRequest,
			BindingResult result) {

		if (result.hasErrors()) {
			log.log(Level.SEVERE, "Create Authentication Token: Validation Errors");
			return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
					result.getAllErrors().get(0).getDefaultMessage(), null));
		} else if (!ticketLeapUtils.isValidEmailAddress(authenticationRequest.getEmail())) {
			log.log(Level.SEVERE, "Create Authentication Token: Invalid EmailAddress");
			return ResponseEntity.ok(
					ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR, "invalid email", null));
		} else {

			try {
				// Perform the security
				final Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail().toLowerCase(),
								authenticationRequest.getPassword()));
				SecurityContextHolder.getContext().setAuthentication(authentication);

				final User user = userDetailsService.getUserByEmail(authenticationRequest.getEmail().toLowerCase());

				final String token = jwtTokenUtil.generateToken(user);
				// Return the token
				return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK,
						"Succefully logged in", ticketLeapUtils.successfullyLoggedIn(user, token)));
			} catch (BadCredentialsException e) {
				System.out.println("Bad credentials");
				return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
						"Invalid Password", null));
			} catch (Exception e) {
				System.out.println("Bad credentials");
				return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
						"Login failed", null));
			}
		}
	}

}
