package com.rest.menuforyou.repository;

import org.springframework.data.repository.CrudRepository;

import com.rest.menuforyou.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

}
