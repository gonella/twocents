package com.twocents.ui.client.components.facade;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.core.populate.PopulateData;
import com.twocents.core.util.StringCheck;
import com.twocents.core.validation.CoreValidation;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Broker;
import com.twocents.model.StockBroker;
import com.twocents.model.TwUser;
import com.twocents.service.AccountService;
import com.twocents.service.BrokerService;
import com.twocents.service.TwUserService;
import com.twocents.spring.ServiceLocator;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.resources.UIMessages;

public class AccountFacade {

	private static Logger logger = Logger.getLogger(AccountFacade.class);

	private final BrokerService brokerService = (BrokerService) ServiceLocator
			.getBean(BrokerService.BEAN_NAME);
	private final TwUserService userService = (TwUserService) ServiceLocator
			.getBean(TwUserService.BEAN_NAME);
	private final AccountService accountService = (AccountService) ServiceLocator
			.getBean(AccountService.BEAN_NAME);

	public TwUserService getTwUserService() {
		return userService;
	}

	@Transactional
	public Account addAccount(TwUser user, StockBroker stockBroker,
			String brokerName, String accountNumber) throws UIException {

		if (!StringCheck.isNumeric(accountNumber)) {
			throw new UIException(UIMessages
					.getMessage("NUMERO_CONTA_INVALIDO"),
					new RuntimeException());
		}

		try {
			CoreValidation.validateUser(user);
		} catch (CoreException e) {
			throw new UIException(e.getMessage(), e);
		}

		TwUser findUserByName;
		try {
			findUserByName = getTwUserService().findUserByName(user.getName());
		} catch (CoreException e) {
			throw new UIException(e.getMessage(), e);
		}

		if (findUserByName != null) {
			throw new UIException(4016);
		}

		if (stockBroker == null) {
			throw new UIException(5015);
		}

		if (brokerName == null) {
			throw new UIException(5011);
		}

		logger.info("Criando conta :" + user.getName() + " stockBroker:"
				+ stockBroker.getName() + " Broker:" + brokerName);

		try {
			userService.addUser(user);
		} catch (CoreException e) {
			throw new UIException(e.getMessage(), e);
		}
		Account ac = associateAccount(user, stockBroker, brokerName,
				accountNumber);
		accountService.persist(ac);

		return ac;
	}

	private Account associateAccount(TwUser user, StockBroker stockBroker,
			String brokerName, String accountNumber) throws UIException {

		Broker broker;
		try {
			broker = brokerService.findBrokerByName(brokerName, ContextHolderUI
					.getStockBrokerLogged());
		} catch (CoreException e) {
			throw new UIException(e.getMessage(), e);
		}
		Account account = new PopulateData().populateAccountInvestiment(broker,
				user, stockBroker, accountNumber);
		List<Account> accountsList = stockBroker.getAccountList();
		if (accountsList == null) {
			accountsList = new ArrayList<Account>();
		}
		accountsList.add(account);

		broker.setAccounts(accountsList);
		// try {
		// brokerService.updateBroker(broker);
		// } catch (CoreException e) {
		// throw new UIException(e.getMessage(), e);
		// }

		return account;
	}

	public Account removeAccount(String accountNumber) throws CoreException {
		Account ac = accountService.getByAccountNumber(accountNumber);

		// brokerService.removeAccount(ac.getBroker(), ac);
		try {
			accountService.removeAccount(ac);
			return ac;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public List<Account> listAccountsFromStockBroker(StockBroker stb) {

		return accountService.listAccountByStockBrokerId(stb.getId());
	}

	public Account getAccount(Long id) {
		return accountService.findById(id);
	}
}
