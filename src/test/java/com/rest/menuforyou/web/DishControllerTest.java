package com.rest.menuforyou.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rest.menuforyou.MenuForYouApplication;
import com.rest.menuforyou.domain.Dish;
import com.rest.menuforyou.domain.Ingredient;
import com.rest.menuforyou.domain.Typedish;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
public class DishControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
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
		Dish dish = new Dish();
		dish.setDescription("Lasagna con pomodoro e mozzarella");
		dish.setName("Lasagna");
		dish.setPrice(Float.valueOf("10.5"));
		Typedish typedish = new Typedish();
		typedish.setId(Long.valueOf(1));
		dish.setTypedish(typedish);
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(Long.valueOf(1));
		Set<Ingredient> ingredients = new HashSet<Ingredient>();
		ingredients.add(ingredient1);
		dish.setIngredients(ingredients);

		String dishJson = json(dish);
		this.mockMvc.perform(post("/menus/1/dishes/?language=IT")
				.contentType(contentType)
				.content(dishJson))
				.andExpect(status().isCreated());
	}

	@Test
	public void testUpdateDishes() throws Exception {
		Dish dish = new Dish();
		dish.setId(Long.valueOf(1));
		dish.setPrice(Float.valueOf("11.5"));
		Typedish typedish = new Typedish();
		typedish.setId(Long.valueOf(1));
		dish.setTypedish(typedish);
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(Long.valueOf(2));
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(Long.valueOf(1));
		Set<Ingredient> ingredients = new HashSet<Ingredient>();
		ingredients.add(ingredient1);
		ingredients.add(ingredient2);
		dish.setIngredients(ingredients);

		String dishJson = json(dish);
		this.mockMvc.perform(post("/menus/1/dishes/?language=IT")
				.contentType(contentType)
				.content(dishJson))
				.andExpect(status().isCreated());
	}

	@Test
	public void testUpdate2Dishes() throws Exception {
		Dish dish = new Dish();
		dish.setId(Long.valueOf(1));
		dish.setName("Gnocco Modified");
		dish.setDescription("Gnocco Language Modified");
		Typedish typedish = new Typedish();
		typedish.setId(Long.valueOf(2));
		dish.setTypedish(typedish);
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(Long.valueOf(3));
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(Long.valueOf(4));
		Set<Ingredient> ingredients = new HashSet<Ingredient>();
		ingredients.add(ingredient1);
		ingredients.add(ingredient2);
		dish.setIngredients(ingredients);

		String dishJson = json(dish);
		this.mockMvc.perform(post("/menus/1/dishes/?language=IT")
				.contentType(contentType)
				.content(dishJson))
				.andExpect(status().isCreated());
	}

}
