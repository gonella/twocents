package com.twocents.dao;

import java.util.List;

import com.twocents.model.Stock;


public interface StockDAO extends BaseDAO<Stock> {

	public Stock findByCode(String code);

	public List<Stock> findByCodePrefix(String codePrefix);

}
