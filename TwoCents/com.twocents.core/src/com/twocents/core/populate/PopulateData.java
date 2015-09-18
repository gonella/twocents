package com.twocents.core.populate;

import com.twocents.core.validation.CoreValidation;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.AccountType;
import com.twocents.model.Broker;
import com.twocents.model.StockBroker;
import com.twocents.model.TwUser;

public class PopulateData extends Populate {

	// private Logger logger = Logger.getLogger(PopulateData.class);

	public TwUser populateUser(String name, String password,
			String accountNumber, String email, String tel) {

		TwUser user = new TwUser();

		user.setName(name);
		user.setAccountNumber(accountNumber);
		user.setEmail(email);
		user.setTelephone(tel);

		return user;

	}

	public Account populateAccountInvestiment(Broker broker, TwUser user,
			StockBroker stockBroker, String accountNumber) {

		Account account = new Account();
		account.setBroker(broker);
		account.setName(AccountType.CI.toString());
		account.setBalance(0.0);
		account.setAccountType(AccountType.CI);
		account.setUser(user);
		account.setStockBroker(stockBroker);
		account.setAccountNumber(accountNumber);

		return account;
	}

	public StockBroker createStockBroker(StockBroker stockBroker, Broker broker)
			throws CoreException {

		// stockBroker.setBroker(broker);

		CoreValidation.validateStockBroker(stockBroker);

		StockBroker findStockBrokerByUsername = getStockBrokerService()
				.findStockBrokerByUsername(stockBroker.getUsername());
		if (findStockBrokerByUsername != null) {
			throw new CoreException(1010);
		}
		getStockBrokerService().persist(stockBroker);
		// getEnvironmentFacade().saveStockBroker(stockBroker);

		return stockBroker;
	}

	/*
	 * public static TwUser createUserAccount(TwUser user, String bovespaId,
	 * StockBroker stockBroker, Broker broker) throws UIException {
	 * 
	 * UIValidation.validateUser(user);
	 * 
	 * TwUser findUserByName = getEnvironmentFacade().findUserByName(
	 * user.getName());
	 * 
	 * if (findUserByName != null) { throw new UIException(4016); }
	 * 
	 * if (stockBroker == null) { throw new UIException(5015); }
	 * 
	 * if (broker == null) { throw new UIException(5011); }
	 * logger.info("Creating the User :" + user.getName() + " stockBroker:" +
	 * stockBroker.getName() + " Broker:" + broker.getName());
	 * 
	 * getEnvironmentFacade().saveUser(user);
	 * 
	 * // Account Setting Account account = populateAccountInvestiment(broker,
	 * user, stockBroker, bovespaId); List<Account> accountsList =
	 * stockBroker.getAccountList(); if (accountsList == null) { accountsList =
	 * new ArrayList<Account>(); } accountsList.add(account);
	 * 
	 * // stockBroker.setAccountList(accountsList);
	 * 
	 * // List<StockBroker> stockBrokers=new ArrayList<StockBroker>(); //
	 * stockBrokers.add(stockBroker); // broker.setStockBrokers(stockBrokers);
	 * 
	 * broker.setAccounts(accountsList);
	 * 
	 * List<Broker> brokers = new ArrayList<Broker>(); brokers.add(broker);
	 * 
	 * getEnvironmentFacade().saveUser(user);
	 * 
	 * return user; }
	 */
}
