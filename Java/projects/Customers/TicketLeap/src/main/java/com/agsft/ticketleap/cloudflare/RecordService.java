package com.agsft.ticketleap.cloudflare;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;



public interface RecordService {
	
	public List<Result> basicDetailsOfRecord(String domainName, String content);

	public Result addSubdomainRecord(String domainName, String content);
	
	public void updateSubdomainRecord(String domainName,String content);
	
	public void deleteSubdomainRecord(String domainName,String content);
	
	
}
