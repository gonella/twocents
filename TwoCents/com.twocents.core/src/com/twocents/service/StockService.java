package com.twocents.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Option;
import com.twocents.model.Stock;


@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Throwable.class, CoreException.class})
public interface StockService extends BaseService<Stock> {
	
	String BEAN_NAME = "com.twocents.service.StockService";

	public Stock findStockByCode(String code);

	public Stock addStock(Stock stock) throws CoreException;

	public void updateAllStockQuotation() throws CoreException;

	public Stock findStockByCodePrefix(String codePrefix) throws CoreException;

	public void updateOptionStock(Option stock) throws CoreException;
}
