package com.rest.menuforyou.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rest.menuforyou.MenuForYouApplication;
import com.rest.menuforyou.databuilder.RestaurantBuilder;
import com.rest.menuforyou.domain.Restaurant;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
public class RestaurantControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void testCreateRestaurant() throws Exception {

		Restaurant restaurant = RestaurantBuilder.restaurant()
				.withName("Ristorante02")
				.withAddress("via del corso")
				.build();

		String restaurantJson = json(restaurant);
		mockMvc.perform(post("/restaurants")
				.with(user("maurizio01").roles("ADMIN"))
				.contentType(contentType)
				.content(restaurantJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.name").value("Ristorante02"))
				.andExpect(jsonPath("$.address").value("via del corso"));
	}

	@Test
	public void testUpdateRestaurant() throws Exception {

		Restaurant restaurant = RestaurantBuilder.restaurant()
				.withId(1)
				.withName("Ristorante02 Menu01")
				.withAddress("via del babbuino")
				.withImageUrl("www.google.it/img.jpg")
				.build();

		String restaurantJson = json(restaurant);
		MvcResult result = mockMvc.perform(put("/restaurants")
				.with(user("maurizio01").roles("ADMIN"))
				.contentType(contentType)
				.content(restaurantJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Ristorante02 Menu01"))
				.andExpect(jsonPath("$.address").value("via del babbuino"))
				.andExpect(jsonPath("$.imageUrl").value("www.google.it/img.jpg"))
				.andExpect(jsonPath("$.menu.name").value("menu01"))
				.andReturn();

		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testGetAllRestaurants() throws Exception {

		mockMvc.perform(get("/restaurants")).
				andExpect(status().isOk()).
				andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void testGetOneRestaurants() throws Exception {

		mockMvc.perform(get("/restaurants/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Restaurant01"))
				.andExpect(jsonPath("$.address").value("viale trastevere"))
				.andExpect(jsonPath("$.menu.name").value("menu01"));
	}

}
