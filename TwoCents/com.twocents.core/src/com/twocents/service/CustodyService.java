package com.twocents.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Buy;
import com.twocents.model.CallSell;
import com.twocents.model.Custody;
import com.twocents.model.CustodyView;
import com.twocents.model.Operation;
import com.twocents.model.Sell;
import com.twocents.model.Stock;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
		Throwable.class, CoreException.class })
public interface CustodyService extends BaseService<Custody> {

	String BEAN_NAME = "com.twocents.service.CustodyService";

	public Double updateCustody(Operation operation) throws CoreException;

	public Double addCustody(Buy operation) throws CoreException;

	public Double removeCustody(Sell operation) throws CoreException;

	public void blockCustody(CallSell operation) throws CoreException;

	public Custody findById(Long id);

	public List<Custody> findCustodyByAccount(Account account)
			throws CoreException;

	public List<CustodyView> findCustodyViewByAccount(Account account);

	public void updateCustodyRemovedOperation(Operation operation)
			throws CoreException;

	public Custody findCustodyByStockAndAccount(Stock stock, Account account);

	public Custody findCustodyByAStockAndAccountBeforeADate(Stock stock,
			Account account, Timestamp date);

	public double getInvestmentMadeByAccount(Account account);

	public double getCurrentPositionValue(Account account);
	
	public void updateCustodyLastPrice(Stock stock, Double lastPrice);
}
