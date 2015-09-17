package com.rest.menuforyou.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rest.menuforyou.MenuForYouApplication;
import com.rest.menuforyou.databuilder.FeedbackBuilder;
import com.rest.menuforyou.domain.Feedback;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FeedbackControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void test1GetListDishesWithFeedback() throws Exception {

		mockMvc.perform(get("/menus/" + TestConst.MENU_ID + "/feedbacks" + TestConst.IT)
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id").value(4001))
				.andExpect(jsonPath("$[0].description").value("Panna Cotta alle fragole"))
				.andExpect(jsonPath("$[0].feedbacks", hasSize(2)))
				.andExpect(jsonPath("$[1].id").value(4002))
				.andExpect(jsonPath("$[1].description").value("Bistecca"))
				.andExpect(jsonPath("$[1].feedbacks", hasSize(1)));

	}

	@Test
	public void test2CreateFeedback() throws Exception {

		int dishId = 4000;

		Feedback feedback = FeedbackBuilder.feedback()
				.withMessage("Too salty")
				.withRating(Long.valueOf("5"))
				.build();

		String feedbackJson = json(feedback);
		mockMvc.perform(post("/dishes/" + dishId + "/feedbacks")
				.contentType(contentType)
				.content(feedbackJson))
				.andExpect(status().isCreated());

	}

	@Test
	public void test3CreateFeedbackBadRequest() throws Exception {

		int dishId = 4000;

		Feedback feedback = FeedbackBuilder.feedback()
				.withMessage("Too salty")
				.build();

		String feedbackJson = json(feedback);
		mockMvc.perform(post("/dishes/" + dishId + "/feedbacks")
				.contentType(contentType)
				.content(feedbackJson))
				.andExpect(status().isBadRequest());

	}

}
