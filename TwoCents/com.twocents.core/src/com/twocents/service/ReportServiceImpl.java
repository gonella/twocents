package com.twocents.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;


@Transactional(propagation=Propagation.REQUIRED)
public class ReportServiceImpl implements ReportService {
	
	private static final Logger logger = Logger.getLogger(ReportServiceImpl.class);
	private OperationService operationService;
	
	private CustodyService custodyService;
	
	public CustodyService getCustodyService() {
		return custodyService;
	}

	public void setCustodyService(CustodyService custodyService) {
		this.custodyService = custodyService;
	}
	
	public OperationService getOperationService() {
		return operationService;
	}
	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}
	
	public Map getDateOfFirstOperation(long accountId) throws CoreException{
	
		HashMap map=new HashMap();
		
		
		
		return map;
	}
}
