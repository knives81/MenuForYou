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
import com.rest.menuforyou.databuilder.TypedishBuilder;
import com.rest.menuforyou.domain.Typedish;
import com.rest.menuforyou.response.JsonOk;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
public class TypedishControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mapper = new ObjectMapper();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void testGetOneTypedish() throws Exception {

		mockMvc.perform(get("/typedishes/1?language=EN")).
				andExpect(status().isOk()).
				andExpect(jsonPath("$.id").value(1)).
				andExpect(jsonPath("$.description").value("Pasta EN")).
				andExpect(jsonPath("$..dishes[0].id").value(1));
	}

	@Test
	public void testGetListTypedishes() throws Exception {

		mockMvc.perform(get("/menus/1/typedishes?language=IT")).
				andExpect(status().isOk()).
				andExpect(jsonPath("$", hasSize(3))).
				andExpect(jsonPath("$[0].id").value(1)).
				andExpect(jsonPath("$[0].description").value("Pasta IT")).
				andExpect(jsonPath("$[1].id").value(2)).
				andExpect(jsonPath("$[1].description").value("Dolci IT")).
				andExpect(jsonPath("$[2].id").value(3)).
				andExpect(jsonPath("$[2].description").value("Antipasto IT"));

	}

	@Test
	public void testCreateTypedishes() throws Exception {

		List<Typedish> typedishes = Arrays.asList(
				TypedishBuilder.typedish().
						withDesc("PizzaWrong").
						build());

		String typedishesJson = json(typedishes);
		MvcResult result = mockMvc.perform(post("/menus/1/typedishes/?language=IT")
				.with(user("maurizio01").roles("ADMIN"))
				.contentType(contentType)
				.content(typedishesJson))
				.andExpect(status().isCreated())
				.andReturn();

		JsonOk jsonOk = mapper.readValue(result.getResponse().getContentAsString(), JsonOk.class);
		Long id = jsonOk.getIds().get(0);

		mockMvc.perform(get("/typedishes/" + id + "?language=IT"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id.intValue()))
				.andExpect(jsonPath("$.description").value("PizzaWrong"));

	}

	@Test
	public void testUpdateTypedishes() throws Exception {

		List<Typedish> typedishes = Arrays.asList(
				TypedishBuilder.typedish().
						withId(3).
						withDesc("Appetizer EN Modified").
						build());

		String typedishesJson = json(typedishes);

		mockMvc.perform(put("/typedishes/?language=IT")
				.with(user("maurizio01").roles("ADMIN"))
				.contentType(contentType)
				.content(typedishesJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.type").value("success"))
				.andExpect(jsonPath("$.ids", hasSize(1)));

	}

	@Test
	public void testDeleteTypedish() throws Exception {

		mockMvc.perform(delete("/typedishes/3")
				.with(user("maurizio01").roles("ADMIN")))
				.andExpect(status().isOk());
		// TODO test get id deleted
	}

}
