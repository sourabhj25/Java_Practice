package com.agsft.authservice.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.agsft.authservice.constant.HttpStatusCode;
import com.agsft.authservice.exception.AuthenticationServiceException;
import com.agsft.authservice.model.Client;
import com.agsft.authservice.model.conversion.ConvertDBModelToResponse;
import com.agsft.authservice.repository.ClientRepository;
import com.agsft.authservice.request.model.ServiceAccessRequestModel;
import com.agsft.authservice.response.model.ClientResponseModel;
import com.agsft.authservice.service.ClientService;
import com.agsft.authservice.utility.AuthenticationServiceUtility;
import com.agsft.authservice.utility.HashCodeEncryptionUtility;

import lombok.extern.java.Log;


@Service
@Log
public class ClientServiceImpl implements ClientService 
{


	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	HashCodeEncryptionUtility hashCodeEncryptionUtility;
	
	@Autowired
	AuthenticationServiceUtility authenticationServiceUtility;
	
	@Autowired
	ConvertDBModelToResponse convertDBModelToResponse;
	
	/**
	 * <ul>
	 * <li>This method used to register client to consume microservice api </li>
	 * <li>Encrypt input client password</li>
	 * <li>Set random unique string as access key</li>
	 * <li>Set random unique string secret key</li>
	 * <li>Convert client model to response model <li>
	 * </ul>
	 */
	@Override
	public ClientResponseModel registerClient(Client client) throws AuthenticationServiceException 
	{
			try
			{
			client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
			client.setAccessKey(authenticationServiceUtility.getRandomUUID());
			client.setSecretKey(authenticationServiceUtility.generateRandomSecreatKey());
			Client saveClient=clientRepository.save(client);
			return convertDBModelToResponse.convertClientToResponseModel(saveClient);
			}
			catch(Exception e)
			{
				log.info("Register client request failed"+e.getMessage());
				throw new AuthenticationServiceException(201,authenticationServiceUtility.getMessage("client.registrion.failed"));
			}
			
	}
	
	
	@Override
	public void getclientByEmail(String email) 
	{
		//TODO BY Nilesh
	}
	
   /**
    * This method used to verify client provided access key and secret key
    * <li>Access request should have send access key in headed with keyword: accessKey </li>
    * <li>Verify client exist with provided accessKey</li>
    * <li>Generate request data hashcode with client secret key </li>
    * <li>Compare generated hashcode with input request hashcode </li>
    */
	@Override
	public Client validateServiceAccess(ServiceAccessRequestModel serviceAccessRequestModel,HttpServletRequest httpServletRequest) throws AuthenticationServiceException 
	{ 
		    //Verify input request contain accessKey value
		    String accessKey=httpServletRequest.getHeader("accessKey");
			if(accessKey==null)
			{
				throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(),authenticationServiceUtility.getMessage("client.accesskey.invalid"));
			}
			//Verify client exist with provided accessKey
			Client client=clientRepository.findClientByAccessKey(accessKey);
			if(client==null)
			{
				throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(),authenticationServiceUtility.getMessage("client.accesskey.invalid"));
			}		    
		    String requestObjectHashcode=null;
			try 
			{
		    log.info("Secret key:"+client.getSecretKey());
		    byte[] hashedSecretKey = hashCodeEncryptionUtility.createMD5(client.getSecretKey());
			//Generate hashcode Data using symmetric key created from hash of secret key
		    requestObjectHashcode=hashCodeEncryptionUtility.generateHashCode(serviceAccessRequestModel.getRequestObject(),hashedSecretKey);
			log.info("hashcode data:"+requestObjectHashcode);
			}
			catch(Exception e) 
			{
				log.warning("Exception occuer while genrating hashcode of login request"+e.getMessage());
				throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(), "Failed to verify access key details");	
			}
			if(!requestObjectHashcode.equals(serviceAccessRequestModel.getHashCode()))
			{
				log.info("Requested hashcode not matched generated hashcode with secret key");
				throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(), authenticationServiceUtility.getMessage("client.secretkey.invalid"));	
			}
		   return client;
			
	}

	@Override
	public Client getClientById(int clientId) {
		return clientRepository.findOne(clientId);
	}

}
