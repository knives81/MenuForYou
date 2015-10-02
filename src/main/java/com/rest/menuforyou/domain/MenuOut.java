package com.rest.menuforyou.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuOut implements Serializable {

	public MenuOut() {
	}

	private List<Typedish> typedishes = new ArrayList<Typedish>();

	public List<Typedish> getTypedishes() {
		return typedishes;
	}

	public void setTypedishes(List<Typedish> typedishes) {
		this.typedishes = typedishes;
	}

	private List<Parameter> parameters = new ArrayList<Parameter>();

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	private Restaurant restaurant;

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
