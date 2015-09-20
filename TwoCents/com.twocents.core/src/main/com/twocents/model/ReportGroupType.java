package com.twocents.model;

public enum ReportGroupType {
	
	ACCOUNT("Carteira"),
	OPERATION("Operações"),
	TAX("Impostos"),
	PERFORMANCE("Desempenho"),
	BALANCE("Saldo"),	
	TEST("Testes")
	;
	
	private String Type;
	
	private ReportGroupType(String Type) {
		this.Type = Type;
	}
	
	public String toString(){
		return this.Type;
	}
}
