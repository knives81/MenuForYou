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
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Typedish;
import com.rest.menuforyou.domain.Views;
import com.rest.menuforyou.error.Error;
import com.rest.menuforyou.error.GenericException;
import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.TypedishService;

@Controller
public class TypedishController {
	@Autowired
	private TypedishService typedishService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/menus/{id}/typedishes", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody JsonOk saveTypedish(@PathVariable long id, @RequestBody Typedish entity, @RequestParam("language") EnumLanguage language) {
		try {
			Long idTypedish = Long.valueOf(0);
			idTypedish = typedishService.saveEntity(id, entity, language);
			return new JsonOk(idTypedish.toString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}

	}

	@RequestMapping(value = "/menus/{id}/typedishes", method = RequestMethod.GET)
	public @ResponseBody String listTypedish(@PathVariable long id, @RequestParam("language") EnumLanguage language) throws JsonProcessingException {
		try {
			List<Typedish> typedishes = (List<Typedish>) typedishService.listEntities(id, language);
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewFromTypedish.class);
			return objectWriter.writeValueAsString(typedishes);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}
	}

	@RequestMapping(value = "/typedishes/{id}", method = RequestMethod.GET)
	public @ResponseBody String getTypedish(@PathVariable long id, @RequestParam("language") EnumLanguage language) throws JsonProcessingException {
		try {
			Typedish typedish = (Typedish) typedishService.getEntity(id, language);
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewFromTypedish.class);
			return objectWriter.writeValueAsString(typedish);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}
	}

	@RequestMapping(value = "/typedishes/{id}", method = RequestMethod.DELETE)
	public @ResponseBody JsonOk deleteTypedish(@PathVariable long id) throws Exception {
		try {
			typedishService.deleteEntity(id);
			return new JsonOk();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new GenericException(Error.UNKNOWN, e.getMessage());
		}

	}

}
