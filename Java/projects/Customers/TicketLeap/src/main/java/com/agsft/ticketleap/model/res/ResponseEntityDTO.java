package com.agsft.ticketleap.model.res;

import com.agsft.ticketleap.constants.HttpStatusCodes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Custom response class
 * 
 * @author Vishal
 *
 */
@ApiModel(description = "Response Body.")
public class ResponseEntityDTO {

	@ApiModelProperty(name = "response_code")
	int response_code;
	@ApiModelProperty(name = "response_message")
	String response_message;
	@ApiModelProperty(name = "response_body")
	Object response_body;

	public ResponseEntityDTO() {
		super();
	}

	public ResponseEntityDTO(int response_code, String response_message, Object response_body) {
		super();
		this.response_code = response_code;
		this.response_message = response_message;
		this.response_body = response_body;
	}

	private ResponseEntityDTO(Builder builder) {
		response_code = builder.response_code;
		response_message = builder.response_message;
		response_body = builder.response_body;

	}

	public int getResponse_code() {
		return response_code;
	}

	public String getResponse_message() {
		return response_message;
	}

	public Object getResponse_body() {
		return response_body;
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
			response_code = val.getValue();
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
