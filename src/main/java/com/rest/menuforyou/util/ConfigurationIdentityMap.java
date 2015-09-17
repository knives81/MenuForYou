package com.rest.menuforyou.util;

import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationIdentityMap {

	private ConcurrentHashMap<Long, ConfigurationInMemory> configurationMap = new ConcurrentHashMap<Long, ConfigurationInMemory>();

	private static class Holder {
		static final ConfigurationIdentityMap INSTANCE = new ConfigurationIdentityMap();
	}

	public static ConfigurationIdentityMap getInstance() {
		return Holder.INSTANCE;
	}

	public void put(Long idMenu, ConfigurationInMemory conf) {
		configurationMap.put(idMenu, conf);
	}

	public ConfigurationInMemory get(Long idMenu) {
		return configurationMap.get(idMenu);
	}

	public boolean containsKey(Long idMenu) {
		return configurationMap.containsKey(idMenu);
	}

}
