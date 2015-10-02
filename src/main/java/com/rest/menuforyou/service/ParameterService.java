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
import com.rest.menuforyou.util.ConfigurationInMemory;
import com.rest.menuforyou.util.Utils;

@Service
public class ParameterService {

	@Autowired
	private MenuRepository menuRepo;

	@Autowired
	private ParameterRepo parameterRepo;

	@Autowired
	private ParameterCache parameterCache;

	@Transactional(readOnly = false)
	public void updateParameter(long idMenu, Parameter parameter) {
		Menu menu = menuRepo.findOne(Long.valueOf(idMenu));
		Utils.checkPermission(menu);
		List<Parameter> parametersDb = parameterRepo.findByMenu(menu);
		Parameter parameterDb = parametersDb.get(parametersDb.indexOf(parameter));
		parameterDb.setValue(parameter.getValue());
		parameterRepo.save(parameterDb);
		parameterCache.invalidate(idMenu);

	}

	public void initParameters(Menu menu, List<Parameter> parameters) {
		for (Parameter parameterToCreate : parameters) {
			parameterToCreate.setMenu(menu);
			parameterRepo.save(parameterToCreate);
		}
	}

	@Transactional(readOnly = true)
	public void forceReload(long idMenu) {
		Menu menu = menuRepo.findOne(Long.valueOf(idMenu));
		Utils.checkPermission(menu);
		parameterCache.refresh(idMenu);

	}

	@Transactional(readOnly = true)
	public ConfigurationInMemory getConfigurationInMemory(Long idMenu) {
		// it is null only the first time the configuration is requested
		ConfigurationInMemory rootConf = parameterCache.get(ROOT_MENU_ID);
		ConfigurationInMemory menuConf = parameterCache.get(idMenu);

		return ConfigurationInMemory.merge(rootConf, menuConf);
	}

	public String getValue(Long idMenu, String parameterName) {
		return parameterCache.get(idMenu).getValue(parameterName);
	}
}
