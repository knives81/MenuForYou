package com.rest.menuforyou.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
	public List<Long> saveEntities(long idMenu, List<? extends EntityWithLanguage> entitiesToSave, EnumLanguage enumLanguage) {
		initialize();
		List<Long> ids = new ArrayList<Long>();
		for (EntityWithLanguage entityToSave : entitiesToSave) {
			saveSingleEntity(idMenu, entityToSave, enumLanguage);
			ids.add(entityToSave.getId());
		}
		return ids;
	}

	@Transactional(readOnly = false)
	public List<Long> updateEntities(List<? extends EntityWithLanguage> entitiesToSave, EnumLanguage enumLanguage) {
		initialize();
		List<Long> ids = new ArrayList<Long>();
		for (EntityWithLanguage entityToSave : entitiesToSave) {
			updateExistingEntity(entityToSave, enumLanguage);
			ids.add(entityToSave.getId());

		}
		return ids;
	}

	public void saveSingleEntity(long idMenu, EntityWithLanguage entityToSave, EnumLanguage enumLanguage) {
		if (entityToSave.getId() == null) {
			createNewEntity(idMenu, entityToSave, enumLanguage);
		} else {
			// TODO exception
		}
	}

	public void updateExistingEntity(EntityWithLanguage entityToSave, EnumLanguage enumLanguage) {
		String descriptionToSave = entityToSave.getDescription();
		Long idEntity = entityToSave.getId();
		Language<?> languageToSave = null;
		EntityWithLanguage entityDb = entityRepo.findOne(idEntity);
		// update entity
		boolean needToBeMerged = false;
		needToBeMerged = mergeCommonFields(entityToSave, entityDb);
		needToBeMerged &= mergeEntity(entityToSave, entityDb);
		if (needToBeMerged) {
			saveEntity(entityDb);
		}

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

	}

	private boolean mergeCommonFields(EntityWithLanguage entityToSave, EntityWithLanguage entityDb) {
		if (entityToSave.getOrder() != null)
		{
			SequenceNumber sequenceNumber = entityDb.getSequenceNumber();
			if (sequenceNumber.getNumber() != entityToSave.getOrder())
			{

				SequenceNumber sequenceNumberNew = sequenceNumberRepo.findOne(entityToSave.getOrder());
				entityDb.setSequenceNumber(sequenceNumberNew);
				return true;
			}
		}
		return false;

	}

	public void createNewEntity(long idMenu, EntityWithLanguage entityToSave, EnumLanguage enumLanguage) {
		String descriptionToSave = entityToSave.getDescription();
		Menu menu = menuRepo.findOne(idMenu);
		entityToSave.setMenu(menu);
		entityToSave.setUsername("maurizio01");
		Language<?> languageToSave = makeLanguageEntity(enumLanguage, descriptionToSave, entityToSave);
		SequenceNumber sq = new SequenceNumber();
		sequenceNumberRepo.save(sq);
		entityToSave.setSequenceNumber(sq);
		saveEntity(entityToSave);
		saveLanguageEntity(languageToSave);
	}

	@Transactional(readOnly = true)
	public List<? extends EntityWithLanguage> listEntities(long id, EnumLanguage language) {

		initialize();
		Sort sort = new Sort(Direction.ASC, "sequenceNumber");
		List<? extends EntityWithLanguage> entities = (List<? extends EntityWithLanguage>) entityRepo.findByMenuId(id, sort);
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

	abstract void initialize();

	abstract void saveEntity(EntityWithLanguage entity);

	abstract void saveLanguageEntity(Language<?> languageToSave);

	abstract Language<?> makeLanguageEntity(EnumLanguage enumLanguage, String descriptionToSave, EntityWithLanguage entityToSave);

	abstract boolean mergeEntity(EntityWithLanguage entityToSave, EntityWithLanguage entityDb);

	abstract void mapCustomFieldsSubEntities(EntityWithLanguage entity, EnumLanguage language);

}
