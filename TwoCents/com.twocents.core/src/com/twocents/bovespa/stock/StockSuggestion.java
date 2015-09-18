package com.twocents.bovespa.stock;

public class StockSuggestion {

	private String name;
	private String code;
	private String type;
	public StockSuggestion(String name, String code, String type) {
		super();
		this.name = name;
		this.code = code;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String toString(){
		return getCode()+"["+getType()+"]"+" - "+ getName();
	}
	
}
