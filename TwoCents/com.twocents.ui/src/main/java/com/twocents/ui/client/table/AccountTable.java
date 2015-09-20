package com.twocents.ui.client.table;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.twocents.ui.client.components.manage.ManageAccountCompositeAction;
import com.twocents.ui.resources.UIMessages;

public class AccountTable {

	private Table tableClient;
	private Composite parent;
	private ManageAccountCompositeAction action;

	public AccountTable(Composite parent, ManageAccountCompositeAction action) {
		this.parent = parent;
		this.action = action;
	}

	@SuppressWarnings("unchecked")
	public Table createTable() {
		tableClient = new Table(parent, SWT.NONE);
		tableClient.setLinesVisible(true);
		tableClient.setHeaderVisible(true);

		TableColumn tableColumn = new TableColumn(tableClient, SWT.NONE);
		tableColumn.setWidth(25);

		TableColumn tableColumn_1 = new TableColumn(tableClient, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("Codigo Bovespa");

		TableColumn tableColumn_2 = new TableColumn(tableClient, SWT.NONE);
		tableColumn_2.setWidth(250);
		tableColumn_2.setText("Nome");

		TableColumn tableColumn_3 = new TableColumn(tableClient, SWT.NONE);
		tableColumn_3.setWidth(200);
		tableColumn_3.setText("Email");

		TableColumn tableColumn_4 = new TableColumn(tableClient, SWT.NONE);
		tableColumn_4.setWidth(80);
		tableColumn_4.setText("Telefone");


		tableClient.addListener(SWT.MouseDown, new DeleteTableItemListener(
				tableClient, UIMessages
						.getMessage("DIALOG_CONFIRMATION_REMOVE_ACCOUNT"),
				(TableAction) action));

		action.populateTable(tableClient);

		return tableClient;

	}

}
