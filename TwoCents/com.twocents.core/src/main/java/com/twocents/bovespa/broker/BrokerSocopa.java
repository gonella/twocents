package com.twocents.bovespa.broker;

public class BrokerSocopa extends BrokerBase{

	public static Double CORRETAGEM=0.005;
	
	public double getCorretagem(Double value) {
		return value*CORRETAGEM;
	}

}
