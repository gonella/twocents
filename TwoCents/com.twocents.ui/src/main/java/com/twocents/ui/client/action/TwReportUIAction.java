package com.twocents.ui.client.action;

import org.apache.log4j.Logger;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.ui.client.TwReportUI;
import com.twocents.ui.client.common.action.UIAction;
import com.twocents.ui.client.report.Report;
import com.twocents.ui.client.report.ReportUIBrokerageNote;
import com.twocents.ui.client.report.ReportUIStockPortfolioChart;
import com.twocents.ui.client.report.ReportUIStockPortfolio;
import com.twocents.ui.client.report.ReportUITradePerformace;
import com.twocents.ui.client.report.ReportUITradeResult;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.resources.UIMessages;

public class TwReportUIAction extends UIAction {

	private Logger logger = Logger.getLogger(TwReportUIAction.class);

	private Report[] reports;

	private Account accountSelected;

	private Report reportSelected;


	public TwReportUIAction(TwReportUI twReportUI) {
		super();
		setTwReportUI(twReportUI);
	}

	public void init() {

		logger.debug("Inicializando relatórios");

		Account accountSelected = null;
		try {
			accountSelected = ContextHolderUI.getAccountSelected();
		} catch (CoreException e) {
			logger.debug("Sem conta selecionada");
		}

		reports = new Report[] {
				new ReportUIStockPortfolio(getTwReportUI().getShowComposite()),
				new ReportUIStockPortfolioChart(getTwReportUI().getShowComposite()),

				new ReportUIBrokerageNote(getTwReportUI().getShowComposite()),
				new ReportUITradeResult(getTwReportUI().getShowComposite()),
				new ReportUITradePerformace(getTwReportUI().getShowComposite()),

		};
	}

	public void doExportToExcel() {
		logger.info("Exportando para XLS");

	}

	public void doExportToPDF() {
		logger.info("Exportando para PDF");

		/*
		 * try { String fileName=openDialogToSaveTheFile(); if(fileName!=null){
		 * 
		 * if(reportSelected!=null){
		 * logger.info("Report selected :"+reportSelected
		 * .getName()+" - "+reportSelected.getGroupType()); ReportOutput
		 * output=new ReportOutput(OutputType.PDF,fileName); try {
		 * 
		 * //generateReportFile(ContextHolderUI.getAccountSelected(),reportSelected
		 * ,output);
		 * 
		 * } catch (CoreException e) { throw new UIException(e.getMessage(),e);
		 * } } else{ logger.info("No Report Selected to EXPORT"); } }
		 * 
		 * } catch (UIException e) { logger.error(e); }
		 */

	}

	public void displayAccountSelected() throws UIException {
		
		logger.info("Preparando os relatorios para a conta selecionada");
		try {
			Account newAccountSelected = ContextHolderUI.getAccountSelected();

			if (accountSelected != null
					&& newAccountSelected != null
					&& accountSelected.getAccountNumber().equals(
							newAccountSelected.getAccountNumber())) {
				logger.info("A mesma conta foi selecionada ["
						+ newAccountSelected.getUser().getName() + "]");
				
				//accountSelected = newAccountSelected;

			} else {
				logger.info("Uma nova conta foi selecionada ["
						+ newAccountSelected.getUser().getName() + "]");
				
				accountSelected = newAccountSelected;
				
				
				if(reportSelected!=null){
					
					getTwReportUI().getReportTableComposite().getTable().deselectAll();
					
					reportSelected.setVisible(false);
					getTwReportUI().getReportContentBlank().setVisible(true);
					getTwReportUI().getShowCompositeStackLayout().topControl=getTwReportUI().getReportContentBlank();
				}
				
			}

		} catch (CoreException e) {
			throw new UIException(
					"Não foi possível selecionar a conta para apresentar os reports",
					e);
		}
	
	}

	public void refresh() {
		logger.debug("Atualizando componentes da ReportUI");
	}

	public Report[] getReports() {
		return reports;
	}

	public void displayReportSelected(Report reportSelected) {
		
		this.reportSelected = reportSelected;
		try {
			
			
			if(accountSelected==null){	
				try {
					accountSelected = ContextHolderUI.getAccountSelected();
				} catch (CoreException e) {
					throw new UIException(e.getMessage(), e);
				}
			}			
			
			getTwReportUI().getReportContentBlank().setVisible(false);
			getTwReportUI().getShowCompositeStackLayout().topControl = reportSelected;
			reportSelected.generateReport(accountSelected);
			reportSelected.setVisible(true);


		} catch (Exception e) {
			logger.error(UIMessages.getMessage(4018), e);
		}
	}

	public void doCollectData() {

	}

}
