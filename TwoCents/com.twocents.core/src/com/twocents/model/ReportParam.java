package com.twocents.model;

public class ReportParam {

	private String stockCode;
	private String stockType;
	
	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	private ReportGroupType type;
	
	private String title;
	
	public ReportGroupType getType() {
		return type;
	}

	public void setType(ReportGroupType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ReportParam(String title,ReportGroupType type){
		this.title = title;
		this.type = type;
		
	}
	
}
