package com.rest.menuforyou.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.Parameter;
import com.rest.menuforyou.domain.User;
import com.rest.menuforyou.error.ResourceNotFoundException;
import com.rest.menuforyou.repository.MenuRepository;
import com.rest.menuforyou.repository.ParameterRepo;
import com.rest.menuforyou.repository.UserRepository;

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
		saveInitParametersFromFile(menu);
	}

	private void saveInitParametersFromFile(Menu menu) {

		InputStream input = null;

		try {

			List<Parameter> parametersToSave = new ArrayList<Parameter>();
			Properties prop = new Properties();

			String filename = "parameters.properties";
			input = InitService.class.getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				throw new ResourceNotFoundException(filename + "not found");
			}

			prop.load(input);

			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String defaultValue = prop.getProperty(name);
				Parameter parameterToSave = new Parameter();
				parameterToSave.setMenu(menu);
				parameterToSave.setName(name);
				parameterToSave.setValue(defaultValue);
				parametersToSave.add(parameterToSave);
			}
			parameterRepo.save(parametersToSave);

		} catch (IOException ex) {
			throw new ResourceNotFoundException("Problem with the file", ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw new ResourceNotFoundException("Problem with the file", e);
				}
			}
		}
	}
}
