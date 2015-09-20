package com.twocents.ui.client.session;

import com.twocents.context.ContextHolder;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.NetworkConfiguration;
import com.twocents.model.QuoteConfiguration;
import com.twocents.ui.client.action.TwoCentsUIAction;
import com.twocents.ui.exceptions.UIException;

/**
 *	Intends to supply the global information 
 *  
 *  @author gonella
 */

public class ContextHolderUI extends ContextHolder {
	
	private static ThreadLocal<TwoCentsUIAction> environmentAction = new ThreadLocal<TwoCentsUIAction>();

	private static ThreadLocal<NetworkConfiguration> currentNetworkConfiguration = new ThreadLocal<NetworkConfiguration>();
	private static ThreadLocal<QuoteConfiguration> currentQuoteConfiguration = new ThreadLocal<QuoteConfiguration>();


	public static NetworkConfiguration getNetworkConfiguration() throws UIException {
		
		NetworkConfiguration config = currentNetworkConfiguration.get();
		if (config == null) {
			throw new UIException(4006);
		}
		
		return config;
	}
	public static QuoteConfiguration getQuoteConfiguration() throws UIException {
		
		QuoteConfiguration config = currentQuoteConfiguration.get();
		if (config == null) {
			throw new UIException(4007);
		}
		
		return config;
	}
	
	
	
	
	public static TwoCentsUIAction getEnvironmentAction() throws UIException {
		
		TwoCentsUIAction env = environmentAction.get();
		if (env == null) {
			throw new UIException(4000);
		}

		return env;		
	}
	
	public static void setNetworkConfiguration(
			NetworkConfiguration networkConfiguration) {
		currentNetworkConfiguration.set(networkConfiguration);
	}
	public static void setQuoteConfiguration(
			QuoteConfiguration quoteConfiguration) {
		currentQuoteConfiguration.set(quoteConfiguration);
	}
	
	
	public static void setEnvironmentAction(TwoCentsUIAction env) {
		environmentAction.set(env);
	}

}


