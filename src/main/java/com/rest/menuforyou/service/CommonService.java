package com.rest.menuforyou.service;

import java.util.ArrayList;
import java.util.Date;
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
import come.rest.menuforyou.util.Utils;

public abstract class CommonService {

	@Autowired
	private SequenceNumberRepo sequenceNumberRepo;

	@Autowired
	private MenuRepository menuRepo;

	EntityWithLanguageRepo<? extends EntityWithLanguage> entityRepo;
	LanguageRepo<? extends Language<?>> languageRepo;

	abstract void initialize();

	abstract void saveEntity(EntityWithLanguage entity);

	abstract void saveLanguageEntity(Language<?> languageToSave);

	abstract Language<?> makeLanguageEntity(EnumLanguage enumLanguage, String descriptionToSave, EntityWithLanguage entityToSave);

	abstract boolean mergeCustomFields(EntityWithLanguage entityToSave, EntityWithLanguage entityDb);

	abstract void mapCustomFieldsSubEntities(EntityWithLanguage entity, EnumLanguage language);

	protected void setEntityRepo(EntityWithLanguageRepo<? extends EntityWithLanguage> entityRepo) {
		this.entityRepo = entityRepo;
	}

	protected void setLanguageRepo(LanguageRepo<? extends Language<?>> languageRepo) {
		this.languageRepo = languageRepo;
	}

	@Transactional(readOnly = false)
	public List<? extends EntityWithLanguage> createEntities(long idMenu, List<? extends EntityWithLanguage> entitiesToCreate, EnumLanguage enumLanguage) {
		initialize();
		List<EntityWithLanguage> entitiesWithLanguage = new ArrayList<EntityWithLanguage>();
		for (EntityWithLanguage entityToCreate : entitiesToCreate) {
			createNewEntity(idMenu, entityToCreate, enumLanguage);
			entitiesWithLanguage.add(entityToCreate);
		}
		return entitiesWithLanguage;
	}

	public void createNewEntity(long idMenu, EntityWithLanguage entityToCreate, EnumLanguage enumLanguage) {

		Menu menu = menuRepo.findOne(idMenu);
		Utils.checkPermission(menu);
		entityToCreate.setMenu(menu);
		entityToCreate.setLastTouched(new Date());
		entityToCreate.setSequenceNumber(sequenceNumberRepo.save(new SequenceNumber()));
		saveEntity(entityToCreate);

		entityToCreate.mapCustomFields(enumLanguage);

		Language<?> languageToCreate = makeLanguageEntity(enumLanguage, entityToCreate.getDescription(), entityToCreate);
		saveLanguageEntity(languageToCreate);
	}

	@Transactional(readOnly = false)
	public List<? extends EntityWithLanguage> updateEntities(List<? extends EntityWithLanguage> entitiesInput, EnumLanguage enumLanguage) {
		initialize();
		List<EntityWithLanguage> entitiesWithLanguage = new ArrayList<EntityWithLanguage>();
		for (EntityWithLanguage entityInput : entitiesInput) {
			EntityWithLanguage entityUpdated = updateExistingEntity(entityInput, enumLanguage);
			entitiesWithLanguage.add(entityUpdated);
		}
		return entitiesWithLanguage;
	}

	public EntityWithLanguage updateExistingEntity(EntityWithLanguage entityInput, EnumLanguage enumLanguage) {

		EntityWithLanguage entityDb = entityRepo.findOne(entityInput.getId());
		Utils.checkPermission(entityDb.getMenu());

		mergeEntity(entityInput, entityDb);
		boolean descriptionUpdated = updateLanguageDescription(enumLanguage, entityDb, entityInput);
		if (!descriptionUpdated) {
			addNewLanguageDescription(enumLanguage, entityDb, entityInput);
		}
		entityDb.mapCustomFields(enumLanguage);
		mapCustomFieldsSubEntities(entityDb, enumLanguage);
		return entityDb;
	}

	public boolean updateLanguageDescription(EnumLanguage enumLanguage, EntityWithLanguage entityDb, EntityWithLanguage entityInput) {
		String descriptionToSave = entityInput.getDescription();
		for (Language<?> languageDb : entityDb.getEntitiesLang()) {
			if (languageDb.getLanguage().equals(enumLanguage) && StringUtils.isNotEmpty(descriptionToSave)) {
				languageDb.setDescription(descriptionToSave);
				saveLanguageEntity(languageDb);
				return true;
			}
		}
		return false;
	}

	public void addNewLanguageDescription(EnumLanguage enumLanguage, EntityWithLanguage entityDb, EntityWithLanguage entityInput) {
		String descriptionToSave = entityInput.getDescription();
		if (StringUtils.isNotEmpty(descriptionToSave)) {
			Language<?> languageToAdd = makeLanguageEntity(enumLanguage, descriptionToSave, entityDb);
			saveLanguageEntity(languageToAdd);
		}
	}

	private void mergeEntity(EntityWithLanguage entityInput, EntityWithLanguage entityDb) {

		boolean needToBeMerged = false;
		needToBeMerged = mergeCommonFields(entityInput, entityDb);
		needToBeMerged &= mergeCustomFields(entityInput, entityDb);
		if (needToBeMerged) {
			saveEntity(entityDb);
		}
	}

	private boolean mergeCommonFields(EntityWithLanguage entityInput, EntityWithLanguage entityDb) {
		if (needToUpdateOrder(entityInput, entityDb)) {
			SequenceNumber sequenceNumberNew = sequenceNumberRepo.findOne(entityInput.getOrder());
			entityDb.setSequenceNumber(sequenceNumberNew);
			return true;
		}
		return false;

	}

	private boolean needToUpdateOrder(EntityWithLanguage entityInput, EntityWithLanguage entityDb)
	{
		if (entityInput.getOrder() != null) {
			SequenceNumber sequenceNumberDb = entityDb.getSequenceNumber();
			if (sequenceNumberDb.getNumber() != entityInput.getOrder()) {
				return true;
			}
		}
		return false;
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
	public Long deleteEntity(long id) {
		initialize();
		EntityWithLanguage entity = entityRepo.findOne(Long.valueOf(id));
		entityRepo.delete(Long.valueOf(id));
		return entity.getMenu().getId();
	}

}
