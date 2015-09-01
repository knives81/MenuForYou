package com.rest.menuforyou.response;

import java.io.Serializable;

public class JsonOk implements Serializable {
	private static final long serialVersionUID = 1L;

	private String type;

	public String getType() {
		return type;
	}

	public JsonOk() {
		this.type = "success";
	}

	@Override
	public String toString() {
		return "JsonOk [type=" + type + "]";
	}

}
