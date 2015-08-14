package com.rest.menuforyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.menuforyou.domain.Dish;
import com.rest.menuforyou.domain.EntityWithLanguage;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Ingredient;
import com.rest.menuforyou.domain.Language;
import com.rest.menuforyou.domain.Typedish;
import com.rest.menuforyou.domain.TypedishLanguage;
import com.rest.menuforyou.repository.TypedishLanguageRepository;
import com.rest.menuforyou.repository.TypedishRepository;

@Service
public class TypedishService extends CommonService {

	@Autowired
	private TypedishRepository typedishRepository;

	@Autowired
	private TypedishLanguageRepository typedishLanguageRepository;

	@Override
	void initialize() {
		setEntityRepo(typedishRepository);
		setLanguageRepo(typedishLanguageRepository);

	}

	@Override
	void saveEntity(EntityWithLanguage entity) {
		typedishRepository.save((Typedish) entity);

	}

	@Override
	void saveLanguageEntity(Language<?> languageToSave) {
		typedishLanguageRepository.save((TypedishLanguage) languageToSave);

	}

	@Override
	Language<?> makeLanguageEntity(EnumLanguage enumLanguage, String descriptionToSave, EntityWithLanguage entityToSave) {
		return new TypedishLanguage(enumLanguage, descriptionToSave, (Typedish) entityToSave);
	}

	@Override
	boolean mergeEntity(EntityWithLanguage entityToSave, EntityWithLanguage entityDb) {
		return false;

	}

	@Override
	void mapCustomFieldsSubEntities(EntityWithLanguage entity, EnumLanguage language) {
		Typedish typedish = (Typedish) entity;
		for (Dish dish : typedish.getDishes()) {
			dish.mapCustomFields(language);
			for (Ingredient ingredient : dish.getIngredients()) {
				ingredient.mapCustomFields(language);
			}
		}

	}

}
