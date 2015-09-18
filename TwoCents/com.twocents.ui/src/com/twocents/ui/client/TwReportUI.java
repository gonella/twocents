package com.twocents.ui.client;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.ui.client.action.TwReportUIAction;
import com.twocents.ui.client.components.ReportContentBlank;
import com.twocents.ui.client.components.ReportGallery;
import com.twocents.ui.client.components.ReportTableComposite;
import com.twocents.ui.client.components.WindowsAdvertisingInReport;
import com.twocents.ui.client.report.header.HeaderReportCompositeEmpty;
import com.twocents.ui.client.report.header.ReportPanel;

public class TwReportUI extends TwBase {


	private Browser browser;

	private TwReportUIAction action;
	private Composite showComposite;
	private Composite composite;
	private Table tableReport;
	private static ReportPanel reportPanel;
	private Composite headerComposite;
	private Composite reportComposite;
	private HeaderReportCompositeEmpty headerEmpty;
	private ReportGallery reportGallery;
	private final Composite containerComponents;
	private StackLayout showCompositeStackLayout;
	private ReportContentBlank reportContentBlank;

	private ReportTableComposite reportTableComposite;


	public TwReportUIAction getAction() {
		
		if(action==null){
			action = new TwReportUIAction(this);
		}
		
		return action;
	}

	

	/**
	 * Create the composite
	 * @param parent
	 * @param compositeCenter 
	 * @param compositeLeftBotton 
	 * @param style
	 */
	public TwReportUI(Composite containerComponents, Composite compositeCenter, Composite compositeLeftBotton, int style,String title,Composite compositeForReportItems)  {
		super(compositeCenter, style,title);
		this.containerComponents = containerComponents;
		createComponents(getCompositeCenter());
		
		setReportTableComposite(new ReportTableComposite(compositeLeftBotton,SWT.NONE, this));

	}

	public void setReportTableComposite(
			ReportTableComposite reportTableComposite) {
		this.reportTableComposite = reportTableComposite;
	}

	public ReportTableComposite getReportTableComposite() {
		return reportTableComposite;
	}
	
	protected void createComponents(Composite center){
		
		Composite main= new Composite(center, SWT.NONE);
		
		main.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		final FillLayout fillLayout_1 = new FillLayout();
		main.setLayout(fillLayout_1);
		//browser.setUrl("");
		
		final Composite composite_1 = new Composite(main, SWT.NONE);
		composite_1.setLayout(new BorderLayout(0, 0));
		composite_1.setBackground(SWTResourceManager.getColor(255, 255, 255));
		//browser.setBounds(10, 22, 659, 538);

		composite = new Composite(composite_1, SWT.NONE);
		composite.setLayout(new BorderLayout(0, 0));
		composite.setBackground(SWTResourceManager.getColor(255, 255, 255));
		composite.setLayoutData(BorderLayout.CENTER);
	
		WindowsAdvertisingInReport windowsAdvertisingInReport=new WindowsAdvertisingInReport(composite);
		
		/*final ToolBar toolBar = new ToolBar(composite_2, SWT.VERTICAL | SWT.FLAT);
		toolBar.setForeground(SWTResourceManager.getColor(125, 190, 255));
		toolBar.setBackground(SWTResourceManager.getColor(155, 205, 255));

		final ToolItem toolItemExportPDF = new ToolItem(toolBar, SWT.PUSH);
		toolItemExportPDF.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				doExportToPDF();
			}
		});
		toolItemExportPDF.setImage(SWTResourceManager.getImage(TwReportUI.class, UIImages.EXPORT_PDF_ICON_48));
		toolItemExportPDF.setText("Exportar para PDF");

		final ToolItem toolItemExportXLS = new ToolItem(toolBar, SWT.PUSH);
		toolItemExportXLS.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				doExportToExcel();
			}
		});
		toolItemExportXLS.setImage(SWTResourceManager.getImage(TwReportUI.class, UIImages.EXPORT_XLS_ICON_48));
		toolItemExportXLS.setText("Exportar para Excel");*/

		createPanelBlank();
		
		
	}

	public void createPanelBlank() {
		
		if(showComposite!=null){
			showComposite.dispose();
		}
		showComposite = new Composite(composite, SWT.NONE);
		showComposite.setBackground(SWTResourceManager.getColor(255, 255, 255));
		showComposite.setLayoutData(BorderLayout.CENTER);
		//showComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		//showComposite.setLayout(new BorderLayout(0, 0));
		
		setShowCompositeStackLayout(new StackLayout());		
		showComposite.setLayout(getShowCompositeStackLayout());
		
		setReportContentBlank(new ReportContentBlank(showComposite));
		
		getShowCompositeStackLayout().topControl=getReportContentBlank();
	}

	public void closeReportGallery(){
		if ((getReportGallery() != null) && (!getReportGallery().isDisposed())) {
			getReportGallery().dispose();
		}
	}
	
	
	protected void doExportToExcel() {
		getAction().doExportToExcel();
		
	}

	protected void doExportToPDF() {
		getAction().doExportToPDF();
		
	}

	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	
	
	public Browser getBrowser() {
		return browser;
	}
	
	public Composite getComposite() {
		return composite;
	}
	public Composite getShowComposite() {
		return showComposite;
	}

	
	public Table getTableReport() {
		return tableReport;
	}
	
	
	/*public void setPanelDisable(){
		
		if(getShelf()!=null){
			getShelf().setEnabled(false);
		}
		
	}
	public void setPanelEnable(){
		if(getShelf()!=null){
			getShelf().setEnabled(true);
		}
		
	}*/

	public ReportPanel getReportPanel() {
		return reportPanel;
	}

	public void setHeaderComposite(Composite headerComposite) {
		this.headerComposite = headerComposite;
	}

	public Composite getHeaderComposite() {
		return headerComposite;
	}

	public void setReportComposite(Composite reportComposite) {
		this.reportComposite = reportComposite;
	}

	public Composite getReportComposite() {
		return reportComposite;
	}

	public void setHeaderEmpty(HeaderReportCompositeEmpty headerEmpty) {
		this.headerEmpty = headerEmpty;
	}

	public HeaderReportCompositeEmpty getHeaderEmpty() {
		return headerEmpty;
	}

	public void setReportGallery(ReportGallery reportGallery) {
		this.reportGallery = reportGallery;
	}

	public ReportGallery getReportGallery() {
		return reportGallery;
	}

	public Composite getContainerComponents() {
		return containerComponents;
	}

	public void setShowCompositeStackLayout(StackLayout showCompositeStackLayout) {
		this.showCompositeStackLayout = showCompositeStackLayout;
	}

	public StackLayout getShowCompositeStackLayout() {
		return showCompositeStackLayout;
	}

	public void setReportContentBlank(ReportContentBlank reportContentBlank) {
		this.reportContentBlank = reportContentBlank;
	}

	public ReportContentBlank getReportContentBlank() {
		return reportContentBlank;
	}

}
