package com.agsft.ticketleap.security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.agsft.ticketleap.constants.HttpStatusCodes;
import com.agsft.ticketleap.util.TicketLeapUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Vishal
 *
 */
@Component
public class JwtAuthEntry implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	TicketLeapUtils ticketLeapUtils;

	@Override
	public void commence(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2)
			throws IOException, ServletException {
		arg1.getWriter().print(new ObjectMapper().writeValueAsString(ticketLeapUtils
				.createResponseEntityDTO(HttpStatusCodes.UNAUTHORIZED, "Unauthorzied Access to API", null)));
		arg1.setContentType("application/json");
		arg1.setCharacterEncoding("UTF-8");
		arg1.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

	}

}
