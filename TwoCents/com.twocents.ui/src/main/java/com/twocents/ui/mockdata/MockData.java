package com.twocents.ui.mockdata;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.twocents.core.populate.PopulateData;
import com.twocents.core.populate.PopulateOperationData;
import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Broker;
import com.twocents.model.FixedTax;
import com.twocents.model.Operation;
import com.twocents.model.OperationKey;
import com.twocents.model.OperationType;
import com.twocents.model.StockBroker;
import com.twocents.model.TwUser;
import com.twocents.ui.client.components.facade.AccountFacade;
import com.twocents.ui.client.facade.EnvironmentFacade;
import com.twocents.ui.client.facade.OperationFacade;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.constant.UIDefault;
import com.twocents.ui.exceptions.UIException;

public class MockData {

	public static final String STOCK_BROKER01 = "StockBroker01";

	private Logger logger = Logger.getLogger(MockData.class);

	private final EnvironmentFacade environmentFacade = new EnvironmentFacade();
	private final AccountFacade accountFacade = new AccountFacade();
	private final OperationFacade operationFacade = new OperationFacade();

	private final PopulateData populateData = new PopulateData();
	private final PopulateOperationData populateOperationData = new PopulateOperationData();

	public static TwUser populateDefaulUser(String name) {

		String username = UIDefault.DEFAULT_USERNAME;
		String password = UIDefault.DEFAULT_PASSWORD;
		String accountName = UIDefault.DEFAULT_USERNAME + name;
		String email = UIDefault.DEFAULT_EMAIL;

		return new PopulateData().populateUser(name, username, password,
				accountName, email);
	}

	public static Broker populateBanifBroker() {

		Broker broker = new Broker();
		broker.setName("BANIF");

		FixedTax tax = new FixedTax();

		tax.setFixed(true);
		tax.setValue(15.99);

		broker.setTax(tax);
		return broker;
	}

	public void createStockBrokerMockData() {

		logger.info("# Criando data default para aplicação");
		Broker broker = null;
		StockBroker stockBroker = null;

		try {
			stockBroker = getEnvironmentFacade().findStockBrokerByUsername(
					STOCK_BROKER01);

			if (stockBroker != null) {
				return;
			} else {
				stockBroker = new StockBroker();
				stockBroker.setName(STOCK_BROKER01);
				stockBroker.setEmail("gonella@gmail.com");
				stockBroker.setTelefone("51-81335747");
				stockBroker.setUsername(STOCK_BROKER01);
				stockBroker.setPassword("");
				ContextHolderUI.setStockBrokerSelected(stockBroker);
			}

			getEnvironmentFacade().saveStockBroker(stockBroker);

			broker = populateBanifBroker();
			broker.setStockBroker(stockBroker);
			List<Broker> brokerFounds = getEnvironmentFacade().listAllBroker();

			// Caso nao exista criar.
			if (brokerFounds == null || brokerFounds.isEmpty()) {
				getEnvironmentFacade().saveBroker(broker);
				logger.info("Corretora criada: " + broker.getName());
			} else {
				return;
			}

		} catch (UIException e) {

			logger.error(e.getMessage());
		}

		int SIZE = 1;

		TwUser user;
		for (int i = 0; i < SIZE; i++) {
			try {
				user = populateDefaulUser("Conta" + i);
				logger.info("Criando Conta :" + user.getName());
				getAccountFacade().addAccount(user, stockBroker,
						broker.getName(), (i + 1) * 31 + "");
				logger.info("**OK");

			} catch (CoreException e) {
				logger.error(e.getMessage());
			}
		}

	}

	public void populateOperationForAllStockBrokers() {
		try {
			populateDataForAllStockBrokers(mockOperationsData());
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private void populateDataForAllStockBrokers(
			List<Map<OperationKey, Object>> list) throws UIException {

		List<StockBroker> findAll = getEnvironmentFacade()
				.getStockBrokerService().findAll();

		for (Iterator iteratorsb = findAll.iterator(); iteratorsb.hasNext();) {

			StockBroker stockBroker = (StockBroker) iteratorsb.next();

			logger.info("Mocking data for StockBroker :"
					+ stockBroker.getName());

			List<Account> accountList = stockBroker.getAccountList();

			for (Iterator iterator = accountList.iterator(); iterator.hasNext();) {
				Account account = (Account) iterator.next();
				logger.info("Account: Mocking :" + account.getUser().getName());
				try {
					List<Operation> findOperationByAccount = getEnvironmentFacade()
							.getOperationService().findOperationByAccount(
									account);

					if (CollectionUtils.isEmpty(findOperationByAccount)) {
						getPopulateOperationData().registerOperations(account,
								list);
					} else {
						logger.info("**Dados não populados :"
								+ account.getUser().getName());
					}
				} catch (CoreException e) {
					logger.error(e);
				}

			}
		}

	}
	
	public List<Map<OperationKey, Object>> mockOperationsData() throws Exception {

		//return null;
		return mockOperationsDataTestStockPortifolio();
	}
	
	public List<Map<OperationKey, Object>> mockOperationsDataTestStockPortifolio() throws Exception {

		List<Map<OperationKey, Object>> list = new ArrayList<Map<OperationKey, Object>>();

		//Posição de carteira
		list.add(PopulateOperationData.createOperationMap("HBOR3",
				OperationType.BUY, 1400, 19.56, FormatUtil
						.parseDateAndTime("25/11/2010 10:00:00"), "-"));
		
		list.add(PopulateOperationData.createOperationMap("HGTX3",
				OperationType.BUY, 1000, 28.30, FormatUtil
						.parseDateAndTime("01/11/2010 10:00:00"), "-"));

		list.add(PopulateOperationData.createOperationMap("ELET6",
				OperationType.BUY, 1000, 28.02, FormatUtil
						.parseDateAndTime("27/10/2010 10:00:00"), "-"));
		
		list.add(PopulateOperationData.createOperationMap("MAGG3",
				OperationType.BUY, 2800, 12.30, FormatUtil
						.parseDateAndTime("03/08/2010 10:00:00"), "-"));

		list.add(PopulateOperationData.createOperationMap("MAGG3",
				OperationType.BUY, 2800, 12.30, FormatUtil
						.parseDateAndTime("03/03/2009 10:00:00"), "-"));

		list.add(PopulateOperationData.createOperationMap("MAGG3",
				OperationType.BUY, 2800, 12.30, FormatUtil
						.parseDateAndTime("03/04/2008 10:00:00"), "-"));

		
		return list;
	}
	

	public List<Map<OperationKey, Object>> mockOperationsDataStressFul() {

		List<Map<OperationKey, Object>> list = new ArrayList<Map<OperationKey, Object>>();

		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -1);

		int QUANT_DAYS = 500;

		for (int i = 0; i < QUANT_DAYS; i++) {
			c.add(Calendar.DAY_OF_YEAR, 1);

			Date date = c.getTime();

			list.add(PopulateOperationData.createOperationMap("FRAS4",
					OperationType.BUY, 1000, 10.00, date, "-"));
			list.add(PopulateOperationData.createOperationMap("FRAS4",
					OperationType.SELL, 1000, 12.00, date, "-"));

			list.add(PopulateOperationData.createOperationMap("PETR4",
					OperationType.BUY, 1000, 11.00, date, "-"));

			list.add(PopulateOperationData.createOperationMap("PETR4",
					OperationType.SELL, 800, 13.00, date, "-"));

			list.add(PopulateOperationData.createOperationMap("CYRE3",
					OperationType.BUY, 1000, 22.00, date, "-"));
			list.add(PopulateOperationData.createOperationMap("CYRE3",
					OperationType.SELL, 900, 21.00, date, "-"));
		}

		return list;
	}

	public List<Map<OperationKey, Object>> mockOperationsDataDefault() throws Exception {

		List<Map<OperationKey, Object>> list = new ArrayList<Map<OperationKey, Object>>();

		// OUTUBRO - 2010
		// Daytrade com lucro
		list.add(PopulateOperationData.createOperationMap("FRAS4",
				OperationType.BUY, 1000, 10.00, FormatUtil
						.parseDateAndTime("04/10/2010 10:00:10"), "-"));
		list.add(PopulateOperationData.createOperationMap("FRAS4",
				OperationType.SELL, 1000, 12.00, FormatUtil
						.parseDateAndTime("04/10/2010 10:00:90"), "-"));

		// longo prazo
		list.add(PopulateOperationData.createOperationMap("FRAS4",
				OperationType.BUY, 1000, 11.00, FormatUtil
						.parseDateAndTime("04/10/2010 12:00:10"), "-"));

		// NOVEMBRO - 2010

		// Prejuizo

		list.add(PopulateOperationData.createOperationMap("FRAS4",
				OperationType.SELL, 800, 10.00, FormatUtil
						.parseDateAndTime("02/11/2010 12:00:90"), "-"));

		// Prejuizo DayTrade
		list.add(PopulateOperationData.createOperationMap("CYRE3",
				OperationType.BUY, 1000, 22.00, FormatUtil
						.parseDateAndTime("04/11/2010 12:00:10"), "-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",
				OperationType.SELL, 1000, 21.00, FormatUtil
						.parseDateAndTime("04/11/2010 12:00:90"), "-"));

		// Daytrade lucro
		list.add(PopulateOperationData.createOperationMap("PETR4",
				OperationType.BUY, 1000, 23.00, FormatUtil
						.parseDateAndTime("04/11/2010 12:01:10"), "-"));
		list.add(PopulateOperationData.createOperationMap("PETR4",
				OperationType.SELL, 1000, 27.00, FormatUtil
						.parseDateAndTime("04/11/2010 12:02:90"), "-"));

		// Lucro
		list.add(PopulateOperationData.createOperationMap("VALE5",
				OperationType.BUY, 1200, 22.46, FormatUtil
						.parseDateAndTime("04/11/2010 12:03:10"), "-"));
		list.add(PopulateOperationData.createOperationMap("VALE5",
				OperationType.SELL, 1200, 24.46, FormatUtil
						.parseDateAndTime("06/11/2010 12:03:10"), "-"));

		// Prejuizo
		list.add(PopulateOperationData.createOperationMap("HBOR3",
				OperationType.BUY, 1200, 22.46, FormatUtil
						.parseDateAndTime("07/11/2010 12:03:10"), "-"));
		list.add(PopulateOperationData.createOperationMap("HBOR3",
				OperationType.SELL, 1000, 21.69, FormatUtil
						.parseDateAndTime("09/11/2010 12:02:90"), "-"));

		// DEZEMBRO - 2010
		// Lucro
		list.add(PopulateOperationData.createOperationMap("ELET6",
				OperationType.BUY, 1200, 20.00, FormatUtil
						.parseDateAndTime("09/12/2010 12:00:10"), "-"));
		list.add(PopulateOperationData.createOperationMap("ELET6",
				OperationType.SELL, 1000, 21.00, FormatUtil
						.parseDateAndTime("10/12/2010 12:02:90"), "-"));

		// Prejuizo Daytrade
		list.add(PopulateOperationData.createOperationMap("MAGG3",
				OperationType.BUY, 1200, 20.00, FormatUtil
						.parseDateAndTime("11/12/2010 12:00:10"), "-"));
		list.add(PopulateOperationData.createOperationMap("MAGG3",
				OperationType.SELL, 1000, 19.00, FormatUtil
						.parseDateAndTime("11/12/2010 12:02:90"), "-"));

		// DayTrade prejuizo
		list.add(PopulateOperationData.createOperationMap("CYRE3",
				OperationType.BUY, 1200, 22.46, FormatUtil
						.parseDateAndTime("04/01/2011 12:00:10"), "-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",
				OperationType.SELL, 1200, 22.19, FormatUtil
						.parseDateAndTime("04/01/2011 12:00:90"), "-"));

		// Daytrade lucro
		list.add(PopulateOperationData.createOperationMap("PETR4",
				OperationType.BUY, 1200, 22.46, FormatUtil
						.parseDateAndTime("04/01/2011 12:01:10"), "-"));
		list.add(PopulateOperationData.createOperationMap("PETR4",
				OperationType.SELL, 1200, 22.69, FormatUtil
						.parseDateAndTime("04/01/2011 12:02:90"), "-"));

		/*
		 * //Lucro
		 * list.add(PopulateOperationData.createOperationMap("VALE5",OperationType
		 * .
		 * BUY,1200,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:03:10"),"-"
		 * ));
		 * list.add(PopulateOperationData.createOperationMap("VALE5",OperationType
		 * .
		 * SELL,1200,24.46,FormatUtil.parseDateAndTime("06/11/2010 12:03:10"),"-"
		 * ));
		 * 
		 * //Prejuizo
		 * list.add(PopulateOperationData.createOperationMap("HBOR3",OperationType
		 * .
		 * BUY,1200,22.46,FormatUtil.parseDateAndTime("07/11/2010 12:03:10"),"-"
		 * ));
		 * list.add(PopulateOperationData.createOperationMap("HBOR3",OperationType
		 * .
		 * SELL,1000,21.69,FormatUtil.parseDateAndTime("09/11/2010 12:02:90"),"-"
		 * ));
		 * 
		 * //Sobra
		 * list.add(PopulateOperationData.createOperationMap("MAGG3",OperationType
		 * .
		 * BUY,1200,22.46,FormatUtil.parseDateAndTime("07/12/2010 12:03:10"),"-"
		 * ));
		 * list.add(PopulateOperationData.createOperationMap("ELPL6",OperationType
		 * .
		 * BUY,1200,22.46,FormatUtil.parseDateAndTime("08/12/2010 12:03:10"),"-"
		 * ));
		 */

		return list;
	}

	public EnvironmentFacade getEnvironmentFacade() {
		return environmentFacade;
	}

	public PopulateData getPopulateData() {
		return populateData;
	}

	public AccountFacade getUserConfigurationFacade() {
		return getAccountFacade();
	}

	public AccountFacade getAccountFacade() {
		return accountFacade;
	}

	public PopulateOperationData getPopulateOperationData() {
		return populateOperationData;
	}

	public OperationFacade getOperationFacade() {
		return operationFacade;
	}
}
