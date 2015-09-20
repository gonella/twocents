package com.twocents.model;

public enum ChartType {
	
	PIE("Pizza"),
	BAR("Barras");

	
	private String Type;
	
	private ChartType(String Type) {
		this.Type = Type;
	}
	
	public String toString(){
		return this.Type;
	}
}
