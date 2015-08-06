package com.rest.menuforyou.response;

import java.io.Serializable;

public class JsonOk implements Serializable {
	private static final long serialVersionUID = 1L;

	private String type;
	private String id;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public JsonOk(String type, String id) {
		this.type = type;
		this.id = id;
	}
	public JsonOk(String id) {
		this.type =  "success";;
		this.id = id;
	}

	public JsonOk() {
		this.type = "success";
		this.id = "0";
	}

}
