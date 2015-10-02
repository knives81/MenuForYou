package com.rest.menuforyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.rest.menuforyou.domain.Restaurant;

@Component
public class RestaurantCache {

	final RestaurantCacheLoader restaurantCacheLoader;

	LoadingCache<Long, Restaurant> cache = null;

	@Autowired
	public RestaurantCache(RestaurantCacheLoader restaurantCacheLoader) {
		this.restaurantCacheLoader = restaurantCacheLoader;
		cache = CacheBuilder.newBuilder()
				.maximumSize(10)
				.build(restaurantCacheLoader);
	}

	public void invalidate(Long idRestaurant) {
		cache.invalidate(idRestaurant);
	}

	public Restaurant get(Long key) {
		return cache.getUnchecked(key);
	}

	public Long size() {
		return cache.size();

	}
}
