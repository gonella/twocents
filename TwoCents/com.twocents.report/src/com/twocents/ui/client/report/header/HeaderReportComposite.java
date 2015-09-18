package com.twocents.ui.client.report.header;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.ui.client.report.Report;

public abstract class HeaderReportComposite extends Composite {
	
	private Logger logger = Logger.getLogger(this.getClass());

	private Report report;

	private Label description;

	
	public HeaderReportComposite(Composite composite) {
		super(composite,SWT.NONE);
		
		setLayout(new BorderLayout(0, 0));
		
		/*final Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new FillLayout());
		composite.setLayoutData(BorderLayout.NORTH);
		
		composite.setBackground(SWTResourceManager.getColor(155, 205, 255));
		
		setDescription(new Label(composite, SWT.NONE));
		getDescription().setText("[ Descrição ]");
		*/
		
		/*
		Composite bodyLeft = new Composite(this, SWT.NONE);
		bodyLeft.setLayoutData(BorderLayout.WEST);
		bodyLeft.setLayout(new GridLayout(1, false));
		
		Composite bodyRight = new Composite(this, SWT.NONE);
		bodyRight.setLayoutData(BorderLayout.EAST);
		bodyRight.setLayout(new GridLayout(1, false));
		
		setBtnConsultar(new Button(bodyRight, SWT.NONE));
		getBtnConsultar().setText("   Consultar   ");
		getBtnConsultar().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {

				
			}
		});
		
		setBody(new Composite(this, SWT.NONE));
		getBody().setLayoutData(BorderLayout.CENTER);
		*/
				
		//
	}	
	
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	
	public abstract void init();
	

	public void setDescription(Label description) {
		this.description = description;
		description.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
	}

	public Label getDescription() {
		return description;
	}
	
	
}
