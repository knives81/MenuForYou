package com.rest.menuforyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.menuforyou.repository.DishLanguageRepository;
import com.rest.menuforyou.repository.DishRepository;

@Service
public class DishService {

	@Autowired
	private DishRepository dishRepository;

	@Autowired
	private DishLanguageRepository dishLanguageRepository;

}
