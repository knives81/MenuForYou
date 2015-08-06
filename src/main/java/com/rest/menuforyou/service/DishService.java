package com.rest.menuforyou.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.menuforyou.domain.Dish;
import com.rest.menuforyou.domain.DishLanguage;
import com.rest.menuforyou.domain.EntityWithLanguage;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Ingredient;
import com.rest.menuforyou.domain.Language;
import com.rest.menuforyou.domain.Typedish;
import com.rest.menuforyou.repository.DishLanguageRepository;
import com.rest.menuforyou.repository.DishRepository;
import com.rest.menuforyou.repository.IngredientRepository;
import com.rest.menuforyou.repository.TypedishRepository;

@Service
public class DishService extends CommonService {

	@Autowired
	private DishRepository dishRepository;

	@Autowired
	private TypedishRepository typedishRepo;

	@Autowired
	private DishLanguageRepository dishLanguageRepository;

	@Autowired
	private IngredientRepository ingredientRepo;

	@Override
	void initialize() {
		setEntityRepo(dishRepository);
		setLanguageRepo(dishLanguageRepository);

	}

	@Override
	void saveEntity(EntityWithLanguage entity) {
		dishRepository.save((Dish) entity);

	}

	@Override
	void saveLanguageEntity(Language<?> languageToSave) {
		dishLanguageRepository.save((DishLanguage) languageToSave);

	}

	@Override
	Language<?> makeLanguageEntity(EnumLanguage enumLanguage, String descriptionToSave, EntityWithLanguage entityToSave) {
		return new DishLanguage(enumLanguage, descriptionToSave, (Dish) entityToSave);
	}

	@Override
	void mergeEntity(EntityWithLanguage entityToSave, EntityWithLanguage entityDb) {
		Dish dishDb = (Dish) entityDb;
		Dish dishToSave = (Dish) entityToSave;
		if (StringUtils.isNotEmpty(dishToSave.getName())) {
			dishDb.setName(dishToSave.getName());
		}
		if (StringUtils.isNotEmpty(dishToSave.getImageUrl())) {
			dishDb.setImageUrl(dishToSave.getImageUrl());
		}
		if (dishToSave.getPrice() > 0.0) {
			dishDb.setPrice(dishToSave.getPrice());
		}
		if (dishToSave.getTypedish() != null) {
			Typedish typedish = typedishRepo.findOne(dishToSave.getTypedish().getId());
			dishDb.setTypedish(typedish);
		}

		Set<Ingredient> ingredientsToSave = new HashSet<Ingredient>();
		for (Ingredient ingredient : dishToSave.getIngredients()) {
			Ingredient ingredientDummy = ingredientRepo.findOne(ingredient.getId());
			ingredientsToSave.add(ingredientDummy);

		}
		if (!ingredientsToSave.isEmpty()) {
			dishDb.setIngredients(ingredientsToSave);
		}

		dishRepository.save(dishDb);

	}

	@Override
	void mapCustomFieldsSubEntities(EntityWithLanguage entity, EnumLanguage language) {
		Dish dish = (Dish) entity;
		dish.getTypedish().mapCustomFields(language);
		for (Ingredient ingredient : dish.getIngredients()) {
			ingredient.mapCustomFields(language);
		}

	}

}
