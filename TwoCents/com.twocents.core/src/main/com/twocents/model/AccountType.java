package com.twocents.model;

public enum AccountType {
	
	CC("Conta Corrente"),
	CI("Conta de Investimento");

	
	private String Type;
	
	private AccountType(String Type) {
		this.Type = Type;
	}
	
	public String toString(){
		return this.Type;
	}
}
