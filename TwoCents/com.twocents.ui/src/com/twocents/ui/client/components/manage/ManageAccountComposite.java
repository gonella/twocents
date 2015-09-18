package com.twocents.ui.client.components.manage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import swing2swt.layout.BorderLayout;

import com.twocents.model.Account;
import com.twocents.ui.client.common.windows.PanelCompositeBase;
import com.twocents.ui.client.components.listener.UpdateUserAccountListener;
import com.twocents.ui.client.table.AccountTable;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.resources.UIMessages;
import com.twocents.ui.util.DialogUtil;
import org.eclipse.swt.layout.grouplayout.GroupLayout;

public class ManageAccountComposite extends PanelCompositeBase {

	private Composite composite1;
	private ManageAccountCompositeAction action;
	private Composite composite;
	private Composite tableClientComposite;
	private Group tableClientGroup;
	private Table table;
	private Composite composite_2;
	private Composite composite_3;
	private Label lblCodigoBovespa;
	private Label lblNome;
	private Label lblEmail;
	private Label lblTelefone;
	private Text codigoBovespa;
	private Text nome;
	private Text email;
	private Text telefone;
	private Label lblCorretora;
	private Combo corretora;
	private Button btnAdicionar;

	private List<UpdateUserAccountListener> listeners = new ArrayList<UpdateUserAccountListener>();
	private Label lblAtenoConfigurarCoretora;

	public ManageAccountComposite(Composite parent,
			List<UpdateUserAccountListener> updateAccountListeners) {

		super(parent);
		this.listeners = updateAccountListeners;
		action = new ManageAccountCompositeAction(this, updateAccountListeners);
		setLayout(new BorderLayout(0, 0));

		createComponents();

	}

	private void createComponents() {
		composite1 = new Composite(this, SWT.NONE);

		composite1.setLayout(new BorderLayout(0, 0));

		composite = new Composite(composite1, SWT.NONE);
		composite.setLayoutData(BorderLayout.NORTH);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new GridLayout(2, false));

		lblCodigoBovespa = new Label(composite_2, SWT.NONE);
		lblCodigoBovespa.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblCodigoBovespa.setText("Codigo Bovespa");

		codigoBovespa = new Text(composite_2, SWT.BORDER);
		codigoBovespa.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		lblNome = new Label(composite_2, SWT.NONE);
		lblNome.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblNome.setText("Nome");

		nome = new Text(composite_2, SWT.BORDER);
		nome
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
						1, 1));

		lblEmail = new Label(composite_2, SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblEmail.setText("Email");

		email = new Text(composite_2, SWT.BORDER);
		email.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));

		lblTelefone = new Label(composite_2, SWT.NONE);
		lblTelefone.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblTelefone.setText("Telefone");

		telefone = new Text(composite_2, SWT.BORDER);
		telefone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		lblCorretora = new Label(composite_2, SWT.NONE);
		lblCorretora.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblCorretora.setAlignment(SWT.RIGHT);
		lblCorretora.setText("Corretora");

		setCorretora(new Combo(composite_2, SWT.BORDER | SWT.READ_ONLY));
		getCorretora().setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				new Label(composite_2, SWT.NONE);
				
						btnAdicionar = new Button(composite_2, SWT.NONE);
						btnAdicionar.setText("Adicionar");
						btnAdicionar.setLayoutData(
								new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
							
								btnAdicionar.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(final SelectionEvent arg0) {
						
										Account ac = null;
										try {
											ac = getAction().addUserAccount(
													getCodigo().getText(),
													getNome().getText(),
													getEmail().getText(),
													getTel().getText(),
													getCorretora().getItem(
															getCorretora().getSelectionIndex()));
										} catch (UIException e) {
											DialogUtil.errorMessage(getShell(), "Error", e.getMessage());
											return;
										}
						
										for (UpdateUserAccountListener listener : listeners) {
											listener.onAccountUpdate(ac);
										}
						
									}
								});

		composite_3 = new Composite(composite, SWT.NONE);
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));

		tableClientComposite = new Composite(composite1, SWT.NONE);
		tableClientComposite.setLayoutData(BorderLayout.CENTER);
		tableClientComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		tableClientGroup = new Group(tableClientComposite, SWT.NONE);
		tableClientGroup.setText("Lista de Clientes");
		tableClientGroup.setLayout(new FillLayout(SWT.HORIZONTAL));

		createTableClient();

		// table = new Table(tableClientGroup, SWT.NONE);
		// table.setLinesVisible(true);
		// table.setHeaderVisible(true);
		//
		// tableColumn = new TableColumn(table, SWT.NONE);
		// tableColumn.setWidth(25);
		//
		// tableColumn_1 = new TableColumn(table, SWT.NONE);
		// tableColumn_1.setWidth(100);
		// tableColumn_1.setText("Codigo Bovespa");
		//
		// tableColumn_2 = new TableColumn(table, SWT.NONE);
		// tableColumn_2.setWidth(250);
		// tableColumn_2.setText("Nome");
		//
		// tableColumn_3 = new TableColumn(table, SWT.NONE);
		// tableColumn_3.setWidth(200);
		// tableColumn_3.setText("Email");
		//
		// tableColumn_4 = new TableColumn(table, SWT.NONE);
		// tableColumn_4.setWidth(80);
		// tableColumn_4.setText("Telefone");
		//
		// table.addListener(SWT.MouseDown, new Listener() {
		// public void handleEvent(Event event) {
		// Point pt = new Point(event.x, event.y);
		// TableItem item = table.getItem(pt);
		//
		// if (item == null)
		// return;
		// for (int i = 0; i < table.getColumnCount(); i++) {
		// Rectangle rect = item.getBounds(i);
		// if (rect.contains(pt)) {
		// int index = table.indexOf(item);
		//
		// deleteItemSelectedOnTable(index, item, i);
		//
		// break;
		// }
		// }
		// }
		// });

		getAction().init();
	}

	public Text getTel() {
		return telefone;
	}

	public Text getCodigo() {
		return codigoBovespa;
	}

	public Text getEmail() {
		return email;
	}

	public Text getNome() {
		return nome;
	}

	public Table getTableClient() {
		return table;
	}

	private void createTableClient() {
		AccountTable acTable = new AccountTable(tableClientGroup, action);
		table = acTable.createTable();
	}

	public Table cleanUsersTable() {
		if (table != null) {
			table.removeAll();
			return table;
		}

		return new AccountTable(composite, action).createTable();
	}

	public void disableAddButton() {
		if (getCorretora().getItems() != null
				&& getCorretora().getItems().length < 1) {
			btnAdicionar.setEnabled(false);
			lblAtenoConfigurarCoretora = new Label(composite_3, SWT.NONE);
			lblAtenoConfigurarCoretora
					.setText(UIMessages.getMessage("BROKER_DOES_NOT_EXIST"));
		}

	}

	protected void deleteItemSelectedOnTable(int index, TableItem item, int col) {

		boolean showDialogConfirmation = DialogUtil
				.showRemoveConfirmation(getShell());

		if (showDialogConfirmation) {
			getAction().deleteItemFromTable(index, item, col);
		}

	}

	@Override
	public void createComponents(Composite parent) {

	}

	@Override
	public void initAction() {

	}

	public ManageAccountCompositeAction getAction() {
		return action;
	}

	public void setBrokerSlt(Combo brokerSlt) {
		/*
		 * brokerSlt.setEnabled(false); brokerSlt.setListVisible(true);
		 * brokerSlt.setEditable(false);
		 */
	}

	public void setCorretora(Combo corretora) {
		this.corretora = corretora;
	}

	public Combo getCorretora() {
		return corretora;
	}

	public void addListener(UpdateUserAccountListener updateAccountListener) {
		this.listeners.add(updateAccountListener);
	}
}
