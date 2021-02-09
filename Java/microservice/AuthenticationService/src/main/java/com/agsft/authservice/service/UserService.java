package com.agsft.authservice.service;

import com.agsft.authservice.exception.AuthenticationServiceException;
import com.agsft.authservice.model.Client;
import com.agsft.authservice.request.model.UserRegisterRequestModel;
import com.agsft.authservice.request.model.UserUpdateModel;
import com.agsft.authservice.response.model.UserRegisterResponseModel;

public interface UserService {

	UserRegisterResponseModel userRegister(UserRegisterRequestModel userRequest,Client client) throws AuthenticationServiceException;
	String userActivate(String userToken,int userId);
	void updateUser(UserUpdateModel userUpdateModel,  String username, Client client) throws AuthenticationServiceException;
	void deactivateUser(String username, Client client) throws AuthenticationServiceException;
}
