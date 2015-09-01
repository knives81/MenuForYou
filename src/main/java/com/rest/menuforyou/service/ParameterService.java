package com.rest.menuforyou.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.Parameter;
import com.rest.menuforyou.repository.MenuRepository;
import com.rest.menuforyou.repository.ParameterRepo;
import come.rest.menuforyou.util.Utils;

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

	}

	@Transactional(readOnly = true)
	public void loadParameters() {

	}
}
