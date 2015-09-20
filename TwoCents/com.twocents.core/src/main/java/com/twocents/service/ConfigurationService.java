package com.twocents.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Configuration;
import com.twocents.model.GeneralConfiguration;
import com.twocents.model.NetworkConfiguration;
import com.twocents.model.QuoteConfiguration;
import com.twocents.model.StateConfiguration;


@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Throwable.class, CoreException.class})
public interface ConfigurationService extends BaseService<Configuration> {
	
	String BEAN_NAME = "com.twocents.service.ConfigurationService";

	NetworkConfiguration uniqueNetworkConfiguration() throws CoreException;

	QuoteConfiguration uniqueQuoteConfiguration() throws CoreException;

	GeneralConfiguration uniqueGeneralConfiguration() throws CoreException;

	StateConfiguration uniqueStateConfiguration() throws CoreException;

	
}
