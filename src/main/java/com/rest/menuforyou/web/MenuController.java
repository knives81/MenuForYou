package com.rest.menuforyou.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rest.menuforyou.service.MenuService;

@Controller
public class MenuController {
	@Autowired
	private MenuService menuService;

}
