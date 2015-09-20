package com.twocents.ui.client.components.wizard;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.TableItem;

import com.swtdesigner.SWTResourceManager;
import com.twocents.core.populate.PopulateData;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Broker;
import com.twocents.model.StockBroker;
import com.twocents.ui.client.common.action.UIAction;
import com.twocents.ui.resources.UIImages;

public class WizardAction extends UIAction {

	private Logger logger = Logger.getLogger(WizardAction.class);

	public void populateRowString(StockBroker stockBroker, TableItem item) {

		// X|NOME|EMAIL|TEL|USERNAME
		item.setText(new String[] { "", stockBroker.getName(),
				stockBroker.getEmail(), stockBroker.getTelefone(),
				stockBroker.getUsername() });

		item.setImage(SWTResourceManager.getImage(this.getClass(),
				UIImages.DELETE_ICON_16));

	}

	public StockBroker addStockBroker(StockBroker stb) throws CoreException {
		return addStockBroker(stb.getName(), stb.getEmail(), stb.getTelefone(),
				stb.getUsername(), stb.getPassword());
	}

	public StockBroker addStockBroker(String name, String email, String tel,
			String username, String password) throws CoreException {
		logger.info("Criando Corretor: " + name);
		try {

			StockBroker stockBroker = new StockBroker();

			stockBroker.setName(name);
			stockBroker.setEmail(email);
			stockBroker.setTelefone(tel);
			stockBroker.setUsername(username);
			stockBroker.setPassword(password);
			// stockBroker.setBroker(broker);

			StockBroker created = new PopulateData().createStockBroker(
					stockBroker, null);

			refresh();

			return created;
		} catch (CoreException e) {
			throw e;
		}
	}

	public void addBroker(Broker broker) {
		logger.info("Adding broker");

		try {

			super.getEnvironmentFacade().getBrokerService().addBroker(broker);
			logger.info("* Broker Created");

		} catch (Exception e) {
			logger.error(e);
		}

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
