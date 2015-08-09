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
import com.rest.menuforyou.domain.Ingredient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
public class IngredientControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void testGetOneIngredient() throws Exception {

		mockMvc.perform(get("/ingredients/1?language=IT")).
				andExpect(status().isOk()).
				andExpect(jsonPath("$.id").value(1)).
				andExpect(jsonPath("$.description").value("Burro"));
	}

	@Test
	public void testGetListIngredients() throws Exception {

		mockMvc.perform(get("/menus/1/ingredients?language=EN")).
				andExpect(status().isOk()).
				andExpect(jsonPath("$", hasSize(4))).
				andExpect(jsonPath("$[0].id").value(1)).
				andExpect(jsonPath("$[0].description").value("Butter")).
				andExpect(jsonPath("$[1].id").value(2)).
				andExpect(jsonPath("$[1].description").value("Sugar")).
				andExpect(jsonPath("$[2].id").value(3)).
				andExpect(jsonPath("$[2].description").value("Eggs")).
				andExpect(jsonPath("$[3].id").value(4)).
				andExpect(jsonPath("$[3].description").value("Salt"));

	}

	@Test
	public void testCreateIngredient() throws Exception {
		Ingredient ingredient = new Ingredient();
		ingredient.setDescription("Farina");

		String ingredientJson = json(ingredient);
		this.mockMvc.perform(post("/menus/1/ingredients/?language=IT")
				.contentType(contentType)
				.content(ingredientJson))
				.andExpect(status().isCreated());
	}

	@Test
	public void testUpdateIngredient() throws Exception {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(Long.valueOf(2));
		ingredient.setDescription("Sugar Modified");

		String ingredientJson = json(ingredient);
		this.mockMvc.perform(post("/menus/1/ingredients/?language=EN")
				.contentType(contentType)
				.content(ingredientJson))
				.andExpect(status().isCreated());
	}

}
