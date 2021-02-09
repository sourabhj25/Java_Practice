/**
 * 
 */
package com.agsft.authservice.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import lombok.extern.java.Log;


/**
 * This class used to Generate hashcode of provided request object based on secret key client
 * @author nilesh
 *
 */
@Component
@Log
public class HashCodeEncryptionUtility 
{

	//Generate Byte code of secretKey
	public byte[] createMD5(String secretKey)
			   throws NoSuchAlgorithmException {
			  //SHA-256
			  MessageDigest md = MessageDigest.getInstance("MD5");
			  md.update(secretKey.getBytes());
			  byte byteData[] = md.digest();
			  return byteData;
			 }
	  
	/**
	 * Generate hashcode of requested object
	 * @param data
	 * @param hashedSecretKey
	 * @return
	 * @throws Exception
	 */
	public String generateHashCode(Object data, byte[] hashedSecretKey) throws Exception{
	   //Generate Key using hash of secretKey
	   SecretKey secretKey = new SecretKeySpec(hashedSecretKey, "AES");
	   Cipher cipher = Cipher.getInstance("AES");
	   cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	   Gson gson = new Gson();
	   String jsonData=gson.toJson(data).toString();
	   log.info("Request data"+jsonData);
	   byte[] cipherData = cipher.doFinal(jsonData.getBytes());
	   
	   String hashCode = Base64.encodeBase64String(cipherData);
	   
	   return hashCode;
	  }

  
}


