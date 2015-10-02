package com.rest.menuforyou.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.menuforyou.MenuForYouApplication;
import com.rest.menuforyou.databuilder.TestConst;
import com.rest.menuforyou.databuilder.TypedishBuilder;
import com.rest.menuforyou.domain.Typedish;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TypedishControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mapper = new ObjectMapper();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void test1GetOneTypedish() throws Exception {

		int typedishId = 3000;
		int dishId = 4000;
		String typedishDesc = "Pasta EN";

		mockMvc.perform(get("/typedishes/" + typedishId + TestConst.EN)).
				andExpect(status().isOk()).
				andExpect(jsonPath("$.id").value(typedishId)).
				andExpect(jsonPath("$.description").value(typedishDesc)).
				andExpect(jsonPath("$..dishes[0].id").value(dishId));
	}

	@Test
	public void test2GetListTypedishes() throws Exception {

		mockMvc.perform(get("/menus/" + TestConst.MENU_ID + "/typedishes" + TestConst.IT)).
				andExpect(status().isOk()).
				andExpect(jsonPath("$", hasSize(3))).
				andExpect(jsonPath("$[0].id").value(3000)).
				andExpect(jsonPath("$[0].description").value("Pasta IT")).
				andExpect(jsonPath("$[1].id").value(3001)).
				andExpect(jsonPath("$[1].description").value("Dolci IT")).
				andExpect(jsonPath("$[2].id").value(3002)).
				andExpect(jsonPath("$[2].description").value("Antipasto IT"));

	}

	@Test
	public void test3CreateTypedishes() throws Exception {

		int typedishId = 3003;
		String typedishDesc = "PizzaWrong";

		List<Typedish> typedishes = Arrays.asList(
				TypedishBuilder.typedish().
						withDesc(typedishDesc).
						build());

		String typedishesJson = json(typedishes);
		mockMvc.perform(post("/menus/" + TestConst.MENU_ID + "/typedishes/" + TestConst.IT)
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE))
				.contentType(contentType)
				.content(typedishesJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$[0].id").value(typedishId))
				.andExpect(jsonPath("$[0].description").value(typedishDesc))
				.andExpect(jsonPath("$[0].order").value(5011));

	}

	@Test
	public void test4UpdateTypedishes() throws Exception {

		int typedishId = 3001;
		String typedishDesc = "Appetizer EN Modified";

		List<Typedish> typedishes = Arrays.asList(
				TypedishBuilder.typedish().
						withId(typedishId).
						withDesc(typedishDesc).
						build());

		String typedishesJson = json(typedishes);

		mockMvc.perform(put("/typedishes/" + TestConst.EN)
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE))
				.contentType(contentType)
				.content(typedishesJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$[0].id").value(typedishId))
				.andExpect(jsonPath("$[0].description").value(typedishDesc))
				.andExpect(jsonPath("$[0].order").value(5001));

	}

	@Test
	public void test5DeleteTypedish() throws Exception {

		int typedishId = 3002;

		mockMvc.perform(delete("/typedishes/" + typedishId)
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE)))
				.andExpect(status().isOk());
		// TODO test get id deleted
	}

}
