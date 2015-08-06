package com.rest.menuforyou.response;

import java.io.Serializable;

import com.rest.menuforyou.error.GenericException;

public class JsonError implements Serializable {
	private static final long serialVersionUID = 1L;

	private String status;
	private String code;
	private String message;
	private String developerMessage;
	private String moreInfo;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JsonError(GenericException e) {
		this.code = e.getErrCode();
		this.message = e.getErrMsg();
	}

	public JsonError() {
	}

}
