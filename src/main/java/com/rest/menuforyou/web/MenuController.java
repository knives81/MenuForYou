package com.rest.menuforyou.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.MenuOut;
import com.rest.menuforyou.domain.Views;
import com.rest.menuforyou.error.ResourceNotFoundException;
import com.rest.menuforyou.service.MenuService;

@RestController
public class MenuController {
	@Autowired
	private MenuService menuService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/restaurantmenus/{id}", method = RequestMethod.GET)
	public String getMenu(@PathVariable long id, @RequestParam("language") EnumLanguage language) {
		try {
			MenuOut menuOut = menuService.getMenu(id, language);
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewFromTypedish.class);
			return objectWriter.writeValueAsString(menuOut);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Exception Menu load", e);
		}

	}

}
