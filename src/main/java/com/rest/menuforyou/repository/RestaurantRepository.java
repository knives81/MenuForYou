package com.rest.menuforyou.repository;

import org.springframework.data.repository.CrudRepository;

import com.rest.menuforyou.domain.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

}
