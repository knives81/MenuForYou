package com.rest.menuforyou.web;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

public class BaseTest {

	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	protected MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	protected HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		List<HttpMessageConverter<?>> converterList = Arrays.asList(converters);
		for (HttpMessageConverter<?> converter : converterList) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				this.mappingJackson2HttpMessageConverter = converter;
			}
		}
		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(
				o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
