package com.agsft.ticketleap.model.req;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.agsft.ticketleap.model.Roles;
import com.agsft.ticketleap.model.User;

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
@ApiModel(description = "User Body.")
@Data
@ToString(includeFieldNames = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class UserDTO {

	@Valid
	@NotNull(message = "Full Name cannot be null")
	@Size(min = 0, max = 50, message = "Full Name should be between 1 to 50 characters")
	@ApiModelProperty(name = "full_name")
	String fullName;
	
	@Valid
	@NotNull(message = "Email cannot be null")
	@ApiModelProperty(name = "emailId")
	String email;
	
	@Valid
	@NotNull(message = "Password cannot be null")
	@ApiModelProperty(name = "password")
	String password;

	@Valid
	@NotNull(message = "Organization Id cannot be null")
	@ApiModelProperty(name="organization_Id")
	String organisationIds;
	
	@Valid
	@NotNull(message = "role type cannot be null")
	@ApiModelProperty(name = "role_Type")
	String roleType;
}
