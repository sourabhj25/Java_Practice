package com.agsft.ticketleap.cloudflare;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class HeadersUtility {

	private MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();

	public MultiValueMap<String, String> addHeaderParameters() {

		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("X-Auth-Key", "e546b5406b6205d9b00f0ce6531ea2b0d91aa");
		headers.set("X-Auth-Email", "ds@trackwayz.com");
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		return headers;

	}
}
