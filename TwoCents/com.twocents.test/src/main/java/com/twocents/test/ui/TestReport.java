package com.twocents.test.ui;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.twocents.core.database.DatabaseSetUp;
import com.twocents.model.Account;
import com.twocents.model.StockBroker;
import com.twocents.ui.TwoCentsUI;
import com.twocents.ui.client.action.TwoCentsUIAction;
import com.twocents.ui.client.report.ReportUIBrokerageNote;

public class TestReport {
	private static Logger logger = Logger.getLogger(TestReport.class);

	private static Shell shell;

	private Composite headerComposite;

	private Composite reportComposite;

	private final int windowsWitdh;

	private final int windowsHeight;

	public static void main(String[] args) {
		try {
			
			DatabaseSetUp.startHsqlInServerMode();
			TwoCentsUIAction twoCentsUIAction = new TwoCentsUIAction();
			
			Account accountSelectedTmp = null;
			List<StockBroker> listAllStockBroker = twoCentsUIAction.getEnvironmentFacade().listAllStockBroker();
			
			for (Iterator iteratorsb = listAllStockBroker.iterator(); iteratorsb
					.hasNext();) {
				StockBroker stockBroker = (StockBroker) iteratorsb.next();

				logger.info("Recuperando contas do corretor : "	+ stockBroker.getName());

				List<Account> accountList = stockBroker.getAccountList();

				for (Iterator iterator = accountList.iterator(); iterator
						.hasNext();) {
					accountSelectedTmp = (Account) iterator.next();
					
					logger.info("Conta Selecionada :"+accountSelectedTmp.getName());
					
					break;
				}
			}
						
			final Account accountSelected=accountSelectedTmp;
			final TestReport testUIReport = new TestReport(TwoCentsUI.windowsWitdh,TwoCentsUI.windowsHeight);
			/*final TwReportUIAction twReportUIAction = new TwReportUIAction(null){
				private  Logger logger = Logger.getLogger(TwReportUIAction.class);

				@Override
				public void displayReportSelected(Object reportObject) {
					logger.info("Apresentando relatorio com novos parametros");

					if(reportObject!=null){
						
						Report reportSelected = (Report)reportObject;
						
						try {
							reportSelected.generateReport(accountSelected);
						} catch (CoreException e) {
							logger.error(e);
						}
						
					}
				}
				
			};
*/			
			final ReportUIBrokerageNote report = new ReportUIBrokerageNote(testUIReport.getShell());
			
			report.generateReport(accountSelected);
			
			testUIReport.open();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public TestReport(int windowsWitdh, int windowsHeight) {
		this.windowsWitdh = windowsWitdh;
		this.windowsHeight = windowsHeight;
		createContents();
	}

	public void open() {
		final Display display = Display.getDefault();
		
		getShell().addDisposeListener(new DisposeListener() {
			public void widgetDisposed(final DisposeEvent arg0) {
				System.exit(0);
			}

		});
		getShell().setSize(windowsWitdh, windowsHeight);
		getShell().setMinimumSize(windowsWitdh, windowsHeight);

		
		getShell().open();
		getShell().layout();
		while (!getShell().isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		setShell(new Shell());
		getShell().setLocation(100,50);
		getShell().setSize(400, 400);
		getShell().setText("Report Test");
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public Shell getShell() {
		return shell;
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

}
