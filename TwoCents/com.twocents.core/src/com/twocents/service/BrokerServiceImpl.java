package com.twocents.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.twocents.dao.BrokerDAO;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Broker;
import com.twocents.model.StockBroker;
import com.twocents.resources.CoreMessages;

public class BrokerServiceImpl extends BaseServiceImpl<Broker, BrokerDAO>
		implements BrokerService {

	private static final Logger log = Logger.getLogger(BrokerServiceImpl.class);

	public void setBrokerDAO(BrokerDAO dao) {
		super.setDao(dao);
	}

	public BrokerDAO getBrokerDAO() {
		return super.getDao();
	}

	public Broker findBrokerByName(String name, StockBroker stockBroker) throws CoreException {

		try {
			return super.getDao().findBrokerByName(name, stockBroker);
		} catch (RuntimeException e) {
			log.error(CoreMessages.getMessage(1001));
			return null;
		}
	}

	public void addBroker(Broker broker) throws CoreException {
		getDao().persist(broker);
	}

	public void removeBroker(Broker broker) throws CoreException {
		getDao().delete(broker);
	}

	@Override
	public void updateBroker(Broker broker) throws CoreException {
		getDao().persist(broker);

	}

	@Override
	public void removeAccount(Broker broker, Account ac) {
		broker.getAccounts().remove(ac);
		getDao().persist(broker);

	}

	@Override
	public List<Broker> findBrokerByStockBroker(StockBroker stockBroker)
			throws CoreException {
		return getDao().findBrokerByStockBoker(stockBroker);
	}

}
