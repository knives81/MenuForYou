package com.rest.menuforyou.web;

import java.net.BindException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.menuforyou.domain.Restaurant;
import com.rest.menuforyou.error.DeleteException;
import com.rest.menuforyou.error.ResourceNotFoundException;
import com.rest.menuforyou.error.SaveException;
import com.rest.menuforyou.service.RestaurantService;

@RestController
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/restaurants", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurant createRestaurant(@RequestBody Restaurant restaurant) throws BindException {
		try {
			return restaurantService.createRestaurant(restaurant);
		} catch (Exception e) {
			throw new SaveException("Exception Restaurant save", e);
		}
	}

	@RequestMapping(value = "/restaurants", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurant addRestaurant(@RequestBody Restaurant restaurant) throws BindException {
		try {
			return restaurantService.updateRestaurant(restaurant);
		} catch (Exception e) {
			throw new SaveException("Exception Restaurant save", e);
		}

	}

	@RequestMapping(value = "/restaurants", method = RequestMethod.GET)
	public Iterable<Restaurant> listRestaurant() throws BindException, JsonProcessingException {
		try {
			return restaurantService.listRestaurant();
		} catch (Exception e) {
			throw new ResourceNotFoundException("Exception Restaurant load", e);
		}

	}

	@RequestMapping(value = "/restaurants/{id}", method = RequestMethod.GET)
	public Restaurant getRestaurant(@PathVariable long id) throws BindException, JsonProcessingException {
		try {
			return restaurantService.getRestaurant(id);
		} catch (Exception e) {
			throw new DeleteException("Exception Restaurant delete", e);
		}

	}

}
