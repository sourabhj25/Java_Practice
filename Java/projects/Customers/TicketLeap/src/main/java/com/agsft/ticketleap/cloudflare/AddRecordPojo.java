package com.agsft.ticketleap.cloudflare;

import java.util.ArrayList;

public class AddRecordPojo {

	private Result result;

	public Result getResult() {
		return this.result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	private ResultInfo result_info;

	public ResultInfo getResultInfo() {
		return this.result_info;
	}

	public void setResultInfo(ResultInfo result_info) {
		this.result_info = result_info;
	}

	private boolean success;

	public boolean getSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	private ArrayList<Object> errors;

	public ArrayList<Object> getErrors() {
		return this.errors;
	}

	public void setErrors(ArrayList<Object> errors) {
		this.errors = errors;
	}

	private ArrayList<Object> messages;

	public ArrayList<Object> getMessages() {
		return this.messages;
	}

	public void setMessages(ArrayList<Object> messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "AddRecordPojo [result=" + result + ", result_info=" + result_info + ", success=" + success + ", errors="
				+ errors + ", messages=" + messages + "]";
	}

}
