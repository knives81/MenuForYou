package com.rest.menuforyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.menuforyou.repository.TypedishLanguageRepository;
import com.rest.menuforyou.repository.TypedishRepository;

@Service
public class TypedishService {

	@Autowired
	private TypedishRepository typedishRepository;

	@Autowired
	private TypedishLanguageRepository typedishLanguageRepository;

}
