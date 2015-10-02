package com.rest.menuforyou.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rest.menuforyou.domain.Typedish;

public interface TypedishRepository extends EntityWithLanguageRepo<Typedish> {

	List<Typedish> findByMenuId(Long id);

	List<Typedish> findByMenuId(Long id, Sort sort);

	List<Typedish> findByMenuIdIn(Collection<Long> ids, Sort sort);

	@Query("SELECT td FROM Typedish td INNER JOIN td.menu m WHERE m.id = :idMenu")
	List<Typedish> findByMenuIdWithDishes(@Param("idMenu") Long idMenu);

}
