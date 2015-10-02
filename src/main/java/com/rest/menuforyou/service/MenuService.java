package com.rest.menuforyou.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.MenuOut;
import com.rest.menuforyou.domain.Parameter;
import com.rest.menuforyou.domain.Restaurant;
import com.rest.menuforyou.domain.Typedish;
import com.rest.menuforyou.util.KeyMenuInMemory;

@Service
public class MenuService {

	@Autowired
	private TypedishCache menuCache;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private ParameterService parameterService;

	public MenuOut getMenu(long idRestaurant, EnumLanguage language) {

		MenuOut menuOut = new MenuOut();
		Restaurant restaurant = restaurantService.getRestaurant(idRestaurant);
		menuOut.setRestaurant(restaurant);

		Long idMenu = restaurant.getMenu().getId();

		KeyMenuInMemory keyMenuInMemory = new KeyMenuInMemory(idMenu, language);
		List<Typedish> typedishes = menuCache.get(keyMenuInMemory);
		menuOut.setTypedishes(typedishes);

		List<Parameter> parameters = parameterService.getConfigurationInMemory(idMenu).getParameters();
		menuOut.setParameters(parameters);

		return menuOut;

	}
}
