package com.agsft.ticketleap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agsft.ticketleap.model.Partner;
import com.agsft.ticketleap.repo.PartnerRepository;
import com.agsft.ticketleap.services.PartnerService;

@Service
public class PartnerServiceImpl implements PartnerService {

	@Autowired
	PartnerRepository partnerRepo;

	@Override
	public List<Partner> getAllPartnerList() {

		return partnerRepo.findAll();
	}

	@Override
	public Partner getPartnerbyId(String partnerId) {

		return partnerRepo.findOne(partnerId);
	}

	@Override
	public Partner getpartnerByName(String partnerName) {

		return partnerRepo.findByName(partnerName);
	}

}
