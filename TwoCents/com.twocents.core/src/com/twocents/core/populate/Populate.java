package com.twocents.core.populate;

import com.twocents.service.AccountService;
import com.twocents.service.BrokerageNoteItemService;
import com.twocents.service.BrokerageNoteService;
import com.twocents.service.CustodyService;
import com.twocents.service.OperationService;
import com.twocents.service.QuoteService;
import com.twocents.service.StockBrokerService;
import com.twocents.service.TwUserService;
import com.twocents.spring.ServiceLocator;

public class Populate {

	private final CustodyService custodyService = (CustodyService) ServiceLocator.getBean(CustodyService.BEAN_NAME);
	
	private final StockBrokerService stockBrokerService = (StockBrokerService) ServiceLocator.getBean(StockBrokerService.BEAN_NAME);
	private final TwUserService userService = (TwUserService) ServiceLocator.getBean(TwUserService.BEAN_NAME);
	private final AccountService accountService = (AccountService) ServiceLocator.getBean(AccountService.BEAN_NAME);
	private final OperationService operationService = (OperationService) ServiceLocator.getBean(OperationService.BEAN_NAME);
	private final QuoteService quoteService = (QuoteService) ServiceLocator.getBean(QuoteService.BEAN_NAME);

	private final BrokerageNoteService brokerageNoteService = (BrokerageNoteService) ServiceLocator.getBean(BrokerageNoteService.BEAN_NAME);
	private final BrokerageNoteItemService brokerageNoteItemService = (BrokerageNoteItemService) ServiceLocator.getBean(BrokerageNoteItemService.BEAN_NAME);
	
	public StockBrokerService getStockBrokerService() {
		return stockBrokerService;
	}
	public TwUserService getUserService() {
		return userService;
	}
	public AccountService getAccountService() {
		return accountService;
	}
	public OperationService getOperationService() {
		return operationService;
	}
	public BrokerageNoteService getBrokerageNoteService() {
		return brokerageNoteService;
	}
	public BrokerageNoteItemService getBrokerageNoteItemService() {
		return brokerageNoteItemService;
	}
	public CustodyService getCustodyService() {
		return custodyService;
	}
	
	public QuoteService getQuoteService() {
		return quoteService;
	}

}
