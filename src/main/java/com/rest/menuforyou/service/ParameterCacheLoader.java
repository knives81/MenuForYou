package com.rest.menuforyou.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheLoader;
import com.rest.menuforyou.domain.Parameter;
import com.rest.menuforyou.repository.ParameterRepo;
import com.rest.menuforyou.util.ConfigurationInMemory;

@Component
public class ParameterCacheLoader extends CacheLoader<Long, ConfigurationInMemory> {

	@Autowired
	private ParameterRepo parameterRepo;

	@Override
	public ConfigurationInMemory load(Long idMenu) throws Exception {
		List<Parameter> parametersDb = parameterRepo.findByMenuId(idMenu);
		ConfigurationInMemory conf = new ConfigurationInMemory(parametersDb);
		return conf;
	}
}
