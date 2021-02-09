/**
 * 
 */
package com.agsft.authservice.service.impl;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.agsft.authservice.constant.HttpStatusCode;
import com.agsft.authservice.constant.TokenType;
import com.agsft.authservice.exception.AuthenticationServiceException;
import com.agsft.authservice.model.Client;
import com.agsft.authservice.model.User;
import com.agsft.authservice.model.UserToken;
import com.agsft.authservice.model.UserTokenType;
import com.agsft.authservice.model.conversion.ConvertDBModelToResponse;
import com.agsft.authservice.model.conversion.ConvertRequestToDBModel;
import com.agsft.authservice.repository.UserRepository;
import com.agsft.authservice.repository.UserTokenRepository;
import com.agsft.authservice.repository.UserTokenTypeRepository;
import com.agsft.authservice.request.model.UserRegisterRequestModel;
import com.agsft.authservice.request.model.UserUpdateModel;
import com.agsft.authservice.response.model.ActivationTemplateModel;
import com.agsft.authservice.response.model.UserRegisterResponseModel;
import com.agsft.authservice.service.UserService;
import com.agsft.authservice.utility.AuthenticationServiceUtility;
import com.agsft.authservice.utility.EmailUtility;
import com.agsft.authservice.utility.VelocityUtility;

import lombok.extern.java.Log;

/**
 * @author nilesh
 *
 */
@Service
@Log
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	ConvertRequestToDBModel convertRequestToDbModel;
	
	@Autowired
	ConvertDBModelToResponse convertDbModelToResponse;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bcrayptPasswordEncoder;
	
	@Autowired
	AuthenticationServiceUtility authServiceUtility;
	
	@Autowired
	EmailUtility emailUtility;
	
	@Autowired
	VelocityUtility velocityUtility;
	
	@Autowired
	UserTokenTypeRepository userTokenTypeRepo;
	
	@Autowired
	UserTokenRepository userTokenRepository;
	
	@Value("${hostUrl.endpoint}")
	String hostUrl;
	
	/**
	 * This method used to register end user as per client
	 * <li> Verify user exist with provided username and client to avoid duplicate user per client</li>
	 * <li> Set user client as provided client <li>
	 * <li> Convert user details to response object <li>
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class,
			Exception.class }, readOnly = false)
	public UserRegisterResponseModel userRegister(UserRegisterRequestModel userRequest,Client client) throws AuthenticationServiceException {
		log.info("Inside registration service.Finding user with username"+userRequest.getUsername());
		
		User isUserExists=userRepository.findByUsernameAndClient(userRequest.getUsername(),client.getClientId());
		if(isUserExists!=null)
		throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(),authServiceUtility.getMessage("user.exists")); 
		
		User user=convertRequestToDbModel.userRequestToUserModel(userRequest);
		user.setPassword(bcrayptPasswordEncoder.encode(user.getPassword()));
		user.setClient(client);
		User saveUser=userRepository.save(user);
		UserToken usetToken=getUserToken(TokenType.ACTIVAITON_TOKEN,saveUser);
		userTokenRepository.save(usetToken);
		if(saveUser!=null)
		{   
			ActivationTemplateModel activationTemplate=convertDbModelToResponse.convertUserToActivationModel(user);
			activationTemplate.setActivationLink(hostUrl+"/activate/"+usetToken.getToken()+"/"+saveUser.getUserId());
			String emailContent=velocityUtility.getTemplatetoText("templates/activationTemplate.vm", activationTemplate);
		    emailUtility.sendMail(user.getEmail(), emailContent,"User Activation Request");
			return convertDbModelToResponse.convertUserToRegistrationModel(user);
		}
		throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(),authServiceUtility.getMessage("user.register.failed")); 
	}
	
	private UserToken getUserToken(Integer tokenTypeID,User user)
	{
		UserToken userToken=new UserToken();
		userToken.setUser(user);
		userToken.setToken(authServiceUtility.getRandomUUID());
		UserTokenType tokenType=userTokenTypeRepo.findOne(tokenTypeID);
		userToken.setTokenType(tokenType);
		return userToken;
	}

	@Override
	public String userActivate(String token, int userId) {
		
		UserToken userToken=userTokenRepository.findUserToken(token,userId);
		Map<String, String> map=new HashMap<String, String>();
	
		if(userToken!=null)
		{
			User user=userToken.getUser();
			user.setActive(true);
			userRepository.save(user);
			map.put("responseMessage", authServiceUtility.getMessage("user.activate.success"));
		}
		else
		{
			map.put("responseMessage", authServiceUtility.getMessage("user.activation.failed"));
		}
		String emailContent=velocityUtility.getTemplatetoText("templates/activationResponse.vm", map);
		return emailContent;
	}
	
	@Override
	public void updateUser(UserUpdateModel userUpdateModel, String username, Client client)
			throws AuthenticationServiceException {
		
		if(Objects.isNull(client)){
			throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(),authServiceUtility.getMessage("client.doesn't.exists"));
		}
		
		User user = userRepository.findByUsernameAndClient(username,client.getClientId());
		
		if(Objects.isNull(user)){
			throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(),authServiceUtility.getMessage("user.doesn't.exists"));
		}else{
			
			if(Objects.nonNull(userUpdateModel.getFirstName())){
				user.setFirstName(userUpdateModel.getFirstName());
			}
			
			if(Objects.nonNull(userUpdateModel.getLastName())){
				user.setLastName(userUpdateModel.getLastName());
			}
			
			if(Objects.nonNull(userUpdateModel.getEmail())){
				user.setEmail(userUpdateModel.getEmail());
			}
			
			//save the updated values of user
			userRepository.save(user);
		}
		
	}

	@Override
	public void deactivateUser(String username, Client client) throws AuthenticationServiceException {
		if(Objects.isNull(client)){
			throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(),authServiceUtility.getMessage("client.doesn't.exists"));
		}
		
		User user = userRepository.findByUsernameAndClient(username,client.getClientId());
		
		if(Objects.isNull(user)){
			throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(),authServiceUtility.getMessage("user.doesn't.exists"));
		}else{
			user.setActive(false);
			userRepository.save(user);
			
		}
	}

}
