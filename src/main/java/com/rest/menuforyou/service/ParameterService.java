package com.rest.menuforyou.service;

import static com.rest.menuforyou.util.Const.ROOT_MENU_ID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.Parameter;
import com.rest.menuforyou.repository.MenuRepository;
import com.rest.menuforyou.repository.ParameterRepo;
import com.rest.menuforyou.util.ConfigurationIdentityMap;
import com.rest.menuforyou.util.ConfigurationInMemory;
import com.rest.menuforyou.util.Utils;

@Service
public class ParameterService {

	@Autowired
	private MenuRepository menuRepo;

	@Autowired
	private ParameterRepo parameterRepo;

	@Transactional(readOnly = false)
	public void updateParameter(long idMenu, Parameter parameter) {
		Menu menu = menuRepo.findOne(Long.valueOf(idMenu));
		Utils.checkPermission(menu);
		List<Parameter> parametersDb = parameterRepo.findByMenu(menu);
		Parameter parameterDb = parametersDb.get(parametersDb.indexOf(parameter));
		parameterDb.setValue(parameter.getValue());
		parameterRepo.save(parameterDb);
		loadParametersInMemory(idMenu);

	}

	@Transactional(readOnly = true)
	public void forceReload(long idMenu) {
		Menu menu = menuRepo.findOne(Long.valueOf(idMenu));
		Utils.checkPermission(menu);
		loadParametersInMemory(idMenu);

	}

	// Only entry point to write the ConfigurationInMemory

	private void loadParametersInMemory(Long idMenu) {
		List<Parameter> parametersDb = parameterRepo.findByMenuId(idMenu);
		ConfigurationInMemory conf = new ConfigurationInMemory(parametersDb);
		ConfigurationIdentityMap.getInstance().put(idMenu, conf);

	}

	@Transactional(readOnly = true)
	public ConfigurationInMemory getConfigurationInMemory(Long idMenu) {
		// it is null only the first time the configuration is requested
		if (!ConfigurationIdentityMap.getInstance().containsKey(ROOT_MENU_ID)) {
			loadParametersInMemory(ROOT_MENU_ID);
		}
		if (!ConfigurationIdentityMap.getInstance().containsKey(idMenu)) {
			loadParametersInMemory(idMenu);
		}
		ConfigurationInMemory rootConf = ConfigurationIdentityMap.getInstance().get(ROOT_MENU_ID);
		ConfigurationInMemory menuConf = ConfigurationIdentityMap.getInstance().get(idMenu);

		return ConfigurationInMemory.merge(rootConf, menuConf);
	}

	public String getValue(Long idMenu, String parameterName) {
		return getConfigurationInMemory(idMenu).getValue(parameterName);
	}
}
