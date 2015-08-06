package com.rest.menuforyou.repository;

import java.util.List;

import com.rest.menuforyou.domain.Ingredient;

public interface IngredientRepository extends EntityWithLanguageRepo<Ingredient> {

	List<Ingredient> findByMenuId(Long id);

}
