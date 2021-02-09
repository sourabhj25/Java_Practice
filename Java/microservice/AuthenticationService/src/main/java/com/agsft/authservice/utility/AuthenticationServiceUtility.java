package com.agsft.authservice.utility;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import com.agsft.authservice.constant.HttpStatusCode;
import com.agsft.authservice.response.model.ResponseEntityModel;
/**
 * @author nilesh
 * 
 * 
 */

@Component
public class AuthenticationServiceUtility {

	@Autowired
	MessageSource messageSource;
	
	private static final String CHAR_LIST ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final int RANDOM_STRING_LENGTH = 10;
	
	@Autowired
	@Qualifier("jsr303Validator")
	Validator validator;
	
	public String errorMsg(BindingResult bindingResult) {
		FieldError fieldError = bindingResult.getFieldError();
		return "Mandatory Fields should not be empty. Please enter data for : "
				.concat(fieldError.getField().concat(" "));
	}
	
	/**
	 * Validate object
	 * @param object
	 * @param bindingResult
	 */
	public void validateEntity(final Object object, BindingResult bindingResult) {
		validator.validate(object, bindingResult);
	}
	
	public ResponseEntityModel createResponseEntityDTO(HttpStatusCode httpStatusCodes, String message, Object body) {
		return ResponseEntityModel.response().withResponseCode(httpStatusCodes).withResponseMessage(message)
				.withResponseBody(body).build();
	}

	public ResponseEntityModel createResponseEntityDTO(HttpStatusCode validationError, List<ObjectError> allErrors,
			Object body) {
		return ResponseEntityModel.response().withResponseCode(validationError).withResponseBody(allErrors)
				.withResponseBody(body).build();
	}

	public ResponseEntityModel createResponseEntityDTO(int code, String message, Object body) {
		return ResponseEntityModel.response().withResponseCode(code).withResponseMessage(message).withResponseBody(body)
				.build();
	}

	/**
	 * Read message value from message property file
	 * @param key
	 * @return
	 */
	public String getMessage(String key) {
		try {
			return messageSource.getMessage(key, null, Locale.US);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Read message value from message property file as argument
	 * @param key
	 * @return
	 */
	public String getMessage(String key, String[] args) {
		try {
			return messageSource.getMessage(key, args, Locale.US);
		} catch (Exception e) {
			return null;
		}
	}
	
	 public String generateRandomSecreatKey()
	   {
	        
	        StringBuffer randStr = new StringBuffer();
	        for(int i=0; i<RANDOM_STRING_LENGTH; i++){
	            int number = getRandomNumber();
	            char ch = CHAR_LIST.charAt(number);
	            randStr.append(ch);
	        }
	        return randStr.toString();
	    }
		 /**
	     * This method generates random numbers
	     * @return int
	     */
	    private int getRandomNumber() 
	    {
	        int randomInt = 0;
	        Random randomGenerator = new Random();
	        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
	        if (randomInt - 1 == -1) {
	            return randomInt;
	        } else {
	            return randomInt - 1;
	        }
	    }
	
	    public String getRandomUUID()
		{
			 UUID uuid = UUID.randomUUID();
			 return uuid.toString();
		}
}
