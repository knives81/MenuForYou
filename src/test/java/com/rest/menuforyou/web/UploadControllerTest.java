package com.rest.menuforyou.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rest.menuforyou.MenuForYouApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MenuForYouApplication.class)
@WebAppConfiguration
public class UploadControllerTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void testUpload() throws Exception {

		Resource picture = new ClassPathResource("/img/foto.JPG");
		MockMultipartFile multipartFile = new MockMultipartFile("file", picture.getInputStream());

		mockMvc.perform(fileUpload("/uploadDishImage?id=1").
				file(multipartFile)).
				andExpect(status().isOk());
	}

}
