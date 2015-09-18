package com.twocents.ui.client.common.action;

import com.twocents.core.common.CommonAction;
import com.twocents.ui.client.ConfigurationData;
import com.twocents.ui.client.TwOperationUI;
import com.twocents.ui.client.TwReportUI;
import com.twocents.ui.client.components.StockBrokerAccountComposite;
import com.twocents.ui.client.components.facade.CommentConfigurationFacade;
import com.twocents.ui.client.components.facade.AccountFacade;
import com.twocents.ui.client.facade.EnvironmentFacade;
import com.twocents.ui.client.facade.OperationFacade;
import com.twocents.ui.client.facade.ReportFacade;
import com.twocents.ui.client.facade.StockFacade;
import com.twocents.ui.mockdata.MockData;

public abstract class UIAction extends CommonAction {

	private final MockData defaultData = new MockData();
	private final ConfigurationData configurationData = new ConfigurationData();
	private final EnvironmentFacade environmentFacade = new EnvironmentFacade();
	private final AccountFacade userConfigurationFacade = new AccountFacade();
	private final StockFacade stockFacade = new StockFacade();
	private final ReportFacade reportFacade = new ReportFacade();

	private final CommentConfigurationFacade commentConfigurationFacade = new CommentConfigurationFacade();
	private final OperationFacade operationFacade = new OperationFacade();

	private TwOperationUI twOperationUI;
	private TwReportUI twReportUI;
	private StockBrokerAccountComposite accountPanel;

	public EnvironmentFacade getEnvironmentFacade() {
		return environmentFacade;
	}

	public AccountFacade getUserConfigurationFacade() {
		return userConfigurationFacade;
	}

	public StockFacade getStockFacade() {
		return stockFacade;
	}

	public MockData getDefaultData() {
		return defaultData;
	}

	public ConfigurationData getConfigurationData() {
		return configurationData;
	}

	public CommentConfigurationFacade getCommentConfigurationFacade() {
		return commentConfigurationFacade;
	}

	public OperationFacade getOperationFacade() {
		return operationFacade;
	}

	public void setTwOperationUI(TwOperationUI twOperationUI) {
		this.twOperationUI = twOperationUI;
	}

	public TwOperationUI getTwOperationUI() {
		return twOperationUI;
	}

	public void setTwReportUI(TwReportUI twReportUI) {
		this.twReportUI = twReportUI;
	}

	public TwReportUI getTwReportUI() {
		return twReportUI;
	}

	public ReportFacade getReportFacade() {
		return reportFacade;
	}

	public StockBrokerAccountComposite getAccountPanel() {
		return accountPanel; 
	}
	
	public void setAccountPanel(StockBrokerAccountComposite accountPanel) {
		this.accountPanel = accountPanel;
	}
}
