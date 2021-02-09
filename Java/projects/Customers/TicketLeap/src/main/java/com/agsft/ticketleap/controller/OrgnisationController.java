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
import com.agsft.ticketleap.model.Organisation;
import com.agsft.ticketleap.model.Partner;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.req.OrganizationReqDTO;
import com.agsft.ticketleap.model.res.OrganisationDTO;
import com.agsft.ticketleap.model.res.UserResponseDTO;
import com.agsft.ticketleap.security.JwtTokenUtil;
import com.agsft.ticketleap.services.OrganisationService;
import com.agsft.ticketleap.services.PartnerService;
import com.agsft.ticketleap.services.UserService;
import com.agsft.ticketleap.util.TicketLeapUtils;

import io.swagger.annotations.ApiOperation;

/**
 * @author Nilesh
 *
 */
@RestController
@CrossOrigin
public class OrgnisationController {

	@Autowired
	OrganisationService organisationService;

	@Autowired
	PartnerService partnerService;

	@Autowired
	TicketLeapUtils ticketLeapUtils;

	@Value("${jwt.header}")
	private String header;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/get/orgnisation/{partnerId}", method = RequestMethod.GET)
	@ApiOperation(value = "Get organization for particaular partner id")
	public ResponseEntity<?> getOrgnisationByPartner(@PathVariable("partnerId") String partnerId,
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
			Partner partner = partnerService.getPartnerbyId(partnerId);
			if (partner != null) {
				System.out.println("partner found with id" + partnerId + " Details" + partner);

				List<OrganisationDTO> organisationList = organisationService.getOrganisationsByPartner(partner.get_id(),
						user);
				return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK,
						"Fetch orgnisation list successfully", organisationList));
			} else {
				System.out.println("no partner found");
				return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
						"No Partner Found", null));
			}

		}
	}

	@RequestMapping(value = "/add/organisation", method = RequestMethod.POST)
	@ApiOperation(value = "Add organization")
	public ResponseEntity<?> addOrgnisationByPartner(@Valid @RequestBody OrganizationReqDTO org,
			HttpServletRequest httpServletRequest) {

		String authToken = httpServletRequest.getHeader(this.header);
		String email = jwtTokenUtil.getUsernameFromToken(authToken);

		if (StringUtils.isEmpty(email)) {
			return ResponseEntity
					.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND, "User not found", null));
		} else {
			Partner partner = partnerService.getPartnerbyId(org.getPartnerId());
			if (partner != null) {
				System.out.println("partner found with id" + org.getPartnerId() + " Details" + partner);
				Organisation organisation = organisationService.getOrganisationByName(org.getOrgName());
				if (organisation == null) {
					Organisation respOrganisation = organisationService.addOrganisation(org);
					return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK,
							"Orgnisation added successfully", respOrganisation));
				} else {
					return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK,
							"Orgnisation already exits.", null));
				}
			} else {
				System.out.println("no partner found");
				return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND,
						"Partner doesn't exists.Failed to add organization under partner", null));
			}
		}

	}

	@ApiOperation(value = "Register user list")
	@RequestMapping(value = "/get/user/{orgId}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("orgId") String orgId, HttpServletRequest httpServletRequest) {

		String authToken = httpServletRequest.getHeader(this.header);
		String email = jwtTokenUtil.getUsernameFromToken(authToken);

		if (StringUtils.isEmpty(email)) {
			return ResponseEntity
					.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND, "User not found", null));
		} else {
			Organisation org = organisationService.getOrganisation(orgId);
			if (org != null) {
				List<UserResponseDTO> userList = userService.getUserByOrganization(orgId);
				return ResponseEntity
						.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK, "sucess", userList));
			} else {
				return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND,
						"Organisation not found", null));
			}
		}
	}

}
