package com.rest.menuforyou.databuilder;

import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.Restaurant;
import com.rest.menuforyou.domain.User;

public class RestaurantBuilder {
	Restaurant restaurant = new Restaurant();

	public static RestaurantBuilder restaurant()
	{
		return new RestaurantBuilder();
	}

	public Restaurant build() {
		return restaurant;
	}

	public RestaurantBuilder withId(int id) {
		restaurant.setId(Long.valueOf(id));
		return this;
	}

	public RestaurantBuilder withName(String name) {
		restaurant.setName(name);
		return this;
	}

	public RestaurantBuilder withAddress(String address) {
		restaurant.setAddress(address);
		return this;
	}

	public RestaurantBuilder withImageUrl(String imageUrl) {
		restaurant.setImageUrl(imageUrl);
		return this;
	}

	public RestaurantBuilder withUser(User user) {
		restaurant.setUser(user);
		return this;
	}

	public RestaurantBuilder withMenu(Menu menu) {
		restaurant.setMenu(menu);
		return this;
	}

}
