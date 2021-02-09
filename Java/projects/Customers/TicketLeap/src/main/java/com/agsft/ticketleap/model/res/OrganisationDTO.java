package com.agsft.ticketleap.model.res;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
@NoArgsConstructor
public class OrganisationDTO {

	String organisationId;
	String organisationName;
	String organisationUrl;
	
}
