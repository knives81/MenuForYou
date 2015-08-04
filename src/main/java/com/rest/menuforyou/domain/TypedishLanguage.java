package com.rest.menuforyou.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_TYPEDISHLANG")
public class TypedishLanguage extends Language<Typedish> implements
		Serializable {

	private static final long serialVersionUID = 8326573469894220946L;

	public TypedishLanguage() {
		super();
	}

	public TypedishLanguage(EnumLanguage language, String description,
			Typedish entity) {
		super(language, description, entity);

	}

	public String toString() {
		return "Typedish(id:" + super.toString() + ")";
	}
}