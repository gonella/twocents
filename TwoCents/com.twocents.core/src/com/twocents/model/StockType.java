package com.twocents.model;

public enum StockType {
	OPTION("Opção"),
	SHARE("Ação");
	
	private String type;
	
	private StockType(String type) {
		this.type = type;
	}
	
	public String toString(){
		return this.type;
	}

	public static StockType toType(String str){
		StockType type=null;
		StockType[] values = values();
		
		for (StockType stockType : values) {
			if(stockType.toString().equals(str)){
				type = stockType;
			}
		}
		return type;
	}
}
