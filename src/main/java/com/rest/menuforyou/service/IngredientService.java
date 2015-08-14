package com.rest.menuforyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.menuforyou.domain.EntityWithLanguage;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Ingredient;
import com.rest.menuforyou.domain.IngredientLanguage;
import com.rest.menuforyou.domain.Language;
import com.rest.menuforyou.repository.IngredientLanguageRepository;
import com.rest.menuforyou.repository.IngredientRepository;

@Service
public class IngredientService extends CommonService {

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private IngredientLanguageRepository ingredientLanguageRepository;

	@Override
	void initialize() {
		setEntityRepo(ingredientRepository);
		setLanguageRepo(ingredientLanguageRepository);

	}

	@Override
	void saveEntity(EntityWithLanguage entity) {
		ingredientRepository.save((Ingredient) entity);
	}

	@Override
	void saveLanguageEntity(Language<?> languageToSave) {
		ingredientLanguageRepository.save((IngredientLanguage) languageToSave);

	}

	@Override
	public Language<?> makeLanguageEntity(EnumLanguage enumLanguage, String descriptionToSave, EntityWithLanguage entityToSave) {
		return new IngredientLanguage(enumLanguage, descriptionToSave, (Ingredient) entityToSave);
	}

	@Override
	public boolean mergeEntity(EntityWithLanguage entityToSave, EntityWithLanguage entityDb) {
		return false;
	}

	@Override
	protected void mapCustomFieldsSubEntities(EntityWithLanguage entity, EnumLanguage language) {
	}

}
