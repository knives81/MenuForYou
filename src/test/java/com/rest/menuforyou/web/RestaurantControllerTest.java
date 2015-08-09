package com.rest.menuforyou.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rest.menuforyou.MenuForYouApplication;
import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.Restaurant;
import com.rest.menuforyou.domain.User;

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
		Restaurant restaurant = new Restaurant();
		restaurant.setName("Ristorante02");
		restaurant.setAddress("via del corso");
		User user = new User();
		user.setUsername("maurizio03");
		user.setPassword("password");
		user.setEnabled(true);
		restaurant.setUser(user);
		Menu menu = new Menu();
		menu.setName("Menu02");
		restaurant.setMenu(menu);

		String restaurantJson = json(restaurant);
		this.mockMvc.perform(post("/restaurants")
				.contentType(contentType)
				.content(restaurantJson))
				.andExpect(status().isCreated());
	}

	// user logged
	@Test
	public void testAddRestaurant() throws Exception {
		Restaurant restaurant = new Restaurant();
		restaurant.setName("Ristorante02 Menu01");
		restaurant.setAddress("via del corso");

		String restaurantJson = json(restaurant);
		this.mockMvc.perform(post("/restaurants")
				.contentType(contentType)
				.content(restaurantJson))
				.andExpect(status().isCreated());
	}

}
