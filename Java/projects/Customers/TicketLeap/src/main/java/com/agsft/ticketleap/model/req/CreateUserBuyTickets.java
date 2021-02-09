package com.agsft.ticketleap.model.req;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @author shekhar
 *
 */
@Data
@ToString(includeFieldNames = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@ApiModel(description = "Create user on buy tickets")
public class CreateUserBuyTickets {

	String fullName;

	String email;

	String eventId;
}
