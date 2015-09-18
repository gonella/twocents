package com.twocents.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.twocents.dao.ConfigurationDAO;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Configuration;
import com.twocents.model.GeneralConfiguration;
import com.twocents.model.NetworkConfiguration;
import com.twocents.model.QuoteConfiguration;
import com.twocents.model.StateConfiguration;

@SuppressWarnings("unchecked")
public class ConfigurationServiceImpl extends BaseServiceImpl<Configuration, ConfigurationDAO> implements ConfigurationService {
	
	private static final Logger logger = Logger.getLogger(ConfigurationServiceImpl.class);
	
	public void setConfigurationDAO(ConfigurationDAO dao) {
		super.setDao(dao);
	}
	
	public ConfigurationDAO getConfigurationDAO() {
		return super.getDao();
	}
	
	public void addConfiguration(Configuration config) throws CoreException {
		getDao().persist(config);
	}
	public void removeConfiguration(Configuration config) throws CoreException {
		getDao().delete(config);
	}
	
	public List<NetworkConfiguration> listNetworkConfiguration() throws CoreException {
		return (List<NetworkConfiguration>) getDao().findByType(NetworkConfiguration.class);
	}
	public List<QuoteConfiguration> listQuoteConfiguration() throws CoreException {
		return (List<QuoteConfiguration>) getDao().findByType(QuoteConfiguration.class);
	}
	public List<GeneralConfiguration> listGeneralConfiguration() throws CoreException {
		return (List<GeneralConfiguration>) getDao().findByType(GeneralConfiguration.class);
	}
	public List<StateConfiguration> listStateConfiguration() throws CoreException {
		return (List<StateConfiguration>) getDao().findByType(StateConfiguration.class);
	}
	
	public NetworkConfiguration uniqueNetworkConfiguration() throws CoreException {
		List<NetworkConfiguration> list = listNetworkConfiguration();
		NetworkConfiguration config=null;		
		if(! CollectionUtils.isEmpty(list)){
			config=list.get(0);
		}
		return config;
	}
	public QuoteConfiguration uniqueQuoteConfiguration() throws CoreException {
		List<QuoteConfiguration> list = listQuoteConfiguration();
		QuoteConfiguration config=null;		
		if(! CollectionUtils.isEmpty(list)){
			config=list.get(0);
		}
		return config;
	}
	public GeneralConfiguration uniqueGeneralConfiguration() throws CoreException {
		List<GeneralConfiguration> list = listGeneralConfiguration();
		GeneralConfiguration config=null;		
		if(! CollectionUtils.isEmpty(list)){
			config=list.get(0);
		}
		return config;
	}
	
	public StateConfiguration uniqueStateConfiguration() throws CoreException {
		List<StateConfiguration> list = listStateConfiguration();
		StateConfiguration config=null;		
		if(! CollectionUtils.isEmpty(list)){
			config=list.get(0);
		}
		return config;
	}
}
