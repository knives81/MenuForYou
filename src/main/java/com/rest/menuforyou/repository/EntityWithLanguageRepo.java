package com.rest.menuforyou.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityWithLanguageRepo<T> extends CrudRepository<T, Long> {

	List<T> findByMenuId(Long id);

}
