package com.twocents.test.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Broker;
import com.twocents.model.OperationKey;
import com.twocents.model.OperationType;
import com.twocents.model.StockBroker;
import com.twocents.ui.exceptions.UIException;

public class TestCoreImport extends TestEnvironment {

	public static final Logger logger = Logger.getLogger(TestCoreImport.class);

	public static void main(String arg[]) throws CoreException {

		new TestCoreImport();

	}

	public void createStockBrokerMockData() {
		getDefaultdata().createStockBrokerMockData();
	}

	public static void loadDataForStockBrokers(
			List<Map<OperationKey, Object>> list) throws UIException {

		/*
		 * Calendar instance=Calendar.getInstance(); instance.set(2010, 01, 01);
		 * Timestamp startDate=new Timestamp(instance.getTime().getTime());
		 * 
		 * Calendar instance2 = Calendar.getInstance(); instance2.set(2010, 11,
		 * 11); Timestamp endDate=new Timestamp(instance2.getTime().getTime());
		 */
		List<StockBroker> listAllStockBroker = getEnvironmentFacade()
				.listAllStockBroker();

		for (Iterator iteratorsb = listAllStockBroker.iterator(); iteratorsb
				.hasNext();) {
			StockBroker stockBroker = (StockBroker) iteratorsb.next();

			logger.info("Getting information about StockBroker:"
					+ stockBroker.getName());

			List<Account> accountList = stockBroker.getAccountList();

			for (Iterator iterator = accountList.iterator(); iterator.hasNext();) {
				Account account = (Account) iterator.next();

				registerOperations(account, list);

				// List<Map<String, Object>>
				// listOperation=getOperationFacade().findOperationByAccountWithDateOrdered(account,
				// startDate, endDate);
				// logger.info("#"+account.getUser().getName()+" - Operation Size:"+listOperation.size());
			}

		}
	}

	private static void registerOperations(Account account,
			List<Map<OperationKey, Object>> list) {

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<OperationKey, Object> map = (Map<OperationKey, Object>) iterator
					.next();

			String stockCode = (String) map.get(OperationKey.STOCK_ID);
			OperationType operationType = (OperationType) map
					.get(OperationKey.OPERATION_TYPE);
			Integer amount = (Integer) map.get(OperationKey.AMOUNT);
			Double price = (Double) map.get(OperationKey.PRICE);
			Broker broker = (Broker) map.get(OperationKey.BROKER);
			// String description=(String) map.get(OperationKey.DESCRIPTION);
			Date date = (Date) map.get(OperationKey.OPERATION_DATE);

			// PopulateOperationData.registerOperation(operationType,stockCode,
			// amount, price, brokerType, description,timeStamp);

			try {
				getPopulateOperationdata().registerOperation(operationType,
						date, stockCode, amount, price, account, broker);
			} catch (CoreException e) {
				e.printStackTrace();
			}

		}

	}

	public List<Map<OperationKey, Object>> mockOperationsData() {
		List<Map<OperationKey, Object>> list = new ArrayList<Map<OperationKey, Object>>();

		// list.add(PopulateOperationData.createOperationMap("BRKM5",OperationType.BUY,1000,30.0,FormatUtil.parseDateAndTime("01/06/2010 11:00:00"),BrokerType.BANIFINVEST,"-"));
		// list.add(PopulateOperationData.createOperationMap("BRKM5",OperationType.SELL,500,37.0,FormatUtil.parseDateAndTime("05/06/2010 12:00:00"),BrokerType.BANIFINVEST,"-"));
		//		
		// list.add(PopulateOperationData.createOperationMap("FESA4",OperationType.BUY,1000,30.0,FormatUtil.parseDateAndTime("06/06/2010 11:00:00"),BrokerType.BANIFINVEST,"-"));
		// list.add(PopulateOperationData.createOperationMap("FESA4",OperationType.SELL,500,37.0,FormatUtil.parseDateAndTime("07/06/2010 12:00:00"),BrokerType.BANIFINVEST,"-"));
		//		
		// list.add(PopulateOperationData.createOperationMap("PETR4",OperationType.BUY,1000,30.0,FormatUtil.parseDateAndTime("01/07/2010 11:00:00"),BrokerType.BANIFINVEST,"-"));
		// list.add(PopulateOperationData.createOperationMap("PETR4",OperationType.SELL,500,37.0,FormatUtil.parseDateAndTime("01/07/2010 12:00:00"),BrokerType.BANIFINVEST,"-"));
		//		
		// list.add(PopulateOperationData.createOperationMap("GGBR4",OperationType.BUY,1000,15.0,FormatUtil.parseDateAndTime("02/07/2010 13:00:00"),BrokerType.BANIFINVEST,"-"));
		// list.add(PopulateOperationData.createOperationMap("GGBR4",OperationType.SELL,1000,13.0,FormatUtil.parseDateAndTime("02/07/2010 14:00:00"),BrokerType.BANIFINVEST,"-"));
		//		
		// list.add(PopulateOperationData.createOperationMap("VALE5",OperationType.BUY,1000,20.0,FormatUtil.parseDateAndTime("04/07/2010 13:00:00"),BrokerType.BANIFINVEST,"-"));
		// list.add(PopulateOperationData.createOperationMap("VALE5",OperationType.BUY,3000,30.0,FormatUtil.parseDateAndTime("05/07/2010 15:00:00"),BrokerType.BANIFINVEST,"-"));
		//		
		// list.add(PopulateOperationData.createOperationMap("GGBR4",OperationType.BUY,3000,20.0,FormatUtil.parseDateAndTime("05/07/2010 10:00:00"),BrokerType.BANIFINVEST,"-"));
		// list.add(PopulateOperationData.createOperationMap("GGBR4",OperationType.SELL,1500,30.0,FormatUtil.parseDateAndTime("05/07/2010 11:00:00"),BrokerType.BANIFINVEST,"-"));
		//		
		// list.add(PopulateOperationData.createOperationMap("GFSA3",OperationType.BUY,1000,20.0,FormatUtil.parseDateAndTime("05/07/2010 15:00:00"),BrokerType.BANIFINVEST,"-"));
		//		
		// list.add(PopulateOperationData.createOperationMap("POMO4",OperationType.BUY,1000,20.0,FormatUtil.parseDateAndTime("05/07/2010 15:00:00"),BrokerType.BANIFINVEST,"-"));
		// list.add(PopulateOperationData.createOperationMap("POMO4",OperationType.SELL,1000,21.0,FormatUtil.parseDateAndTime("06/07/2010 16:00:00"),BrokerType.BANIFINVEST,"-"));
		//		
		// list.add(PopulateOperationData.createOperationMap("PDGR3",OperationType.BUY,1000,10.0,FormatUtil.parseDateAndTime("07/07/2010 15:00:00"),BrokerType.BANIFINVEST,"-"));
		// list.add(PopulateOperationData.createOperationMap("PDGR3",OperationType.SELL,1000,9.0,FormatUtil.parseDateAndTime("08/07/2010 16:00:00"),BrokerType.BANIFINVEST,"-"));
		//		
		//		
		// list.add(PopulateOperationData.createOperationMap("BBDC4",OperationType.BUY,1000,10.0,FormatUtil.parseDateAndTime("08/07/2010 15:00:00"),BrokerType.BANIFINVEST,"-"));
		//		
		// list.add(PopulateOperationData.createOperationMap("BBDC4",OperationType.SELL,500,13.0,FormatUtil.parseDateAndTime("09/07/2010 10:00:00"),BrokerType.BANIFINVEST,"-"));
		//		
		// list.add(PopulateOperationData.createOperationMap("BBDC4",OperationType.BUY,1000,10.0,FormatUtil.parseDateAndTime("09/07/2010 15:00:00"),BrokerType.BANIFINVEST,"-"));
		// list.add(PopulateOperationData.createOperationMap("BBDC4",OperationType.SELL,500,14.0,FormatUtil.parseDateAndTime("09/07/2010 16:00:00"),BrokerType.BANIFINVEST,"-"));
		//		
		// list.add(PopulateOperationData.createOperationMap("MMXM3",OperationType.BUY,1000,10.0,FormatUtil.parseDateAndTime("09/07/2010 15:01:00"),BrokerType.BANIFINVEST,"-"));
		//		
		return list;

	}

	/*
	 * private static List<Map<OperationKey, Object>> mockRealOperationData01()
	 * throws CoreException{ List<Map<OperationKey, Object>> list=new
	 * ArrayList<Map<OperationKey,Object>>();
	 * 
	 * 
	 * list.add(PopulateOperationData.createOperationMap("PETR4",OperationType.BUY
	 * ,1000,30.0,FormatUtil.parseDateAndTime("01/07/2010 11:00:00"),BrokerType.
	 * BANIFINVEST,"-"));
	 * list.add(PopulateOperationData.createOperationMap("PETR4"
	 * ,OperationType.SELL
	 * ,500,37.0,FormatUtil.parseDateAndTime("01/07/2010 12:00:00"
	 * ),BrokerType.BANIFINVEST,"-"));
	 * 
	 * 
	 * list.add(PopulateOperationData.createOperationMap("GGBR4",OperationType.BUY
	 * ,1000,15.0,FormatUtil.parseDateAndTime("02/07/2010 13:00:00"),BrokerType.
	 * BANIFINVEST,"-"));
	 * list.add(PopulateOperationData.createOperationMap("GGBR4"
	 * ,OperationType.SELL
	 * ,1000,13.0,FormatUtil.parseDateAndTime("02/07/2010 14:00:00"
	 * ),BrokerType.BANIFINVEST,"-"));
	 * 
	 * 
	 * list.add(PopulateOperationData.createOperationMap("VALE5",OperationType.BUY
	 * ,1000,20.0,FormatUtil.parseDateAndTime("04/07/2010 13:00:00"),BrokerType.
	 * BANIFINVEST,"-"));
	 * list.add(PopulateOperationData.createOperationMap("VALE5"
	 * ,OperationType.BUY
	 * ,3000,30.0,FormatUtil.parseDateAndTime("05/07/2010 15:00:00"
	 * ),BrokerType.BANIFINVEST,"-"));
	 * 
	 * 
	 * list.add(PopulateOperationData.createOperationMap("GGBR4",OperationType.BUY
	 * ,3000,20.0,FormatUtil.parseDateAndTime("05/07/2010 10:00:00"),BrokerType.
	 * BANIFINVEST,"-"));
	 * list.add(PopulateOperationData.createOperationMap("GGBR4"
	 * ,OperationType.SELL
	 * ,1500,30.0,FormatUtil.parseDateAndTime("05/07/2010 11:00:00"
	 * ),BrokerType.BANIFINVEST,"-"));
	 * 
	 * 
	 * list.add(PopulateOperationData.createOperationMap("GFSA3",OperationType.BUY
	 * ,1000,20.0,FormatUtil.parseDateAndTime("05/07/2010 15:00:00"),BrokerType.
	 * BANIFINVEST,"-"));
	 * 
	 * return list;
	 * 
	 * }
	 */

}
