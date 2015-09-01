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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rest.menuforyou.domain.Dish;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Views;
import com.rest.menuforyou.error.DeleteException;
import com.rest.menuforyou.error.ResourceNotFoundException;
import com.rest.menuforyou.error.SaveException;
import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.DishService;

@RestController
public class DishController {
	@Autowired
	private DishService dishService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/menus/{id}/dishes", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String saveDishes(@PathVariable long id, @RequestBody List<Dish> entities, @RequestParam("language") EnumLanguage language) {
		try {
			List<Dish> dishes = (List<Dish>) dishService.createEntities(id, entities, language);
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewFromDish.class);
			return objectWriter.writeValueAsString(dishes);
		} catch (Exception e) {
			throw new SaveException("Exception Dish save", e);
		}

	}

	@RequestMapping(value = "/dishes", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public String updateDishes(@RequestBody List<Dish> entities, @RequestParam("language") EnumLanguage language) {
		try {
			List<Dish> dishes = (List<Dish>) dishService.updateEntities(entities, language);
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewFromDish.class);
			return objectWriter.writeValueAsString(dishes);
		} catch (Exception e) {
			throw new SaveException("Exception Dish save", e);
		}

	}

	@RequestMapping(value = "/menus/{id}/dishes", method = RequestMethod.GET)
	public String listDish(@PathVariable long id, @RequestParam("language") EnumLanguage language) throws JsonProcessingException {
		try {
			List<Dish> dishes = (List<Dish>) dishService.listEntities(id, language);
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewFromDish.class);
			return objectWriter.writeValueAsString(dishes);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Exception Dish load", e);
		}

	}

	@RequestMapping(value = "/dishes/{id}", method = RequestMethod.GET)
	public String getDish(@PathVariable long id, @RequestParam("language") EnumLanguage language) throws JsonProcessingException {
		try {
			Dish dish = (Dish) dishService.getEntity(id, language);
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewFromDish.class);
			return objectWriter.writeValueAsString(dish);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Exception Dish load", e);
		}

	}

	@RequestMapping(value = "/dishes/{id}", method = RequestMethod.DELETE)
	public JsonOk deleteDish(@PathVariable long id) {
		try {
			dishService.deleteEntity(id);
			return new JsonOk();
		} catch (Exception e) {
			throw new DeleteException("Exception Dish delete", e);
		}

	}

}
