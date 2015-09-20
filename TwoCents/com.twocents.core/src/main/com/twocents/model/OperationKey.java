package com.twocents.model;


public enum OperationKey {
	
	ID("id"),
	DESCRIPTION("description"),
	OPERATION_DATE("operationDate"),
	OPERATION_TYPE("operationType"),
	STOCK_TYPE("stockType"),
	PRICE("price"),
	AMOUNT("amount"),
	STOCK("stock"),
	STOCK_ID("stockId"),
	PROFIT("profit"),
	BROKERAGE("brokerage"),
	BOVESPA_TAX("bovespaTax"),
	BROKER("broker")
	;
	
	private String key;
	
	private OperationKey(String key) {
		this.key = key;
	}
	
	public String toString(){
		return this.key;
	}
}
