package com.rest.menuforyou.domain;

public enum EnumLanguage {
	EN("ENGLISH"), IT("ITALIAN");

	private String lang;

	private EnumLanguage(String lang) {
		this.lang = lang;
	}

	public String getUrl() {
		return lang;
	}

}
