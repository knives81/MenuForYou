package com.rest.menuforyou.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonOk implements Serializable {
	private static final long serialVersionUID = 1L;

	final private String type;
	final private List<Long> ids;

	public String getType() {
		return type;
	}

	public JsonOk(String type, List<Long> ids) {
		this.type = type;
		this.ids = ids;
	}

	public JsonOk(List<Long> ids) {
		this.type = "success";
		;
		this.ids = ids;
	}

	public JsonOk() {
		this.type = "success";
		this.ids = new ArrayList<Long>();
	}

}
