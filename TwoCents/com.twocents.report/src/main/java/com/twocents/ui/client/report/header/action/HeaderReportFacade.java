package com.twocents.ui.client.report.header.action;

import java.util.List;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Custody;
import com.twocents.service.CustodyService;
import com.twocents.service.OperationService;
import com.twocents.spring.ServiceLocator;

public class HeaderReportFacade {
	
	OperationService operationService = (OperationService)ServiceLocator.getBean(OperationService.BEAN_NAME);

	CustodyService custodyService = (CustodyService)ServiceLocator.getBean(CustodyService.BEAN_NAME);
	
	public CustodyService getCustodyService() {
		return custodyService;
	}
	public OperationService getOperationService() {
		return operationService;
	}

	
	public List<Custody> listAllCustody() throws CoreException{
			return getCustodyService().findAll();
		
	}

	

	
}
