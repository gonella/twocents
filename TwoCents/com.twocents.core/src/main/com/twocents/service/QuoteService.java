package com.twocents.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Quote;
import com.twocents.model.Stock;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Throwable.class, CoreException.class})
public interface QuoteService extends BaseService<Quote> {

	String BEAN_NAME = "com.twocents.service.QuoteService";

	public void updateStockQuote(List<Stock> stocks) throws CoreException;
	
	public void updateStockQuote(Stock stock) throws CoreException;

	public Map<String, Object> findLastQuoteMapByStockId(Long stockId) throws CoreException;
	
	public Quote getCurrentQuote(Long stockId);
}

