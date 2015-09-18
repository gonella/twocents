package com.twocents.report.handler.progress;


import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;

import com.twocents.ui.client.report.Report;

public abstract class LoadingProcessReport extends Worker {

	private Logger logger = Logger.getLogger(LoadingProcessReport.class);
	
	private JasperPrint jasperPrint;

	private final Report report;
	
	public LoadingProcessReport(Report report) {
		this.report = report;

	}

	@Override
	public void before() {
	}
	
	public abstract void execute();

	@Override
	public void abort() {

		logger.info("Canceling");
	}

	@Override
	public void after() {
		// TODO Auto-generated method stub

	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}


}
