package com.agsft.ticketleap.model.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * Login request object
 * 
 * @author Vishal
 *
 */
@Data
@ToString(includeFieldNames = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@ApiModel(description = " Login DTO request object ")
public class LoginDTO {

	@ApiModelProperty(name = "email")
	@NotNull
	String email;

	@ApiModelProperty(name = "password")
	@NotNull
	String password;

}
