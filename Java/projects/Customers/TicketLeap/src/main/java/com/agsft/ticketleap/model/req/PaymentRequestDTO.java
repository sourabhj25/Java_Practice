package com.agsft.ticketleap.model.req;

import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@ToString(includeFieldNames = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@ApiModel(description = " PaymentRequest DTO request object ")
public class PaymentRequestDTO {

	@Size(min = 0, max = 50)
	@ApiModelProperty(name = "email")
	Optional<String> email;

	@ApiModelProperty(name = "token_id")
	@NotNull
	String token_id;

	@ApiModelProperty(name = "amount")
	@NotNull
	int amount;

	@ApiModelProperty(name = "cc_number")
	@NotNull
	String cc_number;

}
