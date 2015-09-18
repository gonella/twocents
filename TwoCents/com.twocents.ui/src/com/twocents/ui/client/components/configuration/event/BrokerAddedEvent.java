package com.twocents.ui.client.components.configuration.event;

import com.twocents.model.Broker;

public class BrokerAddedEvent extends BrokerEvent {

	private static final long serialVersionUID = -7437056103542727554L;

	public BrokerAddedEvent(Object source, Broker broker) {
		super(source, broker);
	}

}
