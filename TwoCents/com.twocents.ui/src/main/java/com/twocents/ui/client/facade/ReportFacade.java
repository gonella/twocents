package com.twocents.ui.client.facade;

import com.twocents.service.ReportService;
import com.twocents.spring.ServiceLocator;

public class ReportFacade {
	
	ReportService reportService = (ReportService)ServiceLocator.getBean(ReportService.BEAN_NAME);
	
	public ReportService getReportService() {
		return reportService;
	}
}
