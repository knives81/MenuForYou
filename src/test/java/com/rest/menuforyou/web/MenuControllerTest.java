package com.rest.menuforyou.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.menuforyou.MenuForYouApplication;
import com.rest.menuforyou.databuilder.TestConst;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MenuControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mapper = new ObjectMapper();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void test1GetMenu() throws Exception {

		int restaurantId = 1;

		mockMvc.perform(get("/restaurantmenus/" + restaurantId + TestConst.IT))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.typedishes", hasSize(2)))
				.andExpect(jsonPath("$.parameters", hasSize(4)))
				.andExpect(jsonPath("$.restaurant.id").value(1));

		mockMvc.perform(get("/stats"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.typedishCache").value(1))
				.andExpect(jsonPath("$.parameterCache").value(2))
				.andExpect(jsonPath("$.restaurantCache").value(1));

		mockMvc.perform(get("/restaurantmenus/" + restaurantId + TestConst.EN))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.typedishes", hasSize(2)))
				.andExpect(jsonPath("$.parameters", hasSize(4)))
				.andExpect(jsonPath("$.restaurant.id").value(1));

		mockMvc.perform(get("/stats"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.typedishCache").value(2))
				.andExpect(jsonPath("$.parameterCache").value(2))
				.andExpect(jsonPath("$.restaurantCache").value(1));

	}
}
