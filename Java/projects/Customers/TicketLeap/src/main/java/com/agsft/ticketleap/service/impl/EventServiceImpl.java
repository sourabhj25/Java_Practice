package com.agsft.ticketleap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agsft.ticketleap.constants.RolesConstants;
import com.agsft.ticketleap.model.Event;
import com.agsft.ticketleap.model.Organisation;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.req.EventRequestDTO;
import com.agsft.ticketleap.model.res.EventDTO;
import com.agsft.ticketleap.repo.EventRepository;
import com.agsft.ticketleap.repo.OrganisationRepository;
import com.agsft.ticketleap.services.EventService;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepo;

	@Autowired
	OrganisationRepository orgRepo;

	private List<EventDTO> getEventList(List<Event> eventList) {
		List<EventDTO> responseEventList = new ArrayList<>();

		for (Event event : eventList) {
			EventDTO eventDto = new EventDTO();
			eventDto.setDescription(event.getDescription());
			eventDto.setEventID(event.get_id());
			eventDto.setEventName(event.getEventName());
			responseEventList.add(eventDto);
		}
		return responseEventList;
	}

	@Override
	public List<EventDTO> getEventByOrganisation(String organisationName, User user) {
		List<Event> eventList = new ArrayList<>();

		List<EventDTO> eventResponseList = new ArrayList<>();

		if (user.getRoles() != null) {
			System.out.println("User Role" + user.getRoles());

			if (user.getRoles().getRoleType().equals(RolesConstants.OWNER.getRoleValue())) {
				eventList = eventRepo.findEventByOrgName(organisationName);
				eventResponseList = getEventList(eventList);
			} else if (user.getRoles().getRoleType().equals(RolesConstants.ADMIN.getRoleValue())) {
				if (user.getOrganisationIds() != null && user.getOrganisationIds().size() > 0) {
					Organisation organisation = orgRepo.findOrgById(user.getOrganisationIds().get(0));
					if (organisation.getOrgName().equals(organisationName)) {
						eventList = eventRepo.findEventByOrgName(organisationName);
						eventResponseList = getEventList(eventList);
					}
				}
			} else if (user.getRoles().getRoleType().equals(RolesConstants.END_USER.getRoleValue())) {
				for (String registerEventID : user.getRegisterEventsIDs()) {
					Event event = eventRepo.findEventById(registerEventID);
					eventList.add(event);
				}
				eventResponseList = getEventList(eventList);
			}
		} else {
			Organisation organisation = orgRepo.findOrgById(user.getOrganisationIds().get(0));
			if (organisation != null) {
				eventList = eventRepo.findEventByOrgName(organisationName);
				eventResponseList = getEventList(eventList);
			}
		}
		return eventResponseList;
	}
	
	
	@Override
	public List<EventDTO> getEventByOrganisation(String organisationName) {
		List<Event> eventList = new ArrayList<>();

		List<EventDTO> eventResponseList = new ArrayList<>();

			Organisation organisation = orgRepo.findOrgByName(organisationName);
					if (organisation!=null) {
						eventList = eventRepo.findEventByOrgName(organisationName);
						eventResponseList = getEventList(eventList);
					}
		return eventResponseList;
	}

	@Override
	public Event findEventById(String eid) {
		return eventRepo.findEventById(eid);
	}

	@Override
	public Boolean unAuthorisedOrgAdmin(String org, User user) {

		if (user.getRoles() != null && user.getRoles().getRoleType().equals(RolesConstants.ADMIN.getRoleValue())) {
			if (user.getOrganisationIds() != null && user.getOrganisationIds().size() > 0) 
			{
				Organisation organisation = orgRepo.findOrgById(user.getOrganisationIds().get(0));
				if (!organisation.getOrgName().equals(org)) {

					return true;

				}
			}
		}
		return false;
	}

	@Override
	public Event getEventByName(String eventName) {
		return eventRepo.findEventByName(eventName);
	}

	@Override
	public Event addEvent(EventRequestDTO eventDto,Organisation org) {
		
		Event event=new Event();
		event.setDescription(eventDto.getEventDescripation());
		event.setEventName(eventDto.getEventName());
		event.setOrganisationId(eventDto.getOrganizationId());
		event.setOrgnisationName(org.getOrgName());
		eventRepo.save(event);
		List<Event> eventList=new ArrayList<>();
		eventList.add(event);
		org.setEvents(eventList);
		orgRepo.save(org);
		return event;
	}

	
}
