package com.rest.menuforyou.repository;

import java.util.List;

import com.rest.menuforyou.domain.Dish;

public interface DishRepository extends EntityWithLanguageRepo<Dish> {

	List<Dish> findByMenuId(Long id);

}
