package com.twocents.ui.client.components.facade;

import com.twocents.model.Configuration;
import com.twocents.model.GeneralConfiguration;
import com.twocents.model.NetworkConfiguration;
import com.twocents.model.QuoteConfiguration;
import com.twocents.model.StateConfiguration;
import com.twocents.service.ConfigurationService;
import com.twocents.spring.ServiceLocator;
import com.twocents.ui.exceptions.UIException;

public class ConfigurationFacade {
	
	ConfigurationService configurationService = (ConfigurationService)ServiceLocator.getBean(ConfigurationService.BEAN_NAME);

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void addConfiguration(Configuration config) throws UIException {
		try {
			getConfigurationService().persist(config);
		} catch (Exception e) {
			throw new UIException(e);
		}
	}
	
	public void removeConfiguration(Configuration config) throws UIException {
		try {
			getConfigurationService().delete(config);
		} catch (Exception e) {
			throw new UIException(e);
		}
	}
	
	
	public QuoteConfiguration uniqueQuoteConfiguration() throws UIException {
		try {
			return getConfigurationService().uniqueQuoteConfiguration();
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	public NetworkConfiguration uniqueNetworkConfiguration() throws UIException {
		try {
			return getConfigurationService().uniqueNetworkConfiguration();
		} catch (Exception e) {
			throw new UIException(e);
		}
	}
	public StateConfiguration uniqueStateConfiguration() throws UIException {
		try {
			return getConfigurationService().uniqueStateConfiguration();
		} catch (Exception e) {
			throw new UIException(e);
		}
	}
	public GeneralConfiguration uniqueGeneralConfiguration() throws UIException {
		try {
			return getConfigurationService().uniqueGeneralConfiguration();
		} catch (Exception e) {
			throw new UIException(e);
		}
	}
	
	
}
