package com.rest.menuforyou.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.menuforyou.domain.Parameter;
import com.rest.menuforyou.error.SaveException;
import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.ParameterService;

@RestController
public class ParameterController {

	@Autowired
	private ParameterService parameterService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/menus/{id}/parameters", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public JsonOk updateParameter(@PathVariable long id, @RequestBody Parameter parameter) {
		try {
			parameterService.updateParameter(id, parameter);
			return new JsonOk();
		} catch (Exception e) {
			throw new SaveException("Exception Parameter update", e);
		}
	}

}
