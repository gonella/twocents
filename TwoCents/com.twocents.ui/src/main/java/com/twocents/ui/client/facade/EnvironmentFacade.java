package com.twocents.ui.client.facade;

import java.util.List;

import com.twocents.model.Account;
import com.twocents.model.Broker;
import com.twocents.model.Operation;
import com.twocents.model.StockBroker;
import com.twocents.model.TwUser;
import com.twocents.service.AccountService;
import com.twocents.service.BrokerService;
import com.twocents.service.ConfigurationService;
import com.twocents.service.OperationService;
import com.twocents.service.StockBrokerService;
import com.twocents.service.TwUserService;
import com.twocents.spring.ServiceLocator;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.exceptions.UIException;

public class EnvironmentFacade {
	
	private final BrokerService brokerService = (BrokerService)ServiceLocator.getBean(BrokerService.BEAN_NAME);
	private final StockBrokerService stockBrokerService = (StockBrokerService)ServiceLocator.getBean(StockBrokerService.BEAN_NAME);
	private final TwUserService twUserService = (TwUserService)ServiceLocator.getBean(TwUserService.BEAN_NAME);
	private final ConfigurationService uiConfigurationService = (ConfigurationService)ServiceLocator.getBean(ConfigurationService.BEAN_NAME);
	private final AccountService accountService = (AccountService)ServiceLocator.getBean(AccountService.BEAN_NAME);
	private final OperationService operationService = (OperationService)ServiceLocator.getBean(OperationService.BEAN_NAME);
	
	public Broker findBrokerByName(String name) throws UIException{
		
		try {
			return getBrokerService().findBrokerByName(name, ContextHolderUI.getStockBrokerLogged());
		} catch (Exception e) {
			throw new UIException(e.getMessage(),e);	
		}
	}

	public StockBroker findStockBrokerByName(String name) throws UIException {
		try {
			return getStockBrokerService().findStockBrokerByName(name);
		} catch (Exception e) {
			throw new UIException(e.getMessage(),e);	
		}
	}
	
	public StockBroker findStockBrokerByUsernameAndPassword(String username,String password) throws UIException {
		try {
			return getStockBrokerService().findStockBrokerByUsernameAndPassword(username,password);
		} catch (Exception e) {
			throw new UIException(e.getMessage(),e);	
		}
	}
	
	public StockBroker findStockBrokerByUsername(String username) throws UIException {
		try {
			return getStockBrokerService().findStockBrokerByUsername(username);
		} catch (Exception e) {
			throw new UIException(e.getMessage(),e);	
		}
	}

	public TwUser findUserByName(String name) throws UIException {
		try {
			return getTwUserService().findUserByName(name);
		} catch (Exception e) {
			throw new UIException(e.getMessage(),e);	
		}
	}
	
	/*
	public TwUser findUserByUserName(String userName) throws UIException {
		try {
			return getTwUserService().findUserByUserName(userName);
		} catch (Exception e) {
			throw new UIException(e.getMessage(),e);	
		}
	}
	
	public TwUser findUserByUserNameAndPassword(String login,String password) throws UIException {
		try {
			return getTwUserService().findUserByLoginByPassword(login, password);
		} catch (Exception e) {
			throw new UIException(e.getMessage(),e);	
		}
	}
	*/
	
	public BrokerService getBrokerService() {
		return brokerService;
	}
	
	public StockBrokerService getStockBrokerService() {
		return stockBrokerService;
	}

	public TwUserService getTwUserService() {
		return twUserService;
	}
	public ConfigurationService getUiConfigurationService() {
		return uiConfigurationService;
	}
	public List<Broker> listAllBroker() throws UIException{	
		return getBrokerService().findAll();
	}

	public List<StockBroker> listAllStockBroker() throws UIException {

		try {
			return getStockBrokerService().findAll();
		} catch (Exception e) {
			throw new UIException(e.getMessage(),e);	
		}
	}
	
	public List<TwUser> listAllUser() throws UIException {
		return getTwUserService().findAll();
	}
	
	public List<Operation> listAllOperation() throws UIException {
		return getOperationService().findAll();
	}
	
	public void saveBroker(Broker broker) throws UIException{
		getBrokerService().persist(broker);
	}

	public void saveStockBroker(StockBroker stockBroker) throws UIException {
		getStockBrokerService().persist(stockBroker);
	}

	public void saveUser(TwUser user) throws UIException {
		getTwUserService().persist(user);
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void removeAccount(Account account) throws UIException{
		try {
			getAccountService().removeAccount(account);
		} catch (Exception e) {
			throw new UIException(e.getMessage(),e);	
		}
	}

	public void removeUser(TwUser user) throws UIException{
		try {
			getTwUserService().removeUser(user);
		} catch (Exception e) {			
			throw new UIException(e.getMessage(),e);	
		}
	}
	
	public List<Account> listAccountByUserId(Long userId) throws UIException{
		try {
			return getAccountService().listAccountByUserId(userId);
		} catch (Exception e) {			
			throw new UIException(e.getMessage(),e);			
		}
	}

	public OperationService getOperationService() {
		return operationService;
	}
	
}
