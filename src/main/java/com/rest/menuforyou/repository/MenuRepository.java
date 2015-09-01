package com.rest.menuforyou.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.User;

public interface MenuRepository extends CrudRepository<Menu, Long> {
	List<Menu> findByUser(User user);
}
