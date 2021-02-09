package com.agsft.authservice.service;

import javax.servlet.http.HttpServletRequest;

import com.agsft.authservice.exception.AuthenticationServiceException;
import com.agsft.authservice.model.Client;
import com.agsft.authservice.request.model.ServiceAccessRequestModel;
import com.agsft.authservice.response.model.ClientResponseModel;

public interface ClientService {

	public ClientResponseModel registerClient(Client client) throws AuthenticationServiceException;
	public void getclientByEmail(String email);
	public Client validateServiceAccess(ServiceAccessRequestModel validateRequest,HttpServletRequest httpServletRequest) throws AuthenticationServiceException;
	public Client getClientById(int clientId);
}
