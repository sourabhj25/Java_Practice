package com.agsft.ticketleap.model.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = " Register Object ")
public class RegisterDTO {

	@ApiModelProperty(name = "fullName")
	@NotNull(message = "FullName cannot be null")
	String fullName;
	
	@ApiModelProperty(name = "email")
	@NotNull(message = "Email cannot be null")
	String email;
	
	@ApiModelProperty(name = "password")
	@NotNull(message = "Password cannot be null")
	String password;
	
	@ApiModelProperty(name = "User Register Event")
	String eventId;
	
}
