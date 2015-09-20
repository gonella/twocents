package com.twocents.ui.client.report.header.action;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.ReportParam;
import com.twocents.model.ReportParamChart;
import com.twocents.model.ReportParamInterval;
import com.twocents.ui.client.report.Report;

public abstract class HeaderReportAction {

	private final Logger logger = Logger.getLogger(this.getClass());

	private Report report=null;
	
	
	private HeaderReportFacade facade=new HeaderReportFacade();


	public HeaderReportFacade getFacade() {
		return facade;
	}


	public HeaderReportAction(Report report) {
		setReport(report);
	
	}

	//Responsavel por iniciar todos os componentes do header
	public abstract void init();
	
	//Metodo que seta as configuracoes de busca 
	public abstract ReportParam submitData();

	public void storeParameter() {
		logger.info("Armazenando dados para filtragem");
		HashMap map=new HashMap<String,ReportParam>();
		
		ReportParam submitData = submitData();

		if(submitData instanceof ReportParamInterval){
			ReportParamInterval rpi=(ReportParamInterval)submitData;
			
			logger.info("Intervalo da pesquisa ["+FormatUtil.formatDate(new Date(rpi.getDateStart().getTime()))+" , "+FormatUtil.formatDate(rpi.getDateEnd().getTime())+"]");
		}
		else if(submitData instanceof ReportParamChart){
			ReportParamChart rp=(ReportParamChart)submitData;
			
			logger.info("Tipo de Chart para pesquisa ["+rp.getChartType()+"]");
		}
		
		map.put(getReport().REPORT_PARAM_KEY, submitData);
		getReport().setReportParam(map);
	}


	public void setReport(Report report) {
		this.report = report;
	}


	public Report getReport() {
		return report;
	}

	public void doDiplay() throws CoreException{
		
		getReport().generateReport();
		
	}
	
}
