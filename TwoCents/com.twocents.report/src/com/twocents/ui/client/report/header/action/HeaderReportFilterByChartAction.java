package com.twocents.ui.client.report.header.action;

import org.apache.log4j.Logger;

import com.twocents.exceptions.CoreException;
import com.twocents.model.ChartType;
import com.twocents.model.ReportParamChart;
import com.twocents.report.util.DialogUtil;
import com.twocents.ui.client.report.Report;
import com.twocents.ui.client.report.header.HeaderReportFilterByChartTypeComposite;

public class HeaderReportFilterByChartAction extends HeaderReportAction {

	private Logger logger = Logger.getLogger(HeaderReportFilterByChartAction.class);
	private HeaderReportFilterByChartTypeComposite UI;
	private ChartType selectChartType=null;
	
	public HeaderReportFilterByChartAction(HeaderReportFilterByChartTypeComposite UI, Report report) {
		super(report);
		this.UI = UI;
		init();
	}

	public HeaderReportFilterByChartTypeComposite getUI() {
		return UI;
	}

	public void init() {
		
		if(this.selectChartType==null){
			this.selectChartType = ChartType.PIE;
			storeParameter();
		}

	
	}

	public void refresh() {
		
	}
	@Override
	public ReportParamChart submitData() {
		ReportParamChart param=new ReportParamChart(getReport().getName(),getReport().getGroupType());
		
		param.setChartType(selectChartType);
		
		return param;
	}

	public void setChartType(ChartType selectChartType) {
		
		this.selectChartType = selectChartType;
		
		
		storeParameter();
		try {
			doDiplay();
		} catch (CoreException e) {
			logger.error(e);
			DialogUtil.errorMessage(getReport(), "Não foi possível gerar relatorio baseado no tipo de grafíco");
		}	
		
	}
}
