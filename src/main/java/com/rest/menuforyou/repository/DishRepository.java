package com.rest.menuforyou.repository;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.rest.menuforyou.domain.Dish;

public interface DishRepository extends EntityWithLanguageRepo<Dish> {

	List<Dish> findByMenuId(Long id);

	List<Dish> findByMenuId(Long id, Sort sort);

}
