package com.twocents.model;


public class ReportParamChart extends ReportParam{

	private ChartType chartType;
	
	public ChartType getChartType() {
		return chartType;
	}

	public void setChartType(ChartType chartType) {
		this.chartType = chartType;
	}

	public ReportParamChart(String title,ReportGroupType type){
		super(title,type);
	}
	
}
