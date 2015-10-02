package com.rest.menuforyou.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Typedish;
import com.rest.menuforyou.util.KeyMenuInMemory;

@Component
public class TypedishCache {

	final TypedishCacheLoader typedishCacheLoader;

	LoadingCache<KeyMenuInMemory, List<Typedish>> cache = null;

	@Autowired
	public TypedishCache(TypedishCacheLoader typedishCacheLoader) {
		this.typedishCacheLoader = typedishCacheLoader;
		cache = CacheBuilder.newBuilder()
				.maximumSize(10)
				.build(typedishCacheLoader);
	}

	public void invalidate(Long idMenu) {
		List<KeyMenuInMemory> keys = new ArrayList<KeyMenuInMemory>();
		for (EnumLanguage language : EnumLanguage.values()) {
			KeyMenuInMemory key = new KeyMenuInMemory(idMenu, language);
			keys.add(key);
		}
		cache.invalidateAll(keys);
	}

	public void invalidate(Long idMenu, EnumLanguage language) {
		KeyMenuInMemory key = new KeyMenuInMemory(idMenu, language);
		this.invalidate(key);
	}

	public void invalidate(KeyMenuInMemory key) {
		cache.invalidate(key);
	}

	public List<Typedish> get(KeyMenuInMemory key) {
		return cache.getUnchecked(key);
	}

	public Long size() {
		return cache.size();

	}
}
