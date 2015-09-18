package com.twocents.ui.client.components.configuration.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Broker;
import com.twocents.ui.client.common.action.UIConfigAction;
import com.twocents.ui.client.components.facade.BrokerConfigurationFacade;
import com.twocents.ui.client.components.listener.UpdateUserAccountListener;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.exceptions.UIException;

public class BrokerConfigurationAction extends UIConfigAction {

	private Logger logger = Logger.getLogger(BrokerConfigurationAction.class);
	private BrokerConfigurationFacade facade = new BrokerConfigurationFacade();
	private List<UpdateUserAccountListener> listeners = new ArrayList<UpdateUserAccountListener>();

	public void setListeners(List<UpdateUserAccountListener> listeners) {
		this.listeners = listeners;
	}

	public BrokerConfigurationFacade getFacade() {
		return facade;
	}

	public BrokerConfigurationAction() {
		super();
		init();
	}

	public void init() {

	}

	public void refresh() {

	}

	public void addListener(UpdateUserAccountListener listener) {
		listeners.add(listener);
	}

	public void removeBroker(String brkName) {
		logger.info("Removendo corretora");

		try {
			Broker b = facade.removeBroker(brkName);

			if (listeners != null && !listeners.isEmpty()) {
				for (UpdateUserAccountListener listener : listeners) {
					for (Account ac : b.getAccounts()) {
						listener.onAccountRemove(ac);
					}
				}
			}

		} catch (UIException e) {
			logger.error("ERROR during deletion ", e);
			throw new RuntimeException(e);
		}

	}

	public void addBroker(Broker broker) {
		logger.info("Adding broker");

		try {

			facade.addBroker(broker);
			logger.info("* Broker Created");

		} catch (UIException e) {
			logger.error(e);
		}

	}

	public List<Broker> listAllBroker() {
		return facade.listAllBroker();
	}

	public List<Broker> listBrokerByStockBroker() {
		try {
			return facade.listBrokerByStockBroker(ContextHolderUI
					.getStockBrokerLogged());
		} catch (CoreException e) {
			logger.error(e);
		}
		return new ArrayList<Broker>();
	}

	public Broker getBrokerByBrokerName(String brokerName) {
		try {
			Broker b = facade.getBrokerByName(brokerName);
			return b;
		} catch (UIException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void update(Broker broker) {
		try {
			facade.updateBroker(broker);
		} catch (UIException e) {
			logger.error(e);
		}

	}

}
