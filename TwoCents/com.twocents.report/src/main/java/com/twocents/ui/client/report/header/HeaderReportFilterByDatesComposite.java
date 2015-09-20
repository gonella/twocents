package com.twocents.ui.client.report.header;

import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.report.resources.ReportImages;
import com.twocents.ui.client.report.header.action.HeaderReportFilterByDatesAction;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class HeaderReportFilterByDatesComposite extends HeaderReportCompositeBody {
	
	private Composite fieldComposite;
	
	public Composite getFieldComposite() {
		return fieldComposite;
	}

	public void resetToolBarItems(){
		tltmAno.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_YEAR));
		tltmJan.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_JAN));
		tltmFev.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_FEV));
		tltmMar.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_MAR));
		tltmAbr.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_ABR));
		tltmMai.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_MAI));
		tltmJun.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_JUN));
		tltmJul.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_JUL));
		tltmAgo.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_AGO));
		tltmSet.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_SET));
		tltmOut.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_OUT));
		tltmNov.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_NOV));
		tltmDez.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_DEZ));
	}

	public HeaderReportFilterByDatesComposite(Composite reportComposite) {
		super(reportComposite);
		
		final Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new BorderLayout(0, 0));

		fieldComposite = new Composite(composite, SWT.BORDER);
		fieldComposite.setLayoutData(BorderLayout.NORTH);
		fieldComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		final Composite composite_1 = new Composite(fieldComposite, SWT.NONE);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 3;
		composite_1.setLayout(gridLayout_1);
	
		ToolBar toolBarYear = new ToolBar(composite_1, SWT.NONE);
		
		tltmAno = new ToolItem(toolBarYear, SWT.NONE);
		tltmAno.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_YEAR));
		tltmAno.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				displayYearSelect();
				
				getHeaderAction().selectYear(getComboYear().getItem(getComboYear().getSelectionIndex()));
			}

			
		}
		);
		
		setComboYear(new Combo(composite_1, SWT.READ_ONLY));
		getComboYear().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				getHeaderAction().selectYear(getComboYear().getItem(getComboYear().getSelectionIndex()));
			}
		}
		);
		
		ToolBar toolBar = new ToolBar(composite_1, SWT.NONE);
		
		
		tltmJan = new ToolItem(toolBar, SWT.NONE);
		tltmJan.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_JAN));
		tltmJan.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				resetToolBarItems();
				tltmJan.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_JAN_SELECT));
				
				getHeaderAction().selectMonth(Calendar.JANUARY);
			}
		}
		);
		
		
		
		tltmFev = new ToolItem(toolBar, SWT.NONE);
		tltmFev.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_FEV));
		tltmFev.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				resetToolBarItems();
				tltmFev.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_FEV_SELECT));
				
				getHeaderAction().selectMonth(Calendar.FEBRUARY);
			}
		}
		);
		
		tltmMar = new ToolItem(toolBar, SWT.NONE);
		tltmMar.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_MAR));
		tltmMar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				resetToolBarItems();
				tltmMar.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_MAR_SELECT));
				
				getHeaderAction().selectMonth(Calendar.MARCH);
			}
		}
		);
		
		tltmAbr = new ToolItem(toolBar, SWT.NONE);
		tltmAbr.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_ABR));
		tltmAbr.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				resetToolBarItems();
				tltmAbr.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_ABR_SELECT));
				
				getHeaderAction().selectMonth(Calendar.APRIL);
			}
		}
		);
		
		tltmMai = new ToolItem(toolBar, SWT.NONE);
		tltmMai.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_MAI));
		tltmMai.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				resetToolBarItems();
				tltmMai.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_MAI_SELECT));
				
				getHeaderAction().selectMonth(Calendar.MAY);
			}
		}
		);
		
		tltmJun = new ToolItem(toolBar, SWT.NONE);
		tltmJun.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_JUN));
		tltmJun.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				resetToolBarItems();
				tltmJun.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_JUN_SELECT));
				
				getHeaderAction().selectMonth(Calendar.JUNE);
			}
		}
		);
		
		tltmJul = new ToolItem(toolBar, SWT.NONE);
		tltmJul.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_JUL));
		tltmJul.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				resetToolBarItems();
				tltmJul.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_JUL_SELECT));
				
				getHeaderAction().selectMonth(Calendar.JULY);
			}
		}
		);
		
		tltmAgo = new ToolItem(toolBar, SWT.NONE);
		tltmAgo.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_AGO));
		tltmAgo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				resetToolBarItems();
				tltmAgo.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_AGO_SELECT));
				
				getHeaderAction().selectMonth(Calendar.AUGUST);
			}
		}
		);
		
		tltmSet = new ToolItem(toolBar, SWT.NONE);
		tltmSet.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_SET));
		tltmSet.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				resetToolBarItems();
				tltmSet.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_SET_SELECT));
				
				getHeaderAction().selectMonth(Calendar.SEPTEMBER);
			}
		}
		);
		
		tltmOut = new ToolItem(toolBar, SWT.NONE);
		tltmOut.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_OUT));
		tltmOut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				resetToolBarItems();
				tltmOut.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_OUT_SELECT));
				
				getHeaderAction().selectMonth(Calendar.OCTOBER);
			}
		}
		);
		
		tltmNov = new ToolItem(toolBar, SWT.NONE);
		tltmNov.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_NOV));
		tltmNov.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				resetToolBarItems();
				tltmNov.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_NOV_SELECT));
				
				getHeaderAction().selectMonth(Calendar.NOVEMBER);
			}
		}
		);
		
		tltmDez = new ToolItem(toolBar, SWT.NONE);
		tltmDez.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_DEZ));
		tltmDez.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				resetToolBarItems();
				tltmDez.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_MONTH_DEZ_SELECT));
				
				getHeaderAction().selectMonth(Calendar.DECEMBER);
			}
		}
		);

	}	

	protected void checkSubclass() {
	}

	public HeaderReportFilterByDatesAction getHeaderAction() {
		return headerAction;
	}
	private HeaderReportFilterByDatesAction headerAction;
	private Combo comboYear;
	private ToolItem tltmAno;
	private ToolItem tltmJan;
	private ToolItem tltmFev;
	private ToolItem tltmMar;
	private ToolItem tltmAbr;
	private ToolItem tltmMai;
	private ToolItem tltmJun;
	private ToolItem tltmJul;
	private ToolItem tltmAgo;
	private ToolItem tltmSet;
	private ToolItem tltmOut;
	private ToolItem tltmNov;
	private ToolItem tltmDez;

	@Override
	public void init() {
		if(headerAction==null){
			headerAction = new HeaderReportFilterByDatesAction(this,getReport());
		}
	}


	public void setComboYear(Combo comboYear) {
		this.comboYear = comboYear;
	}


	public Combo getComboYear() {
		return comboYear;
	}

	public void displayYearSelect() {
		resetToolBarItems();
		tltmAno.setImage(SWTResourceManager.getImage(HeaderReportFilterByDatesComposite.class, ReportImages.BUTTON_REPORT_YEAR_SELECT));
	}
	
	
}
