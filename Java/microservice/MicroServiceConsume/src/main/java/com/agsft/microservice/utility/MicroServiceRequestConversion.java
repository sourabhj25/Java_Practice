package com.agsft.microservice.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.agsft.microservice.request.model.LoginRequestModel;
import com.agsft.microservice.request.model.MicroServiceRequestModel;
import com.agsft.microservice.request.model.UserRegisterRequestModel;
import com.google.gson.Gson;

import lombok.extern.java.Log;

@Component
@Log
public class MicroServiceRequestConversion {

	@Value("${microservice.secret.key}")
	String microserviceSecreteKey;
	
	public MicroServiceRequestModel serviceAccessRequest(LoginRequestModel loginModel) throws Exception
	{
		 MicroServiceRequestModel validateAccess=new MicroServiceRequestModel();
		 validateAccess.setRequestObject(loginModel);
		 // Hash User Password using MD5
		 byte[] hashedSecretKey = createMD5(microserviceSecreteKey);
		 String requestObjHashCode=generateHashCode(loginModel,hashedSecretKey);
		 log.info("Hashed code Data : "+ requestObjHashCode);
		 validateAccess.setHashCode(requestObjHashCode);
		 return validateAccess;
	}
	
	
	public MicroServiceRequestModel serviceAccessRequest(UserRegisterRequestModel userRegistrationModel) throws Exception
	{
		MicroServiceRequestModel validateAccess=new MicroServiceRequestModel();
		validateAccess.setRequestObject(userRegistrationModel);
		 // Hash secreteKey
		 byte[] hashedSecretKey = createMD5(microserviceSecreteKey);
		 String requestObjHashCode=generateHashCode(userRegistrationModel,hashedSecretKey);
		 log.info("hashcode Data : "+ requestObjHashCode);
		 validateAccess.setHashCode(requestObjHashCode);
		 return validateAccess;
	}
	
	private byte[] createMD5(String secretKey)
			   throws NoSuchAlgorithmException {
			  //SHA-256
			  MessageDigest md = MessageDigest.getInstance("MD5");
			  md.update(secretKey.getBytes());
			  byte byteData[] = md.digest();
			  return byteData;
			 }
	   
	public String generateHashCode(Object requestObj, byte[] hashedSecretKey) throws Exception{
		   //Generate Key using hash of password
		   SecretKey secretKey = new SecretKeySpec(hashedSecretKey, "AES");
		   Cipher cipher = Cipher.getInstance("AES");
		   cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		   Gson gson = new Gson();
		   log.info("json:"+gson.toJson(requestObj));
		   String jsonData=gson.toJson(requestObj).toString();
		   System.out.println("Request data:"+jsonData);
		   byte[] cipherData = cipher.doFinal(jsonData.toString().getBytes());	   
		   String requestObjHashCode = Base64.encodeBase64String(cipherData);
		   return requestObjHashCode;
		  }
	
	
}
