package com.rest.menuforyou.repository;

import org.springframework.data.repository.CrudRepository;

import com.rest.menuforyou.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);

}
