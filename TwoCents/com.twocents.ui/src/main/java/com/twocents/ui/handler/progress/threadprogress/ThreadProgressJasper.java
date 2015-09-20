package com.twocents.ui.handler.progress.threadprogress;

import net.sf.jasperreports.engine.JasperPrint;

import org.eclipse.swt.widgets.Shell;

public abstract class ThreadProgressJasper extends ThreadProgress {

	JasperPrint jasperPrint;
	
	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}
	public ThreadProgressJasper(Shell shellToModal,Shell shellToCenter) {
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
