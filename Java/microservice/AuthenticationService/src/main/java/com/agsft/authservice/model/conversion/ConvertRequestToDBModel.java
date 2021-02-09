package com.agsft.authservice.model.conversion;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agsft.authservice.constant.HttpStatusCode;
import com.agsft.authservice.exception.AuthenticationServiceException;
import com.agsft.authservice.model.Client;
import com.agsft.authservice.model.SecurityType;
import com.agsft.authservice.model.User;
import com.agsft.authservice.repository.SecurityTypeRepository;
import com.agsft.authservice.request.model.ClientRequestModel;
import com.agsft.authservice.request.model.LoginRequestModel;
import com.agsft.authservice.request.model.UserRegisterRequestModel;

/**
 * This class is used to convert Request model to Database model
 * @author nilesh
 *
 */

@Component
public class ConvertRequestToDBModel {

	
	@Autowired
	SecurityTypeRepository securityTypeRepo;
	
	public Client convertClientRequestToClient(ClientRequestModel clientRequest) throws AuthenticationServiceException
	{
	   Client client=new Client();
	   ModelMapper modelMapper = new ModelMapper();
	   client=modelMapper.map(clientRequest, Client.class);
	   SecurityType secuityType=securityTypeRepo.findOne(clientRequest.getSecurityType());
	   if(secuityType==null)
	   {
		   throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(), "Security type is not found");
	   }
	   else
	   {
		   client.setSecurityType(secuityType);   
	   }
	   return client;
	}
	

	public User userRequestToUserModel(UserRegisterRequestModel request)
	{
		ModelMapper mapper=new ModelMapper();
		User user=mapper.map(request, User.class);
		return user;
	}
	
	
	
}
