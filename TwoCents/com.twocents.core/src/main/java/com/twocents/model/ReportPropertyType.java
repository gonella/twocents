package com.twocents.model;

public enum ReportPropertyType {
	
	HORA("Hora"),
	ATIVO("ativo"),
	QUANTIDADE("quantidade"),
	TIPO_ATIVO("tipoAtivo"),
	TIPO_OPERACAO("tipoOperacao"),	
	PRECO("preco"), 
	VALOR_TOTAL("valorTotal"),
	COTACAO("cotacao"),
	RENTABILIDADE("rentabilidade"), 
	MES("mes"),
	ANO("ano"),
	EMOLUMENTOS("emolumento"),
	CORRETAGEM("corretagem"),
	PRECO_MEDIO("precoMedio"),
	PRECO_VENDA("precoVenda"),
	DAYTRADE("daytrade"), 
	DESCRICAO("descricao"), 
	DATA("data"), 
	IMAGE("image"), FIELD("field"), 
	VALOR("valor"),
	VALOR_POSICAO("valorPosicao"),
	
	TAXA_DE_LIQUIDACAO("Taxa de Liquidação"),
	
	//Account Data
	LUCRO("lucro"), 
	IMPOSTO_A_SER_PAGO("imposto_a_ser_pago"), 
	//IMPOSTO_RETIDO("imposto_retido"),
	TAXAS_RETIDAS("TAXAS_RETIDAS"),

	LUCRO_DAYTRADE("lucro_daytrade"), 
	IMPOSTO_A_SER_PAGO_DAYTRADE("imposto_a_ser_pago_daytrade"), 
	IMPOSTO_RETIDO_DAYTRADE("imposto_retido_daytrade"),
	
	PREJUIZO("prejuizo"),
	PREJUIZO_DAYTRADE("prejuizo_daytrade"),
	
	VALOR_VENDAS("valor_vendas"), 	
	LUCRO_DE_ACOES_ISENTO_IMPOSTOS("lucro_de_acoes_isento_impostos"), 
	
	MES_E_ANO("Mes e Ano"), 
	TIPO_DE_TRADE("Tipo de Trade"), 
	ISS("ISS")
	
	;
	
	
	private String property;
	
	private ReportPropertyType(String property) {
		this.property = property;
	}
	
	public String toString(){
		return this.property;
	}
}
