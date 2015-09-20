package com.twocents.ui.client.facade;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Buy;
import com.twocents.model.CallSell;
import com.twocents.model.CustodyView;
import com.twocents.model.Operation;
import com.twocents.model.OperationView;
import com.twocents.model.Sell;
import com.twocents.service.BrokerageNoteItemService;
import com.twocents.service.BrokerageNoteService;
import com.twocents.service.CustodyService;
import com.twocents.service.OperationService;
import com.twocents.service.QuoteService;
import com.twocents.spring.ServiceLocator;
import com.twocents.ui.exceptions.UIException;

public class OperationFacade {

	private Logger logger = Logger.getLogger(OperationFacade.class);

	private OperationService operationService = (OperationService) ServiceLocator
			.getBean(OperationService.BEAN_NAME);
	private CustodyService custodyService = (CustodyService) ServiceLocator
			.getBean(CustodyService.BEAN_NAME);
	private QuoteService quoteService = (QuoteService) ServiceLocator
			.getBean(QuoteService.BEAN_NAME);
	private BrokerageNoteService brokerageNoteService = (BrokerageNoteService) ServiceLocator
			.getBean(BrokerageNoteService.BEAN_NAME);
	private BrokerageNoteItemService brokerageNoteItemService = (BrokerageNoteItemService) ServiceLocator
			.getBean(BrokerageNoteItemService.BEAN_NAME);

	public OperationService getOperationService() {
		return operationService;
	}

	public CustodyService getCustodyService() {
		return custodyService;
	}

	public void registerOperation(Operation operation) throws UIException {
		try {
			getOperationService().registerOperation(operation);
		} catch (CoreException e) {
			throw new UIException(e);
		}
	}

	public Operation findById(Long id) throws UIException {
		return getOperationService().findById(id);
	}

	public List<CustodyView> findCustodyByAccount(Account account)
			throws UIException {
		long start = System.currentTimeMillis();
		List<CustodyView> custodies = null;
		try {
			custodies = getCustodyService().findCustodyViewByAccount(account);
			logger.debug("Custodia: Tempo total: " + (System.currentTimeMillis() - start+"ms"));
		} catch (Exception e) {
			throw new UIException(e);
		}
		return custodies;
	}

	public List<Map<String, Object>> findOperationByAccount(Account account,
			Timestamp startDate, Timestamp endDate) throws UIException {
		try {
			return getOperationService().findOperationMapByAccount(account,
					startDate, endDate);
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	public List<OperationView> findOperationViewByAccount(Account account,
			Timestamp startDate, Timestamp endDate) throws UIException {
		try {
			return getOperationService().findOperationViewByAccount(account,
					startDate, endDate);
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	public List<Map<String, Object>> findOperationByAccountWithDateOrdered(
			Account account, Timestamp starDate, Timestamp endDate)
			throws UIException {
		try {
			return getOperationService().findOperationByAccountWithDateOrdered(
					account, starDate, endDate);
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	public List<Map<String, Object>> findOperationByAccountWithDateOrderedAsc(
			Account account, Timestamp starDate, Timestamp endDate)
			throws UIException {
		try {
			return getOperationService()
					.findOperationByAccountWithDateOrderedAsc(account,
							starDate, endDate);
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	public List<Map<String, Object>> findOperationByAccountWithDateOrderedDesc(
			Account account, Timestamp starDate, Timestamp endDate)
			throws UIException {
		try {
			return getOperationService()
					.findOperationByAccountWithDateOrderedDesc(account,
							starDate, endDate);
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	public void removeOperation(Operation operation) throws CoreException {
		getOperationService().removeOperation(operation);
	}

	public List<Map<String, Object>> findSellMapByAccount(Account account,
			Timestamp startDate, Timestamp endDate) throws UIException {
		try {
			return getOperationService().findOperationMapByAccount(account,
					startDate, endDate, Sell.class);
		} catch (Exception e) {
			throw new UIException(e);
		}

	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findBuyMapByAccount(Account account,
			Timestamp startDate, Timestamp endDate) throws UIException {
		try {
			Map<Long, Map> stockQuotation = new HashMap<Long, Map>();
			List<Map<String, Object>> result = getOperationService()
					.findOperationMapByAccount(account, startDate, endDate,
							Buy.class);
			for (Map<String, Object> map : result) {
				Long stockId = (Long) map.get("stockId");
				Map<String, Object> quotation = stockQuotation.get(stockId);
				if (quotation == null) {
					quotation = getQuoteService().findLastQuoteMapByStockId(
							stockId);
					stockQuotation.put(stockId, quotation);
				}
				map.putAll(quotation);
			}

			return result;
		} catch (Exception e) {
			throw new UIException(e);
		}

	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findCallSellMapByAccount(Account account,
			Timestamp startDate, Timestamp endDate) throws UIException {
		try {
			Map<Long, Map> stockQuotation = new HashMap<Long, Map>();
			List<Map<String, Object>> result = getOperationService()
					.findOperationMapByAccount(account, startDate, endDate,
							CallSell.class);
			for (Map<String, Object> map : result) {
				Long stockId = (Long) map.get("stockId");
				Map<String, Object> quotation = stockQuotation.get(stockId);
				if (quotation == null) {
					quotation = getQuoteService().findLastQuoteMapByStockId(
							stockId);
					stockQuotation.put(stockId, quotation);
				}
				map.putAll(quotation);
			}
			return result;
		} catch (Exception e) {
			throw new UIException(e);
		}

	}

	public QuoteService getQuoteService() {
		return quoteService;
	}

	public void setBrokerageNoteService(
			BrokerageNoteService brokerageNoteService) {
		this.brokerageNoteService = brokerageNoteService;
	}

	public BrokerageNoteService getBrokerageNoteService() {
		return brokerageNoteService;
	}

	public void setBrokerageNoteItemService(
			BrokerageNoteItemService brokerageNoteItemService) {
		this.brokerageNoteItemService = brokerageNoteItemService;
	}

	public BrokerageNoteItemService getBrokerageNoteItemService() {
		return brokerageNoteItemService;
	}

}
