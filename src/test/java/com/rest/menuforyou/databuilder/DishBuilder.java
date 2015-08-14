package com.rest.menuforyou.databuilder;

import com.rest.menuforyou.domain.Dish;
import com.rest.menuforyou.domain.Ingredient;
import com.rest.menuforyou.domain.Typedish;

public class DishBuilder {
	Dish dish = new Dish();

	public static DishBuilder dish()
	{
		return new DishBuilder();
	}

	public Dish build() {
		return dish;
	}

	public DishBuilder withId(int id)
	{
		dish.setId(Long.valueOf(id));
		return this;
	}

	public DishBuilder withDesc(String desc)
	{
		dish.setDescription(desc);
		return this;
	}

	public DishBuilder withOrder(int order)
	{
		dish.setOrder(Long.valueOf(order));
		return this;
	}

	public DishBuilder withName(String name)
	{
		dish.setName(name);
		return this;
	}

	public DishBuilder withPrice(String price)
	{
		dish.setPrice(Float.parseFloat(price));
		return this;
	}

	public DishBuilder withTypedish(Typedish typedish)
	{
		dish.setTypedish(typedish);
		return this;
	}

	public DishBuilder addIngredient(Ingredient ingredient)
	{
		dish.addIngredient(ingredient);
		return this;
	}

}
