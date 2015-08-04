package com.rest.menuforyou.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rest.menuforyou.service.TypedishService;

@Controller
public class TypedishController {
	@Autowired
	private TypedishService typedishService;

}
