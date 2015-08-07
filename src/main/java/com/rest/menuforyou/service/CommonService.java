package com.rest.menuforyou.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.rest.menuforyou.domain.EntityWithLanguage;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Language;
import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.SequenceNumber;
import com.rest.menuforyou.repository.EntityWithLanguageRepo;
import com.rest.menuforyou.repository.LanguageRepo;
import com.rest.menuforyou.repository.MenuRepository;
import com.rest.menuforyou.repository.SequenceNumberRepo;

public abstract class CommonService {

	@Autowired
	private SequenceNumberRepo sequenceNumberRepo;

	@Autowired
	private MenuRepository menuRepo;

	EntityWithLanguageRepo<? extends EntityWithLanguage> entityRepo;
	LanguageRepo<? extends Language<?>> languageRepo;

	protected void setEntityRepo(EntityWithLanguageRepo<? extends EntityWithLanguage> entityRepo) {
		this.entityRepo = entityRepo;
	}

	protected void setLanguageRepo(LanguageRepo<? extends Language<?>> languageRepo) {
		this.languageRepo = languageRepo;
	}

	@Transactional(readOnly = false)
	public Long saveEntity(long idMenu, EntityWithLanguage entityToSave, EnumLanguage enumLanguage) {
		initialize();
		String descriptionToSave = entityToSave.getDescription();
		Long idEntity = entityToSave.getId();
		Language<?> languageToSave = null;

		if (idEntity != null) {
			EntityWithLanguage entityDb = entityRepo.findOne(idEntity);
			// update entity
			mergeEntity(entityToSave, entityDb);

			// update description
			for (Language<?> languageDb : entityDb.getEntitiesLang()) {
				if (languageDb.getLanguage().equals(enumLanguage) && StringUtils.isNotEmpty(descriptionToSave)) {
					languageToSave = languageDb;
					languageToSave.setDescription(descriptionToSave);
					saveLanguageEntity(languageToSave);
					break;
				}
			}
			// new language to be added
			if (languageToSave == null && StringUtils.isNotEmpty(descriptionToSave)) {
				languageToSave = makeLanguageEntity(enumLanguage, descriptionToSave, entityDb);
				saveLanguageEntity(languageToSave);

			}
		} else {
			Menu menu = menuRepo.findOne(idMenu);
			entityToSave.setMenu(menu);
			entityToSave.setUsername("maurizio01");
			languageToSave = makeLanguageEntity(enumLanguage, descriptionToSave, entityToSave);
			SequenceNumber sq = new SequenceNumber();
			sequenceNumberRepo.save(sq);
			entityToSave.setSequenceNumber(sq);
			saveEntity(entityToSave);
			saveLanguageEntity(languageToSave);
		}

		return entityToSave.getId();
	}

	@Transactional(readOnly = true)
	public List<? extends EntityWithLanguage> listEntities(long id, EnumLanguage language) {

		initialize();
		List<? extends EntityWithLanguage> entities = (List<? extends EntityWithLanguage>) entityRepo.findByMenuId(id);
		for (EntityWithLanguage entity : entities) {
			entity.mapCustomFields(language);
			mapCustomFieldsSubEntities(entity, language);
		}

		return entities;
	}

	@Transactional(readOnly = true)
	public EntityWithLanguage getEntity(long id, EnumLanguage language) {
		initialize();
		EntityWithLanguage entity = entityRepo.findOne(Long.valueOf(id));
		entity.mapCustomFields(language);
		mapCustomFieldsSubEntities(entity, language);
		return entity;
	}

	@Transactional(readOnly = false)
	public void deleteEntity(long id) {
		initialize();
		entityRepo.delete(Long.valueOf(id));
	}

	protected static String getUsernameLogged() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	abstract void initialize();

	abstract void saveEntity(EntityWithLanguage entity);

	abstract void saveLanguageEntity(Language<?> languageToSave);

	abstract Language<?> makeLanguageEntity(EnumLanguage enumLanguage, String descriptionToSave, EntityWithLanguage entityToSave);

	abstract void mergeEntity(EntityWithLanguage entityToSave, EntityWithLanguage entityDb);

	abstract void mapCustomFieldsSubEntities(EntityWithLanguage entity, EnumLanguage language);

}
