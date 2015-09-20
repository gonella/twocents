package com.twocents.model;



public enum FinancialSummaryType {
	
	//RESUMO DOS NEGOCIOS
	VALOR_DAS_OPERACOES("VALOR_DAS_OPERACOES"),
	
	//RESUMO FINANCEIRO
	//TOTAL CBLC
	//Diferença entre compra e venda
	VALOR_LIQUIDO_DAS_OPERACOES("VALOR_LIQUIDO_DAS_OPERACOES"),
	TAXA_LIQUIDACAO("TAXA_LIQUIDACAO"),
	TAXA_REGISTRO("TAXA_REGISTRO"),
	
	//TOTAL BOVESPA
	TAXA_DE_TERMO_OPCOES("TAXA_DE_TERMO_OPCOES"), 	//TODO Verificar se valores entram
	TAXA_ANA("TAXA_ANA"), 							//TODO Verificar se valores entram
	EMOLUMENTOS("EMOLUMENTOS"),
	
	//TOTAL CORRETAGEM/DESPESAS
	CORRETAGEM("CORRETAGEM"),
	ISS("ISS"),
	
	
	
	
	IMPOSTO_RETIDO("IMPOSTO_RETIDO"),
	RENTABILIDADE_ACAO("RENTABILIDADE_ACAO"),
	RENTABILIDADE_ACAO_DAY_TRADE("RENTABILIDADE_ACAO_DAY_TRADE"),
	RENTABILIDADE_OPCAO("RENTABILIDADE_OPCAO"),
	RENTABILIDADE_OPCAO_DAY_TRADE("RENTABILIDADE_OPCAO_DAY_TRADE");
	
	
	private String Type;
	
	private FinancialSummaryType(String Type) {
		this.Type = Type;
	}
	
	public String toString(){
		return this.Type;
	}
	
	public static FinancialSummaryType toType(String str){
		FinancialSummaryType type=null;
		FinancialSummaryType[] values = values();
		
		for (FinancialSummaryType revenueType : values) {
			if(revenueType.toString().equals(str)){
				type = revenueType;
			}
		}
		return type;
	}
}
