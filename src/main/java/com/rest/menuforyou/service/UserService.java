package com.rest.menuforyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.menuforyou.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

}
