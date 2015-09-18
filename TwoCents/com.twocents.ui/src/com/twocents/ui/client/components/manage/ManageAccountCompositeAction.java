package com.twocents.ui.client.components.manage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.springframework.util.CollectionUtils;

import com.swtdesigner.SWTResourceManager;
import com.twocents.core.populate.PopulateData;
import com.twocents.core.util.StringCheck;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Broker;
import com.twocents.model.StockBroker;
import com.twocents.model.TwUser;
import com.twocents.ui.client.common.action.UIDialogAction;
import com.twocents.ui.client.components.facade.AccountFacade;
import com.twocents.ui.client.components.facade.BrokerConfigurationFacade;
import com.twocents.ui.client.components.listener.UpdateUserAccountListener;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.client.table.TableAction;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.resources.UIImages;

public class ManageAccountCompositeAction extends UIDialogAction implements
		TableAction<Account> {

	private final ManageAccountComposite manageClientComposite;
	private AccountFacade accountFacade;
	private BrokerConfigurationFacade brokerService;
	private List<UpdateUserAccountListener> updateAccountListeners = new ArrayList<UpdateUserAccountListener>();

	public ManageAccountCompositeAction(
			ManageAccountComposite manageClientDialog,
			List<UpdateUserAccountListener> updateAccountListeners) {
		this.manageClientComposite = manageClientDialog;
		brokerService = new BrokerConfigurationFacade();
		accountFacade = new AccountFacade();
		this.updateAccountListeners = updateAccountListeners;
	}

	public ManageAccountComposite getUI() {
		return manageClientComposite;
	}

	public void populateTable(Table sourceTable) {

		List<Account> list;
		try {
			list = listStockBrokerAccountClients();
		} catch (UIException e) {
			logger.error("Não foi possível popular clientes na tabela", e);
			return;
		}

		TableItem item;
		for (Account account : list) {
			item = new TableItem(sourceTable, SWT.NONE);

			// X|ID|DATA|ATIVO|OPERACAO|PREÇO|QTDE|TOTAL|DESC
			item.setText(new String[] { "", account.getAccountNumber(),
					account.getUser().getName(), account.getUser().getEmail(),
					account.getUser().getTelephone() });

			item.setImage(SWTResourceManager.getImage(this.getClass(),
					UIImages.DELETE_ICON_16));
		}

	}

	public List<Account> listStockBrokerAccountClients() throws UIException {

		StockBroker stockBrokerLogged;
		try {
			stockBrokerLogged = ContextHolderUI.getStockBrokerLogged();
		} catch (CoreException e) {
			throw new UIException(e.getMessage(), e);
		}

		List<Account> accountList = accountFacade
				.listAccountsFromStockBroker(stockBrokerLogged);

		return accountList;
	}

	public void refresh() {
		Table sourceTable = manageClientComposite.cleanUsersTable();
		populateTable(sourceTable);
	}

	public List<Account> getAccountClients() throws UIException {

		StockBroker stockBrokerLogged;
		try {
			stockBrokerLogged = ContextHolderUI.getStockBrokerLogged();
		} catch (CoreException e) {
			throw new UIException(e.getMessage(), e);
		}

		List<Account> accountList = accountFacade
				.listAccountsFromStockBroker(stockBrokerLogged);

		return accountList;
	}

	public List<Broker> listAllBrokers() {
		try {
			return brokerService.listBrokerByStockBroker(ContextHolderUI
					.getStockBrokerLogged());
		} catch (CoreException e) {
			logger.error("Could not list brokers for the logged stock borker.",
					e);
		}
		return new ArrayList<Broker>();
	}

	public Account deleteItemFromTable(int index, TableItem item, int col) {

		try {
			if (col != 0)
				return null;
			String accountNumber = item.getText(1);

			if (StringCheck.isNumeric(accountNumber)) {
				Account ac = accountFacade.removeAccount(accountNumber);
				logger.info("Conta removida: " + accountNumber);


				for (UpdateUserAccountListener listener : updateAccountListeners) {
					listener.onAccountRemove(ac);
				}
				refresh();

				return ac;

			} else {
				logger.info("Id não é um numero.");
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}

	public Account addUserAccount(String accountNumber, String nome,
			String email, String tel, String brokerName) throws UIException {
		logger.info("Adicionando Conta: " + nome);
		try {

			TwUser user = new PopulateData().populateUser(nome, "",
					accountNumber, email, tel);

			StockBroker stockBroker = ContextHolderUI.getStockBrokerLogged();
			Account ac = accountFacade.addAccount(user, stockBroker,
					brokerName, accountNumber);

			refresh();

			return ac;
		} catch (UIException e) {
			logger.error(e);
			throw e;
		} catch (CoreException e) {
			logger.error(e);
			throw new UIException("Internal Error", e);
		}
	}

	@Override
	public void pressOkButton() {
		getUI().getShell().close();
	}

	@Override
	public void pressCancelButton() {
		getUI().getShell().close();
	}

	@Override
	public void init() {

		logger.debug("Gerenciador de Contas : Carregando todas as corretoras");
		getUI().getCorretora().removeAll();
		List<Broker> allBrokers = listAllBrokers();

		if (allBrokers.size() > 0) {
			for (Broker broker : allBrokers) {
				getUI().getCorretora().add(broker.getName());
			}
		} else {
			getUI().disableAddButton();
		}
		if (!CollectionUtils.isEmpty(allBrokers)) {
			getUI().getCorretora().select(0);
		}

		// populateTable(getUI().getTableClient());

		getUI().getCodigo().setFocus();

	}

	public void addListener(UpdateUserAccountListener updateAccountListener) {
		this.updateAccountListeners.add(updateAccountListener);
	}

	@Override
	public Account editTableItem(int index, TableItem item, int col) {
		return null;
	}

}
