package com.twocents.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.twocents.context.ContextHolder;
import com.twocents.core.adapter.QuoteAdapter;
import com.twocents.dao.QuoteDAO;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Quote;
import com.twocents.model.Stock;

public class QuoteServiceImpl extends BaseServiceImpl<Quote, QuoteDAO> implements QuoteService {

	private static final Logger log = Logger.getLogger(QuoteServiceImpl.class);
	
	public void setQuoteDAO(QuoteDAO dao) {
		super.setDao(dao);
	}
	
	public QuoteDAO getQuoteDAO() {
		return super.getDao();
	}
	
	public void updateStockQuote(List<Stock> stocks) throws CoreException {
		QuoteAdapter quoteAdapter = ContextHolder.getCurrentQuoteAdapter();
		List<Quote> quotes = quoteAdapter.findQuote(stocks);
		
		for (Quote quote : quotes) {
			super.persist(quote);	
		}
				
	}
	
	public void updateStockQuote(Stock stock) throws CoreException {
		log.debug("Looking the Quote for Stock: " + stock.getCode());
		QuoteAdapter quoteAdapter = ContextHolder.getCurrentQuoteAdapter();
		if(quoteAdapter != null) {
			Quote quotation = quoteAdapter.findQuote(stock);
			if(quotation != null && quotation.getPrice()!=null && quotation.getQuoteDate()!=null) {
				log.debug("New price: " + quotation.getPrice());
				log.debug("Date: " + quotation.getQuoteDate());
				super.persist(quotation);
			} else {
				log.info("Quotation not found.");
			}
		} else {
			log.debug("Quote adapater not available.");
		}
	}

	public Map<String, Object> findLastQuoteMapByStockId(Long stockId)
			throws CoreException {
		return getQuoteDAO().findLastQuoteMapByStockId(stockId);
	}
	
	public Quote getCurrentQuote(Long stockId) {
		return getQuoteDAO().getCurrentQuote(stockId);
	}
	
	
}
