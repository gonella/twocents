package com.twocents.ui.client.components.configuration.action;

import org.apache.log4j.Logger;

import com.twocents.ui.client.common.action.UIConfigAction;
import com.twocents.ui.client.components.configuration.GeneralConfiguration;
import com.twocents.ui.client.components.facade.ConfigurationFacade;
import com.twocents.ui.exceptions.UIException;

public class GeneralConfigurationAction extends UIConfigAction {

	private Logger logger = Logger.getLogger(GeneralConfigurationAction.class);
	private ConfigurationFacade facade = new ConfigurationFacade();

	private final GeneralConfiguration UI;

	public ConfigurationFacade getFacade() {
		return facade;
	}

	public GeneralConfiguration getUI() {
		return UI;
	}

	public GeneralConfigurationAction(GeneralConfiguration UI) {
		super();
		this.UI = UI;

		init();
	}

	public void init() {
		logger.debug("Loading General Configuration");
		
		try{
			
			com.twocents.model.GeneralConfiguration config=getFacade().uniqueGeneralConfiguration();
			if(config!=null){
				
				getUI().getActivateInternetConnectivityCheckBox().setSelection(config.isActiveInternetConnectivity());
				
			}
			
				
			
		}catch(UIException e){
			logger.error(e);
		}
	}

	public void applyConfiguration(){
		logger.debug("Applying the General configuration");
		
		try{
			
			com.twocents.model.GeneralConfiguration config=getFacade().uniqueGeneralConfiguration();
			if(config==null){
				config=new com.twocents.model.GeneralConfiguration();
			}
			
			config.setActiveInternetConnectivity(getUI().getActivateInternetConnectivityCheckBox().getSelection());
			
			getFacade().addConfiguration(config);
			
			
		}catch(UIException e){
			logger.error(e);
		}
		
		
	}
	
	
	public void refresh() {

	}

	
}
