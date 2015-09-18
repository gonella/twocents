package com.twocents.ui.client.components.configuration.action;

import org.apache.log4j.Logger;

import com.twocents.context.ContextHolder;
import com.twocents.core.adapter.QuoteAdapter;
import com.twocents.spring.ServiceLocator;
import com.twocents.ui.client.common.action.UIConfigAction;
import com.twocents.ui.client.components.configuration.QuoteConfiguration;
import com.twocents.ui.client.components.facade.ConfigurationFacade;
import com.twocents.ui.exceptions.UIException;

public class QuoteConfigurationAction extends UIConfigAction {

	private Logger logger = Logger.getLogger(QuoteConfigurationAction.class);
	private ConfigurationFacade facade = new ConfigurationFacade();


	private final QuoteConfiguration UI;

	public ConfigurationFacade getFacade() {
		return facade;
	}

	public QuoteConfiguration getUI() {
		return UI;
	}

	public QuoteConfigurationAction(QuoteConfiguration UI) {
		super();
		this.UI = UI;

		init();
	}

	public void init() {
		logger.debug("Loading quote configuration");

		// try {
		//
		// com.twocents.model.QuoteConfiguration config = getFacade()
		// .uniqueQuoteConfiguration();
		// if (config != null) {
		// getUI().getLinkText().setText(config.getUrl());
		//
		// getUI().getActivateUpdateQuoteCheckbox().setSelection(
		// config.isActivateUpdateQuote());
		//
		// getUI().getIntervalUpdateText().setText(
		// new Long(config.getUpdateInterval()).toString());
		//
		// }
		//
		// } catch (UIException e) {
		// logger.error(e);
		// }

	}

	public void applyQuoteConfiguration(QuoteAdapter adapter) {

		logger.info("Aplicando o novo provedor de cotação :"+adapter.getAdapterName());
		try {

			com.twocents.model.QuoteConfiguration config = getFacade()
					.uniqueQuoteConfiguration();

			if (config == null) {
				config = new com.twocents.model.QuoteConfiguration();
			}

			config.setAdapterBeanName(adapter.getAdapterBeanName());
			// try {
			// Integer interval = new Integer(getUI().getIntervalUpdateText()
			// .getText());
			// config.setUpdateInterval(interval);
			//
			// } catch (NumberFormatException e) {
			// new UIException(9500);
			// }

			getFacade().addConfiguration(config);
			ContextHolder.setCurrentQuoteAdapter(adapter);
			logger.info("** Provedor de cotação aplicado");

		} catch (UIException e) {
			logger.error(e);
		}

	}

	public void refresh() {

	}

	public QuoteAdapter getCurrentQuoteAdapter() {
		try {
			String beanName = facade.uniqueQuoteConfiguration()
					.getAdapterBeanName();

			QuoteAdapter currentQuoteAdapter = ContextHolder
					.getCurrentQuoteAdapter();

			if (currentQuoteAdapter == null) {
				return (QuoteAdapter) ServiceLocator.getQuoteAdapter(beanName);
			}
			return currentQuoteAdapter;

		} catch (Exception e) {
			return null;
		}
	}

}
