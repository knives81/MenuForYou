package com.rest.menuforyou.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rest.menuforyou.domain.Restaurant;
import com.rest.menuforyou.domain.User;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
	List<Restaurant> findByUser(User user);

}
