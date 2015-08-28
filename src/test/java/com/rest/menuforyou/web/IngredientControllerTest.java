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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.menuforyou.MenuForYouApplication;
import com.rest.menuforyou.databuilder.IngredientBuilder;
import com.rest.menuforyou.domain.Ingredient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
public class IngredientControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mapper = new ObjectMapper();
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

		List<Ingredient> ingredients = Arrays.asList(
				IngredientBuilder.ingredient().
						withDesc("Farina").
						build());
		String ingredientsJson = json(ingredients);
		mockMvc.perform(post("/menus/1/ingredients/?language=IT")
				.with(user("maurizio01").roles("ADMIN"))
				.contentType(contentType)
				.content(ingredientsJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$[0].id").value(5))
				.andExpect(jsonPath("$[0].description").value("Farina"));

		// JsonOk jsonOk =
		// mapper.readValue(result.getResponse().getContentAsString(),
		// JsonOk.class);
		// Long id = jsonOk.getIds().get(0);
		//
		// mockMvc.perform(get("/ingredients/" + id + "?language=IT"))
		// .andExpect(status().isOk())
		// .andExpect(jsonPath("$.id").value(id.intValue()))
		// .andExpect(jsonPath("$.description").value("Farina"));

	}

	@Test
	public void testUpdateIngredient() throws Exception {

		List<Ingredient> ingredients = Arrays.asList(
				IngredientBuilder.ingredient()
						.withId(1)
						.withDesc("Sugar Modified")
						.build());

		String ingredientsJson = json(ingredients);
		this.mockMvc.perform(put("/ingredients/?language=EN")
				.with(user("maurizio01").roles("ADMIN"))
				.contentType(contentType)
				.content(ingredientsJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.type").value("success"))
				.andExpect(jsonPath("$.ids", hasSize(1)));
	}

	@Test
	public void testDeleteIngredient() throws Exception {

		mockMvc.perform(delete("/ingredients/1")
				.with(user("maurizio01").roles("ADMIN")))
				.andExpect(status().isOk());

		// TODO test get id deleted

	}

}
