package com.twocents.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Broker;
import com.twocents.model.StockBroker;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
		Throwable.class, CoreException.class })
public interface BrokerService extends BaseService<Broker> {

	String BEAN_NAME = "com.twocents.service.BrokerService";

	public Broker findBrokerByName(String name, StockBroker stockBroker) throws CoreException;

	public void addBroker(Broker broker) throws CoreException;

	public void removeBroker(Broker broker) throws CoreException;

	public void updateBroker(Broker broker) throws CoreException;

	public void removeAccount(Broker broker, Account ac);

	public List<Broker> findBrokerByStockBroker(StockBroker stockBroker)
			throws CoreException;

}
