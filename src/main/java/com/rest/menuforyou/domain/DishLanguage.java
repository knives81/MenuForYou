package com.rest.menuforyou.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_DISHLANG")
public class DishLanguage extends Language<Dish> implements Serializable {

	private static final long serialVersionUID = 8326573469891220946L;

	public DishLanguage() {
		super();
	}

	public DishLanguage(EnumLanguage language, String description, Dish entity) {
		super(language, description, entity);
	}

	public String toString() {
		return "Dish(id:" + super.toString() + ")";
	}
}
