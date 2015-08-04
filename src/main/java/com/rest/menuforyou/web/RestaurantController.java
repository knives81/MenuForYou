package com.rest.menuforyou.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rest.menuforyou.service.RestaurantService;

@Controller
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantService;

}
