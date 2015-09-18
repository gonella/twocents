package com.twocents.ui.client.components.configuration;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.twocents.model.ConfigurationGroupType;
import com.twocents.ui.client.common.windows.PanelConfigurationComposite;
import com.twocents.ui.client.components.configuration.action.GeneralConfigurationAction;
import com.twocents.ui.resources.UIMessages;

public class GeneralConfiguration extends PanelConfigurationComposite {

	private Button ActivateInternetConnectivityCheckBox;
	private Button applyButton;
	private GeneralConfigurationAction action;

	public GeneralConfigurationAction getAction() {
		return action;
	}

	public GeneralConfiguration(Composite arg0) {
		super(arg0, ConfigurationGroupType.GENERAL, UIMessages
				.getMessage("CONFIGURATION_GENERAL"));

	}

	public void createComponents(Composite parent) {

		setLayout(new FormLayout());

		final Group group = new Group(this, SWT.NONE);
		final FormData fd_group = new FormData();
		fd_group.bottom = new FormAttachment(100, -5);
		fd_group.right = new FormAttachment(100, -5);
		fd_group.top = new FormAttachment(0, 10);
		fd_group.left = new FormAttachment(0, 5);
		group.setLayoutData(fd_group);
		group.setLayout(new FormLayout());
		group.setText(getTitle());

		applyButton = new Button(group, SWT.NONE);
		applyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				applyConfiguration();

			}
		});

		final FormData fd_applyButton = new FormData();
		fd_applyButton.bottom = new FormAttachment(0, 338);
		fd_applyButton.top = new FormAttachment(0, 315);
		fd_applyButton.right = new FormAttachment(0, 479);
		fd_applyButton.left = new FormAttachment(0, 365);
		applyButton.setLayoutData(fd_applyButton);
		applyButton.setText("Aplicar");

		ActivateInternetConnectivityCheckBox = new Button(group, SWT.CHECK);
		final FormData fd_activateInternetConnectivityCheckBox = new FormData();
		fd_activateInternetConnectivityCheckBox.top = new FormAttachment(0, 15);
		fd_activateInternetConnectivityCheckBox.right = new FormAttachment(0,
				188);
		fd_activateInternetConnectivityCheckBox.left = new FormAttachment(0, 15);
		ActivateInternetConnectivityCheckBox
				.setLayoutData(fd_activateInternetConnectivityCheckBox);
		ActivateInternetConnectivityCheckBox.setText("Conectado na Internet");

	}

	protected void applyConfiguration() {
		getAction().applyConfiguration();

	}

	public void initAction() {
		action = new GeneralConfigurationAction(this);
	}

	public PanelConfigurationComposite rebuild(Composite composite) {
		return new GeneralConfiguration(composite);
	}

	public Button getApplyButton() {
		return applyButton;
	}

	public Button getActivateInternetConnectivityCheckBox() {
		return ActivateInternetConnectivityCheckBox;
	}

}
