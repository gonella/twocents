package com.twocents.test.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.twocents.core.populate.PopulateOperationData;
import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.BrokerType;
import com.twocents.model.BrokerageNote;
import com.twocents.model.BrokerageNoteItem;
import com.twocents.model.OperationKey;
import com.twocents.model.OperationType;

public class TestAddOperation extends TestEnvironment{
	
	public static final Logger logger = Logger.getLogger(TestAddOperation.class);

	
	public static void main(String arg[]) throws CoreException{
	
		
		new TestAddOperation();
		
		List<BrokerageNote> findAll = getOperationFacade().getBrokerageNoteService().findAll();
		
		for (Iterator iterator = findAll.iterator(); iterator.hasNext();) {
			BrokerageNote brokerageNote = (BrokerageNote) iterator.next();
			Date date = brokerageNote.getDate();
			
			Set<BrokerageNoteItem> brokerageItemNoteList = brokerageNote.getBrokerageItemNoteList();
			logger.info(brokerageNote.toString());
			
			for (Iterator iterator2 = brokerageItemNoteList.iterator(); iterator2
			.hasNext();) {
				BrokerageNoteItem brokerageNoteItem = (BrokerageNoteItem) iterator2
					.next();

				logger.info(brokerageNoteItem.toString());
			}
			
		}
	}
	
	public void createStockBrokerMockData() {
		getDefaultdata().createStockBrokerMockData();
	}
	
	/*private static List<Map<OperationKey, Object>> mockRealData(){
		List<Map<OperationKey, Object>> list=new ArrayList<Map<OperationKey,Object>>();
	
		//Nota de corretagens reais
		//Nota 1
		//list.add(PopulateOperationData.createOperationMap("HGTX3",OperationType.BUY,1000,28.3,FormatUtil.parseDateAndTime("01/11/2010 11:00:00"),BrokerType.BANIFINVEST,"-"));

		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,1200,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:10"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.SELL,1200,22.69,FormatUtil.parseDateAndTime("04/11/2010 12:00:90"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("PETR4",OperationType.BUY,1200,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:01:10"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("PETR4",OperationType.SELL,1200,22.69,FormatUtil.parseDateAndTime("04/11/2010 12:02:90"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("VALE5",OperationType.BUY,1200,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:03:10"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("EZTC3",OperationType.BUY,1200,22.46,FormatUtil.parseDateAndTime("05/11/2010 12:03:10"),BrokerType.BANIFINVEST,"-"));
		
		//Nota 2
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,500,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:10"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:20"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:30"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:40"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:50"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.SELL,100,22.69,FormatUtil.parseDateAndTime("04/11/2010 12:00:60"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:70"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:80"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.SELL,1000,22.69,FormatUtil.parseDateAndTime("04/11/2010 12:00:90"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:01:00"),BrokerType.BANIFINVEST,"-"));

		//Nota 3
		//list.add(PopulateOperationData.createOperationMap("HBOR3",OperationType.BUY,1400,19.56,FormatUtil.parseDateAndTime("25/11/2010 12:01:00"),BrokerType.BANIFINVEST,"-"));
		
		return list;
	}*/

	
	public List<Map<OperationKey, Object>> mockOperationsData(){
		List<Map<OperationKey, Object>> list=new ArrayList<Map<OperationKey,Object>>();
//		
//		//DayTrade prejuizo
//		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,1200,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:10"),BrokerType.BANIFINVEST,"-"));
//		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.SELL,1200,22.19,FormatUtil.parseDateAndTime("04/11/2010 12:00:90"),BrokerType.BANIFINVEST,"-"));
//		
//		//Daytrade lucro
//		list.add(PopulateOperationData.createOperationMap("PETR4",OperationType.BUY,1200,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:01:10"),BrokerType.BANIFINVEST,"-"));
//		list.add(PopulateOperationData.createOperationMap("PETR4",OperationType.SELL,1200,22.69,FormatUtil.parseDateAndTime("04/11/2010 12:02:90"),BrokerType.BANIFINVEST,"-"));
//		
//		//Lucro
//		list.add(PopulateOperationData.createOperationMap("VALE5",OperationType.BUY,1200,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:03:10"),BrokerType.BANIFINVEST,"-"));
//		list.add(PopulateOperationData.createOperationMap("VALE5",OperationType.SELL,1200,24.46,FormatUtil.parseDateAndTime("06/11/2010 12:03:10"),BrokerType.BANIFINVEST,"-"));
//		
//		//Prejuizo
//		list.add(PopulateOperationData.createOperationMap("HBOR3",OperationType.BUY,1200,22.46,FormatUtil.parseDateAndTime("07/11/2010 12:03:10"),BrokerType.BANIFINVEST,"-"));
//		list.add(PopulateOperationData.createOperationMap("HBOR3",OperationType.SELL,1000,21.69,FormatUtil.parseDateAndTime("09/11/2010 12:02:90"),BrokerType.BANIFINVEST,"-"));
//
//		//Sobra
//		list.add(PopulateOperationData.createOperationMap("MAGG3",OperationType.BUY,1200,22.46,FormatUtil.parseDateAndTime("07/12/2010 12:03:10"),BrokerType.BANIFINVEST,"-"));
//		list.add(PopulateOperationData.createOperationMap("ELPL6",OperationType.BUY,1200,22.46,FormatUtil.parseDateAndTime("08/12/2010 12:03:10"),BrokerType.BANIFINVEST,"-"));
//		
		return list;
//		
	}
	
	
}
