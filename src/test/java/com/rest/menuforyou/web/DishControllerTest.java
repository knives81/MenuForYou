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
import com.rest.menuforyou.databuilder.DishBuilder;
import com.rest.menuforyou.databuilder.IngredientBuilder;
import com.rest.menuforyou.databuilder.TestConst;
import com.rest.menuforyou.databuilder.TypedishBuilder;
import com.rest.menuforyou.domain.Dish;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DishControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mapper = new ObjectMapper();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void test1GetOneDish() throws Exception {

		int dishId = 4000;
		int typedishId = 3000;

		mockMvc.perform(get("/dishes/" + dishId + TestConst.IT))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(dishId))
				.andExpect(jsonPath("$.description").value("Lasagna al sugo"))
				.andExpect(jsonPath("$.price").value(15.0))
				.andExpect(jsonPath("$.typedish.id").value(typedishId))
				.andExpect(jsonPath("$.typedish.description").value("Pasta IT"))
				.andExpect(jsonPath("$.ingredients", hasSize(2)));
	}

	@Test
	public void test2GetListDishes() throws Exception {

		mockMvc.perform(get("/menus/" + TestConst.MENU_ID + "/dishes" + TestConst.IT)).
				andExpect(status().isOk()).
				andExpect(jsonPath("$", hasSize(3))).
				andExpect(jsonPath("$[0].id").value(4000)).
				andExpect(jsonPath("$[0].description").value("Lasagna al sugo")).
				andExpect(jsonPath("$[0].ingredients", hasSize(2))).
				andExpect(jsonPath("$[1].id").value(4001)).
				andExpect(jsonPath("$[1].description").value("Panna Cotta alle fragole")).
				andExpect(jsonPath("$[1].ingredients", hasSize(0))).
				andExpect(jsonPath("$[2].id").value(4002)).
				andExpect(jsonPath("$[2].description").value("Bistecca")).
				andExpect(jsonPath("$[2].ingredients", hasSize(1)));

	}

	@Test
	public void test3CreateDishes() throws Exception {

		int dishId = 4003;
		String dishDesc = "Lasagna con pomodoro e mozzarella";

		List<Dish> dishes = Arrays.asList(
				DishBuilder.dish().
						withDesc(dishDesc).
						withName("Lasagna").
						withPrice("10.5").
						withTypedish(
								TypedishBuilder.typedish().
										withId(3000).
										build()).
						addIngredient(
								IngredientBuilder.ingredient().
										withId(2000).
										build()).
						build());

		String dishesJson = json(dishes);
		mockMvc.perform(post("/menus/" + TestConst.MENU_ID + "/dishes/" + TestConst.IT)
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE))
				.contentType(contentType)
				.content(dishesJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$[0].id").value(dishId))
				.andExpect(jsonPath("$[0].description").value(dishDesc))
				.andExpect(jsonPath("$[0].order").value(13));

	}

	@Test
	public void test4UpdateDishes() throws Exception {

		int dishId = 4000;
		int order = 1;
		String price = "11.5";

		List<Dish> dishes = Arrays.asList(
				DishBuilder.dish().
						withId(dishId).
						withPrice(price).
						withOrder(order).
						withTypedish(
								TypedishBuilder.typedish().
										withId(3000).
										build()).
						addIngredient(
								IngredientBuilder.ingredient().
										withId(2001).
										build()).
						addIngredient(
								IngredientBuilder.ingredient().
										withId(2000).
										build()).
						build());

		String dishesJson = json(dishes);
		this.mockMvc.perform(put("/dishes/" + TestConst.IT)
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE))
				.contentType(contentType)
				.content(dishesJson))
				.andExpect(jsonPath("$[0].id").value(dishId))
				.andExpect(jsonPath("$[0].order").value(order))
				.andExpect(jsonPath("$[0].price").value(Double.valueOf(price)));
	}

	@Test
	public void test5Update2Dishes() throws Exception {

		int dishId = 4000;
		String dishDesc = "Gnocco Language Modified";
		String dishName = "Gnocco Modified";
		int ingredientId1 = 1;
		int ingredientId2 = 2;
		int typedishId = 3002;
		int order = 1;

		List<Dish> dishes = Arrays.asList(
				DishBuilder.dish().
						withId(dishId).
						withName(dishName).
						withDesc(dishDesc).
						withOrder(order).
						withTypedish(
								TypedishBuilder.typedish().
										withId(typedishId).
										build()).
						addIngredient(
								IngredientBuilder.ingredient().
										withId(ingredientId1).
										build()).
						addIngredient(
								IngredientBuilder.ingredient().
										withId(ingredientId2).
										build()).
						build());

		String dishesJson = json(dishes);
		mockMvc.perform(put("/dishes/" + TestConst.EN)
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE))
				.contentType(contentType)
				.content(dishesJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$[0].id").value(dishId))
				.andExpect(jsonPath("$[0].order").value(order))
				.andExpect(jsonPath("$[0].description").value(dishDesc))
				.andExpect(jsonPath("$[0].typedish.id").value(typedishId))
				.andExpect(jsonPath("$[0].ingredients[0].id").value(ingredientId1))
				.andExpect(jsonPath("$[0].ingredients[1].id").value(ingredientId2));

	}

	@Test
	public void test6DeleteDish() throws Exception {

		int dishId = 4001;

		mockMvc.perform(delete("/dishes/" + dishId)
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE)))
				.andExpect(status().isOk());
	}
}
