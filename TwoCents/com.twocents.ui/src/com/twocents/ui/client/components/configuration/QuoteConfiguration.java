package com.twocents.ui.client.components.configuration;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.twocents.model.ConfigurationGroupType;
import com.twocents.ui.client.common.windows.PanelConfigurationComposite;
import com.twocents.ui.client.components.configuration.action.QuoteConfigurationAction;
import com.twocents.ui.client.components.configuration.ui.QuoteConfigurationUI;
import com.twocents.ui.resources.UIMessages;

public class QuoteConfiguration extends PanelConfigurationComposite {

	private Button ActivateUpdateQuoteCheckbox;
	private Text intervalUpdateText;
	private Button applyButton;
	private Label httpProxyLabel;
	private Text linkText;
	private QuoteConfigurationAction action;
	private QuoteConfigurationUI quoteConfigurationUI;

	public QuoteConfigurationAction getAction() {
		return action;
	}

	public QuoteConfiguration(Composite parent) {
		super(parent, ConfigurationGroupType.NETWORK, UIMessages
				.getMessage("CONFIGURATION_QUOTE"));
		//createComponents(parent);
	}

	public void createComponents(Composite parent) {
		quoteConfigurationUI = new QuoteConfigurationUI(parent, SWT.NONE, new QuoteConfigurationAction(this));
		quoteConfigurationUI.createComponents();
	}

	public void initAction() {
//		action = new QuoteConfigurationAction(this);
//		quoteConfigurationUI.setAction(action);
	}

	public PanelConfigurationComposite rebuild(Composite composite) {
		return new QuoteConfiguration(composite);
	}

	public Label getHttpProxyLabel() {
		return httpProxyLabel;
	}

	public Text getLinkText() {
		return linkText;
	}

	public Button getApplyButton() {
		return applyButton;
	}

	public Button getActivateUpdateQuoteCheckbox() {
		return ActivateUpdateQuoteCheckbox;
	}

	public Text getIntervalUpdateText() {
		return intervalUpdateText;
	}

}
