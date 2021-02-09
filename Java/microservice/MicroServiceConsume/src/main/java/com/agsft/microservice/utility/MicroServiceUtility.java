package com.agsft.microservice.utility;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.agsft.microservice.constant.HttpStatusCode;
import com.agsft.microservice.response.model.ResponseEntityDTO;

@Component
public class MicroServiceUtility {


	@Autowired
	MessageSource messageSource;
	
	public ResponseEntityDTO createResponseEntityDTO(HttpStatusCode httpStatusCodes, String message, Object body) {
		return ResponseEntityDTO.response().withResponseCode(httpStatusCodes).withResponseMessage(message)
				.withResponseBody(body).build();
	}

	public ResponseEntityDTO createResponseEntityDTO(HttpStatusCode validationError, List<ObjectError> allErrors,
			Object body) {
		return ResponseEntityDTO.response().withResponseCode(validationError).withResponseBody(allErrors)
				.withResponseBody(body).build();
	}

	public ResponseEntityDTO createResponseEntityDTO(int code, String message, Object body) {
		return ResponseEntityDTO.response().withResponseCode(code).withResponseMessage(message).withResponseBody(body)
				.build();
	}

	public ResponseEntityDTO createResponseEntityDTO(HttpStatusCode httpStatusCodes, String message, Object body,
			String name) {
		return ResponseEntityDTO.response().withResponseCode(httpStatusCodes).withResponseMessage(message)
				.withResponseBody(body).withResponseBody(name).build();
	}
	
	public String getMessage(String key) {
		try {
			return messageSource.getMessage(key, null, Locale.US);
		} catch (Exception e) {
			return null;
		}
	}

	public String getMessage(String key, String[] args) {
		try {
			return messageSource.getMessage(key, args, Locale.US);
		} catch (Exception e) {
			return null;
		}
	}
	
	public String errorMsg(BindingResult bindingResult) {
		FieldError fieldError = bindingResult.getFieldError();
		return "Mandatory Fields should not be empty. Please enter data for : "
				.concat(fieldError.getField().concat(" "));
	}
	
	
}
