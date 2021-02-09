package com.phoenix.md5reader.util;

import org.springframework.stereotype.Component;

import com.phoenix.md5reader.constants.HttpStatusCodes;
import com.phoenix.md5reader.dto.ResponseEntityDTO;

/**
 * @author Vishal Arora
 *
 */
@Component
public class ApplicationUtils {

	public ResponseEntityDTO createResponseEntityDTO(HttpStatusCodes httpStatusCodes, String message, Object body) {
		return ResponseEntityDTO.response().withResponseCode(httpStatusCodes).withResponseMessage(message)
				.withResponseBody(body).build();
	}
	
}
