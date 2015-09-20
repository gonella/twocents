package com.twocents.ui.client.components.configuration.action;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.TreeItem;

import com.twocents.model.ConfigurationGroupType;
import com.twocents.ui.client.common.action.UIConfigAction;
import com.twocents.ui.client.common.windows.ConfigurationUIDialog;
import com.twocents.ui.client.common.windows.PanelCompositeBase;
import com.twocents.ui.client.common.windows.PanelConfigurationComposite;
import com.twocents.ui.client.components.configuration.BrokerConfiguration;
import com.twocents.ui.client.components.configuration.GeneralConfiguration;
import com.twocents.ui.client.components.configuration.NetworkConfiguration;
import com.twocents.ui.client.components.configuration.QuoteConfiguration;
import com.twocents.ui.client.components.listener.UpdateUserAccountListener;
import com.twocents.ui.exceptions.UIException;

public class ConfigurationAction extends UIConfigAction {

	private Logger logger = Logger.getLogger(ConfigurationAction.class);

	private ConfigurationUIDialog configurationUI;
	private PanelConfigurationComposite[] panelConfigurationComposite;

	public ConfigurationUIDialog getConfigurationUI() {
		return configurationUI;
	}

	public ConfigurationAction(ConfigurationUIDialog configurationUI) {
		super();
		this.configurationUI = configurationUI;

		init();
	}

	public void init() {

		UpdateUserAccountListener listener=getConfigurationUI().getTwoCentsUI().getAccountPanel();
		
		BrokerConfiguration brokerConfiguration = new BrokerConfiguration(getConfigurationUI().getConfigurationPanelComposite());
		brokerConfiguration.addListener(listener);

		panelConfigurationComposite = new PanelConfigurationComposite[] {
				brokerConfiguration,
				new NetworkConfiguration(getConfigurationUI()
						.getConfigurationPanelComposite()),
				new QuoteConfiguration(getConfigurationUI()
						.getConfigurationPanelComposite()),
				new GeneralConfiguration(getConfigurationUI()
						.getConfigurationPanelComposite()) };

		// getConfigurationUI().getConfigurationPanelComposite().

		logger.info("Carregando painel de configurações :"
				+ panelConfigurationComposite.length);
		ConfigurationGroupType[] configGroup = ConfigurationGroupType.values();
		TreeItem tItemGroup[] = new TreeItem[configGroup.length];
		for (int i = 0; i < configGroup.length; i++) {
			tItemGroup[i] = new TreeItem(getConfigurationUI()
					.getConfigurationTree(), 0);
			tItemGroup[i].setText(configGroup[i].toString());
		}

		TreeItem tItem;
		for (int i = 0; i < panelConfigurationComposite.length; i++) {

			for (int k = 0; k < configGroup.length; k++) {

				if (panelConfigurationComposite[i].getType().equals(
						configGroup[k])) {
					tItem = new TreeItem(tItemGroup[k], 0);
					tItem.setText(panelConfigurationComposite[i].getTitle());
					tItem.setData(panelConfigurationComposite[i]);

					panelConfigurationComposite[i].setVisible(false);
				}
			}
		}
		getConfigurationUI().getShell().layout(true);


	}

	public void refresh() {

	}

	public void displaySelected(TreeItem tItem) {
		try {
			PanelConfigurationComposite cSelected = getTreeItemSelected(tItem);

			if (cSelected != null) {
				logger.info("Configuração selecionada :" + cSelected.getTitle()
						+ " - " + cSelected.getType());

				if ((getConfigurationUI().getConfigurationPanelComposite() != null)
						&& (!getConfigurationUI()
								.getConfigurationPanelComposite().isDisposed())) {
					getConfigurationUI().getConfigurationPanelComposite()
							.dispose();
				}

				cSelected = cSelected.rebuild(getConfigurationUI()
						.rebuildCompositePanel());

				getConfigurationUI().getHeader().setText(
						cSelected.getType().toString());

				cSelected.setVisible(true);
				getConfigurationUI().getShell().layout(true);

			}
		} catch (UIException e) {
			logger.error(e);
		}
	}

	private PanelConfigurationComposite getTreeItemSelected(TreeItem tItem)
			throws UIException {

		PanelConfigurationComposite cSelected = null;

		if (tItem != null && tItem.getData() != null) {

			if (tItem.getData() instanceof PanelCompositeBase) {
				cSelected = (PanelConfigurationComposite) tItem.getData();
			}
		}
		return cSelected;

	}

	public void dispatchOkForConfiguration() {

		logger.debug("Applying the configuration on system");

		getConfigurationUI().getShell().close();
	}

	public void dispatchCancelForConfiguration() {
		logger.debug("Canceling of apply configuration on system");

		getConfigurationUI().getShell().close();
	}

}
