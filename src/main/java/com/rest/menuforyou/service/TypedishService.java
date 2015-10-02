package com.rest.menuforyou.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.menuforyou.domain.EntityWithLanguage;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Language;
import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.SequenceNumber;
import com.rest.menuforyou.domain.Typedish;
import com.rest.menuforyou.domain.TypedishLanguage;
import com.rest.menuforyou.repository.SequenceNumberRepo;
import com.rest.menuforyou.repository.TypedishLanguageRepository;
import com.rest.menuforyou.repository.TypedishRepository;

@Service
public class TypedishService extends CommonService {

	@Autowired
	private TypedishRepository typedishRepository;

	@Autowired
	private TypedishLanguageRepository typedishLanguageRepository;

	@Autowired
	private TypedishCache menuCache;
	@Autowired
	private SequenceNumberRepo sequenceNumberRepo;

	public void initEntity(Menu menu, HashMap<String, HashMap<String, String>> groupLanguagesAndDescriptions) {
		for (HashMap<String, String> languageAndDescription : groupLanguagesAndDescriptions.values()) {
			Typedish typedish = new Typedish();
			typedish.setMenu(menu);
			typedish.setLastTouched(new Date());
			typedish.setSequenceNumber(sequenceNumberRepo.save(new SequenceNumber()));
			saveEntity(typedish);
			for (Entry<String, String> entry : languageAndDescription.entrySet()) {
				String language = entry.getKey();
				EnumLanguage enumLanguage = EnumLanguage.valueOf(language);
				String description = entry.getValue();
				Language<?> languageToCreate = makeLanguageEntity(enumLanguage, description, typedish);
				saveLanguageEntity(languageToCreate);
			}
		}

	}

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
	}

}
