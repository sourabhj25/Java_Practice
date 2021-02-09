package com.agsft.authservice.model.conversion;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.agsft.authservice.request.model.LoginRequestModel;
import com.agsft.authservice.request.model.UserRegisterRequestModel;

@Component
public class ConvertRequestObjToRequest {

	public LoginRequestModel convertObjRequestTologinRequest(Object objectRequest)
	{
		ModelMapper mapper=new ModelMapper();
		LoginRequestModel loginRequest=mapper.map(objectRequest, LoginRequestModel.class);
	    return loginRequest;
	}
	
	public UserRegisterRequestModel convertObjRequestToRegistrationRequest(Object objectRequest)
	{
		ModelMapper mapper=new ModelMapper();
		UserRegisterRequestModel loginRequest=mapper.map(objectRequest, UserRegisterRequestModel.class);
	    return loginRequest;
	}
}
