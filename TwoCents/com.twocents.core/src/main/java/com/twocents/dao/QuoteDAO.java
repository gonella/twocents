package com.twocents.dao;

import java.util.Map;

import com.twocents.model.Quote;


public interface QuoteDAO extends BaseDAO<Quote> {

	Map<String, Object> findLastQuoteMapByStockId(Long stockId);
	
	public Quote getCurrentQuote(Long stockId);


}
