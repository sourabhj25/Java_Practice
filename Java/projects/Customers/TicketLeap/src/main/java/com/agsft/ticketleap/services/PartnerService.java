package com.agsft.ticketleap.services;

import java.util.List;

import com.agsft.ticketleap.model.Partner;

public interface PartnerService {

	public List<Partner> getAllPartnerList();

	public Partner getPartnerbyId(String partnerId);

	public Partner getpartnerByName(String partnerName);

}
