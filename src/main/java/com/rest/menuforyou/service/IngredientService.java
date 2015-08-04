package com.rest.menuforyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.menuforyou.repository.IngredientLanguageRepository;
import com.rest.menuforyou.repository.IngredientRepository;

@Service
public class IngredientService {

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private IngredientLanguageRepository ingredientLanguageRepository;

}
