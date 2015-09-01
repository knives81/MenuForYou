package com.rest.menuforyou.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.Parameter;
import com.rest.menuforyou.domain.User;
import com.rest.menuforyou.repository.MenuRepository;
import com.rest.menuforyou.repository.ParameterRepo;
import com.rest.menuforyou.repository.UserRepository;
import come.rest.menuforyou.util.ParameterMap;

@Service
public class InitService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private MenuRepository menuRepo;
	@Autowired
	private ParameterRepo parameterRepo;

	@Transactional(readOnly = false)
	public void createUser(User user) {
		user.setAuthority("ADMIN");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		userRepo.save(user);
		Menu menu = new Menu();
		menu.setName(user.getUsername() + "_menu");
		menu.setUser(user);
		menuRepo.save(menu);
		initParameters(menu);
	}

	private void initParameters(Menu menu) {
		List<Parameter> parametersToSave = new ArrayList<Parameter>();
		for (Map.Entry<String, String> entry : ParameterMap.initConfiguration.entrySet()) {
			String name = entry.getKey();
			String defaultValue = entry.getValue();
			Parameter parameterToSave = new Parameter();
			parameterToSave.setMenu(menu);
			parameterToSave.setName(name);
			parameterToSave.setValue(defaultValue);
			parametersToSave.add(parameterToSave);
		}
		parameterRepo.save(parametersToSave);

	}

}
