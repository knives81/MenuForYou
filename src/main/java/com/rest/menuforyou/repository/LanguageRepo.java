package com.rest.menuforyou.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LanguageRepo<T> extends CrudRepository<T, Long> {

}
