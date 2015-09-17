package com.rest.menuforyou.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.Restaurant;
import com.rest.menuforyou.domain.User;
import com.rest.menuforyou.repository.MenuRepository;
import com.rest.menuforyou.repository.RestaurantRepository;
import com.rest.menuforyou.repository.UserRepository;
import com.rest.menuforyou.util.Utils;

@Service
public class RestaurantService {

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private RestaurantRepository restaurantRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private MenuRepository menuRepo;

	@Transactional(readOnly = false)
	public Restaurant createRestaurant(Restaurant restaurant) {

		User user = userRepo.findByUsername(Utils.getUsernameLogged());
		Menu menu = menuRepo.findByUser(user).get(0);
		restaurant.setMenu(menu);
		restaurant.setLastTouched(new Date());
		restaurant = restaurantRepo.save(restaurant);

		return restaurant;

	}

	@Transactional(readOnly = false)
	public Restaurant updateRestaurant(Restaurant restaurantInput) {

		Restaurant restaurantDb = restaurantRepo.findOne(restaurantInput.getId());
		Utils.checkPermission(restaurantDb);

		if (StringUtils.isNotEmpty(restaurantInput.getName())) {
			restaurantDb.setName(restaurantInput.getName());
		}
		if (StringUtils.isNotEmpty(restaurantInput.getAddress())) {
			restaurantDb.setAddress(restaurantInput.getAddress());
		}
		if (StringUtils.isNotEmpty(restaurantInput.getImageUrl())) {
			restaurantDb.setImageUrl(restaurantInput.getImageUrl());
		}
		restaurantDb.setLastTouched(new Date());
		restaurantRepo.save(restaurantDb);
		return restaurantDb;
	}

	@Transactional(readOnly = true)
	public Iterable<Restaurant> listRestaurant() {
		Iterable<Restaurant> restaurants = restaurantRepo.findAll();
		return restaurants;
	}

	@Transactional(readOnly = true)
	public Restaurant getRestaurant(long idRestaurant) {
		Restaurant restaurant = restaurantRepo.findOne(Long.valueOf(idRestaurant));
		return restaurant;
	}

}
