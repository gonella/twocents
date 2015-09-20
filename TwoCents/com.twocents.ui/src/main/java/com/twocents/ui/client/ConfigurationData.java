package com.twocents.ui.client;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.twocents.core.adapter.QuoteAdapter;
import com.twocents.core.util.StringCheck;
import com.twocents.model.CommentPattern;
import com.twocents.model.GeneralConfiguration;
import com.twocents.model.NetworkConfiguration;
import com.twocents.model.QuoteConfiguration;
import com.twocents.spring.ServiceLocator;
import com.twocents.ui.client.components.facade.CommentConfigurationFacade;
import com.twocents.ui.client.components.facade.ConfigurationFacade;
import com.twocents.ui.client.components.facade.AccountFacade;
import com.twocents.ui.client.facade.StockFacade;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.constant.UIDefault;
import com.twocents.ui.exceptions.UIException;

/*
 * Classe responsavel por restaurar configurações setadas pelo usuário, isso ao iniciar o sistema.
 * 
 */
public class ConfigurationData {

	private Logger logger = Logger.getLogger(ConfigurationData.class);

	private final CommentConfigurationFacade commentConfigurationFacade=new CommentConfigurationFacade();
	private final ConfigurationFacade configurationFacade=new ConfigurationFacade();
	private final AccountFacade userConfigurationFacade=new AccountFacade();
	private final StockFacade stockFacade = new StockFacade();
	
	
	public void applyConfigurationInRunTime() throws UIException{
		
		logger.debug("Applying the configuration existent in RUNTIME");
		
		applyGeneralConfiguration();
		
		applyNetworkConfiguration();
		
		applyQuoteConfiguration();
		
		applyCommentConfiguration();
		
		
	}
	private void applyGeneralConfiguration() throws UIException {
		logger.debug("INIT: General Configuration");
		
		GeneralConfiguration config = getConfigurationFacade().uniqueGeneralConfiguration();
		
		if(config!=null){
			
		}
	}
	
	public void applyQuoteConfiguration() throws UIException {
		logger.debug("INIT: Quote Configuration");
		QuoteConfiguration config = getConfigurationFacade().uniqueQuoteConfiguration();
		if(config != null){
			ContextHolderUI.setQuoteConfiguration(config);
			QuoteAdapter quoteAdapter = ServiceLocator.getQuoteAdapter(config.getAdapterBeanName());
			if(StringCheck.isNotBlank(config.getUrl())) {
				quoteAdapter.setUrl(config.getUrl());
			}
			com.twocents.context.ContextHolder.setCurrentQuoteAdapter(quoteAdapter);
			if(config.isActivateUpdateQuote()) {
				logger.debug("Creating update stock quotation task. Interval in minutes: " + config.getUpdateInterval());
				getStockFacade().createUpdateStockQuoteTask(config.getUpdateInterval() * 60000);
			} else {
				logger.debug("The quote update is inactive.");
			}
		}
	}
	
	private void applyCommentConfiguration() throws UIException {
		logger.debug("INIT: Comment Configuration");
		
		List<CommentPattern> list=getCommentConfigurationFacade().listAllCommentPattern();
		
		if(!CollectionUtils.isEmpty(list)){
			
			for(CommentPattern elem:list){
				if(elem.isDefaultComment()){
					//getOperationUI().getCommentaryText().setText(elem.getCommentPattern());
				}
			}			
		}		
	}
	

	private void applyNetworkConfiguration() throws UIException {
		logger.debug("INIT: Network Configuration");
		
		NetworkConfiguration nConfig = getConfigurationFacade().uniqueNetworkConfiguration();
		
		if(nConfig!=null){ 
			
				ContextHolderUI.setNetworkConfiguration(nConfig);
			
				if(nConfig.isManualProxyConfiguration()){
				
					logger.debug("Setting the system environment("+UIDefault.proxyProperty+","+UIDefault.proxyPortProperty+") with values : Proxy:"+nConfig.getHttpProxy()+" - Port:"+nConfig.getHttpProxyPort());
					
					System.setProperty(UIDefault.proxyProperty,nConfig.getHttpProxy());
					System.setProperty(UIDefault.proxyPortProperty,nConfig.getHttpProxyPort());
					
					
					if (nConfig.isProxyAuthentication()) {
		
						//nConfig.getUsername();
						//nConfig.getPassword();
					}
				}
		}
		
		
	}
	
	
	public void savingState() throws UIException{
		logger.debug("Saving the configuration");
		/*
		try {
		
		StateConfiguration uConfig = getConfigurationFacade().uniqueStateConfiguration();
		if (uConfig==null) {
			uConfig = new StateConfiguration();
		} 
		
		uConfig.setUserSelected(getUserLogged());
		uConfig.setAccountSelected(getAccountSelectedForUserLogged());
		
		if (getOperationUI().getTypeBuyRadio().getSelection()) {					
			uConfig.setOperationTypeSelected(OperationType.BUY);
		}
		else if (getOperationUI().getTypeSellRadio().getSelection()) {					
			uConfig.setOperationTypeSelected(OperationType.SELL);
		}
		else if (getOperationUI().getTypeSellCallOptionRadio().getSelection()) {					
			uConfig.setOperationTypeSelected(OperationType.SELL_CALL_OPTION);
		}
		
		uConfig.setBrokerSelected(getAccountSelectedForUserLogged().getBroker());
		
		logger.debug("Saving in DB");
		getConfigurationFacade().addConfiguration(uConfig);
		
		
		} catch (UIException e) {
			logger.error("Erro em salvar a sessão", e);
			throw e;
		}
		*/
	}
	
	
	
	
	
	
	public AccountFacade getUserConfigurationFacade() {
		return userConfigurationFacade;
	}
	public ConfigurationFacade getConfigurationFacade() {
		return configurationFacade;
	}
	public CommentConfigurationFacade getCommentConfigurationFacade() {
		return commentConfigurationFacade;
	}
	public StockFacade getStockFacade() {
		return stockFacade;
	}
}
