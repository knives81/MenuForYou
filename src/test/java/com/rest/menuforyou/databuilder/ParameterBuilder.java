package com.rest.menuforyou.databuilder;

import com.rest.menuforyou.domain.Parameter;

public class ParameterBuilder {
	Parameter parameter = new Parameter();

	public static ParameterBuilder parameter()
	{
		return new ParameterBuilder();
	}

	public Parameter build() {
		return parameter;
	}

	public ParameterBuilder withName(String name) {
		parameter.setName(name);
		return this;
	}

	public ParameterBuilder withValue(String value) {
		parameter.setValue(value);
		return this;
	}

}
