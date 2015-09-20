package com.twocents.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;
import com.twocents.model.StockBroker;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Throwable.class, CoreException.class})
public interface StockBrokerService extends BaseService<StockBroker> {

	String BEAN_NAME = "com.twocents.service.StockBrokerService";

	public StockBroker findStockBrokerByName(String name) throws CoreException;
	public StockBroker findStockBrokerByUsernameAndPassword(String username,String password) throws CoreException;
	public StockBroker findStockBrokerByUsername(String username) throws CoreException;


}

