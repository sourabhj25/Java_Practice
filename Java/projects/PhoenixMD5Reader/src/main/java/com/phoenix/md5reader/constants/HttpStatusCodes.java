package com.phoenix.md5reader.constants;

public enum HttpStatusCodes {

		OK(200, "OK"),

		VALIDATION_ERROR(201, "Validation Error");

		private final int value;

		private final String reasonPhrase;

		HttpStatusCodes(int value, String reasonPhrase) {
			this.value = value;
			this.reasonPhrase = reasonPhrase;
		}

		public int value() {
			return this.value;
		}

		public String getReasonPhrase() {
			return this.reasonPhrase;
		}

		/**
		 * @param statusCode
		 * @return status
		 */
		public static HttpStatusCodes valueOf(int statusCode) {
			for (HttpStatusCodes status : values()) {
				if (status.value == statusCode) {
					return status;
				}
			}
			throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
		}

	
}
