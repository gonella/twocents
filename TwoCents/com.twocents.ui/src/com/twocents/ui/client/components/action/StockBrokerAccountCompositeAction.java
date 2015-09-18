package com.twocents.ui.client.components.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;
import org.springframework.util.CollectionUtils;

import com.swtdesigner.SWTResourceManager;
import com.twocents.context.ContextHolder;
import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.StockBroker;
import com.twocents.ui.client.TwBase;
import com.twocents.ui.client.TwOperationUI;
import com.twocents.ui.client.common.action.UIAction;
import com.twocents.ui.client.components.StockBrokerAccountComposite;
import com.twocents.ui.client.components.facade.AccountFacade;
import com.twocents.ui.client.components.facade.CustodyFacade;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.resources.UIImages;

public class StockBrokerAccountCompositeAction extends UIAction {

	private Logger logger = Logger
			.getLogger(StockBrokerAccountCompositeAction.class);

	private final StockBrokerAccountComposite ui;

	private AccountFacade accountFacade;
	private CustodyFacade custodyFacade;

	public StockBrokerAccountCompositeAction(
			StockBrokerAccountComposite stockBrokerAccountComposite) {
		super();
		this.ui = stockBrokerAccountComposite;
		this.accountFacade = new AccountFacade();
		custodyFacade = new CustodyFacade();

	}

	public StockBrokerAccountCompositeAction(
			StockBrokerAccountComposite stockBrokerAccountComposite,
			TwBase compositeBase) {
		super();
		this.ui = stockBrokerAccountComposite;

	}

	public void init() {

		try {

			StockBroker stockBrokerLogged = null;
			try {
				stockBrokerLogged = ContextHolderUI.getStockBrokerLogged();
			} catch (CoreException e1) {
				logger.error("Não foi selecionado o corretor", e1);
				throw new UIException(e1.getMessage(), e1);
			}

			List<Account> accountList = accountFacade
					.listAccountsFromStockBroker(stockBrokerLogged);

			if (accountList.size() > 0) {
				try {
					Account ac = ContextHolder.getAccountSelected();
					if (ac != null) {
						populateTableAccount(accountList, ac);
					} else {
						populateTableAccount(accountList, null);
						displayAccountSelected(accountList.get(0));
						getUI().getListClient().select(0);
					}
				} catch (CoreException e) {
					logger.error("Opsss, talvez não tenha nenhuma conta selecionada ainda, usando a primeira como padrão.");
					populateTableAccount(accountList, null);
					displayAccountSelected(accountList.get(0));
					getUI().getListClient().select(0);
				}

			}

			/*
			 * if (!CollectionUtils.isEmpty(accountList)) { // SETANDO A
			 * PRIMEIRA ACCOUNT PARA A PRIMEIRA VEZ INICIADO // No if acima
			 * estou garantindo que não vai entrar vazio
			 * 
			 * try { Account ac = ContextHolder.getAccountSelected(); if (ac !=
			 * null) { displayAccountSelected(ac);
			 * getUI().getListClient().select(3); } else {
			 * displayAccountSelected(accountList.get(0)); } } catch
			 * (CoreException e) { displayAccountSelected(accountList.get(0)); }
			 * }
			 */

		} catch (UIException e) {
			logger.error("Falhou em popular o painel de contas", e);
		}

	}

	private void populateTableAccount(List<Account> accountList, Account ac)
			throws UIException {

		getUI().getListClient().removeAll();

		int index = 0;

		TableItem item;
		for (Account account : accountList) {
			item = new TableItem(getUI().getListClient(), SWT.NONE);

			item.setText(new String[] {
					account.getUser().getName(),
					FormatUtil.toPercentage(custodyFacade
							.getCustodyAverage(account) * 100) });

			item.setData(account);
			item.setImage(SWTResourceManager.getImage(this.getClass(),
					UIImages.BULLET_ACCOUNT2_ICON_32));

			// item.setChecked(true);

			if (ac != null) {
				if (account.getId().equals(ac.getId())) {
					getUI().getListClient().select(index);
				}
				index++;
			}

		}

	}

	public void refresh() {
		logger.info("Atualizando aplicacao");
		init();
	}

	private StockBrokerAccountComposite getUI() {
		return ui;
	}

	public void displayAccountSelected(Object data) {

		try {

			boolean isOperationUI = getUI().getOperationUI().getVisible();
			// boolean isReportUI = getUI().getReportUI().getVisible();

			try {
				ContextHolderUI.setAccountSelected((Account) data);
				logger.info("Cliente selecionado :"
						+ ContextHolderUI.getAccountSelected().getUser()
								.getName());

			} catch (CoreException e) {
				throw new UIException(e.getMessage(), e);
			}

			if (isOperationUI) {

				getUI().getOperationUI().getAction().displayAccountSelected();
				getUI().getOperationUI().setPanelEnable();

			} else {
				getUI().getReportUI().getAction().displayAccountSelected();
				// getUI().getReportUI().setPanelEnable();
				// twReportUI.getAction().populateReport();

				// ContextHolderUI.getEnvironmentAction().getReportTableComposite().populateReport();
			}

		} catch (UIException e) {
			logger.error(4003, e);
		}
	}

	public void cleanUpOnDeleteAccount() {
		try {
			TwOperationUI twOperationUI = ContextHolderUI
					.getEnvironmentAction().getTwOperationUI();
			twOperationUI.getCustodyTable().removeAll();
			twOperationUI.getOperationTable().removeAll();

		} catch (UIException e) {
			logger.error(4003, e);
		}

	}

	public void doCollectData() {
		// TODO Auto-generated method stub

	}

}
