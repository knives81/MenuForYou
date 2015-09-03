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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Typedish;
import com.rest.menuforyou.domain.Views;
import com.rest.menuforyou.error.DeleteException;
import com.rest.menuforyou.error.ResourceNotFoundException;
import com.rest.menuforyou.error.SaveException;
import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.TypedishService;
import come.rest.menuforyou.util.MenuIdentityMap;

@RestController
public class TypedishController {
	@Autowired
	private TypedishService typedishService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/menus/{id}/typedishes", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public List<Typedish> saveTypedish(@PathVariable long id, @RequestBody List<Typedish> entities, @RequestParam("language") EnumLanguage language) {
		try {
			List<Typedish> typedishes = (List<Typedish>) typedishService.createEntities(id, entities, language);
			MenuIdentityMap.getInstance().flagToBeUpdated(id);
			return typedishes;
		} catch (Exception e) {
			throw new SaveException("Exception Typedish save", e);
		}
	}

	@RequestMapping(value = "/typedishes", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public List<Typedish> updateDishes(@RequestBody List<Typedish> entities, @RequestParam("language") EnumLanguage language) {
		try {
			List<Typedish> typedishes = (List<Typedish>) typedishService.updateEntities(entities, language);
			MenuIdentityMap.getInstance().flagToBeUpdated(typedishes.get(0).getMenu().getId());
			return typedishes;
		} catch (Exception e) {
			throw new SaveException("Exception Typedish save", e);
		}
	}

	@RequestMapping(value = "/menus/{id}/typedishes", method = RequestMethod.GET)
	public String listTypedish(@PathVariable long id, @RequestParam("language") EnumLanguage language) {
		try {
			List<Typedish> typedishes = MenuIdentityMap.getInstance().getMenu(id, language);
			if (typedishes == null) {
				typedishes = (List<Typedish>) typedishService.listEntities(id, language);
				MenuIdentityMap.getInstance().putMenu(id, language, typedishes);
			}
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewFromTypedish.class);
			return objectWriter.writeValueAsString(typedishes);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Exception Typedish load", e);
		}

	}

	@RequestMapping(value = "/typedishes/{id}", method = RequestMethod.GET)
	public String getTypedish(@PathVariable long id, @RequestParam("language") EnumLanguage language) {
		try {
			Typedish typedish = (Typedish) typedishService.getEntity(id, language);
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewFromTypedish.class);
			return objectWriter.writeValueAsString(typedish);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Exception Typedish load", e);
		}

	}

	@RequestMapping(value = "/typedishes/{id}", method = RequestMethod.DELETE)
	public JsonOk deleteTypedish(@PathVariable long id) throws Exception {
		try {
			Long idMenu = typedishService.deleteEntity(id);
			MenuIdentityMap.getInstance().flagToBeUpdated(idMenu);
			return new JsonOk();
		} catch (Exception e) {
			throw new DeleteException("Exception Typedish delete", e);
		}
	}

}
