package com.twocents.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.twocents.core.util.CoreUtil;
import com.twocents.dao.StockDAO;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Option;
import com.twocents.model.Stock;
import com.twocents.resources.CoreMessages;

public class StockServiceImpl extends BaseServiceImpl<Stock, StockDAO>
		implements StockService {

	private static final Logger log = Logger.getLogger(StockServiceImpl.class);

	private QuoteService quotationService;
	
	public QuoteService getQuotationService() {
		return quotationService;
	}

	public void setQuotationService(QuoteService quotationService) {
		this.quotationService = quotationService;
	}

	public void setStockDAO(StockDAO dao) {
		super.setDao(dao);
	}

	public StockDAO getStockDAO() {
		return super.getDao();
	}

	public Stock findStockByCode(String code) {
		try {
			return getStockDAO().findByCode(code);
		} catch (RuntimeException e) {
			log.debug(CoreMessages.getMessage(1004) + " - " + e.getMessage());
			return null;
		}
	}

	public Stock addStock(Stock stock) throws CoreException {
		String code = stock.getCode();
		if (StringUtils.isBlank(code) || code.length() < 5) {
			throw new CoreException("Stock code invalid.");
		}

		String start = code.substring(0, 5);

		boolean isOption = StringUtils.isAlpha(start);

		String end = null;
		if (isOption) {
			start = code.substring(0, 5);
			end = code.substring(5);
		} else {
			start = code.substring(0, 4);
			end = code.substring(4);
		}
		if (!StringUtils.isAlpha(start)) {
			throw new CoreException("Stock code invalid.");
		}
		if (!StringUtils.isNumeric(end) || end.length() > 2) {
			throw new CoreException("Stock code invalid.");
		}
		if (isOption) {
			String codePrefix = start.substring(4);
			Option option = new Option();
			option.setDescription(stock.getDescription());
			option.setExpirationDate(CoreUtil
					.getOptionExpirationDate(codePrefix));
			option.setStrikePrice(Double.valueOf(end));
			stock = option;
		}
		stock.setCode(code.toUpperCase());
		super.persist(stock);

//		final Stock usedToUpdateQuote = stock;
//		
//		Thread t = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//					quotationService.updateStockQuote(usedToUpdateQuote);
//				} catch (Exception e) {
//					log.error(e);
//				}
//			}
//		});
//		
//		t.start();

		return stock;
	}

	public void updateAllStockQuotation() throws CoreException {
		quotationService.updateStockQuote(super.findAll());

	}

	public Stock findStockByCodePrefix(String codePrefix) throws CoreException {
		List<Stock> stocks = getStockDAO().findByCodePrefix(codePrefix);
		if (!stocks.isEmpty()) {
			return stocks.get(0);
		}
		return null;
	}

	public void updateOptionStock(Option option) throws CoreException {
		if (option.getStock() == null) {
			Stock stock = this.findStockByCodePrefix(option.getCode()
					.substring(0, 4));
			if (stock == null) {
				return;
			}
			option.setStock(stock);
		}
		super.persist(option);

	}

}
