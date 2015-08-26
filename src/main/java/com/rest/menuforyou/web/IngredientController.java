package com.rest.menuforyou.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Ingredient;
import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.IngredientService;

@RestController
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;

	@RequestMapping(value = "/menus/{id}/ingredients", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public JsonOk saveIngredient(@PathVariable long id, @RequestBody List<Ingredient> entities, @RequestParam("language") EnumLanguage language) {
		List<Long> ids = ingredientService.saveEntities(id, entities, language);
		return new JsonOk(ids);

	}

	@RequestMapping(value = "/ingredients", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public JsonOk updateDishes(@RequestBody List<Ingredient> entities, @RequestParam("language") EnumLanguage language) {
		List<Long> ids = ingredientService.updateEntities(entities, language);
		return new JsonOk(ids);

	}

	@RequestMapping(value = "/menus/{id}/ingredients", method = RequestMethod.GET)
	public List<Ingredient> listIngredient(@PathVariable long id, @RequestParam("language") EnumLanguage language) {
		return (List<Ingredient>) ingredientService.listEntities(id, language);

	}

	@RequestMapping(value = "/ingredients/{id}", method = RequestMethod.GET)
	public Ingredient getIngredient(@PathVariable long id, @RequestParam("language") EnumLanguage language) {
		return (Ingredient) ingredientService.getEntity(id, language);

	}

	@RequestMapping(value = "/ingredients/{id}", method = RequestMethod.DELETE)
	public JsonOk deleteDish(@PathVariable long id) {
		ingredientService.deleteEntity(id);
		return new JsonOk();
	}

}