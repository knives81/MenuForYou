package com.rest.menuforyou.repository;

import org.springframework.data.repository.CrudRepository;

import com.rest.menuforyou.domain.SequenceNumber;

public interface SequenceNumberRepo extends CrudRepository<SequenceNumber, Long> {

}
