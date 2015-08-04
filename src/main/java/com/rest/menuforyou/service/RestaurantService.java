package com.rest.menuforyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.menuforyou.repository.MenuRepository;

@Service
public class RestaurantService {

	@Autowired
	private MenuRepository menuRepository;

}
