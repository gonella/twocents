package com.twocents.core.adapter;

import java.util.List;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Quote;
import com.twocents.model.Stock;

public interface QuoteAdapter {
	
	String CURRENT_ADAPTER = "CURRENT_ADAPTER";
	
	public String getAdapterName();
	
	public String getAdapterBeanName();
	
	public Quote findQuote(Stock stock) throws CoreException;
	
	public List<Quote> findQuote(List<Stock> stock) throws CoreException;
	
	public void setUrl(String url);

}
