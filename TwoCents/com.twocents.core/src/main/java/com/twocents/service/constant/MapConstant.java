package com.twocents.service.constant;

public enum MapConstant {
	
	STOCK_CODE("stockCode"), 
	QUOTE_PRICE("quotePrice");
	
	private final String key;

	private MapConstant(String key){
		this.key = key;
		
	}
	
	public String toString(){
		return key;
	}
}
