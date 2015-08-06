package com.rest.menuforyou.repository;

import java.util.List;

import com.rest.menuforyou.domain.Typedish;

public interface TypedishRepository extends EntityWithLanguageRepo<Typedish> {

	List<Typedish> findByMenuId(Long id);

}
