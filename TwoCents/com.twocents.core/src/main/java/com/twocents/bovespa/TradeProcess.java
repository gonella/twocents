package com.twocents.bovespa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.BrokerageNote;
import com.twocents.model.BrokerageNoteItem;
import com.twocents.model.Income;
import com.twocents.model.OperationType;

/**
 * Responsável por calcular o rendimento de cada operação. Vai trabalhar em
 * todas as notas de corretagem passadas pelo argumento. Existe dois ponto a
 * serem analisados nessa classe: 1. Calcular a custodia temporaria(+mais
 * preciso medio) para cada momento da nota analisada(doChangeAmount,
 * doLiquidade, doLiquidadeDayTrade). E liquidando quando houver lotes a serem
 * liquidados tanto na COMPRA OU VENDA. 2. Durante CADA liquidação vai ser
 * calculado a rendimento(lucro, prejuizo, taxas envolvidas). A
 * rendimento(income) será mensal.
 * 
 */
public class TradeProcess {

	public static Logger logger = Logger.getLogger(TradeProcess.class);

	private Map<String, Map<String, DataAmount>> stockAmountMap = new HashMap<String, Map<String, DataAmount>>();

	/**
	 * Estamos assumindo que a lista de operações estão ordenadas por
	 * data(primeiros elementos são os mais velhos)
	 * 
	 * @param listOperation
	 * @return TreeMap - Ordenada pela date DESC
	 * @throws CoreException
	 */
	public Map<String, Income> findMonthIncome(
			List<BrokerageNote> listBrokerageNote) throws CoreException {

		logger.debug("# Retornando a  rentabilidade cada operação realizada");
		if (CollectionUtils.isEmpty(listBrokerageNote)) {
			throw new CoreException(7000);
		}
		// TODO Verificar que a listagem das ordens estao ordenadas por data
		// TOOD Talvez no futuro invés de pegar as BrokerageNote, pega todos os
		// items através de uma query.

		String stockCode;
		OperationType operationType;
		Double price;
		Integer amount;
		boolean isDaytrade;

		String dateDayAndMonthAndYear = null;
		String dateDayAndMonthAndYearOld = null;
		String dateMonthAndYearOld = null;
		String dateMonthAndYear = null;
		// TreMap para deixar os objetos ja ordenados
		Map<String, Income> result = new TreeMap<String, Income>(new ComparatorSortDESDateByMonthAndYear());
		Income income = null;

		int i = 0;
		for (Iterator iterator = listBrokerageNote.iterator(); iterator
				.hasNext();) {

			BrokerageNote brokerageNote = (BrokerageNote) iterator.next();

			logger.info(brokerageNote);

			dateMonthAndYear = FormatUtil
					.formatDateByYearAndMonth(brokerageNote.getDate());

			if (!dateMonthAndYear.equals(dateMonthAndYearOld)) {
				income = new Income(brokerageNote.getDate());
			}

			Set<BrokerageNoteItem> setBrokerageNoteItem = brokerageNote
					.getBrokerageItemNoteList();
			List<BrokerageNoteItem> listBrokerageNoteItem = new ArrayList<BrokerageNoteItem>(
					setBrokerageNoteItem);
			// Sort Date by ASC
			Collections.sort(listBrokerageNoteItem);

			for (Iterator iterator2 = listBrokerageNoteItem.iterator(); iterator2.hasNext();) {

				BrokerageNoteItem brokerageNoteItem = (BrokerageNoteItem) iterator2.next();
				logger.info(brokerageNoteItem);

				// vai acumulando todas as taxas encontradas nos items e agrupando mensalmente na rentatividade.
				income.populateWithTax(brokerageNoteItem);

				stockCode = brokerageNoteItem.getStockCode();
				dateDayAndMonthAndYear = FormatUtil.formatDate(brokerageNoteItem.getOperation().getOperationDate());
				operationType = brokerageNoteItem.getOperationType();
				price = brokerageNoteItem.getPrice();
				amount = brokerageNoteItem.getAmount();
				isDaytrade = brokerageNoteItem.isDayTrade();

				// Adicionado lotes comprados
				if (OperationType.BUY.equals(operationType)) {

					Integer retrieveAmount = retrieveAmount(stockCode);

					// Encontro lotes negativos de VENDA, ou seja, da para liquidar, ou seja, VENDA é LIQUÍDA a COMPRA
					if (retrieveAmount < 0) {

						income.doIncome(isDaytrade, retrieveAmount,
								retrieveAveragePrice(stockCode), price);

						if (isDaytrade) {
							doLiquidateAsDayTrade(dateDayAndMonthAndYear,stockCode, amount);
						} else {
							doLiquidate(stockCode, amount);
						}

					}
					// Nao existe, logo, guardo a compra
					else {
						doChangeAmount(stockCode, dateDayAndMonthAndYear,new DataAmount(amount, price));
					}

				}
				// Quando encontrar uma venda na lista, a operação vai ser liquidada com as quantidades guardadas na hashmap "stockAmountMap"
				else if (OperationType.SELL.equals(operationType)) {

					Integer retrieveAmount = retrieveAmount(stockCode);

					// Encontro lotes positivos de COMPRA, ou seja, da para
					// liquidar, ou seja, COMPRA é LIQUIDA a VENDA
					if (retrieveAmount > 0) {

						income.doIncome(isDaytrade, amount,retrieveAveragePrice(stockCode), price);

						if (isDaytrade) {
							doLiquidateAsDayTrade(dateDayAndMonthAndYear,
									stockCode, amount);
						} else {
							doLiquidate(stockCode, amount);
						}

					}
					// Nao existe, logo, guardo a venda
					else {
						doChangeAmount(stockCode, dateDayAndMonthAndYear,
								new DataAmount(-amount, price));
					}
				}

			}

			dateDayAndMonthAndYearOld = dateDayAndMonthAndYear;
			dateMonthAndYearOld = dateMonthAndYear;

			result.put(dateMonthAndYear, income);

			i++;
		}

		// Collections.sort(result, new ComparatorSortDESDateByMonthAndYear());

		return result;
	}

	/**
	 * Procura pela data mais velha para liquidar
	 * 
	 */
	private void doLiquidate(String keyStock, Integer amountToLiquidate

	) {

		logger.debug("[Liquidando] Operação [NÃO] Day Trade");

		Map<String, DataAmount> map = stockAmountMap.get(keyStock);
		amountToLiquidate = Math.abs(amountToLiquidate);

		DataAmount calcAmount = new DataAmount();

		Set<String> keySet2 = map.keySet();
		for (Iterator iterator2 = keySet2.iterator(); iterator2.hasNext();) {
			String keyDate = (String) iterator2.next();
			DataAmount amountAvailable = map.get(keyDate);

			calcAmount.setAmount(amountAvailable.getAmount()
					- amountToLiquidate);

			if (calcAmount.getAmount() == 0) {
				map.remove(keyDate);

				break;
			} else if (calcAmount.getAmount() > 0) {
				map.put(keyDate, calcAmount);
				break;
			} else if (calcAmount.getAmount() < 0) {
				map.remove(keyDate);
				amountToLiquidate = calcAmount.getAmount();
			}
		}
		stockAmountMap.put(keyStock, map);
	}

	private Integer doLiquidateAsDayTrade(String dateDayAndMonthAndYear,
			String stock, int amount

	) {
		logger.debug("[Liquidando] Operação Day Trade");

		return doChangeAmount(stock, dateDayAndMonthAndYear, new DataAmount(
				(-amount), 0.0));

	}

	private Integer retrieveAmount(String stock) {

		Map<String, DataAmount> map = stockAmountMap.get(stock);
		Integer totalAmountOfStock = 0;
		if (map == null) {
			return totalAmountOfStock;
		}

		Set<String> keySet2 = map.keySet();
		for (Iterator iterator2 = keySet2.iterator(); iterator2.hasNext();) {
			String keyDate = (String) iterator2.next();
			DataAmount amount = map.get(keyDate);

			totalAmountOfStock += amount.getAmount();
		}

		return totalAmountOfStock;
	}

	private Double retrieveAveragePrice(String stockCode) {

		Map<String, DataAmount> map = stockAmountMap.get(stockCode);

		if (map == null) {
			return -1.0;
		}

		Double volumeTmp = 0.0;
		Integer amountTmp = 0;

		Set<String> keySet2 = map.keySet();
		for (Iterator iterator2 = keySet2.iterator(); iterator2.hasNext();) {
			String keyDate = (String) iterator2.next();

			DataAmount data = map.get(keyDate);

			volumeTmp = volumeTmp + (data.getAmount() * data.getAveragePrice());

			amountTmp = amountTmp + (data.getAmount());
		}

		Double newAveragePrice = volumeTmp / amountTmp;

		return newAveragePrice;
	}

	public void viewAmountMap() {

		logger.info("----View the Temp Custody Map");

		Set<String> keySet = stockAmountMap.keySet();

		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String keyStock = (String) iterator.next();

			logger.info("# " + keyStock);
			Map<String, DataAmount> map = stockAmountMap.get(keyStock);

			Set<String> keySet2 = map.keySet();
			for (Iterator iterator2 = keySet2.iterator(); iterator2.hasNext();) {
				String keyDate = (String) iterator2.next();

				DataAmount data = map.get(keyDate);

				logger.info("	- " + keyDate + " - Qtd: " + data.getAmount());
			}
		}
	}

	private class DataAmount {

		private Integer amount = 0;

		private Double averagePrice = 0.0;

		public DataAmount(Integer amount, Double averagePrice) {
			this.amount = amount;
			this.averagePrice = averagePrice;

		}

		public DataAmount() {

		}

		public void setAmount(Integer amount) {
			this.amount = amount;
		}

		public Integer getAmount() {
			return amount;
		}

		public void setAveragePrice(Double averagePrice) {
			this.averagePrice = averagePrice;
		}

		public Double getAveragePrice() {
			return averagePrice;
		}

	}

	/**
	 * Modifica a quantidade de lotes para cada ação. È passado o dia e a
	 * quantidade para ser alterada no map. Porém exite detalhes de quando a
	 * liquidação de um dia for maior, * logo preciso pegar de outro
	 * dia(retornando a qtd não liquidada).
	 */
	private Integer doChangeAmount(String stock, String dateDayAndMonthAndYear,
			DataAmount newAmount) {

		// logger.debug(">> Changing Amount: Stock :" + stock + " (" + date +
		// ") - [" + amount + "]");

		Integer AmountNotLiquidate = 0;
		int tempCalc = 0;

		if (stockAmountMap.containsKey(stock)) {

			Map<String, DataAmount> map = stockAmountMap.get(stock);

			if (map.containsKey(dateDayAndMonthAndYear)) {

				DataAmount value = map.get(dateDayAndMonthAndYear);

				tempCalc = value.getAmount() + newAmount.getAmount();

				value.setAmount(value.getAmount() + newAmount.getAmount());

				if (value.getAmount() == 0) {
					// AmountNotLiquidate=Math.abs(tempCalc);
					map.remove(dateDayAndMonthAndYear);
				} else {
					map.put(dateDayAndMonthAndYear, value);
				}

			} else {

				if (map == null) {
					map = new HashMap<String, DataAmount>();
				}
				map.put(dateDayAndMonthAndYear, newAmount);
			}
			stockAmountMap.put(stock, map);

		} else {
			Map<String, DataAmount> map = new HashMap<String, DataAmount>();
			map.put(dateDayAndMonthAndYear, newAmount);
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
	private boolean isDayTradeInAmount(String stock, String date) {
		boolean result = false;

		if (stockAmountMap.containsKey(stock)) {

			Map<String, DataAmount> map = stockAmountMap.get(stock);

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

	class ComparatorSortASCDateByMonthAndYear implements Comparator {
		public int compare(Object o1, Object o2) {

			String s1 = (String) o1;
			String s2 = (String) o2;

			Date d1 = FormatUtil.parseDateByMonth(s1);
			Date d2 = FormatUtil.parseDateByMonth(s2);

			int results = d1.compareTo(d2);
			return results;

			// return s1.toUpperCase ( ) .compareTo ( s2.toUpperCase ( ) ) ;
		}

	}

	class ComparatorSortDESDateByMonthAndYear implements Comparator {
		public int compare(Object o1, Object o2) {

			String s1 = (String) o1;
			String s2 = (String) o2;

			Date d1 = FormatUtil.parseDateByMonth(s1);
			Date d2 = FormatUtil.parseDateByMonth(s2);

			int results = d2.compareTo(d1);
			return results;

			// return s1.toUpperCase ( ) .compareTo ( s2.toUpperCase ( ) ) ;
		}

	}

}
