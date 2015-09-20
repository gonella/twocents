package com.twocents.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.twocents.model.Account;
import com.twocents.model.Operation;
import com.twocents.model.OperationView;
import com.twocents.model.Stock;
import com.twocents.model.StockOperation;


public interface OperationDAO extends BaseDAO<Operation> {
	

	public List<Operation> findOperationByAccount(Account account);
	
	public List<Map<String, Object>> findOperationMapByAccount(Account account, Timestamp starDate, Timestamp endDate);

	public List<Map<String, Object>> findOperationMapByAccount(Account account, Timestamp starDate, Timestamp endDate, Class<?> classe);
	
	public List<? extends StockOperation> findOperationByAccount(Account account, Class<?> classe);

	public Long findTotalByStockDate(Account account, Stock stock, Timestamp start,	Timestamp end, Class<?> classe);

	public List<Map<String, Object>> findOperationByAccountWithDateOrdered(Account account, Timestamp starDate, Timestamp endDate);
	public List<Map<String, Object>> findOperationByAccountWithDateOrderedAsc(Account account, Timestamp starDate, Timestamp endDate);
	public List<Map<String, Object>> findOperationByAccountWithDateOrderedDesc(Account account, Timestamp starDate, Timestamp endDate);

	public List<Map<String, Object>> findOperationByAccountWithDateOrderedAsc(Account account);


	public Long findAmountOfStock(Account account, Stock stock, Timestamp startDate,
			Timestamp endDate);

	public List<Integer> findYearOfOperations(Account account);
	
	public List<OperationView> findOperationViewByAccount(Account account,
			Timestamp starDate, Timestamp endDate);

}
