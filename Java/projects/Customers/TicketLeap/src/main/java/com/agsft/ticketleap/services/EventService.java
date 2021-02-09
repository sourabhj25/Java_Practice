package com.agsft.ticketleap.services;

import java.util.List;

import com.agsft.ticketleap.model.Event;
import com.agsft.ticketleap.model.Organisation;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.req.EventRequestDTO;
import com.agsft.ticketleap.model.res.EventDTO;

public interface EventService {

	 public List<EventDTO> getEventByOrganisation(String OrganisationName, User user);
	 
	 
	 public List<EventDTO> getEventByOrganisation(String OrganisationName);
	 
	
	 public Boolean unAuthorisedOrgAdmin(String org,User user);
	 
	 public Event getEventByName(String eventName);
	 
	 public Event addEvent(EventRequestDTO eventDto,Organisation org);
	 
     public Event findEventById(String eid);
}
