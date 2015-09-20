package com.rest.menuforyou.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheLoader;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Typedish;
import com.rest.menuforyou.repository.TypedishRepository;
import com.rest.menuforyou.util.KeyMenuInMemory;

@Component
public class MenuCacheLoader extends CacheLoader<KeyMenuInMemory, List<Typedish>> {

	@Autowired
	private TypedishRepository typedishRepository;

	@Override
	public List<Typedish> load(KeyMenuInMemory keyMenuInMemory) throws Exception {
		Long id = keyMenuInMemory.getIdMenu();
		EnumLanguage language = keyMenuInMemory.getLanguage();

		Sort sort = new Sort(Direction.ASC, "sequenceNumber");
		List<Typedish> typedishes = typedishRepository.findByMenuId(id, sort);
		for (Typedish entity : typedishes) {
			entity.mapCustomFields(language);
			entity.mapCustomFieldsSubEntities(language);
		}
		return typedishes;
	}
}
