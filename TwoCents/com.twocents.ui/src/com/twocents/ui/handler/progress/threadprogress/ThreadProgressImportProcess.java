package com.twocents.ui.handler.progress.threadprogress;

import java.util.List;

import org.eclipse.swt.widgets.Shell;

import com.twocents.model.StockBroker;

public abstract class ThreadProgressImportProcess extends ThreadProgress {

	private List<String> itemsProcessed;
	private final String fileNameSelected;
	private final StockBroker stockBroker;
	
	public ThreadProgressImportProcess(Shell shellToModal,Shell shellToCenter, String fileNameSelected, StockBroker stockBroker) {
		super(shellToModal,shellToCenter);
		this.fileNameSelected = fileNameSelected;
		this.stockBroker = stockBroker;
	}

	@Override
	public void before() {

	}

	@Override
	public void after() {
	}

	@Override
	public void abort() {
	}

	@Override
	public abstract void execute();

	public void setItemsProcessed(List<String> itemsProcessed) {
		this.itemsProcessed = itemsProcessed;
	}

	public List<String> getItemsProcessed() {
		return itemsProcessed;
	}

	public String getFileNameSelected() {
		return fileNameSelected;
	}

	public StockBroker getStockBroker() {
		return stockBroker;
	}

}
