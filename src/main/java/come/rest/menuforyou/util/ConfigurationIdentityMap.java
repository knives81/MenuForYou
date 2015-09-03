package come.rest.menuforyou.util;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.rest.menuforyou.domain.Parameter;
import com.rest.menuforyou.repository.ParameterRepo;

public class ConfigurationIdentityMap {

	@Autowired
	private ParameterRepo parameterRepo;

	private static final Long ROOT_MENU_ID = 1L;

	private HashMap<Long, ConfigurationInMemory> configurationMap = new HashMap<Long, ConfigurationInMemory>();

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
		if (configurationMap.get(ROOT_MENU_ID) == null) {
			loadParametersInMemory(ROOT_MENU_ID);
		}
		if (configurationMap.get(idMenu) == null) {
			loadParametersInMemory(idMenu);
		}
		ConfigurationInMemory rootConf = configurationMap.get(ROOT_MENU_ID);
		ConfigurationInMemory menuConf = configurationMap.get(idMenu);

		return ConfigurationInMemory.merge(rootConf, menuConf);
	}

	// Only entry point to write the ConfigurationInMemory
	@Transactional(readOnly = true)
	public synchronized void loadParametersInMemory(Long idMenu) {
		List<Parameter> parametersDb = parameterRepo.findByMenuId(idMenu);
		ConfigurationInMemory conf = new ConfigurationInMemory(parametersDb);
		configurationMap.put(idMenu, conf);
	}

}
