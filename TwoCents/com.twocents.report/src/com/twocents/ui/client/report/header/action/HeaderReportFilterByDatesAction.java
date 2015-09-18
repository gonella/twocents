package com.twocents.ui.client.report.header.action;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.twocents.core.util.DateUtil;
import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.ReportParamInterval;
import com.twocents.report.util.DialogUtil;
import com.twocents.ui.client.report.Report;
import com.twocents.ui.client.report.header.HeaderReportFilterByDatesComposite;

public class HeaderReportFilterByDatesAction extends HeaderReportAction {

	private static final String _TODOS = "-- Todos --";

	
	private final Logger logger = Logger.getLogger(HeaderReportFilterByDatesAction.class);

	private final HeaderReportFilterByDatesComposite UI;

	private Calendar monthSelectedStart;

	private Calendar monthSelectedEnd;

	public HeaderReportFilterByDatesComposite getUI() {
		return UI;
	}

	public HeaderReportFilterByDatesAction(
			HeaderReportFilterByDatesComposite UI, Report report) {
		super(report);
		this.UI = UI;
		//It must exist
		init();
		
	}

	public void init() {
		
		logger.debug("Iniciando cabeçalho de consulta");
		
		try {
			Account account = getReport().getAccountSelected();
			if(account==null){
				throw new CoreException("Não existe conta selecionada");
			}
			
			List<Integer> findYearOfOperations = getFacade().getOperationService().findYearOfOperations(account);
			Collections.sort(findYearOfOperations);

			getUI().getComboYear().removeAll();

			//Collections.reverse(findYearOfOperations);


			int index = 0;
			// getUI().getComboYear().setData(arg0, arg1)
			if (!org.springframework.util.CollectionUtils
					.isEmpty(findYearOfOperations)
					&& findYearOfOperations.size() > 1) {


				getUI().getComboYear().add(_TODOS);
				getUI().getComboYear().setData(_TODOS, 0);
				index++;
			}

			for (Integer year : findYearOfOperations) {
				getUI().getComboYear().add(year.toString());
				getUI().getComboYear().setData(year.toString(), year);
				getUI().getComboYear().select(index);
				index++;
			}
			/*
			 * if(!org.springframework.util.CollectionUtils.isEmpty(
			 * findYearOfOperations )){ getUI().getComboYear().select(index); }
			 */

			storeParameter();

		} catch (CoreException e) {
			logger.error("Não foi possível inicializar o cabeçalho de consulta",e);
		}

	}

	public void refresh() {

	}

	@Override
	public ReportParamInterval submitData() {
		ReportParamInterval param = new ReportParamInterval(getReport()
				.getName(), getReport().getGroupType());

		int selectionIndex = getUI().getComboYear().getSelectionIndex();

		if (selectionIndex < 0) {
			return param;
		}

		String item = getUI().getComboYear().getItem(selectionIndex);

		Integer year = (Integer) getUI().getComboYear().getData(item);

		// Selecionado TODOS
		if (year == 0) {
			getUI().resetToolBarItems();
			
			logger.error("Função nao implementada");
			
			return param;
		} else {
					
			Date parseDate;
			try {
				parseDate = FormatUtil.year.parse(year.toString());

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(parseDate);

				Date first = DateUtil.getFirstDayInYear(calendar);
				Date last = DateUtil.getLastDayInYear(calendar);

				//Filtrando pelo mês se tiver selecionado
				if(monthSelectedStart!=null && monthSelectedEnd!=null){
					monthSelectedStart.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
					monthSelectedEnd.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
					
					first=monthSelectedStart.getTime();
					last=monthSelectedEnd.getTime();
				}
				else{
					getUI().displayYearSelect();
				}
				
				
				
				param.setDateStart(FormatUtil.getMinTimestamp(first));
				param.setDateEnd(FormatUtil.getMaxTimestamp(last));

				
				
			} catch (ParseException e) {
				logger.error("Não foi possível converter a data do Combo Ano",e);
			}
		}

		return param;
	}

	public void selectYear(String item) {
		logger.info("Gerar relatorio baseado no ano selecionado: " + item);

		monthSelectedStart = null;
		monthSelectedEnd = null;
	
		
		
		storeParameter();
		try {
			doDiplay();
		} catch (CoreException e) {
			logger.error(e);
			DialogUtil.errorMessage(getReport(), "Não foi possivel filtrar por ano");
		}
	}

	public void selectMonth(int calendarMonth) {
		
		monthSelectedStart = Calendar.getInstance();
		monthSelectedEnd = Calendar.getInstance();
		
		monthSelectedStart.set(Calendar.MONTH, calendarMonth);
		monthSelectedEnd.set(Calendar.MONTH, calendarMonth);

		
		logger.info("Selecionado mês de ["+(DateUtil.getMonthName(calendarMonth))+"] para o relatório");
		
		monthSelectedStart.set(Calendar.DAY_OF_MONTH, monthSelectedStart.getActualMinimum(Calendar.DAY_OF_MONTH));   
		monthSelectedEnd.set(Calendar.DAY_OF_MONTH, monthSelectedEnd.getActualMaximum(Calendar.DAY_OF_MONTH));  
	
		storeParameter();
		try {
			
			doDiplay();
			
		} catch (CoreException e) {
			logger.error(e);
			DialogUtil.errorMessage(getReport(), "Não foi possivel filtrar por mês");
		}	}
}
