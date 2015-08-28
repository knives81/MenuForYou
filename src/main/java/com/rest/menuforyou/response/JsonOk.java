package com.rest.menuforyou.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonOk implements Serializable {
	private static final long serialVersionUID = 1L;

	private String type;
	private List<Long> ids;

	public String getType() {
		return type;
	}

	public List<Long> getIds() {
		return ids;
	}

	public JsonOk(String type, List<Long> ids) {
		this.type = type;
		this.ids = ids;
	}

	public JsonOk(List<Long> ids) {
		this.type = "success";
		this.ids = ids;
	}

	public JsonOk() {
		this.type = "success";
		this.ids = new ArrayList<Long>();
	}

	@Override
	public String toString() {
		return "JsonOk [type=" + type + ", ids=" + ids + "]";
	}

}
