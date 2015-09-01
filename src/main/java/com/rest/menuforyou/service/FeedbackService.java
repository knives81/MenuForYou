package com.rest.menuforyou.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.menuforyou.domain.Dish;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Feedback;
import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.repository.DishRepository;
import com.rest.menuforyou.repository.FeedbackRepo;
import com.rest.menuforyou.repository.MenuRepository;
import come.rest.menuforyou.util.Utils;

@Service
public class FeedbackService {

	@Autowired
	private DishRepository dishRepo;

	@Autowired
	private FeedbackRepo feedbackRepo;

	@Autowired
	private MenuRepository menuRepo;

	@Transactional(readOnly = false)
	public void createFeedback(long idDish, Feedback feedbackToCreate) {

		Dish dishDb = dishRepo.findOne(Long.valueOf(idDish));
		feedbackToCreate.setDish(dishDb);
		feedbackRepo.save(feedbackToCreate);

	}

	@Transactional(readOnly = true)
	public List<Dish> listDishesWithFeedback(long idMenu, EnumLanguage language) {

		Menu menu = menuRepo.findOne(Long.valueOf(idMenu));
		Utils.checkPermission(menu);
		List<Dish> dishesWithFeedbacks = new ArrayList<Dish>();
		List<Dish> dishes = dishRepo.findByMenuId(Long.valueOf(idMenu));
		for (Dish dish : dishes) {
			if (!dish.getFeedbacks().isEmpty()) {
				dish.mapCustomFields(language);
				dishesWithFeedbacks.add(dish);
			}
		}
		return dishesWithFeedbacks;
	}

}
