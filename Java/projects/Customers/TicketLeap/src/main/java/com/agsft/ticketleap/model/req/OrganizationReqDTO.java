package com.agsft.ticketleap.model.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(description = " Organization Object ")
public class OrganizationReqDTO 
{
	@ApiModelProperty(name = "OrganizationName")
	@NotNull
	String orgName;
	
	@NotNull
	@ApiModelProperty(name = "Organization Url")
	String orgUrl;
	
	@NotNull
	@ApiModelProperty(name = "partnerName")
	String partnerId;
	
}
