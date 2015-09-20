package com.twocents.core.populate;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanCopier;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.twocents.core.util.CoreUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Broker;
import com.twocents.model.BrokerageNote;
import com.twocents.model.BrokerageNoteItem;
import com.twocents.model.Buy;
import com.twocents.model.Custody;
import com.twocents.model.Operation;
import com.twocents.model.OperationKey;
import com.twocents.model.OperationType;
import com.twocents.model.Sell;
import com.twocents.model.Stock;
import com.twocents.model.StockOperation;
import com.twocents.model.StockType;

public class PopulateOperationData extends Populate {
	private static Logger logger = Logger
			.getLogger(PopulateOperationData.class);

	@Transactional
	public void registerOperation(final OperationType operationType, Date date,
			String ativo, Integer quantidade, Double preco, Account account,
			Broker broker) throws CoreException {

		long currentTimeMillis1 = System.currentTimeMillis();

		
			logger.debug("# Registrando a operação");
			Operation operation = null;

			if (account == null) {
				throw new CoreException(1014);
			}

			if (OperationType.BUY.equals(operationType)) {
				operation = new Buy();
			} else if (OperationType.SELL.equals(operationType)) {
				operation = new Sell();
			} else {
				throw new CoreException(1015);
			}

			final StockOperation stockOperation = (StockOperation) operation;

			stockOperation.setPrice(preco);
			stockOperation.setAmount(quantidade);

			Stock stock = new Stock();
			stock.setCode(ativo);

			// TODO - È necessario criar uma opção na UI para indicar o tipo
			// do ativo, ou fazer no backend um auto detect pelo
			// codigo(pegar o formato estabelcido pela bovespa)
			stock.setType(StockType.SHARE);
			// /stock.setType(StockUtil.getStockType(stockCode));
			stockOperation.setBroker(broker);

			stockOperation.setStock(stock);

			// Setando a date
			// O uusário pode escolher a data para inserir a operação
			// operation.setOperationDate(new
			// Timestamp(System.currentTimeMillis()));

			// Coloca a date vinda da operação e junta com o hora do momento.
			Calendar dateMoment = Calendar.getInstance();
			// dateMoment.setTimeInMillis(System.currentTimeMillis());
			Calendar dateOpCreated = Calendar.getInstance();
			dateOpCreated.setTime(date);
			dateMoment.set(dateOpCreated.get(Calendar.YEAR), dateOpCreated
					.get(Calendar.MONTH), dateOpCreated.get(Calendar.DATE));

			stockOperation.setOperationDate(new Timestamp(dateMoment.getTime()
					.getTime()));

			// Associando a conta na operação
			stockOperation.setAccount(account);

			// TODO Fazer o rollback da nota de corretagem se a operação falhar.
			logger.info("* Inserindo Operação - " + stockOperation.toString());
			getOperationService().registerOperation(stockOperation);

			if (stockOperation instanceof Buy) {
				getCustodyService().addCustody((Buy) stockOperation);
			} else {
				Double profit = getCustodyService().removeCustody(
						(Sell) stockOperation);
				((Sell) stockOperation).setProfit(profit);
			}

			logger.info("* Atualizando a nota de corretagem. *");
			addBrokerageNote(operationType, stockOperation);

			long currentTimeMillis2 = System.currentTimeMillis();

			logger.debug("* Operação registrada");
			// logger.debug("Total memory after insert: "
			// + Runtime.getRuntime().totalMemory());

			logger.info("[INSERIDO] Tempo gasto :"
					+ (currentTimeMillis2 - currentTimeMillis1) + " ms");

		
	}

	private BrokerageNote addBrokerageNote(OperationType operationType,
			Operation operation) throws CoreException {

		logger.debug("Criando um item da nota de corretagem");
		BrokerageNote brokerageNote = null;
		Date dateOperation = new Date(operation.getOperationDate().getTime());

		// Verifica se existe uma nota de corretagem criada para aquele dia.
		try {
			Timestamp min = CoreUtil.getMinTimestamp(dateOperation);
			Timestamp max = CoreUtil.getMaxTimestamp(dateOperation);

			// logger.info("Query - Account.Id:"+operation.getAccount().getId()+" Date:"+(new
			// Date(min.getTime()))+","+(new Date(max.getTime())));
			brokerageNote = getBrokerageNoteService().findBrokerageNoteByDate(
					operation.getAccount(), min, max);
			// logger.info("FOUND : ["+brokerageNote+"]");

		} catch (CoreException e) {
			String message = "Error ao retonar a nota de corretagem do periodo";
			// throw new CoreException(message);
			logger.warn(message);
		}

		// É necessário criar a primeira vez
		if (brokerageNote == null) {
			logger.debug("Criando a nota de corretagem para a operação");
			brokerageNote = new BrokerageNote();

			Calendar c = Calendar.getInstance();
			c.setTime(dateOperation);
			/*
			 * c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE, 0);
			 * c.set(Calendar.SECOND, 0); c.set(Calendar.MILLISECOND, 0);
			 */
			brokerageNote.setDate(c.getTime());
			brokerageNote.setAccount(operation.getAccount());

			getBrokerageNoteService().persist(brokerageNote);
		}
		// logger.info("BrokerageNote - Account Id:"+brokerageNote.getAccount().getId());
		// Agora com a nota de corretagem criada, basta criar os items internos

		logger.debug("Criando o item da nota de corretagem");
		BrokerageNoteItem item = new BrokerageNoteItem();
		item.setOperationType(operationType);

		item.setStockType(((StockOperation) operation).getStock().getType());
		item.setStockCode(((StockOperation) operation).getStock().getCode());
		item.setAmount(((StockOperation) operation).getAmount());
		item.setPrice(((StockOperation) operation).getPrice());
		item.setOperation(operation);
		item.setBrokerageNote(brokerageNote);
		item.setAccount(operation.getAccount());

		detectDaytradeAndLiquidate(brokerageNote, item, operation);

		// getBrokerageNoteItemService().saveItem(item);

		brokerageNote.addBrokerageItemNote(item);
		brokerageNote.getOperations().add(operation);

		operation.setBrokerageNote(brokerageNote);

		// Calculando as taxas de todos os items da nota e acumulando na mesma.
		brokerageNote.processAllItems();

		getBrokerageNoteService().merge(brokerageNote);

		return brokerageNote;
	}

	/**
	 * Verifica se uma dada operação qualifica-se como um daytrade.
	 * 
	 * Regras:
	 * 
	 * 1 - Se possuem a mesma data de operação e 1.1 - A quantidade em custódia
	 * antes é menor ou igual ao que está sendo operado no momento
	 * 
	 * Exemplos (para um mesmo papel):
	 * 
	 * a) Compra no dia x - 300 Compra no dia y - 400 (Custódia no dia y == 700)
	 * 
	 * Venda no dia y - 700 (Nova Custodia no dia y == 0)
	 * 
	 * Resultado: Operação não é daytrade, pois a venda das 100 acoes é
	 * referente a compra feita no dia x
	 * 
	 * Nova venda no dia y - 600 (Custodia 300)
	 * 
	 * Resultado:
	 * 
	 * 
	 * 
	 * b) Compra no dia x - 300 Compra no dia y - 200 (Custódia no dia y == 500)
	 * 
	 * Venda no dia y - 400 (Nova Custodia no dia y == 100)
	 * 
	 * Resultado: Operação é daytrade para 100 ativos e não é daytrade para 300
	 * 
	 * 
	 * @param oldOp
	 * @param newOp
	 * @param lastItemInTheNote
	 * @param amountBeforeNewOp
	 * @return
	 */
	private Trade getTrade(Operation newOp, BrokerageNote note,
			BrokerageNoteItem newItem, int amountBeforeNewOp) {

		Set<Operation> operations = note.getOperations();

		Trade trade = null;

		if (!operations.isEmpty()) {
			for (Operation oldOp : operations) {

				// Maybe we have a daytrade, lets check...
				if (isOposite((StockOperation) newOp, (StockOperation) oldOp)
						&& (operations.size() + 1) % 2 == 0) {

					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(oldOp.getOperationDate().getTime());

					int dayOldOp = cal.get(Calendar.DAY_OF_WEEK);

					cal.setTimeInMillis(newOp.getOperationDate().getTime());
					int dayNewOp = cal.get(Calendar.DAY_OF_WEEK);

					// Id so, then we have a daytrade
					if (dayOldOp == dayNewOp) {
						trade = new Trade();
						int dtAmount = newItem.getAmount() - amountBeforeNewOp;
						trade.setDayTradeAmount(dtAmount);
						trade.setNonDayTradeAmount(amountBeforeNewOp);
						trade.setClosedOperation(oldOp);
						return trade;
					}
				}
			}

		}

		// Operation is common buy or sell one
		if (trade == null) {
			trade = new Trade();
			trade.setNonDayTradeAmount(newItem.getAmount());
		}

		return trade;
	}

	public boolean isOposite(StockOperation newOp, StockOperation oldOp) {
		return newOp.getStock().equals(oldOp.getStock())
				&& ((newOp instanceof Buy && oldOp instanceof Sell) || (newOp instanceof Sell && oldOp instanceof Buy));
	}

	private int getStockAmountBeforeToday(Stock stock, Operation newOp,
			BrokerageNote note, BrokerageNoteItem newItem) {

		Calendar cal = Calendar.getInstance();
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);

		cal.set(year, month, dayOfMonth, 0, 0);

		Custody c = getCustodyService()
				.findCustodyByAStockAndAccountBeforeADate(stock,
						newOp.getAccount(),
						new Timestamp(cal.getTimeInMillis()));

		if (c == null) {
			return 0;
		}

		return c.getAmount();

	}

	/**
	 * Vai procurar na nota se existe algum item q pode ser liquidado com o novo
	 * item, se tiver é um daytrade.
	 * 
	 * @param note
	 * @param newItem
	 * @throws CoreException
	 */
	public void detectDaytradeAndLiquidate(BrokerageNote note,
			BrokerageNoteItem newItem, Operation newOp) throws CoreException {
		logger.debug("Detectando se o item é daytrade com a nota atual");

		Set<BrokerageNoteItem> list = note.getBrokerageItemNoteList();
		list.add(newItem);

		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		int amountBeforeOperation = getStockAmountBeforeToday(
				((StockOperation) newOp).getStock(), newOp, note, newItem);

		Trade trade = getTrade(newOp, note, newItem, amountBeforeOperation);

		int amount = newOp instanceof Buy ? newItem.getAmount() : newItem
				.getAmount()
				* -1;

		Custody currentCustody = getCustodyService()
				.findCustodyByStockAndAccount(
						((StockOperation) newOp).getStock(), newOp.getAccount());
		int currentAmount = currentCustody != null ? currentCustody.getAmount()
				: 0;

		boolean isLiquidated = currentAmount + amount <= 0;

		// Ocorreu daytrade
		if (trade.getDayTradeAmount() != 0) {
			newItem.setDayTrade(true);

			Operation closedOp = trade.getClosedOperation();

			if (closedOp != null) {
				BrokerageNoteItem closedItem = getBrokerageNoteItemService()
						.findBrokerageNoteItemByOperation(closedOp);

				if (closedItem != null) {
					closedItem.setDayTrade(true);
					if (isLiquidated) {
						closedItem.process(true);
					}
					getBrokerageNoteItemService().persist(closedItem);
				}
			}

			if (isLiquidated) { // Operacao liquidada
				newItem.process(true);
			} else {
				newItem.process(false);
			}

			newItem.setAmount(trade.getDayTradeAmount());

			getBrokerageNoteItemService().saveItem(newItem);

			// Ops... temos que criar um novo item pois o day trade foi feito em
			// cima de custodias anteriores
			if (trade.getNonDayTradeAmount() != 0) {
				BrokerageNoteItem itemForNonDayTrade = copyBrokerageItem(newItem);
				itemForNonDayTrade.setAmount(trade.getNonDayTradeAmount());
				itemForNonDayTrade.setDayTrade(false);
				itemForNonDayTrade.process(isLiquidated);
				getBrokerageNoteItemService().saveItem(itemForNonDayTrade);
			}

		} else {
			newItem.setDayTrade(false);
			newItem.process(isLiquidated);
			getBrokerageNoteItemService().saveItem(newItem);
		}

	}

	public BrokerageNoteItem copyBrokerageItem(BrokerageNoteItem source) {

		BeanCopier beanCreated = BeanCopier.create(BrokerageNoteItem.class,
				BrokerageNoteItem.class, false);
		BrokerageNoteItem itemCopied = new BrokerageNoteItem();
		beanCreated.copy(source, itemCopied, null);
		itemCopied.setId(null);

		logger.debug("Copiando Objecto: " + itemCopied.toString());

		return itemCopied;
	}

	public static Map<OperationKey, Object> createOperationMap(
			String stockCode, OperationType operationType, Integer amount,
			Double price, Date date/* , Broker broker */, String description) {

		Map<OperationKey, Object> map = new HashMap<OperationKey, Object>();

		map.put(OperationKey.STOCK_ID, stockCode);
		map.put(OperationKey.AMOUNT, amount);
		map.put(OperationKey.PRICE, price);
		// map.put(OperationKey.BROKER, broker);
		map.put(OperationKey.DESCRIPTION, description);
		map.put(OperationKey.OPERATION_DATE, date);
		map.put(OperationKey.OPERATION_TYPE, operationType);

		return map;

	}

	public void registerOperations(Account account,
			List<Map<OperationKey, Object>> list) throws CoreException {

		if (account == null) {
			throw new CoreException("Não existe conta para registrar operações");
		}
		if (CollectionUtils.isEmpty(list)/* || (list!=null && list.size()>0) */) {
			throw new CoreException("Não existe operação para ser registrada");
		}

		for (Map<OperationKey, Object> map : list) {

			String stockCode = (String) map.get(OperationKey.STOCK_ID);
			OperationType operationType = (OperationType) map
					.get(OperationKey.OPERATION_TYPE);
			Integer amount = (Integer) map.get(OperationKey.AMOUNT);
			Double price = (Double) map.get(OperationKey.PRICE);

			Date date = (Date) map.get(OperationKey.OPERATION_DATE);

			Broker broker = account.getBroker();

			registerOperation(operationType, date, stockCode, amount, price,
					account, broker);
		}

	}

	private class Trade {
		private int dayTradeAmount;
		private int nonDayTradeAmount;
		private Operation closedOperation;

		public Operation getClosedOperation() {
			return closedOperation;
		}

		public void setClosedOperation(Operation closedOperation) {
			this.closedOperation = closedOperation;
		}

		public int getNonDayTradeAmount() {
			return nonDayTradeAmount;
		}

		public void setNonDayTradeAmount(int nonDayTradeAmount) {
			this.nonDayTradeAmount = nonDayTradeAmount;
		}

		public void setDayTradeAmount(int dayTradeAmount) {
			this.dayTradeAmount = dayTradeAmount;
		}

		public int getDayTradeAmount() {
			return dayTradeAmount;
		}

	}
}
