package com.twocents.ui.client.facade;

import java.util.Timer;

import com.twocents.exceptions.CoreException;
import com.twocents.service.StockService;
import com.twocents.service.tasks.UpdateStockQuoteTask;
import com.twocents.spring.ServiceLocator;
import com.twocents.ui.exceptions.UIException;

public class StockFacade {

	StockService stockService = (StockService) ServiceLocator
			.getBean(StockService.BEAN_NAME);
	private static Timer stockQuoteTimer = null;

	public static Timer getStockQuoteTimer() {
		return stockQuoteTimer;
	}

	public StockService getStockService() {
		return stockService;
	}

	public void createUpdateStockQuoteTask(long interval) {
		if (stockQuoteTimer != null) {
			stockQuoteTimer.cancel();
		}
		stockQuoteTimer = new Timer();

		stockQuoteTimer.schedule(new UpdateStockQuoteTask(stockService), 0,
				interval);
	}

	public void executeSingleUpdate() throws UIException {

		try {
			stockService.updateAllStockQuotation();
		} catch (CoreException e) {
			throw new UIException("Erro durante recuperação dos ativos. " + e.getLocalizedMessage(), e);
		}

	}

	public static void setStockQuoteTimer(Timer stockQuoteTimer) {
		StockFacade.stockQuoteTimer = stockQuoteTimer;
	}

}
