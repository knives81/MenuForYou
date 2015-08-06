package com.rest.menuforyou.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Ingredient;
import com.rest.menuforyou.error.Error;
import com.rest.menuforyou.error.GenericException;
import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.IngredientService;

@Controller
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;

	@RequestMapping(value = "/menus/{id}/ingredients", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody JsonOk saveIngredient(@PathVariable long id, @RequestBody Ingredient entity, @RequestParam("language") EnumLanguage language) {

		try {
			Long idIngredient = Long.valueOf(0);
			idIngredient = ingredientService.saveEntity(id, entity, language);
			return new JsonOk(idIngredient.toString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}

	}

	@RequestMapping(value = "/menus/{id}/ingredients", method = RequestMethod.GET)
	public @ResponseBody List<Ingredient> listIngredient(@PathVariable long id, @RequestParam("language") EnumLanguage language) {
		try {
			return (List<Ingredient>) ingredientService.listEntities(id, language);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}
	}

	@RequestMapping(value = "/ingredients/{id}", method = RequestMethod.GET)
	public @ResponseBody Ingredient getIngredient(@PathVariable long id, @RequestParam("language") EnumLanguage language) {
		try {
			return (Ingredient) ingredientService.getEntity(id, language);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}
	}

	@RequestMapping(value = "/ingredients/{id}", method = RequestMethod.DELETE)
	public @ResponseBody JsonOk deleteDish(@PathVariable long id) {
		try {
			ingredientService.deleteEntity(id);
			return new JsonOk();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}

	}

}