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
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Typedish;
import com.rest.menuforyou.domain.Views;
import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.TypedishService;

@RestController
public class TypedishController {
	@Autowired
	private TypedishService typedishService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/menus/{id}/typedishes", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public JsonOk saveTypedish(@PathVariable long id, @RequestBody List<Typedish> entities, @RequestParam("language") EnumLanguage language) {
		Long idTypedish = Long.valueOf(0);
		idTypedish = typedishService.saveEntities(id, entities, language);
		return new JsonOk(idTypedish.toString());
	}

	@RequestMapping(value = "/typedishes", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public JsonOk updateDishes(@RequestBody List<Typedish> entities, @RequestParam("language") EnumLanguage language) {
		typedishService.updateEntities(entities, language);
		return new JsonOk();

	}

	@RequestMapping(value = "/menus/{id}/typedishes", method = RequestMethod.GET)
	public String listTypedish(@PathVariable long id, @RequestParam("language") EnumLanguage language) throws JsonProcessingException {

		List<Typedish> typedishes = (List<Typedish>) typedishService.listEntities(id, language);
		ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewFromTypedish.class);
		return objectWriter.writeValueAsString(typedishes);

	}

	@RequestMapping(value = "/typedishes/{id}", method = RequestMethod.GET)
	public String getTypedish(@PathVariable long id, @RequestParam("language") EnumLanguage language) throws JsonProcessingException {
		Typedish typedish = (Typedish) typedishService.getEntity(id, language);
		ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewFromTypedish.class);
		return objectWriter.writeValueAsString(typedish);

	}

	@RequestMapping(value = "/typedishes/{id}", method = RequestMethod.DELETE)
	public JsonOk deleteTypedish(@PathVariable long id) throws Exception {
		typedishService.deleteEntity(id);
		return new JsonOk();
	}

}
