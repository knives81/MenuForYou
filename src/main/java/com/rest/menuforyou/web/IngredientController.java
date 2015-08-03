package com.rest.menuforyou.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rest.menuforyou.service.IngredientService;

@Controller
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;

	@RequestMapping(value = "/restaurants/{id}/ingredients", method = RequestMethod.GET)
	public @ResponseBody String listIngredient(@PathVariable long id,
			@RequestParam("language") String language) {
		return "Ciao";
	}

}