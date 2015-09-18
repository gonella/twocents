package com.twocents.model;

public enum ConfigurationGroupType {

	
	GENERAL("Geral"),
	NETWORK("Conectividade"),
	BROKER("Corretoras");
	
	private String Type;
	
	private ConfigurationGroupType(String Type) {
		this.Type = Type;
	}
	
	public String toString(){
		return this.Type;
	}
}
