package com.rest.menuforyou.repository;

import org.springframework.data.repository.CrudRepository;

import com.rest.menuforyou.domain.Dish;

public interface DishRepository extends CrudRepository<Dish, Long> {

}
