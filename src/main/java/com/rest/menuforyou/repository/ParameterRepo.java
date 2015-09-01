package com.rest.menuforyou.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.Parameter;

public interface ParameterRepo extends CrudRepository<Parameter, Long> {

	List<Parameter> findByMenu(Menu menu);

}
