package com.phoenix.md5reader.dto;

import com.phoenix.md5reader.constants.HttpStatusCodes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vishal Arora
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Response Body.")
public class ResponseEntityDTO {

	@ApiModelProperty(name = "response_code")
	int response_code;
	@ApiModelProperty(name = "response_message")
	String response_message;
	@ApiModelProperty(name = "response_body")
	Object response_body;

	private ResponseEntityDTO(Builder builder) {
		response_code = builder.response_code;
		response_message = builder.response_message;
		response_body = builder.response_body;

	}

	public static Builder response() {
		return new Builder();
	}

	public static final class Builder {
		private int response_code;
		private String response_message;
		private Object response_body;

		private Builder() {
		}

		public Builder withResponseCode(HttpStatusCodes val) {
			response_code = val.value();
			return this;
		}

		public Builder withResponseMessage(String val) {
			response_message = val;
			return this;
		}

		public Builder withResponseBody(Object val) {
			response_body = val;
			return this;
		}

		public ResponseEntityDTO build() {
			return new ResponseEntityDTO(this);
		}

	}
}