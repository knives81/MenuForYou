package com.rest.menuforyou.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.menuforyou.domain.User;
import com.rest.menuforyou.error.Error;
import com.rest.menuforyou.error.GenericException;
import com.rest.menuforyou.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;

	@Transactional(readOnly = false)
	public void saveUser(User user) {

		try {
			userRepo.save(user);
		} catch (ConstraintViolationException e) {
			System.err.println(e.getMessage() + " " + e.getErrorCode() + " " + e.getSQLState());
			throw new GenericException(Error.SQL, e.getMessage());
		}
	}
}
