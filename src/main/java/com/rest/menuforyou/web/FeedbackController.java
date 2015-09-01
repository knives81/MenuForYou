package com.rest.menuforyou.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rest.menuforyou.domain.Dish;
import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Feedback;
import com.rest.menuforyou.domain.Views;
import com.rest.menuforyou.error.ResourceNotFoundException;
import com.rest.menuforyou.error.SaveException;
import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.FeedbackService;

@RestController
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/dishes/{id}/feedbacks", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public JsonOk saveFeedback(@PathVariable long id, @RequestBody Feedback feedback) {
		try {
			feedbackService.createFeedback(id, feedback);
			return new JsonOk();
		} catch (Exception e) {
			throw new SaveException("Exception Feedback save", e);
		}
	}

	@RequestMapping(value = "/menus/{id}/feedbacks", method = RequestMethod.GET)
	public String listDish(@PathVariable long id, @RequestParam("language") EnumLanguage language) throws JsonProcessingException {
		try {
			List<Dish> dishes = (List<Dish>) feedbackService.listDishesWithFeedback(id, language);
			ObjectWriter objectWriter = objectMapper.writerWithView(Views.ViewWithFeedback.class);
			return objectWriter.writeValueAsString(dishes);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Exception Feedback load", e);
		}

	}

}
