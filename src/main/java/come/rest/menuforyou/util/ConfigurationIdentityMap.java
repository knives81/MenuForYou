package come.rest.menuforyou.util;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.rest.menuforyou.domain.Parameter;
import com.rest.menuforyou.repository.ParameterRepo;

public class ConfigurationIdentityMap {

	@Autowired
	private ParameterRepo parameterRepo;

	private static final Long ROOT_MENU_ID = 1L;

	private ConcurrentHashMap<Long, ConfigurationInMemory> configurationMap = new ConcurrentHashMap<Long, ConfigurationInMemory>();

	private static class Holder {
		static final ConfigurationIdentityMap INSTANCE = new ConfigurationIdentityMap();
	}

	public static ConfigurationIdentityMap getInstance() {
		return Holder.INSTANCE;
	}

	public String getValue(Long idMenu, String parameterName) {
		return getConfigurationInMemory(idMenu).getValue(parameterName);
	}

	public ConfigurationInMemory getConfigurationInMemory(Long idMenu) {
		// it is null only the first time the configuration is requested
		if (!configurationMap.containsKey(ROOT_MENU_ID)) {
			loadParametersInMemory(ROOT_MENU_ID);
		}
		if (!configurationMap.containsKey(idMenu)) {
			loadParametersInMemory(idMenu);
		}
		ConfigurationInMemory rootConf = configurationMap.get(ROOT_MENU_ID);
		ConfigurationInMemory menuConf = configurationMap.get(idMenu);

		return ConfigurationInMemory.merge(rootConf, menuConf);
	}

	// Only entry point to write the ConfigurationInMemory
	@Transactional(readOnly = true)
	public void loadParametersInMemory(Long idMenu) {
		List<Parameter> parametersDb = parameterRepo.findByMenuId(idMenu);
		ConfigurationInMemory conf = new ConfigurationInMemory(parametersDb);
		configurationMap.put(idMenu, conf);
		
	}

}
