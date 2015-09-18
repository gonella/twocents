package com.twocents.ui.client.components.configuration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.twocents.model.ConfigurationGroupType;
import com.twocents.ui.client.common.windows.PanelConfigurationComposite;
import com.twocents.ui.client.components.configuration.action.BrokerConfigurationAction;
import com.twocents.ui.client.components.configuration.ui.BrokerConfigurationUI;
import com.twocents.ui.client.components.listener.UpdateUserAccountListener;
import com.twocents.ui.resources.UIMessages;

public class BrokerConfiguration extends PanelConfigurationComposite {

	private List<UpdateUserAccountListener> listeners = new ArrayList<UpdateUserAccountListener>();

	private BrokerConfigurationUI brokerConfigurationUI;
	private BrokerConfigurationAction action;

	public BrokerConfiguration(Composite parent) {
		super(parent, ConfigurationGroupType.BROKER, UIMessages
				.getMessage("CONFIGURATION_BROKER"));
		brokerConfigurationUI.createComponents();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public PanelConfigurationComposite rebuild(Composite composite) {
		List<UpdateUserAccountListener> listeners = new ArrayList<UpdateUserAccountListener>(
				this.listeners);
		BrokerConfiguration conf = new BrokerConfiguration(composite);
		conf.action.setListeners(listeners);
		conf.brokerConfigurationUI.setAction(action);
		return conf;
	}

	@Override
	public void createComponents(Composite parent) {
		brokerConfigurationUI = new BrokerConfigurationUI(parent, SWT.NONE);
	}

	@Override
	public void initAction() {
		action = new BrokerConfigurationAction();
		brokerConfigurationUI.setAction(action);
	}

	public void addListener(UpdateUserAccountListener listener) {
		listeners.add(listener);
		action.setListeners(listeners);
	}

	public void setListeners(List<UpdateUserAccountListener> listeners) {
		this.listeners = listeners;
	}

}
