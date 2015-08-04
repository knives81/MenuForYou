package com.rest.menuforyou.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rest.menuforyou.service.DishService;

@Controller
public class DishController {
	@Autowired
	private DishService dishService;

}
