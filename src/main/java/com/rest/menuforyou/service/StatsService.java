package com.rest.menuforyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

	@Autowired
	private MenuCache menuCache;

	@Autowired
	private ParameterCache parameterCache;

	public String getStats() {
		return "MenuCache:" + menuCache.size() + ",ParameterCache:" + parameterCache.size();
	}

}
