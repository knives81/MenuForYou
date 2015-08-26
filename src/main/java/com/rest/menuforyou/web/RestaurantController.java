package com.rest.menuforyou.web;

import java.net.BindException;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

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
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rest.menuforyou.domain.Restaurant;
import com.rest.menuforyou.domain.Views;
import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.RestaurantService;

@RestController
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/restaurants", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public JsonOk createRestaurant(@Valid @RequestBody Restaurant restaurant) throws BindException {
		Long id = restaurantService.createRestaurant(restaurant);
		List<Long> ids = Collections.singletonList(id);
		return new JsonOk(ids);
	}

	@RequestMapping(value = "/restaurants", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public JsonOk addRestaurant(@Valid @RequestBody Restaurant restaurant) throws BindException {
		Long id = Long.valueOf(0);
		id = restaurantService.addRestaurant(restaurant);
		List<Long> ids = Collections.singletonList(id);
		return new JsonOk(ids);
	}

	@RequestMapping(value = "/restaurants", method = RequestMethod.GET)
	public String listRestaurant() throws BindException, JsonProcessingException {
		Iterable<Restaurant> restaurants = restaurantService.listRestaurant();
		ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewWithoutUser.class);
		return objectWriter.writeValueAsString(restaurants);

	}

	@RequestMapping(value = "/restaurants/{id}", method = RequestMethod.GET)
	public String getRestaurant(@PathVariable long id) throws BindException, JsonProcessingException {
		Restaurant restaurant = restaurantService.getRestaurant(id);
		ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewWithoutUser.class);
		return objectWriter.writeValueAsString(restaurant);

	}

}
