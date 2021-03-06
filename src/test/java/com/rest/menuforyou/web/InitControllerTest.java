package com.rest.menuforyou.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rest.menuforyou.MenuForYouApplication;
import com.rest.menuforyou.databuilder.TestConst;
import com.rest.menuforyou.databuilder.UserBuilder;
import com.rest.menuforyou.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
public class InitControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testCreateUser() throws Exception {

		int menuJustCreatedId = 1001;
		User user = UserBuilder.user()
				.withUsername("maurizio03")
				.withPassword("password")
				.isEnabled()
				.build();

		String userJson = json(user);
		mockMvc.perform(post("/users")
				.contentType(contentType)
				.content(userJson))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/menus/" + menuJustCreatedId + "/parameters"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)));

		mockMvc.perform(get("/menus/" + menuJustCreatedId + "/typedishes" + TestConst.IT))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].description").value("Insalata"))
				.andExpect(jsonPath("$[0].order").value(5011))
				.andExpect(jsonPath("$[1].description").value("Dolci"))
				.andExpect(jsonPath("$[1].order").value(5012));

	}
}
