package com.agsft.ticketleap.services;

import java.util.List;

import com.agsft.ticketleap.model.Organisation;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.req.OrganizationReqDTO;
import com.agsft.ticketleap.model.res.OrganisationDTO;

public interface OrganisationService {

	
	      public List<OrganisationDTO> getOrganisationsByPartner(String partnerId,User user);
	      
	      public Organisation getOrganisation(String organisationId);
	        
	      public Organisation getOrganisationByName(String organisationName);
	      
	      public Organisation addOrganisation(OrganizationReqDTO organization);

}
