package com.rest.menuforyou.web;

import java.net.BindException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rest.menuforyou.domain.Restaurant;
import com.rest.menuforyou.domain.Views;
import com.rest.menuforyou.error.Error;
import com.rest.menuforyou.error.GenericException;
import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.RestaurantService;

@Controller
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/restaurants", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody JsonOk saveRestaurant(@RequestBody Restaurant restaurant) throws BindException {
		Long id = Long.valueOf(0);
		try {
			id = restaurantService.saveRestaurant(restaurant);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}
		return new JsonOk(id.toString());
	}

	@RequestMapping(value = "/restaurants", method = RequestMethod.GET)
	public @ResponseBody String listRestaurant() throws BindException, JsonProcessingException {
		try {
			Iterable<Restaurant> restaurants = restaurantService.listRestaurant();
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewWithoutUser.class);
			return objectWriter.writeValueAsString(restaurants);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}

	}

	@RequestMapping(value = "/restaurants/{id}", method = RequestMethod.GET)
	public @ResponseBody String getRestaurant(@PathVariable long id) throws BindException, JsonProcessingException {
		try {
			Restaurant restaurant = restaurantService.getRestaurant(id);
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewWithoutUser.class);
			return objectWriter.writeValueAsString(restaurant);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}

	}

}
