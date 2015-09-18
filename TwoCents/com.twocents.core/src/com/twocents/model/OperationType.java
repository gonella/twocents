package com.twocents.model;

public enum OperationType {
	BUY("BUY"),
	SELL("SELL"),
	DEBIT("DEBIT"),
	CREDIT("CREDIT"),
	SELL_CALL_OPTION("SELL_CALL_OPTION");
	
	private static final String VENDA = "VENDA";
	private static final String COMPRA = "COMPRA";
	private String Operation;
	
	private OperationType(String Operation) {
		this.Operation = Operation;
	}
	
	public String toString(){
		return this.Operation;
	}

	public static OperationType toType(String str){
		OperationType type=null;
		OperationType[] values = values();
		
		for (OperationType operationType : values) {
			if(operationType.toString().equalsIgnoreCase(str)){
				type = operationType;
			}
		}
		return type;
	}
	
	public StockOperation getInstanceOperation(){
		
		if(this.toString().equals(OperationType.BUY.toString())){
			return new Buy();
		}else if(this.toString().equals(OperationType.SELL.toString())){
			return new Sell();
		} 
		else{
			return null;
		}
		
	}
	
	public static OperationType convert(String str){
		
		if(COMPRA.equalsIgnoreCase(str)){
			return OperationType.BUY;
		}
		else if(VENDA.equalsIgnoreCase(str)){
			return OperationType.SELL;
		}
		else{
			return null;
		}
	}
	
	public static String convert(OperationType type){
		
		if(BUY.equals(type)){
			return COMPRA;
		}
		else if(SELL.equals(type)){
			return VENDA;
		}
		return null;
	}
	
}
