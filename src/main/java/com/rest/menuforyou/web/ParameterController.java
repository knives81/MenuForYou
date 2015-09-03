package com.rest.menuforyou.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rest.menuforyou.domain.Parameter;
import com.rest.menuforyou.error.SaveException;
import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.ParameterService;
import come.rest.menuforyou.util.ConfigurationIdentityMap;
import come.rest.menuforyou.util.ConfigurationInMemory;

@RestController
public class ParameterController {

	@Autowired
	private ParameterService parameterService;

	@RequestMapping(value = "/menus/{id}/parameters", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public JsonOk updateParameter(@PathVariable long id, @RequestBody Parameter parameter) {
		try {
			parameterService.updateParameter(id, parameter);
			ConfigurationIdentityMap.getInstance().loadParametersInMemory(id);
			return new JsonOk();
		} catch (Exception e) {
			throw new SaveException("Exception Parameter update", e);
		}
	}

	@RequestMapping(value = "/menus/{id}/parameters/forcereload", method = RequestMethod.GET)
	public JsonOk forceReloadParameters(@PathVariable long id) {
		try {
			parameterService.checkPermission(id);
			ConfigurationIdentityMap.getInstance().loadParametersInMemory(id);
			return new JsonOk();

		} catch (Exception e) {
			throw new SaveException("Exception Parameter get", e);
		}
	}

	@RequestMapping(value = "/menus/{id}/parameters", method = RequestMethod.GET)
	public ConfigurationInMemory getParameters(@PathVariable long id) {
		try {
			return parameterService.getParameters(id);
		} catch (Exception e) {
			throw new SaveException("Exception Parameter get", e);
		}
	}

}
