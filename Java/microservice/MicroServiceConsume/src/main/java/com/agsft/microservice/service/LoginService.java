package com.agsft.microservice.service;

import com.agsft.microservice.exception.MicroServiceException;
import com.agsft.microservice.request.model.LoginRequestModel;
import com.agsft.microservice.request.model.UserRegisterRequestModel;
import com.agsft.microservice.response.model.ResponseEntityDTO;

public interface LoginService {

	public ResponseEntityDTO login(LoginRequestModel loginRequest) throws MicroServiceException;
	public ResponseEntityDTO userRegistration(UserRegisterRequestModel registrationRequest) throws MicroServiceException;
}
