package com.agsft.authservice.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.agsft.authservice.exception.AuthenticationServiceException;
import com.agsft.authservice.model.Client;
import com.agsft.authservice.request.model.LoginRequestModel;
import com.agsft.authservice.response.model.LoginResponseModel;


public interface LoginService {

	public UserDetails userAuthentication(String username, String password,Client client);
	
	public LoginResponseModel userSignIn(LoginRequestModel requestModel,Client client) throws AuthenticationServiceException;
	
	
}
