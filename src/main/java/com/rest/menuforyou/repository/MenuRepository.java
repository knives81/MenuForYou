package com.rest.menuforyou.repository;

import org.springframework.data.repository.CrudRepository;

import com.rest.menuforyou.domain.Menu;

public interface MenuRepository extends CrudRepository<Menu, Long> {

}
