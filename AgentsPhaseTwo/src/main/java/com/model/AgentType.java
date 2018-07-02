package com.model;

import java.io.Serializable;

public class AgentType implements Serializable{
	private String module;
	private String name;
	
	public AgentType(){}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public AgentType(String module, String name) {
		super();
		this.module = module;
		this.name = name;
	}

	@Override
	public String toString() {
		return "AgentType [module=" + module + ", name=" + name + "]";
	}
	
}
