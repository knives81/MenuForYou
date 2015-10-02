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
import com.rest.menuforyou.databuilder.IngredientBuilder;
import com.rest.menuforyou.databuilder.TestConst;
import com.rest.menuforyou.domain.Ingredient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IngredientControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mapper = new ObjectMapper();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void test1GetOneIngredient() throws Exception {

		int ingredientId = 2000;
		String ingredientDesc = "Burro";

		mockMvc.perform(get("/ingredients/" + ingredientId + TestConst.IT)).
				andExpect(status().isOk()).
				andExpect(jsonPath("$.id").value(ingredientId)).
				andExpect(jsonPath("$.description").value(ingredientDesc));
	}

	@Test
	public void test2GetListIngredients() throws Exception {

		mockMvc.perform(get("/menus/" + TestConst.MENU_ID + "/ingredients" + TestConst.EN))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(6)))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].description").value("Common Ingredient"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].description").value("Common Ingredient 2"))
				.andExpect(jsonPath("$[2].id").value(2000))
				.andExpect(jsonPath("$[2].description").value("Butter"))
				.andExpect(jsonPath("$[3].id").value(2001))
				.andExpect(jsonPath("$[3].description").value("Sugar"))
				.andExpect(jsonPath("$[4].id").value(2002))
				.andExpect(jsonPath("$[4].description").value("Eggs"))
				.andExpect(jsonPath("$[5].id").value(2003))
				.andExpect(jsonPath("$[5].description").value("Salt"));
	}

	@Test
	public void test3CreateIngredient() throws Exception {

		int ingredientId = 2004;
		String ingredientDesc = "Farina";

		List<Ingredient> ingredients = Arrays.asList(
				IngredientBuilder.ingredient().
						withDesc(ingredientDesc).
						build());
		String ingredientsJson = json(ingredients);
		mockMvc.perform(post("/menus/" + TestConst.MENU_ID + "/ingredients/" + TestConst.IT)
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE))
				.contentType(contentType)
				.content(ingredientsJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$[0].id").value(ingredientId))
				.andExpect(jsonPath("$[0].description").value(ingredientDesc))
				.andExpect(jsonPath("$[0].order").value(5011));
	}

	@Test
	public void test4DeleteIngredient() throws Exception {

		int ingredientId = 2002;

		mockMvc.perform(delete("/ingredients/" + ingredientId)
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE)))
				.andExpect(status().isOk());
	}

	@Test
	public void test5UpdateIngredient() throws Exception {

		int ingredientId = 2003;
		String ingredientDesc = "Sugar Modified";

		List<Ingredient> ingredients = Arrays.asList(
				IngredientBuilder.ingredient()
						.withId(ingredientId)
						.withDesc(ingredientDesc)
						.build());

		String ingredientsJson = json(ingredients);
		this.mockMvc.perform(put("/ingredients/" + TestConst.EN)
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE))
				.contentType(contentType)
				.content(ingredientsJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$[0].id").value(ingredientId))
				.andExpect(jsonPath("$[0].description").value(ingredientDesc))
				.andExpect(jsonPath("$[0].order").value(5009));
	}

}
