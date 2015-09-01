package com.rest.menuforyou.repository;

import org.springframework.data.repository.CrudRepository;

import com.rest.menuforyou.domain.Feedback;

public interface FeedbackRepo extends CrudRepository<Feedback, Long> {

}
