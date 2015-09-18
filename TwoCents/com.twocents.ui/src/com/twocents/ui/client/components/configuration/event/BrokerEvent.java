package com.twocents.ui.client.components.configuration.event;

import java.util.EventObject;

import com.twocents.model.Broker;

public abstract class BrokerEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	private Broker broker;

	public BrokerEvent(Object source, Broker broker) {
		super(source);
		this.broker = broker;
	}
	
	
	public Broker getBroker() {
		return broker;
	}

	public void setBroker(Broker broker) {
		this.broker = broker;
	}

}
