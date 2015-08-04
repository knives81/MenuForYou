package com.rest.menuforyou.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rest.menuforyou.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

}
