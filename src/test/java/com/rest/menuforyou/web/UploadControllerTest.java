package com.rest.menuforyou.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rest.menuforyou.MenuForYouApplication;
import com.rest.menuforyou.databuilder.TestConst;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UploadControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void test1Upload() throws Exception {

		Resource picture = new ClassPathResource("/img/foto.JPG");
		MockMultipartFile multipartFile = new MockMultipartFile("file", picture.getInputStream());

		mockMvc.perform(fileUpload("/uploadDishImage?id=4000")
				.file(multipartFile)
				.with(user(TestConst.USERNAME).roles(TestConst.ROLE)))
				.andExpect(status().isOk());
	}

	@Test
	public void test2UploadNotFound() throws Exception {

		Resource picture = new ClassPathResource("/img/foto.JPG");
		MockMultipartFile multipartFile = new MockMultipartFile("file", picture.getInputStream());

		mockMvc.perform(fileUpload("/uploadDishImage?id=1").
				file(multipartFile)).
				andExpect(status().isOk());
	}

}
