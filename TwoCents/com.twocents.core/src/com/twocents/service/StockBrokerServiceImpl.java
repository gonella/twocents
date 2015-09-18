package com.twocents.service;

import org.apache.log4j.Logger;

import com.twocents.dao.StockBrokerDAO;
import com.twocents.exceptions.CoreException;
import com.twocents.model.StockBroker;
import com.twocents.resources.CoreMessages;

public class StockBrokerServiceImpl extends BaseServiceImpl<StockBroker, StockBrokerDAO> implements StockBrokerService {

	private static final Logger logger = Logger.getLogger(StockBrokerServiceImpl.class);
	
	public void setStockBrokerDAO(StockBrokerDAO dao) {
		super.setDao(dao);
	}
	
	public StockBrokerDAO getStockBrokerDAO() {
		return super.getDao();
	}

	public StockBroker findStockBrokerByName(String name) throws CoreException {

		try {			
			return getStockBrokerDAO().findStockBrokerByName(name);
		} catch (RuntimeException e) {
			//logger.error(CoreMessages.getMessage(1000));
			return null;
		}
	}

	@Override
	public StockBroker findStockBrokerByUsernameAndPassword(String username,
			String password) throws CoreException {
		try {			
			return getStockBrokerDAO().findStockBrokerByUsernameAndPassword(username,password);
		} catch (RuntimeException e) {
			//logger.error(CoreMessages.getMessage(1000));
			return null;
		}
	}

	@Override
	public StockBroker findStockBrokerByUsername(String username) throws CoreException {
		try {			
			return getStockBrokerDAO().findStockBrokerByUsername(username);
		} catch (RuntimeException e) {
			//logger.error(CoreMessages.getMessage(1000));
			return null;
		}
	}
	
	
	
}
