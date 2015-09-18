package com.twocents.ui.client.report;

import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.ReportGroupType;
import com.twocents.report.handler.progress.LoadingProcessReport;
import com.twocents.report.handler.progress.WaitLoading;
import com.twocents.report.jasper.JasperReport;
import com.twocents.service.BrokerageNoteService;
import com.twocents.service.CustodyService;
import com.twocents.service.OperationService;
import com.twocents.service.QuoteService;
import com.twocents.service.ReportService;
import com.twocents.spring.ServiceLocator;
import com.twocents.ui.client.report.header.HeaderReportComposite;
import com.twocents.ui.client.report.header.ReportPanel;

public abstract class Report extends Composite{
	
	private Logger logger = Logger.getLogger(Report.class);

	public String REPORT_PARAM_KEY="REPORT_PARAM";
	
	private final ReportService reportService = (ReportService)ServiceLocator.getBean(ReportService.BEAN_NAME);
	private final OperationService operationService = (OperationService)ServiceLocator.getBean(OperationService.BEAN_NAME);
	private final CustodyService custodyService = (CustodyService)ServiceLocator.getBean(CustodyService.BEAN_NAME);
	private final BrokerageNoteService brokerageNoteService = (BrokerageNoteService)ServiceLocator.getBean(BrokerageNoteService.BEAN_NAME);
	private final QuoteService quoteService = (QuoteService)ServiceLocator.getBean(QuoteService.BEAN_NAME);

	private String name;
	private ReportGroupType groupType;
	private String description;
	
	private HashMap reportParam=new HashMap();
	private HeaderReportComposite header;
	private Account accountSelected;
	
	private Composite headerComposite;
	private Composite reportComposite;

	private ReportPanel reportPanel;
	
	public String getDescription() {
		return description;
	}
	public ReportGroupType getGroupType() {
		return groupType;
	}
	public void setGroupType(ReportGroupType groupType) {
		this.groupType = groupType;
	}
	public String getName() {
		return name;
	}
	
	public Account getAccountSelected() {
		return accountSelected;
	}
	
	public Report(Composite parentComposite,String name,ReportGroupType groupType,String description){
		super(parentComposite,SWT.NONE);
		this.name=name;
		this.groupType=groupType;
		this.description=description;
		
		setBackground(SWTResourceManager.getColor(255, 255, 255));
		setLayoutData(BorderLayout.CENTER);
		setLayout(new BorderLayout(0, 0));
		
		setHeaderComposite(new Composite(this, SWT.NONE));
		getHeaderComposite().setLayoutData(BorderLayout.NORTH);
		getHeaderComposite().setLayout(new FillLayout(SWT.HORIZONTAL));
		
		setReportComposite(new Composite(this, SWT.NONE));
		getReportComposite().setLayoutData(BorderLayout.CENTER);
		getReportComposite().setLayout(new BorderLayout(0, 0));
		
		buildHeader(getHeaderComposite());
		
		setVisible(true);
		
	}
	
	public abstract JasperPrint generateData(Account account, HashMap param) throws CoreException;

	public abstract void buildHeader(Composite parentComposite);
	
	public HashMap getReportParam() {
		return reportParam;
	}
	public void setReportParam(HashMap reportParam) {
		this.reportParam = reportParam;
	}

	public BrokerageNoteService getBrokerageNoteService() {
		return brokerageNoteService;
	}
	public QuoteService getQuoteService() {
		return quoteService;
	}

	public ReportService getReportService() {
		return reportService;
	}
	
	public OperationService getOperationService() {
		return operationService;
	}
	public CustodyService getCustodyService() {
		return custodyService;
	}
	public void setHeader(HeaderReportComposite header) {
		this.header = header;
	}
	public HeaderReportComposite getHeader() {
		return header;
	}
	public void setAccountSelected(Account accountSelected) {
		this.accountSelected = accountSelected;
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
	public void plot(JasperPrint jasperPrint) {
		logger.info("[Plot] Criando painel para o relatório ser contruido");
		if(reportPanel!=null){
			reportPanel.dispose();
		}
		reportPanel = new ReportPanel(getReportComposite(),jasperPrint);
		reportPanel.getShell().layout(true);
		getReportComposite().redraw();
	}
	
	
	public void generateReport() throws CoreException{
		generateReport(getAccountSelected());
	}	
	public void generateReport(Account accountSelected) throws CoreException{
		logger.info("# Relatório selecionado: [" + getName()+ " - " + getGroupType() + "]");

		logger.info("[relatorio] gerando para a conta ["+accountSelected.getUser().getName()+"]");
		
		load(accountSelected);
		
		LoadingProcessReport process=new LoadingProcessReport(this){
			public void execute() {
				try {
					setJasperPrint(generateJasper());
				} catch (CoreException e) {
					logger.error(e.getMessage(),e);
				}
			}			
		};
		WaitLoading waitLoading=new WaitLoading(getParent(),process,SWT.NONE);
		waitLoading.open();
		
		plot(process.getJasperPrint());
		
		this.getParent().getShell().layout(true,true);
	}

	private void load(Account accountSelected) {
		this.setAccountSelected(accountSelected);

		if(getHeader()!=null){
			getHeader().init();
		}
	}
	private JasperPrint generateJasper() throws CoreException{
		
		logger.debug("[REPORT] - "+getName());
		try {
			
			JasperPrint generateData = generateData(getAccountSelected(),getReportParam());
			logger.info("[** Relatorio gerado com sucesso.]");
			return generateData;
			
		} catch (Exception e) {
			logger.error("Não foi possível gerar relatório",e);
			return JasperReport.generateReportError(e);
		}	
	}
	
}
