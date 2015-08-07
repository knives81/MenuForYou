package com.rest.menuforyou.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.Restaurant;
import com.rest.menuforyou.domain.User;
import com.rest.menuforyou.error.Error;
import com.rest.menuforyou.error.GenericException;
import com.rest.menuforyou.repository.MenuRepository;
import com.rest.menuforyou.repository.RestaurantRepository;
import com.rest.menuforyou.repository.UserRepository;

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
	public Long saveRestaurant(Restaurant restaurant) {
		try {

			String username = "maurizio01";
			// String username = Utils.getUsernameLogged();
			if (StringUtils.isEmpty(username)) {
				User user = restaurant.getUser();
				userRepo.save(user);
				Menu menu = restaurant.getMenu();
				menuRepo.save(menu);
				restaurant.setUsername(user.getUsername());
				restaurantRepo.save(restaurant);
			} else {
				User user = userRepo.findByUsername(username);
				List<Restaurant> restaurants = restaurantRepo.findByUser(user);
				restaurant.setUser(user);
				restaurant.setMenu(restaurants.get(0).getMenu());
				restaurantRepo.save(restaurant);
			}

		} catch (ConstraintViolationException e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.SQL, e.getMessage());
		}
		return restaurant.getId();

	}

	@Transactional(readOnly = true)
	public Iterable<Restaurant> listRestaurant() {
		// List<Restaurant> restaurants = new ArrayList<Restaurant>();
		Iterable<Restaurant> restaurants = restaurantRepo.findAll();
		return restaurants;
	}

	@Transactional(readOnly = true)
	public Restaurant getRestaurant(long idRestaurant) {
		Restaurant restaurant = restaurantRepo.findOne(Long.valueOf(idRestaurant));
		return restaurant;
	}

}
