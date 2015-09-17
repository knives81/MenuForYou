package com.rest.menuforyou.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;

import com.rest.menuforyou.domain.Ingredient;

public interface IngredientRepository extends EntityWithLanguageRepo<Ingredient> {

	public final static String FIND_WITH_MULTIPLE_MENU = "SELECT i FROM Ingredient i LEFT JOIN Menu m.menu WHERE m.id = :id";

	List<Ingredient> findByMenuId(Long id);

	List<Ingredient> findByMenuId(Long id, Sort sort);

	List<Ingredient> findByMenuIdIn(Collection<Long> ids, Sort sort);

	// @Query(FIND_WITH_MULTIPLE_MENU)
	// public List<Ingredient> findByMultipleMenu(@Param("id") Long id);

}
