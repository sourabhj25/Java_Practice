package com.agsft.authservice.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MicroService 
{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long microserviceId;
	
	@Column(name="microservice_name")
	String microserviceName;
	
	
}
