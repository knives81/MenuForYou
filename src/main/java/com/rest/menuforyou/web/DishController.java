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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rest.menuforyou.domain.Dish;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Views;
import com.rest.menuforyou.error.Error;
import com.rest.menuforyou.error.GenericException;
import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.DishService;

@Controller
public class DishController {
	@Autowired
	private DishService dishService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/menus/{id}/dishes", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody JsonOk saveDish(@PathVariable long id, @RequestBody Dish entity, @RequestParam("language") EnumLanguage language) {
		try {
			Long idDish = Long.valueOf(0);
			idDish = dishService.saveEntity(id, entity, language);
			return new JsonOk(idDish.toString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}

	}

	@RequestMapping(value = "/menus/{id}/dishes", method = RequestMethod.GET)
	public @ResponseBody String listDish(@PathVariable long id, @RequestParam("language") EnumLanguage language) throws JsonProcessingException {
		try {
			List<Dish> dishes = (List<Dish>) dishService.listEntities(id, language);
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewFromDish.class);
			return objectWriter.writeValueAsString(dishes);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}

	}

	@RequestMapping(value = "/dishes/{id}", method = RequestMethod.GET)
	public @ResponseBody String getDish(@PathVariable long id, @RequestParam("language") EnumLanguage language) throws JsonProcessingException {
		try {
			Dish dish = (Dish) dishService.getEntity(id, language);
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewFromDish.class);
			return objectWriter.writeValueAsString(dish);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}
	}

	@RequestMapping(value = "/dishes/{id}", method = RequestMethod.DELETE)
	public @ResponseBody JsonOk deleteDish(@PathVariable long id) {
		try {
			dishService.deleteEntity(id);
			return new JsonOk();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}

	}

}
