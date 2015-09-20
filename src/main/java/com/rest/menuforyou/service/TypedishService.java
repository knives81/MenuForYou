package com.rest.menuforyou.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.menuforyou.domain.EntityWithLanguage;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Language;
import com.rest.menuforyou.domain.Typedish;
import com.rest.menuforyou.domain.TypedishLanguage;
import com.rest.menuforyou.repository.TypedishLanguageRepository;
import com.rest.menuforyou.repository.TypedishRepository;
import com.rest.menuforyou.util.KeyMenuInMemory;

@Service
public class TypedishService extends CommonService {

	@Autowired
	private TypedishRepository typedishRepository;

	@Autowired
	private TypedishLanguageRepository typedishLanguageRepository;

	@Autowired
	private MenuCache menuCache;

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
	boolean mergeCustomFields(EntityWithLanguage entityToSave, EntityWithLanguage entityDb) {
		return false;

	}

	@Override
	void mapCustomFieldsSubEntities(EntityWithLanguage entity, EnumLanguage language) {
		entity.mapCustomFields(language);

	}

	@Override
	public List<? extends EntityWithLanguage> listEntities(long id, EnumLanguage language) {
		KeyMenuInMemory keyMenuInMemory = new KeyMenuInMemory(id, language);
		return menuCache.get(keyMenuInMemory);
	}
}
