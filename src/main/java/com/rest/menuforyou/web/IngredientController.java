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
import com.rest.menuforyou.error.DeleteException;
import com.rest.menuforyou.error.ResourceNotFoundException;
import com.rest.menuforyou.error.SaveException;
import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.IngredientService;

@RestController
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;

	@RequestMapping(value = "/menus/{id}/ingredients", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public List<Ingredient> saveIngredient(@PathVariable long id, @RequestBody List<Ingredient> entities, @RequestParam("language") EnumLanguage language) {
		try {
			return (List<Ingredient>) ingredientService.createEntities(id, entities, language);
		} catch (Exception e) {
			throw new SaveException("Exception Ingredient save", e);
		}
	}

	@RequestMapping(value = "/ingredients", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public List<Ingredient> updateDishes(@RequestBody List<Ingredient> entities, @RequestParam("language") EnumLanguage language) {
		try {
			return (List<Ingredient>) ingredientService.updateEntities(entities, language);
		} catch (Exception e) {
			throw new SaveException("Exception Ingredient update", e);
		}
	}

	@RequestMapping(value = "/menus/{id}/ingredients", method = RequestMethod.GET)
	public List<Ingredient> listIngredient(@PathVariable long id, @RequestParam("language") EnumLanguage language) {
		try {
			return (List<Ingredient>) ingredientService.listEntities(id, language);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Exception Ingredient load", e);
		}
	}

	@RequestMapping(value = "/ingredients/{id}", method = RequestMethod.GET)
	public Ingredient getIngredient(@PathVariable long id, @RequestParam("language") EnumLanguage language) {
		try {
			return (Ingredient) ingredientService.getEntity(id, language);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Exception Ingredient load", e);
		}
	}

	@RequestMapping(value = "/ingredients/{id}", method = RequestMethod.DELETE)
	public JsonOk deleteDish(@PathVariable long id) {
		try {
			ingredientService.deleteEntity(id);
			return new JsonOk();
		} catch (Exception e) {
			throw new DeleteException("Exception Ingredient delete", e);
		}
	}

}