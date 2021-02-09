package com.agsft.ticketleap.controller;

import java.util.List;

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
import com.agsft.ticketleap.model.Event;
import com.agsft.ticketleap.model.Organisation;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.req.EventRequestDTO;
import com.agsft.ticketleap.model.res.EventDTO;
import com.agsft.ticketleap.security.JwtTokenUtil;
import com.agsft.ticketleap.services.EventService;
import com.agsft.ticketleap.services.OrganisationService;
import com.agsft.ticketleap.services.UserService;
import com.agsft.ticketleap.util.TicketLeapUtils;

import io.swagger.annotations.ApiOperation;

/**
 * @author Nilesh
 *
 */
@RestController
@CrossOrigin
public class EventController {

	@Autowired
	OrganisationService organisationService;

	@Autowired
	EventService eventService;

	@Autowired
	TicketLeapUtils ticketLeapUtils;

	@Value("${jwt.header}")
	private String header;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/get/event/by/{organizationName}", method = RequestMethod.POST)
	@ApiOperation(value = "Get event for particular organization")
	public ResponseEntity<?> getEventByPartner(@PathVariable("organizationName") String organizationName,
			HttpServletRequest httpServletRequest) {
		/**
		 * Login user as admin and set details expected from login
		 */

		String authToken = httpServletRequest.getHeader(this.header);
		String email = jwtTokenUtil.getUsernameFromToken(authToken);

		if (StringUtils.isEmpty(email)) {
			return ResponseEntity
					.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND, "User not found", null));
		} else {
			User user = userService.findByEmail(email);

			Organisation organisation = organisationService.getOrganisationByName(organizationName);
			if (organisation != null) {
				if (eventService.unAuthorisedOrgAdmin(organizationName, user)) {
					return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
							"You don't have permission to access this organization", null));
				} else {
					List<EventDTO> getEventList = eventService.getEventByOrganisation(organisation.getOrgName(), user);
					return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK,
							"Event list successfully", getEventList));
				}
			} else {
				System.out.println("no partner found");
				return ResponseEntity
						.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND, "No Event Found", null));
			}
		}

	}

	@RequestMapping(value = "/get/enduserevent/{organizationName}", method = RequestMethod.GET)
	@ApiOperation(value = "Get event for particular organization")
	public ResponseEntity<?> getEventByOrg(@PathVariable("organizationName") String organizationName) {
		/**
		 * Login user as admin and set details expected from login
		 */

		Organisation organisation = organisationService.getOrganisationByName(organizationName);
		if (organisation != null) {

			List<EventDTO> getEventList = eventService.getEventByOrganisation(organisation.getOrgName());
			return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK,
					"Fetch Event list successfully", getEventList));
		} else {
			System.out.println("no partner found");
			return ResponseEntity
					.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND, "No Event Found", null));
		}

	}

	@RequestMapping(value = "/get/registerUser/event", method = RequestMethod.GET)
	public ResponseEntity<?> getEndUserRegisterEvent(HttpServletRequest httpServletRequest) {
		/**
		 * Login user as admin and set details expected from login
		 */

		String authToken = httpServletRequest.getHeader(this.header);
		String email = jwtTokenUtil.getUsernameFromToken(authToken);

		if (StringUtils.isEmpty(email)) {
			return ResponseEntity
					.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND, "User not found", null));
		} else {
			User user = userService.findByEmail(email);
			Event event = eventService.findEventById(user.getRegisterEventsIDs().get(0));
			if (event != null) {
				return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK,
						"Fetch Event list successfully", event));
			} else {
				return ResponseEntity
						.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND, "No Event Found", null));
			}

		}

	}

	@RequestMapping(value = "/add/event", method = RequestMethod.POST)
	@ApiOperation(value = "Add event in particular organization")
	public ResponseEntity<?> addEventByOrg(@Valid @RequestBody EventRequestDTO eventDto) {

		Organisation organization = organisationService.getOrganisation(eventDto.getOrganizationId());
		if (organization != null) {
			System.out.println("Organization found with id" + eventDto.getOrganizationId() + " Details" + organization);
			Event event = eventService.getEventByName(eventDto.getEventName());

			if (event == null) {
				Event eventResp = eventService.addEvent(eventDto, organization);
				return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK,
						"Event added successfully", eventResp));
			} else {
				return ResponseEntity
						.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK, "Event already exits.", null));
			}
		} else {
			System.out.println("no partner found");
			return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
					"Organization doesn't exists.Failed to add event under organization", null));
		}

	}

}
