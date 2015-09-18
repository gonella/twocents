package com.twocents.service.tasks;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.twocents.exceptions.CoreException;
import com.twocents.service.StockService;

public class UpdateStockQuoteTask extends TimerTask {
	
	private static final Logger log = Logger.getLogger(UpdateStockQuoteTask.class);
	
	private StockService stockService;

	public UpdateStockQuoteTask(StockService stockService) {
		this.stockService = stockService;
	}

	@Override
	public void run() {
		try {
			stockService.updateAllStockQuotation();
		} catch (CoreException e) {
			log.error(e);
		}
	}

}
