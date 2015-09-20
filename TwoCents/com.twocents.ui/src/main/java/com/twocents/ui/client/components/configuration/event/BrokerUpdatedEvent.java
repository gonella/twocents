package com.twocents.ui.client.components.configuration.event;

import com.twocents.model.Broker;

public class BrokerUpdatedEvent extends BrokerEvent {

	private static final long serialVersionUID = 6794142253545275398L;

	public BrokerUpdatedEvent(Object source, Broker broker) {
		super(source, broker);
	}

}
