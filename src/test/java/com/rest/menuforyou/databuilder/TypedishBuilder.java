package com.rest.menuforyou.databuilder;

import com.rest.menuforyou.domain.Typedish;

public class TypedishBuilder {
	Typedish typedish = new Typedish();

	public static TypedishBuilder typedish()
	{
		return new TypedishBuilder();
	}

	public Typedish build() {
		return typedish;
	}

	public TypedishBuilder withId(int id)
	{
		typedish.setId(Long.valueOf(id));
		return this;
	}

	public TypedishBuilder withDesc(String desc)
	{
		typedish.setDescription(desc);
		return this;
	}

	public TypedishBuilder withOrder(int order)
	{
		typedish.setOrder(Long.valueOf(order));
		return this;
	}

}
