package com.twocents.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Buy;
import com.twocents.model.CallSell;
import com.twocents.model.Operation;
import com.twocents.model.OperationView;
import com.twocents.model.Sell;
import com.twocents.model.Stock;
import com.twocents.model.StockOperation;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
		Throwable.class, CoreException.class })
public interface OperationService extends BaseService<Operation> {

	String BEAN_NAME = "com.twocents.service.OperationService";

	public void buyOperation(Buy operation) throws CoreException;

	public void sellOperation(Sell operation) throws CoreException;

	public void callSellOperation(CallSell operation) throws CoreException;

	public void removeOperation(Operation operation) throws CoreException;

	public List<Buy> findBuyByAccount(Account account) throws CoreException;

	public List<Sell> findSellByAccount(Account account) throws CoreException;

	public List<CallSell> findCallSellByAccount(Account account)
			throws CoreException;

	public List<StockOperation> findStockOperationByAccount(Account account)
			throws CoreException;

	public List<Operation> findOperationByAccount(Account account)
			throws CoreException;

	public List<Map<String, Object>> findOperationMapByAccount(Account account,
			Timestamp starDate, Timestamp endDate) throws CoreException;

	public List<Map<String, Object>> findOperationMapByAccount(Account account,
			Timestamp starDate, Timestamp endDate,
			Class<? extends Operation> classOperation) throws CoreException;

	public List<Map<String, Object>> findStockOperationByAccountAndInterval(
			Account account, Timestamp dateStart, Timestamp dateEnd)
			throws CoreException;

	public List<Map<String, Object>> findOperationByAccountWithDateOrdered(
			Account account, Timestamp starDate, Timestamp endDate)
			throws CoreException;

	public List<Map<String, Object>> findOperationByAccountWithDateOrderedAsc(
			Account account, Timestamp starDate, Timestamp endDate)
			throws CoreException;

	public List<Map<String, Object>> findOperationByAccountWithDateOrderedDesc(
			Account account, Timestamp starDate, Timestamp endDate)
			throws CoreException;

	public void registerOperation(Operation operation) throws CoreException;

	public List<Map<String, Object>> findOperationByAccountWithDateOrderedAsc(
			Account account) throws CoreException;

	public Long findAmountOfStock(Account account, Stock stock,
			Timestamp startDate, Timestamp endDate);

	public List<Integer> findYearOfOperations(Account account)
			throws CoreException;

	public List<OperationView> findOperationViewByAccount(Account account,
			Timestamp startDate, Timestamp endDate);
}
