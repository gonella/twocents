package com.twocents.dao;

import java.util.List;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Broker;
import com.twocents.model.StockBroker;

public interface BrokerDAO extends BaseDAO<Broker> {

	public Broker findBrokerByName(String name, StockBroker stockBroker) throws CoreException;

	public List<Broker> findBrokerByStockBoker(StockBroker stockBroker)
			throws CoreException;

}
