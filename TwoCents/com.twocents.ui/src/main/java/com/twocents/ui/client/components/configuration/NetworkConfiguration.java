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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.twocents.model.ConfigurationGroupType;
import com.twocents.ui.client.common.windows.PanelConfigurationComposite;
import com.twocents.ui.client.components.configuration.action.NetworkConfigurationAction;
import com.twocents.ui.resources.UIMessages;

public class NetworkConfiguration extends PanelConfigurationComposite {

	private Label portLabel;
	private Button directlyConnectedToInternetRadio;
	private Button manualConfigurationProxyRadio;
	private Button applyButton;
	private Label passwordLabel;
	private Label usernameLabel;
	private Button authenticationProxyCheckbox;
	private Label httpProxyLabel;
	private Text passwordText;
	private Text usernameText;
	private Text portText;
	private Text httpProxyText;
	private NetworkConfigurationAction action;

	public NetworkConfigurationAction getAction() {
		return action;
	}

	public NetworkConfiguration(Composite arg0) {
		super(arg0, ConfigurationGroupType.NETWORK, UIMessages
				.getMessage("CONFIGURATION_NETWORK"));
	}

	protected void displayProxyFields() {
		getAction().displayProxyField();

	}

	protected void applyProxy() {
		getAction().applyProxy();

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

		directlyConnectedToInternetRadio = new Button(group, SWT.RADIO);
		directlyConnectedToInternetRadio.setSelection(true);
		directlyConnectedToInternetRadio
				.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(final SelectionEvent arg0) {
						displayProxyFields();
					}
				});
		final FormData fd_directlyConnectedToInternetRadio = new FormData();
		fd_directlyConnectedToInternetRadio.top = new FormAttachment(0, 20);
		fd_directlyConnectedToInternetRadio.left = new FormAttachment(0, 10);
		directlyConnectedToInternetRadio
				.setLayoutData(fd_directlyConnectedToInternetRadio);
		directlyConnectedToInternetRadio
				.setText("Conectado diretamente na internet");

		manualConfigurationProxyRadio = new Button(group, SWT.RADIO);
		manualConfigurationProxyRadio
				.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(final SelectionEvent arg0) {
						displayProxyFields();
					}
				});
		final FormData fd_manualConfigurationProxyRadio = new FormData();
		fd_manualConfigurationProxyRadio.top = new FormAttachment(0, 44);
		fd_manualConfigurationProxyRadio.bottom = new FormAttachment(0, 60);
		fd_manualConfigurationProxyRadio.right = new FormAttachment(
				directlyConnectedToInternetRadio, 190, SWT.LEFT);
		fd_manualConfigurationProxyRadio.left = new FormAttachment(
				directlyConnectedToInternetRadio, 0, SWT.LEFT);
		manualConfigurationProxyRadio
				.setLayoutData(fd_manualConfigurationProxyRadio);
		manualConfigurationProxyRadio.setText("Configuração manual do proxy");

		final Group group_1 = new Group(group, SWT.NONE);
		final FormData fd_group_1 = new FormData();
		fd_group_1.bottom = new FormAttachment(0, 205);
		fd_group_1.right = new FormAttachment(100, -5);
		fd_group_1.left = new FormAttachment(0, 10);
		fd_group_1.top = new FormAttachment(manualConfigurationProxyRadio, 5,
				SWT.BOTTOM);
		group_1.setLayoutData(fd_group_1);
		group_1.setLayout(new FormLayout());

		httpProxyLabel = new Label(group_1, SWT.NONE);
		httpProxyLabel.setEnabled(false);
		final FormData fd_httpProxyLabel = new FormData();
		fd_httpProxyLabel.top = new FormAttachment(0, 10);
		fd_httpProxyLabel.left = new FormAttachment(0, 5);
		httpProxyLabel.setLayoutData(fd_httpProxyLabel);
		httpProxyLabel.setText("HTTP Proxy:");

		httpProxyText = new Text(group_1, SWT.BORDER);
		httpProxyText.setEnabled(false);
		final FormData fd_httpProxyText = new FormData();
		fd_httpProxyText.right = new FormAttachment(0, 350);
		fd_httpProxyText.bottom = new FormAttachment(httpProxyLabel, 19,
				SWT.TOP);
		fd_httpProxyText.top = new FormAttachment(httpProxyLabel, 0, SWT.TOP);
		fd_httpProxyText.left = new FormAttachment(httpProxyLabel, 5, SWT.RIGHT);
		httpProxyText.setLayoutData(fd_httpProxyText);

		portLabel = new Label(group_1, SWT.NONE);
		portLabel.setEnabled(false);
		final FormData fd_portLabel = new FormData();
		fd_portLabel.top = new FormAttachment(httpProxyText, 0, SWT.TOP);
		fd_portLabel.left = new FormAttachment(httpProxyText, 5, SWT.RIGHT);
		portLabel.setLayoutData(fd_portLabel);
		portLabel.setText("Porta");

		portText = new Text(group_1, SWT.BORDER);
		portText.setEnabled(false);
		final FormData fd_portText = new FormData();
		fd_portText.top = new FormAttachment(portLabel, 0, SWT.TOP);
		fd_portText.left = new FormAttachment(portLabel, 5, SWT.RIGHT);
		portText.setLayoutData(fd_portText);

		authenticationProxyCheckbox = new Button(group_1, SWT.CHECK);
		authenticationProxyCheckbox.setEnabled(false);
		final FormData fd_authenticationProxyCheckbox = new FormData();
		fd_authenticationProxyCheckbox.top = new FormAttachment(0, 45);
		fd_authenticationProxyCheckbox.left = new FormAttachment(
				httpProxyLabel, 0, SWT.LEFT);
		authenticationProxyCheckbox
				.setLayoutData(fd_authenticationProxyCheckbox);
		authenticationProxyCheckbox.setText("Ativar autenticação do proxy:");

		usernameLabel = new Label(group_1, SWT.NONE);
		usernameLabel.setEnabled(false);
		final FormData fd_usernameLabel = new FormData();
		fd_usernameLabel.top = new FormAttachment(0, 72);
		fd_usernameLabel.bottom = new FormAttachment(0, 85);
		fd_usernameLabel.left = new FormAttachment(authenticationProxyCheckbox,
				0, SWT.LEFT);
		usernameLabel.setLayoutData(fd_usernameLabel);
		usernameLabel.setText("Username:");

		usernameText = new Text(group_1, SWT.BORDER);
		usernameText.setEnabled(false);
		final FormData fd_usernameText = new FormData();
		fd_usernameText.right = new FormAttachment(authenticationProxyCheckbox,
				0, SWT.RIGHT);
		fd_usernameText.bottom = new FormAttachment(usernameLabel, 19, SWT.TOP);
		fd_usernameText.top = new FormAttachment(usernameLabel, 0, SWT.TOP);
		fd_usernameText.left = new FormAttachment(httpProxyText, 0, SWT.LEFT);
		usernameText.setLayoutData(fd_usernameText);

		passwordLabel = new Label(group_1, SWT.NONE);
		passwordLabel.setEnabled(false);
		final FormData fd_passwordLabel = new FormData();
		fd_passwordLabel.bottom = new FormAttachment(0, 113);
		fd_passwordLabel.top = new FormAttachment(0, 100);
		fd_passwordLabel.right = new FormAttachment(usernameLabel, 48, SWT.LEFT);
		fd_passwordLabel.left = new FormAttachment(usernameLabel, 0, SWT.LEFT);
		passwordLabel.setLayoutData(fd_passwordLabel);
		passwordLabel.setText("Password:");

		passwordText = new Text(group_1, SWT.BORDER);
		passwordText.setEnabled(false);
		final FormData fd_passwordText = new FormData();
		fd_passwordText.right = new FormAttachment(usernameText, 0, SWT.RIGHT);
		fd_passwordText.bottom = new FormAttachment(passwordLabel, 19, SWT.TOP);
		fd_passwordText.top = new FormAttachment(passwordLabel, 0, SWT.TOP);
		fd_passwordText.left = new FormAttachment(passwordLabel, 65, SWT.LEFT);
		passwordText.setLayoutData(fd_passwordText);

		applyButton = new Button(group, SWT.NONE);
		applyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				applyProxy();
			}
		});
		final FormData fd_applyButton = new FormData();
		fd_applyButton.left = new FormAttachment(0, 370);
		fd_applyButton.top = new FormAttachment(0, 212);
		fd_applyButton.bottom = new FormAttachment(0, 235);
		fd_applyButton.right = new FormAttachment(group_1, 0, SWT.RIGHT);
		applyButton.setLayoutData(fd_applyButton);
		applyButton.setText("Aplicar");
	}

	public void initAction() {
		action = new NetworkConfigurationAction(this);
	}

	public PanelConfigurationComposite rebuild(Composite composite) {
		return new NetworkConfiguration(composite);
	}

	public Label getHttpProxyLabel() {
		return httpProxyLabel;
	}

	public Text getHttpProxyText() {
		return httpProxyText;
	}

	public Text getPortText() {
		return portText;
	}

	public Button getAuthenticationProxyCheckbox() {
		return authenticationProxyCheckbox;
	}

	public Text getUsernameText() {
		return usernameText;
	}

	public Label getUsernameLabel() {
		return usernameLabel;
	}

	public Label getPasswordLabel() {
		return passwordLabel;
	}

	public Text getPasswordText() {
		return passwordText;
	}

	public Button getApplyButton() {
		return applyButton;
	}

	public Button getManualConfigurationProxyRadio() {
		return manualConfigurationProxyRadio;
	}

	public Button getDirectlyConnectedToInternetRadio() {
		return directlyConnectedToInternetRadio;
	}

	public Label getPortLabel() {
		return portLabel;
	}

}
