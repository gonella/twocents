package com.twocents.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import com.twocents.dao.CustodyDAO;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Buy;
import com.twocents.model.CallSell;
import com.twocents.model.Custody;
import com.twocents.model.CustodyView;
import com.twocents.model.Operation;
import com.twocents.model.Option;
import com.twocents.model.Sell;
import com.twocents.model.Stock;
import com.twocents.resources.CoreMessages;

public class CustodyServiceImpl extends BaseServiceImpl<Custody, CustodyDAO>
		implements CustodyService {

	private static final Logger log = Logger
			.getLogger(CustodyServiceImpl.class);

	private StockService stockService;

	public StockService getStockService() {
		return stockService;
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

	public void setCustodyDAO(CustodyDAO dao) {
		super.setDao(dao);
	}

	public CustodyDAO getCustodyDAO() {
		return super.getDao();
	}

	public Double addCustody(Buy operation) {
		log.debug("finding custody by stock...");
		Stock stock = operation.getStock();
		Integer custodyAmount = operation.getAmount();
		if (stock instanceof Option) {
			Option option = (Option) stock;
			if (option.getStock() != null) {
				Custody custodyStock = findCustodyByStockAndAccount(option
						.getStock(), operation.getAccount());
				if (custodyStock.getAmountBlocked() > 0) {
					custodyAmount = operation.getAmount() > custodyStock
							.getAmountBlocked() ? custodyStock
							.getAmountBlocked() : operation.getAmount();
					custodyStock.setAmountBlocked(custodyStock
							.getAmountBlocked()
							- custodyAmount);
					custodyStock.setLastUpdate(new Timestamp(System
							.currentTimeMillis()));
					super.persist(custodyStock);
				}
			}

		}
		Custody custody = findCustodyByStockAndAccount(stock, operation
				.getAccount());
		Double profit = null;
		if (custody == null) {
			custody = new Custody();
			custody.setAccount(operation.getAccount());
			custody.setAmount(custodyAmount);
			custody.setStock(operation.getStock());
			custody.setAveragePrice(operation.getAvgPrice());
			custody.setLastUpdate(new Timestamp(System.currentTimeMillis()));
			custody.setLastPrice(operation.getAvgPrice());
		} else {
			if (custody.getAmount() + operation.getAmount() == 0) {
				super.delete(custody);
				return profit;
			}
			Integer custodyTotal = custody.getAmount() + operation.getAmount();
			if (custodyTotal > 0) {
				Integer custodyAbs = Math.abs(custody.getAmount());
				custody
						.setAveragePrice((custody.getAveragePrice()
								* custodyAbs + operation.getAvgPrice()
								* operation.getAmount())
								/ (custodyAbs + operation.getAmount()));
			}
			custody.setAmount(custodyTotal);
			custody.setLastUpdate(new Timestamp(System.currentTimeMillis()));
			
			if (custody.getLastPrice() == null) {
				custody.setLastPrice(operation.getAvgPrice());	
			}
			
		}
		super.persist(custody);
		return profit;
	}

	public Double removeCustody(Sell operation) throws CoreException {
		log.debug("finding custody by stock...");
		Custody custody = findCustodyByStockAndAccount(operation.getStock(),
				operation.getAccount());
		Double profit = null;
		if (custody != null) {
			Integer custodyAvailable = custody.getAmount()
					- custody.getAmountBlocked();
			if (custodyAvailable < operation.getAmount()) {
				throw new CoreException(2005, custody.getAmount());
			}
			int custodyAmount = custody.getAmount();
			int operationAmmount = operation.getAmount();
			if (custodyAmount - operationAmmount == 0) {
				super.deleteById(custody.getId());
			} else {
				custody.setAmount(custodyAmount - operationAmmount);
				super.persist(custody);
			}
			profit = (operation.getAvgPrice() - custody.getAveragePrice())
					* operationAmmount;
		} else {
			throw new CoreException(2006);
		}
		return profit;
	}

	public void blockCustody(CallSell operation) throws CoreException {
		Option option = (Option) operation.getStock();
		Stock stockOption = option.getStock();
		if (stockOption == null) {
			throw new CoreException(2008, option.getCode());
		}
		Custody stockCustody = findCustodyByStockAndAccount(stockOption,
				operation.getAccount());
		if (stockCustody == null
				|| (stockCustody.getAmount() - stockCustody.getAmountBlocked()) < operation
						.getAmount()) {
			throw new CoreException(2005, stockCustody.getAmount());
		}
		stockCustody.setAmountBlocked(stockCustody.getAmountBlocked()
				+ operation.getAmount());
		stockCustody.setLastUpdate(new Timestamp(System.currentTimeMillis()));
		super.persist(stockCustody);
		Custody custody = findCustodyByStockAndAccount(option, operation
				.getAccount());
		if (custody != null) {
			if (custody.getAmount() > 0) {
				// lançamento coberto não é permitido qdo existe custódia
				// positiva para a opção.
				throw new CoreException(2012);
			} else {
				custody
						.setAveragePrice((custody.getAveragePrice()
								* Math.abs(custody.getAmount()) + operation
								.getAvgPrice()
								* operation.getAmount())
								/ Math.abs(custody.getAmount()
										- operation.getAmount()));
				custody.setAmount(custody.getAmount() - operation.getAmount());
				custody
						.setLastUpdate(new Timestamp(System.currentTimeMillis()));
				super.persist(custody);
			}
		} else {
			custody = new Custody();
			custody.setAccount(operation.getAccount());
			custody.setAmount(-operation.getAmount());
			custody.setStock(operation.getStock());
			custody.setAveragePrice(operation.getAvgPrice());
			custody.setLastUpdate(new Timestamp(System.currentTimeMillis()));
			super.persist(custody);
		}

	}

	public Custody findCustodyByStockAndAccount(Stock stock, Account account) {
		try {
			return getCustodyDAO().findCustodyByStockAndAccount(stock, account);
		} catch (RuntimeException e) {
			log.debug(CoreMessages.getMessage(1003) + " - " + e.getMessage());
			return null;
		}
	}

	public Custody findById(Long id) {
		return super.findById(id);
	}

	public List<Custody> findCustodyByAccount(Account account)
			throws CoreException {
		return getCustodyDAO().findCustodyByAccount(account);
	}

	public Double updateCustody(Operation operation) throws CoreException {
		if (operation instanceof Buy) {
			return this.addCustody((Buy) operation);
		} else if (operation instanceof Sell) {
			return this.removeCustody((Sell) operation);
		} else if (operation instanceof CallSell) {
			this.blockCustody((CallSell) operation);
		}
		return null;
	}

	public void updateCustodyRemovedOperation(Operation operation)
			throws CoreException {
		if (operation instanceof Buy) {
			this.removeCustodyRemovedOperation((Buy) operation);
		} else if (operation instanceof Sell) {
			this.addCustodyRemovedOperation((Sell) operation);
		} else if (operation instanceof CallSell) {
			this.releaseCustody((CallSell) operation);
		}
	}

	private void releaseCustody(CallSell operation) throws CoreException {
		Stock stockOption = stockService.findStockByCodePrefix(operation
				.getStock().getCode().substring(0, 4));
		if (stockOption == null) {
			throw new CoreException(2008, operation.getStock().getCode());
		}
		Custody stockCustody = findCustodyByStockAndAccount(stockOption,
				operation.getAccount());
		if (stockCustody == null) {
			throw new CoreException(2006);
		}
		stockCustody.setAmountBlocked(stockCustody.getAmountBlocked()
				- operation.getAmount());
		stockCustody.setLastUpdate(new Timestamp(System.currentTimeMillis()));
		super.persist(stockCustody);
		Custody custody = findCustodyByStockAndAccount(operation.getStock(),
				operation.getAccount());
		if (custody != null) {
			if (custody.getAmount() + operation.getAmount() == 0) {
				super.delete(custody);
			} else {
				custody.setAmount(custody.getAmount() + operation.getAmount());
				Double currentAvgPrice = custody.getAveragePrice();
				Double avgDiff = (operation.getAmount() * (custody
						.getAveragePrice() - operation.getAvgPrice()))
						/ custody.getAmount();
				custody.setAveragePrice(currentAvgPrice + avgDiff);
				custody
						.setLastUpdate(new Timestamp(System.currentTimeMillis()));
				super.persist(custody);
			}
		}

	}

	private void addCustodyRemovedOperation(Sell operation) {
		Custody custody = findCustodyByStockAndAccount(operation.getStock(),
				operation.getAccount());
		if (custody == null) {
			custody = new Custody();
			custody.setAccount(operation.getAccount());
			custody.setAmount(operation.getAmount());
			custody.setStock(operation.getStock());
			custody.setAveragePrice(operation.getAvgPrice()
					- (operation.getProfit() / operation.getAmount()));
		} else {
			custody.setAmount(custody.getAmount() + operation.getAmount());
		}
		custody.setLastUpdate(new Timestamp(System.currentTimeMillis()));
		super.persist(custody);
	}

	private void removeCustodyRemovedOperation(Buy operation)
			throws CoreException {
		log.debug("finding custody by stock...");
		Custody custody = findCustodyByStockAndAccount(operation.getStock(),
				operation.getAccount());
		if (custody != null) {
			if ((custody.getAmount() - custody.getAmountBlocked()) < operation
					.getAmount()) {
				throw new CoreException(2005, custody.getAmount());
			}
			int custodyAmount = custody.getAmount();
			int operationAmmount = operation.getAmount();
			if (custodyAmount - operationAmmount == 0) {
				super.deleteById(custody.getId());
			} else {
				custody.setAmount(custodyAmount - operationAmmount);
				Double currentAvgPrice = custody.getAveragePrice();
				Double avgDiff = (operationAmmount * (custody.getAveragePrice() - operation
						.getAvgPrice()))
						/ custody.getAmount();
				custody.setAveragePrice(currentAvgPrice + avgDiff);
				custody
						.setLastUpdate(new Timestamp(System.currentTimeMillis()));
				super.persist(custody);
			}
		} else {
			throw new CoreException(2006);
		}
	}

	public List<CustodyView> findCustodyViewByAccount(Account account) {
		List<CustodyView> list = getCustodyDAO().findCustodyViewByAccount(
				account);
		return list;
	}

	public Custody findCustodyByAStockAndAccountBeforeADate(Stock stock,
			Account account, Timestamp date) {
		try {
			return getCustodyDAO().findCustodyByAStockAndAccountBeforeADate(
					stock, account, date);
		} catch (RuntimeException e) {
			log.debug(CoreMessages.getMessage(1003) + " - " + e.getMessage());
			return null;
		}
	}
	
	public void updateCustodyLastPrice(Stock stock, Double lastPrice) {
		getCustodyDAO().updateCustodyLastPrice(stock, lastPrice);
	}

	@Override
	public double getInvestmentMadeByAccount(Account account) {
		return getDao().getInvestmentMadeByAccount(account);
	}

	public double getCurrentPositionValue(Account account) {
		return getDao().getCurrentPositionValue(account);
	}
}
