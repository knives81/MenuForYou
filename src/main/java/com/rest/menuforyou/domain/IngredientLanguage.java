package com.rest.menuforyou.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_INGREDIENTLANG")
public class IngredientLanguage extends Language<Ingredient> implements
		Serializable {

	private static final long serialVersionUID = 8326573469891220946L;

	public IngredientLanguage() {
		super();
	}

	public IngredientLanguage(EnumLanguage language, String description,
			Ingredient entity) {
		super(language, description, entity);
	}

	public String toString() {
		return "Ingredient(id:" + super.toString() + ")";
	}

}
