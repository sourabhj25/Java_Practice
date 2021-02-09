package com.agsft.ticketleap.cloudflare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class RecorsServiceImpl implements RecordService {

	RestTemplate restTemplate = new RestTemplate();
	Scanner sc = new Scanner(System.in);
	
	@Autowired
	HeadersUtility headersUtility ;

	@Override
	public List<Result> basicDetailsOfRecord(String domainName, String content) {

		// final String url =
		// "https://api.cloudflare.com/client/v4/zones/8d927f85b574ee336b2f9f1f5971dc90/dns_records?type=A&name="+domainName+"&content="+content+"&page=1&per_page=20&order=type&direction=desc&match=all";

		final String url = "https://api.cloudflare.com/client/v4/zones/0465c65b97c419ad0f8a95376ff0e43a/dns_records";
		List<Result> result=null;
		System.out.println("Begin /GET request! for basic details");
		HttpEntity<?> entity = new HttpEntity<>(headersUtility.addHeaderParameters());

		ResponseEntity<RootObject> response = restTemplate.exchange(url, HttpMethod.GET, entity, RootObject.class);
		
		result=	response.getBody().getResult();
		System.out.println("Results are:"+result);
		if (response.getBody() != null) {
			System.out.println("Response for Get Request: " + response.getBody().toString());
		} else {
			System.out.println("Response for Get Request: NULL");
		}
		return result;

	}

	@Override
	public Result addSubdomainRecord(String domainName, String content) {

		Result result=null;
		JSONObject request = new JSONObject();
		// "52.37.194.50"
		try {

			request.put("type", "A");
			request.put("name", domainName);
			request.put("content", content);
			request.put("ttl", 1);
			//request.put("status", "active");
			request.put("proxied", true);
			request.put("proxiable",true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final String nextUrl = "https://api.cloudflare.com/client/v4/zones/0465c65b97c419ad0f8a95376ff0e43a/dns_records";
		HttpEntity<?> entityTwo = new HttpEntity<>(request.toString(), headersUtility.addHeaderParameters());

		ResponseEntity<AddRecordPojo> resultResponse = restTemplate.exchange(nextUrl, HttpMethod.POST, entityTwo,
				AddRecordPojo.class);
		System.out.println("Data:::" +resultResponse);
		result= resultResponse.getBody().getResult();
		if (resultResponse.getBody() != null) {
			System.out.println("Response for Post Request: " + resultResponse.getBody().toString());
		} else {
			System.out.println("Response for Post Request: NULL");
		}
		return result;

	}

	@Override
	public void updateSubdomainRecord(String domainName, String content) {
		/*
		 * System.out.println("Enter the domain name to update"); String
		 * updateDomainName = sc.nextLine();
		 * System.out.println("Enter the ip adress to update"); String updateContentName
		 * = sc.nextLine();
		 */
		List<Result> result = basicDetailsOfRecord(domainName, content);
		String identifiers = "";// response.getBody();
		for (Result resultObj : result) {
			
			if(resultObj.getName().contains(domainName)) {
				System.out.println(resultObj);
				identifiers=resultObj.getId();
				break;
			}else {
				System.out.println("domain name did not match");
			}
		}
		JSONObject request = new JSONObject();
		System.out.println("Enter the domain name to update");
		String updateDomainName = sc.nextLine();
		System.out.println("Enter the ip adress to update");
		String updateContentName = sc.nextLine();
		// "52.37.194.50"
		try {

			request.put("type", "A");
			request.put("name", updateDomainName);
			request.put("content","52.37.194.50");
			request.put("ttl", 1);
			request.put("proxied", false);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String nextUrl = "https://api.cloudflare.com/client/v4/zones/0465c65b97c419ad0f8a95376ff0e43a/dns_records/"+identifiers;
		HttpEntity<?> entity= new HttpEntity<>(request.toString(), headersUtility.addHeaderParameters());

		ResponseEntity<Object> resultResponse = restTemplate.exchange(nextUrl, HttpMethod.PUT, entity,
				Object.class);

		if (resultResponse.getBody() != null) {
			System.out.println("Response for Post Request: " + resultResponse.getBody().toString());
		} else {
			System.out.println("Response for Post Request: NULL");
		}
	}

	@Override
	public void deleteSubdomainRecord(String domainName, String content) {
		
		List<Result> result = basicDetailsOfRecord(domainName, content);
		String identifiers = "";// response.getBody();
		for (Result resultObj : result) {
			
			if(resultObj.getName().contains(domainName)) {
				System.out.println(resultObj);
				identifiers=resultObj.getId();
				break;
			}else {
				System.out.println("domain name did not match");
			}
		}
		
		
		Map<String, String> params = new HashMap<String, String>();
	      params.put("id",identifiers);

		String nextUrl = "https://api.cloudflare.com/client/v4/zones/8d927f85b574ee336b2f9f1f5971dc90/dns_records/{id}";
		  restTemplate.delete ( nextUrl,params);  
	//	HttpEntity<?> entity= new HttpEntity<>(headersUtility.addHeaderParameters());

	/*	ResponseEntity<RootObject> resultResponse = restTemplate.exchange(nextUrl, HttpMethod.DELETE, entity,
				RootObject.class);*/

		
	
			System.out.println("record delted");
		
	}

}
