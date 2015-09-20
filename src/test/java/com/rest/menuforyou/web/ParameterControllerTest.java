package com.rest.menuforyou.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rest.menuforyou.MenuForYouApplication;
import com.rest.menuforyou.databuilder.ParameterBuilder;
import com.rest.menuforyou.databuilder.TestConst;
import com.rest.menuforyou.domain.Parameter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParameterControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void test1UpdateParameter() throws Exception {
		Parameter parameter = ParameterBuilder.parameter()
				.withName("DISPLAY_FEEDBACK")
				.withValue("FALSE")
				.build();

		String parameterJson = json(parameter);

		mockMvc.perform(put("/menus/" + TestConst.MENU_ID + "/parameters")
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE))
				.contentType(contentType)
				.content(parameterJson));
	}

	@Test
	public void test2GetParameters() throws Exception {
		mockMvc.perform(get("/menus/" + TestConst.MENU_ID + "/parameters"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)));

	}

	@Test
	public void test3ForceReload() throws Exception {
		mockMvc.perform(get("/menus/" + TestConst.MENU_ID + "/parameters/forcereload")
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE)))
				.andExpect(status().isOk());

	}
}
