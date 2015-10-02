package com.rest.menuforyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

	@Autowired
	private TypedishCache typedishCache;

	@Autowired
	private ParameterCache parameterCache;

	@Autowired
	private RestaurantCache restaurantCache;

	public String getStats() {
		return "{\"typedishCache\":" + typedishCache.size() +
				",\"parameterCache\":" + parameterCache.size() +
				",\"restaurantCache\":" + restaurantCache.size() + "}";
	}

}
