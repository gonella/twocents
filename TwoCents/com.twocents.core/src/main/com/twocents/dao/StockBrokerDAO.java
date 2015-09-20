package com.twocents.dao;

import com.twocents.model.StockBroker;


public interface StockBrokerDAO extends BaseDAO<StockBroker> {

	public StockBroker findStockBrokerByName(String name);
	public StockBroker findStockBrokerByUsernameAndPassword(String username,String password);
	public StockBroker findStockBrokerByUsername(String username);


}
