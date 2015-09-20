package com.twocents.ui.client.report.header;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.model.ChartType;
import com.twocents.ui.client.report.Report;
import com.twocents.ui.client.report.header.action.HeaderReportFilterByChartAction;

public class HeaderReportFilterByChartTypeComposite extends HeaderReportCompositeBody {
	
	private Combo chartTypeCombo;
	private HeaderReportFilterByChartAction headerAction;
	private Composite fieldComposite;
	
	public Composite getFieldComposite() {
		return fieldComposite;
	}

	public Combo getChartTypeCombo() {
		return chartTypeCombo;
	}

	public HeaderReportFilterByChartAction getHeaderAction() {
		return headerAction;
	}


	public HeaderReportFilterByChartTypeComposite(Composite reportComposite) {
		super(reportComposite);
		
		final Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);
		composite.setLayout(new BorderLayout(0, 0));

		fieldComposite = new Composite(composite, SWT.BORDER);
		fieldComposite.setLayoutData(BorderLayout.NORTH);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 20;
		fieldComposite.setLayout(gridLayout);
		
		final Composite composite_1_1 = new Composite(fieldComposite, SWT.NONE);
		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.numColumns = 5;
		composite_1_1.setLayout(gridLayout_2);

		final Label tipoLabel = new Label(composite_1_1, SWT.NONE);
		tipoLabel.setText("Tipo do Grafico");
		
				ToolBar toolBar = new ToolBar(composite_1_1, SWT.FLAT | SWT.RIGHT);
				
				ToolItem toolItemChartPie = new ToolItem(toolBar, SWT.NONE);
				toolItemChartPie.setImage(SWTResourceManager.getImage(HeaderReportFilterByChartTypeComposite.class, "/com/twocents/report/resources/images/report-chart-pie_48x48.png"));

				toolItemChartPie.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(final SelectionEvent arg0) {
						getHeaderAction().setChartType(ChartType.PIE);
					}
				});

				
				ToolItem toolItemChartBar = new ToolItem(toolBar, SWT.NONE);
				toolItemChartBar.setImage(SWTResourceManager.getImage(HeaderReportFilterByChartTypeComposite.class, "/com/twocents/report/resources/images/report-chart-bar_48x48.png"));
				
				toolItemChartBar.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(final SelectionEvent arg0) {
						getHeaderAction().setChartType(ChartType.BAR);
					}
				});
											
				
	}	
	

	public void init(){
		if(headerAction==null){
			headerAction = new HeaderReportFilterByChartAction(this,getReport());
		}
	}	
	
	protected void checkSubclass() {
	}
	
	
}
