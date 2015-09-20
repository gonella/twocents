package com.twocents.ui.client.components.configuration.action;

import org.apache.log4j.Logger;

import com.twocents.core.util.StringCheck;
import com.twocents.ui.client.common.action.UIConfigAction;
import com.twocents.ui.client.components.configuration.NetworkConfiguration;
import com.twocents.ui.client.components.facade.ConfigurationFacade;
import com.twocents.ui.constant.UIDefault;
import com.twocents.ui.exceptions.UIException;

public class NetworkConfigurationAction extends UIConfigAction {

	private Logger logger = Logger.getLogger(NetworkConfigurationAction.class);
	private ConfigurationFacade facade = new ConfigurationFacade();

	private final NetworkConfiguration UI;

	public ConfigurationFacade getFacade() {
		return facade;
	}

	public NetworkConfiguration getUI() {
		return UI;
	}

	public NetworkConfigurationAction(NetworkConfiguration UI) {
		super();
		this.UI = UI;

		init();
	}

	public void init() {
		logger.debug("Loading network configuration");

		try {
			com.twocents.model.NetworkConfiguration nConfig = getFacade().uniqueNetworkConfiguration();

			if (nConfig!=null){

				if (nConfig.isManualProxyConfiguration()) {

					//disableAllFieldForDirectlyConnected();
					enableAllFieldForManualProxy();

					getUI().getHttpProxyText().setText(nConfig.getHttpProxy());
					getUI().getPortText().setText(nConfig.getHttpProxyPort());
					
					if (nConfig.isProxyAuthentication()) {

						getUI().getUsernameText()
								.setText(nConfig.getUsername());
						getUI().getPasswordText()
								.setText(nConfig.getPassword());
						
						getUI().getAuthenticationProxyCheckbox().setSelection(true);
					}

				} else {
					disableAllFieldForManualProxy();
				}
			}

		} catch (UIException e) {
			logger.error(e);
		}

	}

	public void refresh() {

	}

	public void applyProxy() {
		logger.info("Applying the proxy");
		
		try{
			com.twocents.model.NetworkConfiguration nConfig=getFacade().uniqueNetworkConfiguration();
			
			if(nConfig==null){
				nConfig=new com.twocents.model.NetworkConfiguration();
			}
			
			if (getUI().getDirectlyConnectedToInternetRadio().getSelection()) {
				logger.debug("Setting directly connected to internet configuration");
				if(nConfig!=null){
					
					System.clearProperty(UIDefault.proxyProperty);
					System.clearProperty(UIDefault.proxyPortProperty);
					
					nConfig.setManualProxyConfiguration(false);
					nConfig.setProxyAuthentication(false);
					
					getFacade().addConfiguration(nConfig);
				}
			}
			else if (getUI().getManualConfigurationProxyRadio().getSelection()) {
				
				logger.debug("Setting proxy configuration");
				String httpProxy = getUI().getHttpProxyText().getText();
				String httpProxyPort = getUI().getPortText().getText();
				
				if(StringCheck.isEmpty(httpProxy)){
					throw new UIException(9400);
				}
				
				if(StringCheck.isEmpty(httpProxyPort)){
					throw new UIException(9401);
				}
				
				nConfig.setHttpProxy(httpProxy);
				nConfig.setHttpProxyPort(httpProxyPort);
				
				nConfig.setManualProxyConfiguration(true);
				
				boolean isAuthentication=getUI().getAuthenticationProxyCheckbox().getSelection();
				
				if(isAuthentication){
					String username = getUI().getUsernameText().getText();
					String password = getUI().getPasswordText().getText();
					
					nConfig.setUsername(username);
					nConfig.setPassword(password);
					nConfig.setProxyAuthentication(isAuthentication);
					
				}
				
				getFacade().addConfiguration(nConfig);
				
				logger.debug("Setting the system environment("+UIDefault.proxyProperty+","+UIDefault.proxyPortProperty+") with values : Proxy:"+nConfig.getHttpProxy()+" - Port:"+nConfig.getHttpProxyPort());
				System.setProperty(UIDefault.proxyProperty,nConfig.getHttpProxy());
				System.setProperty(UIDefault.proxyPortProperty,nConfig.getHttpProxyPort());
			}
			logger.debug("** Network Configuration was applied");
			
		}catch(UIException e){
			logger.error(e);
		}
		
	}

	public void displayProxyField() {

		logger.debug("Choosing the network configuration");
		if (getUI().getManualConfigurationProxyRadio().getSelection()) {
			enableAllFieldForManualProxy();
			//disableAllFieldForDirectlyConnected();
		} else if (getUI().getDirectlyConnectedToInternetRadio().getSelection()) {
			//enableAllFieldForDirectlyConnected();
			disableAllFieldForManualProxy();
		}

	}

	public void enableAllFieldForDirectlyConnected() {
		getUI().getDirectlyConnectedToInternetRadio().setEnabled(true);
	}

	public void disableAllFieldForDirectlyConnected() {
		getUI().getDirectlyConnectedToInternetRadio().setEnabled(false);
	}

	public void disableAllFieldForManualProxy() {

		getUI().getDirectlyConnectedToInternetRadio().setSelection(true);
		getUI().getManualConfigurationProxyRadio().setSelection(false);

		getUI().getHttpProxyLabel().setEnabled(false);
		getUI().getHttpProxyText().setEnabled(false);

		getUI().getPortText().setEnabled(false);
		getUI().getPortLabel().setEnabled(false);

		getUI().getUsernameLabel().setEnabled(false);
		getUI().getPasswordLabel().setEnabled(false);
		
		getUI().getAuthenticationProxyCheckbox().setEnabled(false);
		getUI().getPasswordText().setEnabled(false);
		getUI().getUsernameText().setEnabled(false);

	}

	public void enableAllFieldForManualProxy() {

		getUI().getDirectlyConnectedToInternetRadio().setSelection(false);
		getUI().getManualConfigurationProxyRadio().setSelection(true);

		getUI().getHttpProxyLabel().setEnabled(true);
		getUI().getHttpProxyText().setEnabled(true);

		getUI().getPortText().setEnabled(true);
		getUI().getPortLabel().setEnabled(true);

		getUI().getAuthenticationProxyCheckbox().setEnabled(true);
		getUI().getUsernameLabel().setEnabled(true);
		getUI().getPasswordLabel().setEnabled(true);
		getUI().getPasswordText().setEnabled(true);
		getUI().getUsernameText().setEnabled(true);

	}

}
