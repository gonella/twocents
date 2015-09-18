package com.twocents.bovespa.broker;

public class BrokerBanifInvest extends BrokerBase {

	public static Double CORRETAGEM=new Double(15.99);

	public double getCorretagem(Double value) {
		return CORRETAGEM;
	}

}
