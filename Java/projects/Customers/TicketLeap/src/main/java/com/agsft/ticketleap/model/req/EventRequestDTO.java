package com.agsft.ticketleap.model.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(description = "Event Object ")
public class EventRequestDTO 
{
	@ApiModelProperty(name = "Event Name")
	@NotNull
	String eventName;
	@ApiModelProperty(name = "Organization Descripation")
	@NotNull
	String eventDescripation;
	@ApiModelProperty(name = "OrganizationID")
	@NotNull
	String organizationId;
	
	
}
