package com.rest.menuforyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheLoader;
import com.rest.menuforyou.domain.Restaurant;
import com.rest.menuforyou.repository.RestaurantRepository;

@Component
public class RestaurantCacheLoader extends CacheLoader<Long, Restaurant> {

	@Autowired
	private RestaurantRepository restaurantRepo;

	@Override
	public Restaurant load(Long key) throws Exception {
		return restaurantRepo.findOne(Long.valueOf(key));
	}
}
