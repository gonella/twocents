package com.twocents.bovespa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.BrokerageByMonth;
import com.twocents.model.BrokerageNoteItem;
import com.twocents.model.BrokerageNote;
import com.twocents.model.OperationKey;
import com.twocents.model.OperationType;

public class BrokerageProcess {

	
	
	private static final Logger logger = Logger.getLogger(BrokerageProcess.class);
	

	private static Map<String, Map<String, Integer>> stockAmountMap = new HashMap<String, Map<String, Integer>>();

	/**
	 * Estamos assumindo que a lista de operações estão ordenadas por data(primeiros elementos são os mais velhos)
	 * @param listOperation
	 * @return
	 * @throws CoreException 
	 */
	public static Map<String, BrokerageByMonth> calculateBrokerageNodeByMonth(
			List<Map<String, Object>> listOperation) throws CoreException {
		
		/*logger.info("Gerando as notas de corretagens para as operações negociadas ");
		if(CollectionUtils.isEmpty(listOperation)){
			throw new CoreException(7000);
		}
		//TODO Verificar que a listagem das ordens estao ordenadas por data
				
		
		String dateDayAndMonthAndYear = null;
		String stockCode = null;
		String stockType = null;
		Integer amount = null;
		String operationType = null;		
		Double averageBuyPrice=new Double(0);
		Double price=null;
		String dateMonthAndYear=null;		
		Double totalValue=new Double(0);
		Integer amountOfStock=0;
		String brokerType;
		
		Date fullDateInformation=null;
		

		Map<String,Double> averagePriceOfStock=new HashMap<String,Double>();
		
		int i=0;
		
		String dateDayAndMonthAndYearOld=null;
		String dateMonthAndYearOld=null;

		Map<String, BrokerageByMonth> listBrokerageNoteByMonth = new LinkedHashMap<String, BrokerageByMonth>();
		
		BrokerageByMonth brokerageByMonth=null;
		BrokerageNoteItem itemNote=null;
		
		for (Iterator iterator = listOperation.iterator(); iterator.hasNext();) {
			Map<String, Object> operation = (Map<String, Object>) iterator.next();
					
			//Mercado A Vista, Futuro
			stockType = operation.get(OperationKey.STOCK_TYPE.toString()).toString();
			
			//Compra ou Venda
			operationType = operation.get(OperationKey.OPERATION_TYPE.toString()).toString();			
			
			stockCode = operation.get(OperationKey.STOCK.toString()).toString();
			amount = new Integer(operation.get(OperationKey.AMOUNT.toString()).toString());
			price = new Double(operation.get(OperationKey.PRICE.toString()).toString());
			
			brokerType = new String(operation.get(OperationKey.BROKERTYPE.toString()).toString());
			
			fullDateInformation = FormatUtil.parseDateAndTime(FormatUtil.formatDateAndTime(operation.get(OperationKey.OPERATION_DATE.toString())));
			
			dateDayAndMonthAndYear = FormatUtil.formatDate(fullDateInformation);
			dateMonthAndYear = FormatUtil.formatDateByYearAndMonth(fullDateInformation); 

			logger.info("Operation -> Date:"+dateDayAndMonthAndYear+" Type:"+stockType+" ["+operationType+"] - ["+stockCode+"] - Qtd:"+amount+" Preço:"+price+" BrokerType:"+brokerType);
			//logger.info("dateMonthAndYear: "+dateMonthAndYear);						
			
			if(!dateMonthAndYear.equals(dateMonthAndYearOld)){			
				brokerageByMonth=new BrokerageByMonth(FormatUtil.parseDateByYearAndMonth(dateMonthAndYear));
			}
			
			//Adicionado lotes comprados
			if (OperationType.BUY.toString().equalsIgnoreCase(operationType)) {
				doChangeAmount(stockCode, dateDayAndMonthAndYear, amount);
				
				amountOfStock=retrieveAmountOfStock(stockAmountMap, stockCode);
				
				averageBuyPrice=putAveragePriceOfStock(averagePriceOfStock, stockCode, amountOfStock, price, amount);
				
				
				public BrokerageItemNote(String stockCode,String stockType, OperationType operationType,			
						Integer amount,Double averageBuyPrice,Double priceSell			
						,Date operationDate, boolean isDayTrade,String brokerType){
				
				
				itemNote = new BrokerageNoteItem(
						stockCode,stockType,OperationType.BUY, 
						amountOfStock, 
						averageBuyPrice, 
						0.0,
						fullDateInformation,
						false, 
						brokerType);
				
				brokerageByMonth.addBrokerageItemNote(dateDayAndMonthAndYear,itemNote);				
			}
			//Quando encontrar uma venda na lista, a operação vai ser liquidada com as quantidades guardadas na hashmap "stockAmountMap"
			else if (OperationType.SELL.toString().equalsIgnoreCase(operationType)) {
				
				//verificar se tem lotes comprados serao liquidados em daytrade
				boolean isDayTrade=isDayTradeInAssetsBuy(stockAmountMap, stockCode, dateDayAndMonthAndYear);
				if(isDayTrade){
					//Removendo lotes comprados/vendidos em daytrade
					Integer amountNotLiquidate=doLiquidateAsDayTrade(stockCode, dateDayAndMonthAndYear, amount);
					
					itemNote = new BrokerageNoteItem(
							stockCode,stockType,OperationType.SELL, 
							Math.abs(amount - amountNotLiquidate), 
							averageBuyPrice, 
							price,
							fullDateInformation,
							isDayTrade, 
							brokerType);
					
					brokerageByMonth.addBrokerageItemNote(dateDayAndMonthAndYear,itemNote);	
					
					if(!(amountNotLiquidate==0)){
						//Significa que tem mais lotes para ser liquidados, porém não em day trade.
						doLiquidate(stockCode, amountNotLiquidate);
						
						itemNote = new BrokerageNoteItem(
								stockCode,stockType,OperationType.SELL, 
								amountNotLiquidate, 
								averageBuyPrice, 
								price,
								fullDateInformation,
								isDayTrade, 
								brokerType);
						
						brokerageByMonth.addBrokerageItemNote(dateDayAndMonthAndYear,itemNote);	
															
					}
					else{
						averagePriceOfStock.remove(stockCode);
					}
					
				}else{//NOT DAYTRADE
					
					//Procurar por outras datas e assim liquidar com os lotes
					//Removendo lotes comprados com datas passadas através de uma venda
					doLiquidate(stockCode, amount);
					
					itemNote = new BrokerageNoteItem(
							stockCode,stockType,OperationType.SELL, 
							amount, 
							averageBuyPrice, 
							price,
							fullDateInformation,
							isDayTrade, 
							brokerType);
					
					brokerageByMonth.addBrokerageItemNote(dateDayAndMonthAndYear,itemNote);	
					
				}
				
			}
			
			dateDayAndMonthAndYearOld=dateDayAndMonthAndYear;
			dateMonthAndYearOld=dateMonthAndYear;
			
			brokerageByMonth.close();
			
			listBrokerageNoteByMonth.put(dateMonthAndYear, brokerageByMonth);

			i++;
		}

		//viewAmountMap(stockAmountMap);
		return listBrokerageNoteByMonth;

*/
		
		return null;
	}

	
	public  Map<String, BrokerageByMonth> reverseOrder(Map<String, BrokerageByMonth> map){

		Collection<BrokerageByMonth> brokerageNoteByMonthCollection= map.values();
		List<BrokerageByMonth> brokerageNoteByMonthList = new ArrayList<BrokerageByMonth>(brokerageNoteByMonthCollection);
		Collections.sort(brokerageNoteByMonthList,Collections.reverseOrder()); // Sorting it (in reverse order)
		
		for (BrokerageByMonth brokerageByMonth:brokerageNoteByMonthList) {
			
			Collection<BrokerageNote> brokerageNoteCollection = brokerageByMonth.getListNote();
			
			List<BrokerageNote> brokerageNoteList = new ArrayList<BrokerageNote>(brokerageNoteCollection);
			Collections.sort(brokerageNoteList,Collections.reverseOrder()); // Sorting it (in reverse order)
			
		}
		
		return null;
	}
	
	/**
	 * Modifica a quantidade de lotes para cada ação. È passado o dia e a quantidade para ser alterada no map. 
	 * Porém exite detalhes de quando a liquidação de um dia for maior,	 *  logo preciso pegar de outro dia(retornando a qtd não liquidada). 
	 */
	private static Integer doChangeAmount(
			String stock,
			String dateDayAndMonthAndYear, int amount) {

		//logger.info(">> Changing Amount: Stock :" + stock + " (" + date + ") - [" + amount + "]");
		
		Integer AmountNotLiquidate=0;
		int tempCalc=0;

		if (stockAmountMap.containsKey(stock)) {

			Map<String, Integer> map = stockAmountMap.get(stock);

			if (map.containsKey(dateDayAndMonthAndYear)) {

				Integer value = map.get(dateDayAndMonthAndYear);

				tempCalc = value + amount;

				value += amount;
				
				if(value==0){
					//AmountNotLiquidate=Math.abs(tempCalc);
					map.remove(dateDayAndMonthAndYear);
				}else{	
					map.put(dateDayAndMonthAndYear, value);
				}
				
			} else {

				if (map == null) {
					map = new HashMap<String, Integer>();
				}
				map.put(dateDayAndMonthAndYear, amount);
			}
			stockAmountMap.put(stock, map);

		} else {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put(dateDayAndMonthAndYear, amount);
			stockAmountMap.put(stock, map);
		}

		return AmountNotLiquidate;
	}

	/**
	 * 
	 * Dentro do map, apenas existe a quantidade de stock compradas, logo se
	 * existir um venda com um data q esta dentro do map, significa day trade.
	 * 
	 */
	private static boolean isDayTradeInAssetsBuy(
			Map<String, Map<String, Integer>> stockAmountMap, String stock,
			String date) {
		boolean result = false;

		if (stockAmountMap.containsKey(stock)) {

			Map<String, Integer> map = stockAmountMap.get(stock);

			if (map.containsKey(date)) {
				result = true;
			} else {
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}

	private static void viewAmountMap(Map<String, Map<String, Integer>> stockAmountMap) {

		logger.info("----View the Temp Custody Map");

		Set<String> keySet = stockAmountMap.keySet();

		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String keyStock = (String) iterator.next();

			logger.info("# " + keyStock);
			Map<String, Integer> map = stockAmountMap.get(keyStock);

			Set<String> keySet2 = map.keySet();
			for (Iterator iterator2 = keySet2.iterator(); iterator2.hasNext();) {
				String keyDate = (String) iterator2.next();

				Integer integer = map.get(keyDate);

				logger.info("	- " + keyDate + " - Qtd: " + integer);
			}
		}
	}
	
	
	/**
	 * Procura pela data mais velha para liquidar
	 * 
	 */
	private static void doLiquidate(String keyStock,Integer amountToLiquidate){
		
		logger.info("[Liquidando] Operação [NÃO] Day Trade");
		
		Map<String, Integer> map = stockAmountMap.get(keyStock);
		amountToLiquidate=Math.abs(amountToLiquidate);
		
		Integer calcAmount=0;
		
		Set<String> keySet2 = map.keySet();
		for (Iterator iterator2 = keySet2.iterator(); iterator2.hasNext();) {
			String keyDate = (String) iterator2.next();
			Integer amountAvailable = map.get(keyDate);
			
			calcAmount=amountAvailable - amountToLiquidate;
			
			if(calcAmount == 0){		
				map.remove(keyDate);
				
				break;				
			}else if(calcAmount > 0){
				map.put(keyDate, calcAmount);
				break;
			}else if(calcAmount < 0){
				map.remove(keyDate);
				amountToLiquidate=calcAmount;
			}
		}
		stockAmountMap.put(keyStock, map);		
	}
	
	private static Integer doLiquidateAsDayTrade(
			String stock,
			String dateDayAndMonthAndYear, int amount) {
		logger.info("[Liquidando] Operação Day Trade");
				
		return doChangeAmount(stock, dateDayAndMonthAndYear, (- amount) );
	
	}
	
	
	
	private static Integer retrieveAmountOfStock(Map<String, Map<String, Integer>> stockAmountMap,String stock) {
	
		Map<String, Integer> map = stockAmountMap.get(stock);

		Integer totalAmountOfStock=0;
		
		Set<String> keySet2 = map.keySet();
		for (Iterator iterator2 = keySet2.iterator(); iterator2.hasNext();) {
			String keyDate = (String) iterator2.next();
			Integer amount = map.get(keyDate);
			
			totalAmountOfStock+=amount;
		}
		
		return totalAmountOfStock;
	}
	
	private static Double putAveragePriceOfStock(Map<String,Double> averagePriceOfStock,String stock,Integer amountExistent,Double newPrice,Integer newAmount) {
		
		Double currentPrice = averagePriceOfStock.get(stock);
		Double newAveragePrice = newPrice;
		
		if(currentPrice==null){
			averagePriceOfStock.put(stock, newPrice);
		}else{
			if(newPrice!=null && newAmount!=null){
				
				Double currentTotal=amountExistent*currentPrice;
				Double newTotal=newPrice*newAmount;
				
				newAveragePrice=(currentTotal+newTotal)/(amountExistent+newAmount);
				
				averagePriceOfStock.put(stock, newAveragePrice);
			}
			
		}

		return newAveragePrice;
	}
	
	public static void viewCustody(){
		viewAmountMap(stockAmountMap);
	}
	
}
