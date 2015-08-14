package com.rest.menuforyou.repository;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.rest.menuforyou.domain.Ingredient;

public interface IngredientRepository extends EntityWithLanguageRepo<Ingredient> {

	List<Ingredient> findByMenuId(Long id);

	List<Ingredient> findByMenuId(Long id, Sort sort);

}
