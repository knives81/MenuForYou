package com.rest.menuforyou.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface EntityWithLanguageRepo<T> extends PagingAndSortingRepository<T, Long> {

	List<T> findByMenuId(Long id);

	List<T> findByMenuId(Long id, Sort sort);

	List<T> findByMenuIdIn(Collection<Long> ids, Sort sort);

}
