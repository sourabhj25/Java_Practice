package com.agsft.microservice.utility;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.agsft.microservice.request.model.MicroServiceRequestModel;
import com.agsft.microservice.response.model.ResponseEntityDTO;

@Component
public class RestTemplateUtility 
{

	@Value("${microservice.accessKey}")
	String accessKey;
	
	public ResponseEntityDTO microServiceCall(String serviceEndPoint,MicroServiceRequestModel requestModel)
	{
		RestTemplate restTemplate=new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("AccessKey", accessKey);
        HttpEntity<MicroServiceRequestModel> request = new HttpEntity<MicroServiceRequestModel>(requestModel, headers);
		ResponseEntityDTO responseDto=restTemplate.postForObject(serviceEndPoint, request,
				ResponseEntityDTO.class);
		return responseDto;
	}
}
