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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.menuforyou.MenuForYouApplication;
import com.rest.menuforyou.databuilder.DishBuilder;
import com.rest.menuforyou.databuilder.IngredientBuilder;
import com.rest.menuforyou.databuilder.TypedishBuilder;
import com.rest.menuforyou.domain.Dish;
import com.rest.menuforyou.response.JsonOk;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
public class DishControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mapper = new ObjectMapper();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void testGetOneDish() throws Exception {

		mockMvc.perform(get("/dishes/1?language=IT")).
				andExpect(status().isOk()).
				andExpect(jsonPath("$.id").value(1)).
				andExpect(jsonPath("$.description").value("Lasagna al sugo")).
				andExpect(jsonPath("$.price").value(15.0)).
				andExpect(jsonPath("$.typedish.id").value(1)).
				andExpect(jsonPath("$.typedish.description").value("Pasta IT")).
				andExpect(jsonPath("$.ingredients", hasSize(2)));
	}

	@Test
	public void testGetListDishes() throws Exception {

		mockMvc.perform(get("/menus/1/dishes?language=IT")).
				andExpect(status().isOk()).
				andExpect(jsonPath("$", hasSize(3))).
				andExpect(jsonPath("$[0].id").value(1)).
				andExpect(jsonPath("$[0].description").value("Lasagna al sugo")).
				andExpect(jsonPath("$[0].ingredients", hasSize(2))).
				andExpect(jsonPath("$[1].id").value(2)).
				andExpect(jsonPath("$[1].description").value("Panna Cotta alle fragole")).
				andExpect(jsonPath("$[1].ingredients", hasSize(0))).
				andExpect(jsonPath("$[2].id").value(3)).
				andExpect(jsonPath("$[2].description").value("Bistecca")).
				andExpect(jsonPath("$[2].ingredients", hasSize(1)));

	}

	@Test
	public void testCreateDishes() throws Exception {

		List<Dish> dishes = Arrays.asList(
				DishBuilder.dish().
						withDesc("Lasagna con pomodoro e mozzarella").
						withName("Lasagna").
						withPrice("10.5").
						withTypedish(
								TypedishBuilder.typedish().
										withId(1).
										build()).
						addIngredient(
								IngredientBuilder.ingredient().
										withId(1).
										build()).
						build());

		String dishesJson = json(dishes);
		MvcResult result = mockMvc.perform(post("/menus/1/dishes/?language=IT")
				.with(user("maurizio01").roles("ADMIN"))
				.contentType(contentType)
				.content(dishesJson))
				.andExpect(status().isCreated())
				.andReturn();

		JsonOk jsonOk = mapper.readValue(result.getResponse().getContentAsString(), JsonOk.class);
		Long id = jsonOk.getIds().get(0);

		mockMvc.perform(get("/dishes/" + id + "?language=IT"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id.intValue()))
				.andExpect(jsonPath("$.description").value("Lasagna con pomodoro e mozzarella"));
	}

	@Test
	public void testUpdateDishes() throws Exception {

		List<Dish> dishes = Arrays.asList(
				DishBuilder.dish().
						withId(1).
						withPrice("11.5").
						withOrder(1).
						withTypedish(
								TypedishBuilder.typedish().
										withId(1).
										build()).
						addIngredient(
								IngredientBuilder.ingredient().
										withId(2).
										build()).
						addIngredient(
								IngredientBuilder.ingredient().
										withId(1).
										build()).
						build());

		String dishesJson = json(dishes);
		this.mockMvc.perform(put("/dishes/?language=IT")
				.with(user("maurizio01").roles("ADMIN"))
				.contentType(contentType)
				.content(dishesJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.type").value("success"))
				.andExpect(jsonPath("$.ids", hasSize(1)));
	}

	@Test
	public void testUpdate2Dishes() throws Exception {
		List<Dish> dishes = Arrays.asList(
				DishBuilder.dish().
						withId(1).
						withName("Gnocco Modified").
						withDesc("Gnocco Language Modified").
						withOrder(1).
						withTypedish(
								TypedishBuilder.typedish().
										withId(2).
										build()).
						addIngredient(
								IngredientBuilder.ingredient().
										withId(3).
										build()).
						addIngredient(
								IngredientBuilder.ingredient().
										withId(4).
										build()).
						build());

		String dishesJson = json(dishes);
		mockMvc.perform(put("dishes/?language=IT")
				.with(user("maurizio01").roles("ADMIN"))
				.contentType(contentType)
				.content(dishesJson))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/dishes/1?language=IT")).
				andExpect(status().isOk()).
				andExpect(jsonPath("$.id").value(1)).
				andExpect(jsonPath("$.order").value(1)).
				andExpect(jsonPath("$.description").value("Gnocco Language Modified")).
				andExpect(jsonPath("$.typedish.id").value(2)).
				andExpect(jsonPath("$.ingredients[0].id").value(3)).
				andExpect(jsonPath("$.ingredients[1].id").value(4));
	}

	@Test
	public void testDeleteDish() throws Exception {

		mockMvc.perform(delete("/dishes/1")
				.with(user("maurizio01").roles("ADMIN")))
				.andExpect(status().isOk());
	}
}
