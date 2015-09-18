package com.twocents.service;

import java.sql.Timestamp;
import com.twocents.service.OperationService;
import com.twocents.model.Operation;

public class IRService {
	//retido na fonte 0.005% sobre vendas acima de 20k
	private double IRRF = 0.00005;
	
	//1% sobre venda de opções. Ou sobre venda de ações acima de 20k
	private double IRRFDayTrade = 0.01;
	
	private double IR = 0.15;
	
	private double IRDayTrade = 0.20;

	public double getIRRF() {
		return IRRF;
	}

	public void setIRRF(double irrf) {
		IRRF = irrf;
	}

	public double getIRRFDayTrade() {
		return IRRFDayTrade;
	}

	public void setIRRFDayTrade(double dayTrade) {
		IRRFDayTrade = dayTrade;
	}

	public double getIR() {
		return IR;
	}

	public void setIR(double ir) {
		IR = ir;
	}

	public double getIRDayTrade() {
		return IRDayTrade;
	}

	public void setIRDayTrade(double dayTrade) {
		IRDayTrade = dayTrade;
	}
	
	public long getDefaultIR(long sellPrice, long AvgPrice) {
		long profit = 0;
		if (sellPrice > AvgPrice){
			profit = sellPrice - AvgPrice;
		}
		long IR = (long)getIR();

		return profit*IR;
	}
	
	public long getDayTradeIR(long sellPrice, long AvgPrice) {
		long profit = 0;
		if (sellPrice > AvgPrice){
			profit = sellPrice - AvgPrice;
		}
		long IR = (long)getIRDayTrade();

		return profit*IR;
	}
	
	
	
	
	
	

	
}
