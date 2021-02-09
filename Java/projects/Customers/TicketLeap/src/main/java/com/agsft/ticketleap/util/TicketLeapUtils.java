package com.agsft.ticketleap.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import com.agsft.ticketleap.constants.HttpStatusCodes;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.res.LoginResponseDTO;
import com.agsft.ticketleap.model.res.ResponseEntityDTO;

/**
 * Application utility class
 * 
 * @author Vishal
 *
 */

@Component
public class TicketLeapUtils {

	@Autowired
	@Qualifier("jsr303Validator")
	Validator validator;

	public void validateEntity(final Object object, BindingResult bindingResult) {
		validator.validate(object, bindingResult);
	}

	public ResponseEntityDTO createResponseEntityDTO(HttpStatusCodes httpStatusCodes, String message, Object body) {
		return ResponseEntityDTO.response().withResponseCode(httpStatusCodes).withResponseMessage(message)
				.withResponseBody(body).build();
	}

	public boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public LoginResponseDTO successfullyLoggedIn(User user, String token) {
		return new LoginResponseDTO(token, user.getEmail(), user.getRoles(), user.getPartnerId(), user.get_id());
	}
}
