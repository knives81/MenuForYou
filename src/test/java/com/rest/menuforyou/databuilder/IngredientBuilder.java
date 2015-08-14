package com.rest.menuforyou.databuilder;

import com.rest.menuforyou.domain.Ingredient;

public class IngredientBuilder {
	Ingredient ingredient = new Ingredient();

	public static IngredientBuilder ingredient()
	{
		return new IngredientBuilder();
	}

	public Ingredient build() {
		return ingredient;
	}

	public IngredientBuilder withId(int id)
	{
		ingredient.setId(Long.valueOf(id));
		return this;
	}

	public IngredientBuilder withDesc(String desc)
	{
		ingredient.setDescription(desc);
		return this;
	}

	public IngredientBuilder withOrder(int order)
	{
		ingredient.setOrder(Long.valueOf(order));
		return this;
	}

}
