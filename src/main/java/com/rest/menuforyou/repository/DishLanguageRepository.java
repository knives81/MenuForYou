package com.rest.menuforyou.repository;

import org.springframework.data.repository.CrudRepository;

import com.rest.menuforyou.domain.DishLanguage;

public interface DishLanguageRepository extends
		CrudRepository<DishLanguage, Long> {

}
