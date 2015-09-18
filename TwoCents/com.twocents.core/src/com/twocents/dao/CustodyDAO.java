package com.twocents.dao;

import java.sql.Timestamp;
import java.util.List;

import com.twocents.model.Account;
import com.twocents.model.Custody;
import com.twocents.model.CustodyView;
import com.twocents.model.Stock;

public interface CustodyDAO extends BaseDAO<Custody> {
	
	public Custody findCustodyByStockAndAccount(Stock stock, Account account);

	public List<Custody> findCustodyByAccount(Account account);
	
	public List<CustodyView> findCustodyViewByAccount(Account account); 
	
	public Custody findCustodyByAStockAndAccountBeforeADate(Stock stock, Account account, Timestamp beforeDate);

	public double getInvestmentMadeByAccount(Account account);
	
	public double getCurrentPositionValue(Account account);

	public void updateCustodyLastPrice(Stock stock, Double lastPrice);
}
