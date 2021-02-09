package com.agsft.ticketleap.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agsft.ticketleap.constants.HttpStatusCodes;
import com.agsft.ticketleap.model.Event;
import com.agsft.ticketleap.model.Organisation;
import com.agsft.ticketleap.model.Partner;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.repo.EventRepository;
import com.agsft.ticketleap.repo.OrganisationRepository;
import com.agsft.ticketleap.repo.PartnerRepository;
import com.agsft.ticketleap.security.JwtTokenUtil;
import com.agsft.ticketleap.services.UserService;
import com.agsft.ticketleap.util.TicketLeapUtils;

@RestController
@CrossOrigin
public class PartnerController {

	@Autowired
	OrganisationRepository orgRepo;

	@Autowired
	PartnerRepository partnerRepo;

	@Autowired
	EventRepository eventRepo;

	@Autowired
	TicketLeapUtils ticketLeapUtils;

	@Value("${jwt.header}")
	private String header;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	/**
	 * Test Purpose Adding organation and partner
	 * 
	 * @return
	 */

	@RequestMapping(value = "/addOrg", method = RequestMethod.POST)
	public ResponseEntity<?> addOrganation(HttpServletRequest httpServletRequest) {
		String authToken = httpServletRequest.getHeader(this.header);
		String email = jwtTokenUtil.getUsernameFromToken(authToken);

		if (StringUtils.isEmpty(email)) {
			return ResponseEntity
					.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND, "User not found", null));
		} else {
			Partner partner = partnerRepo.findByName("Amazon.com");
			if (partner != null) {
				System.out.println("inside partner");
				Organisation organisation = new Organisation();
				organisation.setOrgName("kids");
				organisation.setOrgUrl("https.kids.amazon.com");
				organisation.setPartnetId(partner.get_id());
				List<Event> eventList = new ArrayList<>();
				Event event1 = new Event();
				event1.setEventName("play1");
				event1.setDescription("Play1 show");
				event1.setEventName(organisation.getOrgName());
				eventRepo.save(event1);

				Event event2 = new Event();
				event2.setEventName("play2");
				event2.setDescription("Play2 show");
				event2.setEventName(organisation.getOrgName());
				eventRepo.save(event2);

				eventList.add(event1);
				eventList.add(event2);
				organisation.setEvents(eventList);
				orgRepo.save(organisation);
				Organisation saveOrg = orgRepo.save(organisation);

				String orgid = saveOrg.get_id();
				event1.setOrganisationId(orgid);
				event2.setOrganisationId(orgid);

				eventRepo.save(event1);
				eventRepo.save(event2);

				partnerRepo.save(partner);

			} else {
				System.out.println("no partner found");
				return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
						"No Partner Found", null));
			}

			return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK,
					"Added Orgnisation successfully", null));
		}
	}
}
