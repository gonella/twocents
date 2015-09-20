package com.twocents.service;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;


@Transactional(propagation=Propagation.REQUIRED)
public interface ReportService {
	
	String BEAN_NAME = "com.twocents.service.ReportService";
	
	
	public Map getDateOfFirstOperation(long accountId) throws CoreException;

}
