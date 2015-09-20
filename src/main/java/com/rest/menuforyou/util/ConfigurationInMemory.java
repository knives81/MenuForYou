package com.rest.menuforyou.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rest.menuforyou.domain.Parameter;

public class ConfigurationInMemory implements Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<String, String> parametersInMemory = new HashMap<String, String>();

	public ConfigurationInMemory() {
	}

	public ConfigurationInMemory(List<Parameter> parametersDb) {
		for (Parameter parameterDb : parametersDb) {
			parametersInMemory.put(parameterDb.getName(), parameterDb.getValue());
		}
	}

	public String getValue(String parameterName) {
		return parametersInMemory.get(parameterName);
	}

	public static ConfigurationInMemory merge(ConfigurationInMemory root, ConfigurationInMemory menu) {
		ConfigurationInMemory merged = new ConfigurationInMemory();
		for (Map.Entry<String, String> entry : root.parametersInMemory.entrySet()) {
			merged.parametersInMemory.put(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<String, String> entry : menu.parametersInMemory.entrySet()) {
			merged.parametersInMemory.put(entry.getKey(), entry.getValue());
		}
		return merged;
	}

	public List<Parameter> getParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();
		for (Map.Entry<String, String> entry : parametersInMemory.entrySet()) {
			Parameter parameter = new Parameter();
			parameter.setName(entry.getKey());
			parameter.setValue(entry.getValue());
			parameters.add(parameter);
		}

		return parameters;
	}

}
