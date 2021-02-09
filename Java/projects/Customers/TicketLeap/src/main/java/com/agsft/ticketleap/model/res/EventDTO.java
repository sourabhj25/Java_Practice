package com.agsft.ticketleap.model.res;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(includeFieldNames=true)
public class EventDTO {

	String eventID;

	String eventName;

	String description;
	
}
