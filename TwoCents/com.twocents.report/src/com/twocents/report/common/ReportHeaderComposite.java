package com.twocents.report.common;

import org.eclipse.swt.widgets.Composite;

public abstract class ReportHeaderComposite extends Composite{

	
	public ReportHeaderComposite(Composite arg0, int arg1) {
		super(arg0, arg1);
	}

	public abstract void doExecute();
	
}
