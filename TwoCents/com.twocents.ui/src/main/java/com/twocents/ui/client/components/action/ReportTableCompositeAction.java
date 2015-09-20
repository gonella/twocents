package com.twocents.ui.client.components.action;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.ui.client.common.action.UIAction;
import com.twocents.ui.client.components.ReportTableComposite;
import com.twocents.ui.client.report.Report;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.resources.UIImages;

public class ReportTableCompositeAction extends UIAction {

	private static Logger logger = Logger.getLogger(ReportTableCompositeAction.class);
	private final ReportTableComposite reportTableComposite;

	public ReportTableCompositeAction(ReportTableComposite reportTableComposite) {
		this.reportTableComposite = reportTableComposite;
	}

	
	@Override
	public void refresh() {
		
	}

	@Override
	public void init() {
			
		Account accountSelected = null;
		try 
		{
			accountSelected = ContextHolderUI.getAccountSelected();
			
			populateReport();
		} catch (CoreException e) {
			logger.debug("Não foi selecionado uma conta de cliente");
		}
	}

	private void populateReport(){
		
		Report[] reports = getReportTableComposite().getTwReportUI().getAction().getReports();

		logger.info("Carregando ["+reports.length+"] relatórios");
		getReportTableComposite().getTable().removeAll();
		TableItem item;
		for(int i=0;i<reports.length;i++){
			item=new TableItem(getReportTableComposite().getTable(),SWT.NONE);
			item.setText(reports[i].getName());
			item.setData(reports[i]);
			item.setImage(com.swtdesigner.SWTResourceManager.getImage(this.getClass(), UIImages.BULLET_REPORT1_ICON_24));
		}	
		
	}

	private ReportTableComposite getReportTableComposite() {
		return reportTableComposite;
	}

	public void doCollectData() {
		// TODO Auto-generated method stub
		
	}
	
	
}
