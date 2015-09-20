package com.twocents.test.core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.twocents.bovespa.BrokerageProcess;
import com.twocents.core.populate.PopulateOperationData;
import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.BrokerType;
import com.twocents.model.BrokerageByMonth;
import com.twocents.model.BrokerageNote;
import com.twocents.model.BrokerageNoteItem;
import com.twocents.model.OperationKey;
import com.twocents.model.OperationType;
import com.twocents.model.StockBroker;

@SuppressWarnings("unchecked")
public class TestCoreBrokerage extends TestEnvironment{

	public static Logger logger = Logger.getLogger(TestCoreBrokerage.class);
	
	private static List<Map<OperationKey, Object>> mockData(){
		List<Map<OperationKey, Object>> list=new ArrayList<Map<OperationKey,Object>>();
	
		//Nota de corretagens reais
		//Nota 1
		//list.add(PopulateOperationData.createOperationMap("HGTX3",OperationType.BUY,1000,28.3,FormatUtil.parseDateAndTime("01/11/2010 11:00:00"),BrokerType.BANIFINVEST,"-"));

		//list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,1200,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:10"),BrokerType.BANIFINVEST,"-"));
		//list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.SELL,1200,22.69,FormatUtil.parseDateAndTime("04/11/2010 12:00:90"),BrokerType.BANIFINVEST,"-"));

		//Nota 2
		/*list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,500,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:10"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:20"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:30"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:40"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:50"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.SELL,100,22.69,FormatUtil.parseDateAndTime("04/11/2010 12:00:60"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:70"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:00:80"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.SELL,1000,22.69,FormatUtil.parseDateAndTime("04/11/2010 12:00:90"),BrokerType.BANIFINVEST,"-"));
		list.add(PopulateOperationData.createOperationMap("CYRE3",OperationType.BUY,100,22.46,FormatUtil.parseDateAndTime("04/11/2010 12:01:00"),BrokerType.BANIFINVEST,"-"));
*/
		//Nota 3
		//list.add(PopulateOperationData.createOperationMap("HBOR3",OperationType.BUY,1400,19.56,FormatUtil.parseDateAndTime("25/11/2010 12:01:00"),BrokerType.BANIFINVEST,"-"));
		
		return list;
	}
	
	public static void main(String arg[]) throws CoreException{
		
		//Se não houver dados populados, chamar metodos abaixo:
		//TestCoreImport.initData(); //default mock
		//ou
		//TestCoreImport.createStockBrokerMockData();
		//TestCoreImport.loadDataForStockBrokers(mockData());
		
		
		Collection<BrokerageByMonth> collectionBrokerageNoteByMonth=null;
		Map<String, BrokerageByMonth> mapBrokerageNoteByMonth=null;

		logger.info("Testando corretagem das operações");
		Calendar instance1 = Calendar.getInstance();
		instance1.set(2010, 0, 1);		
		Timestamp startDate=new Timestamp(instance1.getTime().getTime());		
		
		Calendar instance2 = Calendar.getInstance();
		instance2.set(2010, 11, 11);
		Timestamp endDate=new Timestamp(instance2.getTime().getTime());
		
		List<StockBroker> listAllStockBroker = getEnvironmentFacade().listAllStockBroker();
		if(CollectionUtils.isEmpty(listAllStockBroker)) {
			logger.info("listAllStockBroker empty");
			System.exit(-1);
		}
		for (Iterator iteratorsb = listAllStockBroker.iterator(); iteratorsb
				.hasNext();) {
			StockBroker stockBroker = (StockBroker) iteratorsb.next();
		
			logger.info("Getting information about StockBroker:"+stockBroker.getName());
			
			List<Account> accountList = stockBroker.getAccountList();
			
			for(Account account:accountList){
				
				//List<Map<String, Object>> listOperation = getOperationFacade().findOperationByAccountWithDateOrderedDesc(account, startDate, endDate);
				List<Map<String, Object>> listOperation = getOperationFacade().findOperationByAccountWithDateOrdered(account, startDate, endDate);
				
				if(CollectionUtils.isEmpty(listOperation)) {
					logger.info("List empty");
					break;
				}
				
				logger.info("Operations[" + listOperation.size() + "] for :["
						+ account.getUser().getName()+"] Start Data:"+startDate+" End Date:"+endDate+" - Operation Size:["+listOperation+"]" );
				
				
				/*for (Iterator iterator = listOperation.iterator(); iterator.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) iterator.next();
					 
					logger.info("ID:"+map.get(OperationKey.ID.toString())+" - "+"Tipo: "+map.get(OperationKey.OPERATION_TYPE.toString()) 
							+ " Data: "+FormatUtil.formatDate(map.get(OperationKey.OPERATION_DATE.toString())));
					
				}*/
				
				mapBrokerageNoteByMonth = BrokerageProcess.calculateBrokerageNodeByMonth(listOperation);
				
				logger.info("####### BrokerageByMonth ["+mapBrokerageNoteByMonth.values().size()+"]");
				
				//Reverse Order
				List<BrokerageByMonth> listBrokerageNoteByMonth=new ArrayList<BrokerageByMonth>(mapBrokerageNoteByMonth.values());
				Collections.reverse(listBrokerageNoteByMonth);
				
				Collection<BrokerageNote> collectionbrokerageNote=null;
				
				for (Iterator iterator = listBrokerageNoteByMonth.iterator(); iterator.hasNext();) {
					
					BrokerageByMonth brokerageByMonth = (BrokerageByMonth) iterator.next();
					logger.info("--------------------------- ["+FormatUtil.formatDateByYearAndMonth(brokerageByMonth.getDateYearAndMonth())+"]");
					collectionbrokerageNote = brokerageByMonth.getListNote();
				    logger.info("**** BrokerageNote["+collectionbrokerageNote.size()+"]");

				    List<BrokerageNote> listBrokerageNote=new ArrayList<BrokerageNote>(collectionbrokerageNote);
					Collections.reverse(listBrokerageNote);
					
					for (Iterator iterator2 = listBrokerageNote.iterator(); iterator2
							.hasNext();) {
						BrokerageNote brokerageNote = (BrokerageNote) iterator2.next();
						
						Set<BrokerageNoteItem> brokerageItemNoteList = brokerageNote.getBrokerageItemNoteList();
						
						logger.info("* BrokerageNote: "+FormatUtil.formatDate(brokerageNote.getDate())+" -------- Items dentro da nota:"+brokerageItemNoteList.size());
						
					    //SortedSet<String> sortedset= new TreeSet<String>(theSimpsons.keySet());

						/*Double rentabilidadeAcao = brokerageNote.getFinancialSummary().getRentabilidadeAcao();
						if(rentabilidadeAcao!=null){
							logger.info("  > Rentabilidade :"+rentabilidadeAcao);
						}
						Double rentabilidadeAcaoDayTrade = brokerageNote.getFinancialSummary().getRentabilidadeAcaoDayTrade();
						if(rentabilidadeAcaoDayTrade!=null){
							logger.info("  > Rentabilidade(Daytrade) :"+rentabilidadeAcaoDayTrade);
						}*/
						
						//logger.info("  Valor das operações:"+FormatUtil.formatCurrency(brokerageNote.getFinancialSummary().getValorDasOperações()));
						//logger.info("  > Valor liquido das operações:"+FormatUtil.formatCurrency(brokerageNote.getValorLiquidoDasOperações()));
						//logger.info("  > Taxa de Liquidacao:"+FormatUtil.formatCurrency(brokerageNote.getFinancialSummary().getTaxaLiquidacao()));
						logger.info("  # Total CBLC:"+FormatUtil.toReal(brokerageNote.getTotalCBLC()));
						//logger.info("  > Emolumento:"+FormatUtil.formatCurrency(brokerageNote.getEmolumentos()));
						logger.info("  # Total Bovespa e Soma:"+FormatUtil.toReal(brokerageNote.getTotalBovespaESoma()));
						//logger.info("  > Corretagem:"+FormatUtil.formatCurrency(brokerageNote.getCorretagem()));
						//logger.info("  > ISS:"+FormatUtil.formatCurrency(brokerageNote.getISS()));
						logger.info("  # Total Corretagem e Despesas:"+FormatUtil.toReal(brokerageNote.getTotalCorretagemEDespesas()));
					
						/*
						for (Iterator iterator3 = brokerageItemNoteList.iterator(); iterator3
								.hasNext();) {
							BrokerageItemNote brokerageItemNote = (BrokerageItemNote) iterator3
									.next();
							
							logger.info("Item:"
									+" Stock:"+brokerageItemNote.getStockCode()
									+" Operation:"+brokerageItemNote.getOperationType()
									+" Amount:"+brokerageItemNote.getAmount()
									+" Buy Price:"+brokerageItemNote.getAverageBuyPrice()
									+" Sell Price:"+brokerageItemNote.getPriceSell()
									+" Date:"+brokerageItemNote.getOperationDate());
							
						}
						*/
					}
					
				}
				//BrokerageProcess.viewCustody();
				
				//new TaxProcess().calculateTaxInvolved(listBrokerageNoteByMonth);
				
				//CalculateAccount.calculateAccountDataByStockTypeAndMonth(listBrokerageNote);
			
			//Testando somente na primera conta do corretor	
			break;
			}
		}
		if(CollectionUtils.isEmpty(collectionBrokerageNoteByMonth)) {
			logger.info("listAllStockBroker empty");
			System.exit(-1);
		}
		
	}

	@Override
	public void createStockBrokerMockData() {
		
	}

	@Override
	public List<Map<OperationKey, Object>> mockOperationsData() {
		return null;
	}
	
}
