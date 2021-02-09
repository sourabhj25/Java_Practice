package com.agsft.microservice.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.agsft.microservice.constant.HttpStatusCode;
import com.agsft.microservice.exception.MicroServiceException;
import com.agsft.microservice.request.model.LoginRequestModel;
import com.agsft.microservice.request.model.MicroServiceRequestModel;
import com.agsft.microservice.request.model.UserRegisterRequestModel;
import com.agsft.microservice.response.model.ResponseEntityDTO;
import com.agsft.microservice.utility.MicroServiceRequestConversion;
import com.agsft.microservice.utility.RestTemplateUtility;

import lombok.extern.java.Log;

@Service
@Log
public class LoginServiceImpl implements LoginService{

	@Autowired
	MicroServiceRequestConversion requestConversion;
	
	@Value("${microservice.login.endpoint}")
	String loginServiceEndpoint;
	
	@Value("${microservice.user.registration.endpoint}")
	String userRegistrationEndPoint;
	
	@Autowired
	RestTemplateUtility restTemplateUtility;
	
	/**
	 * Used to call microservice api
	 * Convert required request data
	 * Call microservice with access key and request data and encrypted hashcode
	 */
	@Override
	public ResponseEntityDTO login(LoginRequestModel loginRequest) throws MicroServiceException {
	
		try
		{
	    //Convert request data required for microservice
		MicroServiceRequestModel microserviceRequestModel=requestConversion.serviceAccessRequest(loginRequest);
		//Call microservice
		return restTemplateUtility.microServiceCall(loginServiceEndpoint,microserviceRequestModel);
		}
		catch(Exception e)
		{
			log.info("error"+e.getMessage());
			throw new MicroServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(),"Failed to access microservice"); 
		}
	
	}

	/**
	 * Used to call microservice api for registration
	 * Convert required request data
	 * Call microservice with access key and request data and encrypted hashcode
	 */
	@Override
	public ResponseEntityDTO userRegistration(UserRegisterRequestModel registrationRequest)
			throws MicroServiceException {
		
		try
		{
		//Convert request data required for microservice
		MicroServiceRequestModel microserviceRequestModel=requestConversion.serviceAccessRequest(registrationRequest);
		return restTemplateUtility.microServiceCall(userRegistrationEndPoint,microserviceRequestModel);
		}
		catch(Exception e)
		{
			log.info("error"+e.getMessage());
			throw new MicroServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(),"Failed to access microservice"); 
		}
	
	}

}
