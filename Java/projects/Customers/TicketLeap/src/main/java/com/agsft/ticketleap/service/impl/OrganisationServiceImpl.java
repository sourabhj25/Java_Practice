package com.agsft.ticketleap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agsft.ticketleap.cloudflare.RecordService;
import com.agsft.ticketleap.cloudflare.Result;
import com.agsft.ticketleap.constants.RolesConstants;
import com.agsft.ticketleap.model.Organisation;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.req.OrganizationReqDTO;
import com.agsft.ticketleap.model.res.OrganisationDTO;
import com.agsft.ticketleap.repo.OrganisationRepository;
import com.agsft.ticketleap.repo.UserRepository;
import com.agsft.ticketleap.services.OrganisationService;

@Service
public class OrganisationServiceImpl implements OrganisationService {

	@Autowired
	OrganisationRepository orgRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	RecordService recordService;

	@Override
	public List<OrganisationDTO> getOrganisationsByPartner(String partnerId, User user) {

		List<Organisation> organisationList = new ArrayList<>();

		List<OrganisationDTO> orgResponseList = new ArrayList<>();
		System.out.println("User Role" + user.getRoles() + "Ro" + RolesConstants.OWNER);

		if (user.getRoles().getRoleType().equals(RolesConstants.OWNER.getRoleValue())) {
			organisationList = orgRepo.findOrgByParentId(partnerId);
			orgResponseList = getOrganisation(organisationList);
		} else if (user.getRoles().getRoleType().equals(RolesConstants.ADMIN.getRoleValue())) {

			if (user.getOrganisationIds() != null && user.getOrganisationIds().size() > 0) {
				System.out.println(
						"Get organization list for admin with organisation id" + user.getOrganisationIds().get(0));
				Organisation adminOrg = getOrganisation(user.getOrganisationIds().get(0));
				organisationList.add(adminOrg);
				orgResponseList = getOrganisation(organisationList);
				System.out.println("Fetch organization list successfully");
			}
		} else {
			return orgResponseList;
		}
		return orgResponseList;
	}

	private List<OrganisationDTO> getOrganisation(List<Organisation> organizationList) {
		List<OrganisationDTO> responseOrgList = new ArrayList<>();

		for (Organisation org : organizationList) {
			OrganisationDTO orgDto = new OrganisationDTO();
			orgDto.setOrganisationId(org.get_id());
			orgDto.setOrganisationName(org.getOrgName());
			orgDto.setOrganisationUrl(org.getOrgUrl());
			responseOrgList.add(orgDto);
		}
		return responseOrgList;
	}

	@Override
	public Organisation getOrganisation(String organisationId) {

		return orgRepo.findOrgById(organisationId);
	}

	@Override
	public Organisation getOrganisationByName(String organisationName) {
		return orgRepo.findOrgByName(organisationName);
	}

	@Override
	public Organisation addOrganisation(OrganizationReqDTO organization) {

		Organisation org = new Organisation();
		String recordID = "";
		User ownerUser = userRepo.getOwner(organization.getPartnerId(), RolesConstants.OWNER.getRoleValue());
		Result result = recordService.addSubdomainRecord(organization.getOrgName(), "113.193.63.99");
		recordID = result.getId();

		if (recordID != null)
			org.setRecordId(recordID);

		org.setOrgName(organization.getOrgName());
		org.setOrgUrl(organization.getOrgUrl());
		org.setPartnetId(organization.getPartnerId());
		orgRepo.save(org);
		List<String> orgIds = ownerUser.getOrganisationIds();
		orgIds.add(org.get_id());
		ownerUser.setOrganisationIds(orgIds);
		userRepo.save(ownerUser);
		return org;

	}

}
