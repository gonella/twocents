package com.twocents.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass  
public abstract class Brokerage extends Persistent{

	
	private static final long serialVersionUID = 2915700865853121822L;
	double emolumentos=0.0;
	double corretagem=0.0;
	double valorDasOperacoes=0.0;
	double valorLiquidoDasOperacoes=0.0;
	double ISS=0.0;
	double taxaDeLiquidacao=0.0;
	
	private double IRRF=0.0;
	
	public Brokerage(){
		
	}
	
	
	public double getEmolumentos() {
		return emolumentos;
	}

	public void setEmolumentos(double emolumentos) {
		this.emolumentos = emolumentos;
	}

	public double getCorretagem() {
		return corretagem;
	}

	public void setCorretagem(double corretagem) {
		this.corretagem = corretagem;
	}

	public double getValorDasOperacoes() {
		return valorDasOperacoes;
	}

	public void setValorDasOperacoes(double valorDasOperacoes) {
		this.valorDasOperacoes = valorDasOperacoes;
	}

	public double getValorLiquidoDasOperacoes() {
		return valorLiquidoDasOperacoes;
	}

	public void setValorLiquidoDasOperacoes(double valorLiquidoDasOperacoes) {
		this.valorLiquidoDasOperacoes = valorLiquidoDasOperacoes;
	}

	public double getISS() {
		return ISS;
	}

	public void setISS(double iSS) {
		ISS = iSS;
	}

	public double getTaxaDeLiquidacao() {
		return taxaDeLiquidacao;
	}

	public void setTaxaDeLiquidacao(double taxaDeLiquidacao) {
		this.taxaDeLiquidacao = taxaDeLiquidacao;
	}


	public void setIRRF(double iRNaFonte) {
		IRRF = iRNaFonte;
	}


	public double getIRRF() {
		return IRRF;
	}
	
}
