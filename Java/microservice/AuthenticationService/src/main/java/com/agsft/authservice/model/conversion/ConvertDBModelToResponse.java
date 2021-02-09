package com.agsft.authservice.model.conversion;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agsft.authservice.model.Client;
import com.agsft.authservice.model.User;
import com.agsft.authservice.response.model.ActivationTemplateModel;
import com.agsft.authservice.response.model.ClientResponseModel;
import com.agsft.authservice.response.model.LoginResponseModel;
import com.agsft.authservice.response.model.UserRegisterResponseModel;
import com.agsft.authservice.security.JwtTokenUtil;

/**
 * This class used conversion of database model class to response model class
 *  
 * @author nilesh
 *
 */

@Component
public class ConvertDBModelToResponse {

	/**
	 * Convert user object login response model 
	 * set generate JWT token
	 * @param user
	 * @return
	 */
	public LoginResponseModel convertUserToLoginResponse(User user)
	{
		ModelMapper modelMapper = new ModelMapper();
		LoginResponseModel loginResponse=modelMapper.map(user, LoginResponseModel.class);
		return loginResponse;
	}	
	
	public UserRegisterResponseModel convertUserToRegistrationModel(User user)
	{
		ModelMapper modelMapper = new ModelMapper();
		UserRegisterResponseModel modelResponse=modelMapper.map(user, UserRegisterResponseModel.class);
		return modelResponse;
	}
	
	public ActivationTemplateModel convertUserToActivationModel(User user)
	{
		ModelMapper modelMapper = new ModelMapper();
		ActivationTemplateModel modelResponse=modelMapper.map(user, ActivationTemplateModel.class);
		return modelResponse;
	}
	
	public ClientResponseModel convertClientToResponseModel(Client client)
	{
		ModelMapper modelMapper = new ModelMapper();
		ClientResponseModel modelResponse=modelMapper.map(client, ClientResponseModel.class);
		return modelResponse;
	}
	
	
}
