package com.rest.menuforyou.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.User;
import com.rest.menuforyou.service.InitService;

@RestController
public class InitController {

	@Autowired
	private InitService userCreationService;

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Menu saveUser(@RequestBody User user) {
		return userCreationService.createUser(user);
	}

}
