package com.rest.menuforyou.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
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
import com.rest.menuforyou.repository.UserRepository;

@Service
public class InitService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private MenuRepository menuRepo;
	@Autowired
	private TypedishService typedishService;
	@Autowired
	private ParameterService parameterService;

	private static final String FILE_PARAMETERS = "parameters.properties";
	private static final String FILE_TYPEDSIH = "typedish.properties";

	@Transactional(readOnly = false)
	public Menu createUser(User user) {
		user.setAuthority("ADMIN");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		userRepo.save(user);
		Menu menu = new Menu();
		menu.setName(user.getUsername() + "_menu");
		menu.setUser(user);
		menuRepo.save(menu);
		saveParameters(menu);
		saveTypedishes(menu);
		return menu;
	}

	private void saveParameters(Menu menu) {
		List<Parameter> parametersToSave = new ArrayList<Parameter>();
		Properties prop = loadPropertiesFromFile(FILE_PARAMETERS);
		Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String defaultValue = prop.getProperty(name);
			Parameter parameterToSave = new Parameter();
			parameterToSave.setName(name);
			parameterToSave.setValue(defaultValue);
			parametersToSave.add(parameterToSave);
		}
		parameterService.initParameters(menu, parametersToSave);

	}

	private void saveTypedishes(Menu menu) {
		HashMap<String, HashMap<String, String>> groupLanguagesAndDescriptions = new HashMap<String, HashMap<String, String>>();
		Properties prop = loadPropertiesFromFile(FILE_TYPEDSIH);
		Enumeration<?> e = prop.propertyNames();
		HashMap<String, String> languageAndDescription = new HashMap<String, String>();
		while (e.hasMoreElements()) {

			String groupAndLanguage = (String) e.nextElement();
			String group = groupAndLanguage.substring(0, 2);
			String language = groupAndLanguage.substring(2);
			String typedishDesc = prop.getProperty(groupAndLanguage);

			if (groupLanguagesAndDescriptions.get(group) == null) {
				languageAndDescription = new HashMap<String, String>();
				languageAndDescription.put(language, typedishDesc);
				groupLanguagesAndDescriptions.put(group, languageAndDescription);
			}
			else {
				languageAndDescription = groupLanguagesAndDescriptions.get(group);
				languageAndDescription.put(language, typedishDesc);
			}
		}
		typedishService.initEntity(menu, groupLanguagesAndDescriptions);

	}

	private Properties loadPropertiesFromFile(String filename) {

		InputStream input = null;
		try {
			Properties prop = new Properties();
			input = InitService.class.getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				throw new ResourceNotFoundException(filename + "not found");
			}
			prop.load(input);
			return prop;

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
