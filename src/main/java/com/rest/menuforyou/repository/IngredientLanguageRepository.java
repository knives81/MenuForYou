package com.rest.menuforyou.repository;

import org.springframework.data.repository.CrudRepository;

import com.rest.menuforyou.domain.IngredientLanguage;

public interface IngredientLanguageRepository extends
		CrudRepository<IngredientLanguage, Long> {

}
