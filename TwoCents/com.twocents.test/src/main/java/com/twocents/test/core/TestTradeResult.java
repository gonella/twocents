package com.twocents.test.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.twocents.bovespa.TradeProcess;
import com.twocents.core.populate.PopulateOperationData;
import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.BrokerageNote;
import com.twocents.model.Income;
import com.twocents.model.OperationKey;
import com.twocents.model.OperationType;
import com.twocents.model.StockBroker;
import com.twocents.ui.exceptions.UIException;

@SuppressWarnings("unchecked")
public class TestTradeResult extends TestEnvironment {

	public static final Logger logger = Logger.getLogger(TestTradeResult.class);

	public static void main(String arg[]) {

		TestTradeResult testTradeResult = new TestTradeResult();

		logger.info("Testing-------------------------------------------------");
		List<StockBroker> listAllStockBroker;
		try {
			listAllStockBroker = getEnvironmentFacade().listAllStockBroker();
		} catch (UIException e) {
			e.printStackTrace();
			return;
		}

		for (Iterator iteratorsb = listAllStockBroker.iterator(); iteratorsb
				.hasNext();) {
			StockBroker stockBroker = (StockBroker) iteratorsb.next();

			logger.info("Getting information about StockBroker :"
					+ stockBroker.getName());

			List<Account> accountList = stockBroker.getAccountList();

			for (Iterator iterator = accountList.iterator(); iterator.hasNext();) {
				Account account = (Account) iterator.next();
				logger.info("Conta :" + account.getUser().getName());

				List<BrokerageNote> listAllBrokerageNote = null;

				try {
					listAllBrokerageNote = getOperationFacade()
							.getBrokerageNoteService()
							.findAllBrokerageNoteByAccount(account);
				} catch (CoreException e) {
					logger.error(e);
				}
				int size = listAllBrokerageNote != null ? listAllBrokerageNote
						.size() : 0;

				// logger.info("listAllBrokerageNote: " + size);
				TradeProcess tradeProcess = new TradeProcess();
				try {
					Map<String, Income> listIncome = tradeProcess
							.findMonthIncome(listAllBrokerageNote);

					Set<String> keySet = listIncome.keySet();
					for (Iterator iterator2 = keySet.iterator(); iterator2
							.hasNext();) {
						String keyDate = (String) iterator2.next();
						Income income = listIncome.get(keyDate);
						logger.info(income);
					}

				} catch (CoreException e) {
					logger.error(e);
				}
				tradeProcess.viewAmountMap();

				break;
			}
			break;
		}
		/*
		 * logger.info("######################## REMOVENDO"); List<Account>
		 * findAll = getEnvironmentFacade().getAccountService() .findAll();
		 * 
		 * for (Account account : findAll) {
		 * logger.info(account.getUser().getName());
		 * 
		 * List<BrokerageNote> findAllBrokerageNoteByAccount; try {
		 * findAllBrokerageNoteByAccount = getOperationFacade()
		 * .getBrokerageNoteService() .findAllBrokerageNoteByAccount(account);
		 * 
		 * int size = findAllBrokerageNoteByAccount != null ?
		 * findAllBrokerageNoteByAccount .size() : 0; logger.info("Notes :" +
		 * size);
		 * 
		 * getEnvironmentFacade().getAccountService().removeAccount( account);
		 * logger.info("Removed"); } catch (CoreException e) { logger.error(e);
		 * } }
		 */
	}

	@Override
	public void createStockBrokerMockData() {
		getDefaultdata().createStockBrokerMockData();

	}

	@Override
	public List<Map<OperationKey, Object>> mockOperationsData() {

		List<Map<OperationKey, Object>> list = new ArrayList<Map<OperationKey, Object>>();

		try {

			// Nota de corretagens reais
			// Nota 1
			// list.add(PopulateOperationData.createOperationMap("HGTX3",OperationType.BUY,1000,28.3,FormatUtil.parseDateAndTime("01/11/2010 11:00:00"),"-"));

			// Nota 2
			// list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,1000,26.46,FormatUtil.parseDateAndTime("01/01/2011 11:01:00"),"-"));
			// list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.SELL,1000,26.69,FormatUtil.parseDateAndTime("02/01/2011 11:02:00"),"-"));

			/*
			 * list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType
			 * .
			 * BUY,1000,20.00,FormatUtil.parseDateAndTime("02/01/2011 11:03:00")
			 * ,"-"));
			 * 
			 * //Nota 3
			 * list.add(PopulateOperationData.createOperationMap("CYRE3"
			 * ,OperationType
			 * .SELL,1000,21.00,FormatUtil.parseDateAndTime("02/01/2011 11:04:00"
			 * ),"-"));
			 */

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

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public TestTradeResult() {
		super();
	}

}
