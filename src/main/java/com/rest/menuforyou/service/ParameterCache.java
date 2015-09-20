package com.rest.menuforyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.rest.menuforyou.util.ConfigurationInMemory;

@Component
public class ParameterCache {

	final ParameterCacheLoader parameterCacheLoader;

	LoadingCache<Long, ConfigurationInMemory> cache = null;

	@Autowired
	public ParameterCache(ParameterCacheLoader parameterCacheLoader) {
		this.parameterCacheLoader = parameterCacheLoader;
		cache = CacheBuilder.newBuilder()
				.maximumSize(10)
				.build(parameterCacheLoader);
	}

	public void invalidate(Long key) {
		cache.invalidate(key);
	}

	public ConfigurationInMemory get(Long key) {
		return cache.getUnchecked(key);
	}

	public void refresh(Long key) {
		cache.refresh(key);
	}

	public Long size() {
		return cache.size();
	}

}
