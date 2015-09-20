package com.twocents.report.handler.progress;

import net.sf.jasperreports.engine.JasperPrint;

import org.eclipse.swt.widgets.Shell;

public abstract class ReportThreadProgressJasper extends ReportThreadProgress {

	JasperPrint jasperPrint;
	
	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}
	public ReportThreadProgressJasper(Shell shellToModal,Shell shellToCenter) {
		super(shellToModal,shellToCenter);
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

}
