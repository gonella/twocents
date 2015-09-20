package com.twocents.ui.client.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.twocents.adapter.YahooFinanceQuoteAdapter;
import com.twocents.context.ContextHolder;
import com.twocents.core.adapter.QuoteAdapter;
import com.twocents.model.Account;
import com.twocents.model.QuoteConfiguration;
import com.twocents.model.StockBroker;
import com.twocents.model.TwUser;
import com.twocents.security.license.LicenseValidate;
import com.twocents.spring.ServiceLocator;
import com.twocents.ui.client.common.action.UIAction;
import com.twocents.ui.client.components.ReportTableComposite;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.exceptions.UIException;

public class TwoCentsUIAction extends UIAction {

	private static Logger logger = Logger.getLogger(TwoCentsUIAction.class);
	private boolean isInitialized = false;

	private ReportTableComposite reportTableComposite;

	public TwoCentsUIAction() {
		setInitialized(true);
		init();
		ContextHolderUI.setEnvironmentAction(this);
	}

	public void init() {
		try {

			logger.info("## Configurando ambiente: TwoCents");

			//MockData mockData = new MockData();
			//mockData.createStockBrokerMockData();
			//mockData.populateOperationForAllStockBrokers();

			// initializeValidationLicense();

			// initializeMainUIComponents();

			// applyConfigurationInRunTime();

		} catch (Exception e) {
			logger.error(e);
			TwoCentsUIAction.exit();
		}
	}

	private void initializeValidationLicense() {
		logger.debug("INIT: Licensing");
		LicenseValidate.validateLicense();
	}
	
	public boolean checkFirstAppUsage() {
		List<TwUser> users = getConfigurationData().getUserConfigurationFacade().getTwUserService().findAll();
		return users == null || users.isEmpty();
	}

	public void disposeTheApp() {

		try {

			logger.info("## Fechando o TwoCents");

			getConfigurationData().savingState();

			TwoCentsUIAction.exit();

		} catch (UIException e) {
			logger.error("Error em salvar as configurações do ambiente", e);
			TwoCentsUIAction.exit();
		}

	}

	public void doLogin(String user, String pwd) throws UIException {
		StockBroker returned = getEnvironmentFacade()
				.findStockBrokerByUsernameAndPassword(user, pwd);

		if (returned == null) {
			throw new UIException(4013);
		}
		setStockBrokerLogged(returned);
	}

	public void setStockBrokerLogged(StockBroker stockBroker)
			throws UIException {

		logger.debug("Selecionando o corretor:'" + stockBroker.getName()
				+ "'na sessão");
		ContextHolderUI.setStockBrokerSelected(stockBroker);

	}

	public void removeUser(Account account) throws UIException {
		String stockBrokerName = account.getStockBroker().getName();

		// getUserConfigurationFacade().removeUser(account.getUser());

		StockBroker stockBrokerLogged = getEnvironmentFacade()
				.findStockBrokerByName(stockBrokerName);
		setStockBrokerLogged(stockBrokerLogged);
	}

	public void doUpdateQuote() throws UIException {
		logger.info("Atualizando Cotações");

		QuoteAdapter adapter = ContextHolder.getCurrentQuoteAdapter();

		if (adapter == null) {
			logger.info("Procurando o primeiro provedor de cotações que encontrar");
			QuoteConfiguration configuration = getConfigurationData()
					.getConfigurationFacade().uniqueQuoteConfiguration();

			String beanName = null;
			if (configuration != null) {
				beanName = configuration.getAdapterBeanName();
			}

			if (beanName == null) {
				com.twocents.context.ContextHolder
						.setCurrentQuoteAdapter((QuoteAdapter) ServiceLocator
								.getQuoteAdapter(YahooFinanceQuoteAdapter.BEAN_NAME));

				QuoteConfiguration config = new QuoteConfiguration();

				config.setAdapterBeanName(YahooFinanceQuoteAdapter.BEAN_NAME);
				getConfigurationData().getConfigurationFacade()
						.addConfiguration(config);

			} else {
				com.twocents.context.ContextHolder
						.setCurrentQuoteAdapter((QuoteAdapter) ServiceLocator
								.getQuoteAdapter(beanName));
			}

		}
		else{
			logger.info("Utlizando o provedor de cotações: "+adapter.getAdapterName());
		}

		// getStockFacade().createUpdateStockQuoteTask(15 * 60000);

		getStockFacade().executeSingleUpdate();

		getTwOperationUI().getDisplay().syncExec(new Runnable() {
			public void run() {
				
				getTwOperationUI().getAction().refresh();
				getAccountPanel().getAction().refresh();

			}
		});
		getTwOperationUI().getDisplay().wake();

	}

	public static void exit(String message) {

		logger.debug("* Saindo do TwoCents *");

		if (message != null) {
			logger.info(message);
		}

		System.exit(0);
	}

	public static void exit() {
		exit(null);
	}

	public boolean isInitialized() {
		return isInitialized;
	}

	public void setInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}

	

	@Override
	public void refresh() {

	}

	public void setReportTableComposite(
			ReportTableComposite reportTableComposite) {
		this.reportTableComposite = reportTableComposite;

	}

	public ReportTableComposite getReportTableComposite() {
		return reportTableComposite;
	}

}
