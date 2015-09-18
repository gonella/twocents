package com.twocents.ui.client.components.facade;

import java.util.List;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Broker;
import com.twocents.model.StockBroker;
import com.twocents.service.BrokerService;
import com.twocents.spring.ServiceLocator;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.exceptions.UIException;

public class BrokerConfigurationFacade {

	BrokerService brokerService = (BrokerService) ServiceLocator
			.getBean(BrokerService.BEAN_NAME);

	public BrokerService getBrokerService() {
		return brokerService;
	}

	public List<Broker> listAllBroker() {
		return getBrokerService().findAll();
	}

	public List<Broker> listBrokerByStockBroker(StockBroker stockBroker) throws CoreException {
		return getBrokerService().findBrokerByStockBroker(stockBroker);
	}

	public void addBroker(Broker broker) throws UIException {
		try {
			getBrokerService().addBroker(broker);
		} catch (Exception e) {
			throw new UIException(e);
		}

	}

	public void removeBroker(Broker broker) throws UIException {
		try {
			getBrokerService().removeBroker(broker);
		} catch (Exception e) {
			throw new UIException(e);
		}

	}

	public Broker removeBroker(String brokerName) throws UIException {
		try {
			Broker b = getBrokerService().findBrokerByName(brokerName, ContextHolderUI.getStockBrokerLogged());
			getBrokerService().removeBroker(b);
			return b;
		} catch (Exception e) {
			throw new UIException(e);
		}

	}

	public Broker getBrokerByName(String brokerName) throws UIException {
		try {
			return getBrokerService().findBrokerByName(brokerName, ContextHolderUI.getStockBrokerLogged());
		} catch (CoreException e) {
			throw new UIException(e);
		}
	}

	public void updateBroker(Broker broker) throws UIException {
		getBrokerService().persist(broker);
	}

}
