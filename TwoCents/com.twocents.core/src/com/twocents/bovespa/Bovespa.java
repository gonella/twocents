package com.twocents.bovespa;

import com.twocents.bovespa.broker.BrokerBanifInvest;
import com.twocents.bovespa.broker.BrokerBase;
import com.twocents.bovespa.broker.BrokerSocopa;
import com.twocents.model.BrokerType;
import com.twocents.model.StockType;

/*
 * 
 * 
 * Os percentuais utilizados para o calculo foram obtidos do link abaixo:
 * 
 * http://www.bmfbovespa.com.br/pt-br/regulacao/acoes/custos-operacionais/custos-operacionais.aspx?idioma=pt-br
 * http://wiki.advfn.com/pt/Corretagem
 * 
 */
public class Bovespa {
	//Abaixo as taxas aplicadas pela BOVESPA
	//public static Double EMOLUMENTOS_MERCADO_A_VISTA=0.00027;
	public static Double EMOLUMENTOS_MERCADO_A_VISTA=0.000345;
	//public static Double EMOLUMENTOS_MERCADO_A_VISTA_DAY_TRADE=	0.00019;
	public static Double EMOLUMENTOS_MERCADO_A_VISTA_DAY_TRADE=	0.00025;
	public static Double TAXA_DE_LIQUIDACAO_MERCADO_A_VISTA=0.00008;  
	public static Double TAXA_DE_LIQUIDACAO_MERCADO_A_VISTA_DAY_TRADE=0.00006;  
	
	public static Double EMOLUMENTOS_MERCADO_OPCOES=0.0027;
	public static Double EMOLUMENTOS_MERCADO_OPCOES_DAY_TRADE=0.0019;
	public static Double TAXA_DE_LIQUIDACAO_MERCADO_OPCOES=0.00008;  
	public static Double TAXA_DE_LIQUIDACAO_MERCADO_OPCOES_DAY_TRADE=0.00006;  
	
	//public static double IRRF=0.005;
	public static double IRRF=0.00005;
	//public static double IRRF_DAY_TRADE=0.01;
	public static double IRRF_DAY_TRADE=0.01;
	
	public static double IR_PAGAR=0.15;
	public static double IR_PAGAR_DAY_TRADE=0.20;

	public static double ISS=0.000029;
	
	public static Double getTaxaLiquidacao(boolean dayTrade,StockType stockType,Double value){
		
		Double tax=new Double(1);
		
		if(StockType.SHARE.equals(stockType)){
			
			if(dayTrade){
				tax=TAXA_DE_LIQUIDACAO_MERCADO_A_VISTA_DAY_TRADE;
			}else{
				tax=TAXA_DE_LIQUIDACAO_MERCADO_A_VISTA;
			}
		}
		else if(StockType.OPTION.equals(stockType)){
			if(dayTrade){
				tax=TAXA_DE_LIQUIDACAO_MERCADO_OPCOES_DAY_TRADE;
			}else{
				tax=TAXA_DE_LIQUIDACAO_MERCADO_OPCOES;
			}
		}
		
		return value*tax;
	}

	public static Double getEmolumentos(boolean dayTrade,StockType stockType,Double value){
		
		Double tax=new Double(1);
		
		if(StockType.SHARE.equals(stockType)){
			
			if(dayTrade){
				tax=EMOLUMENTOS_MERCADO_A_VISTA_DAY_TRADE;
			}else{
				tax=EMOLUMENTOS_MERCADO_A_VISTA;
			}
		}
		else if(StockType.OPTION.equals(stockType)){
			if(dayTrade){
				tax=EMOLUMENTOS_MERCADO_OPCOES_DAY_TRADE;
			}else{
				tax=EMOLUMENTOS_MERCADO_OPCOES;
			}
		}
		
		double result = value*tax;
		
		return result;
	}
	/**
	 * Imposto retido na fonte(IRRF)
	 * @param dayTrade
	 * @param value
	 * @return
	 */
	public static Double getImpostoRetidoNaFonte(boolean dayTrade,Double value){
		if(dayTrade){
			return value*IRRF_DAY_TRADE;
		}
		else{
			return value*IRRF;
		}
		
	}
	public static Double getISS(Double value){
		return value*ISS;
	}
	public static Double getCorretagem(BrokerType type,Double value){
		
		BrokerBase broker;
		if(BrokerType.SOCOPA.equals(type)){
			broker=new BrokerSocopa();
		}
		else //if(BrokerType.BANIFINVEST.equals(type))
		{
			broker=new BrokerBanifInvest();
		}
		return broker.getCorretagem(value);
	}

	
	public static Double impostoASerPago(boolean daytrade,double lucro,double prejuizo, double taxas){
		Double result=0.0;
		
		Double r=lucro - prejuizo - taxas;
		
		if(r<0){
			return result;
		}
		
		if(daytrade){
			
			
			
			
		}else{
			
			
		}
		
		
		
		return result;
	}
}
