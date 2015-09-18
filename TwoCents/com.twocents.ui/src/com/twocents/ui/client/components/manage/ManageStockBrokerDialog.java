package com.twocents.ui.client.components.manage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;
import com.twocents.ui.util.DialogUtil;

public class ManageStockBrokerDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private Group group1;
	private Label label5;
	private Text password;
	private Text userName;
	private Label label3;
	private Button button2;
	private Button button1;
	private Label label4;
	private Text tel;
	private Text email;
	private Label label2;
	private Label label1;
	private TableColumn userNameColumn;
	private Text nome;
	private Group group4;
	private Table tableClient;
	private TableColumn tableColumn5;
	private TableColumn tableColumn4;
	private TableColumn tableColumn3;
	private TableColumn tableColumn2;
	private Group group2;

	private ManageStockBrokerAction action;

	/**
	 * Auto-generated main method to display this org.eclipse.swt.widgets.Dialog
	 * inside a new Shell.
	 */
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();

			Shell shell = new Shell(display);
			ManageStockBrokerDialog inst = new ManageStockBrokerDialog(shell,
					SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ManageStockBrokerDialog(Shell parent) {
		super(parent, SWT.APPLICATION_MODAL);
	}

	public ManageStockBrokerDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL);

			// Register as a resource user - SWTResourceManager will
			// handle the obtaining and disposing of resources
			SWTResourceManager.registerResourceUser(dialogShell);

			FillLayout dialogShellLayout = new FillLayout(
					org.eclipse.swt.SWT.HORIZONTAL);
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setSize(675, 500);

			composite1 = new Composite(dialogShell, SWT.NONE);
			FillLayout composite1Layout = new FillLayout(
					org.eclipse.swt.SWT.VERTICAL);
			composite1Layout.type = SWT.VERTICAL;
			composite1.setLayout(composite1Layout);
			group1 = new Group(composite1, SWT.NONE);
			FillLayout group1Layout = new FillLayout(
					org.eclipse.swt.SWT.VERTICAL);
			group1Layout.type = SWT.VERTICAL;
			group1.setLayout(group1Layout);
			group1.setText("Gerenciar Corretores");
			group1.setFont(SWTResourceManager.getFont("Verdana", 12, 1, false,
					false));
			group4 = new Group(group1, SWT.NONE);
			FormLayout group4Layout = new FormLayout();
			group4.setLayout(group4Layout);

			label1 = new Label(group4, SWT.NONE);
			FormData label1LData = new FormData();
			label1LData.left = new FormAttachment(0, 1000, 9);
			label1LData.top = new FormAttachment(0, 1000, 4);
			label1LData.width = 47;
			label1LData.height = 21;
			label1.setLayoutData(label1LData);
			label1.setText("Nome:");
			label1.setFont(SWTResourceManager.getFont("Segoe UI", 10, 0, false,
					false));
			nome = new Text(group4, SWT.NONE);
			FormData text1LData = new FormData();
			text1LData.left = new FormAttachment(0, 1000, 62);
			text1LData.top = new FormAttachment(0, 1000, 4);
			text1LData.width = 357;
			text1LData.height = 21;
			nome.setLayoutData(text1LData);
			nome.setFont(SWTResourceManager.getFont("Segoe UI", 10, 0, false,
					false));
			label2 = new Label(group4, SWT.NONE);
			label2.setText("Email:");
			FormData label2LData = new FormData();
			label2LData.left = new FormAttachment(0, 1000, 9);
			label2LData.top = new FormAttachment(0, 1000, 37);
			label2LData.width = 47;
			label2LData.height = 21;
			label2.setLayoutData(label2LData);
			label2.setFont(SWTResourceManager.getFont("Segoe UI", 10, 0, false,
					false));
			email = new Text(group4, SWT.NONE);
			email.setFont(SWTResourceManager.getFont("Segoe UI", 10, 0, false,
					false));
			FormData text2LData = new FormData();
			text2LData.left = new FormAttachment(0, 1000, 62);
			text2LData.top = new FormAttachment(0, 1000, 37);
			text2LData.width = 357;
			text2LData.height = 21;
			email.setLayoutData(text2LData);
			tel = new Text(group4, SWT.NONE);
			tel.setFont(SWTResourceManager.getFont("Segoe UI", 10, 0, false,
					false));
			FormData text4LData = new FormData();
			text4LData.left = new FormAttachment(0, 1000, 62);
			text4LData.top = new FormAttachment(0, 1000, 70);
			text4LData.width = 357;
			text4LData.height = 21;
			tel.setLayoutData(text4LData);
			label4 = new Label(group4, SWT.NONE);
			label4.setText("Tel:");
			FormData label4LData = new FormData();
			label4LData.left = new FormAttachment(0, 1000, 9);
			label4LData.top = new FormAttachment(0, 1000, 70);
			label4LData.width = 47;
			label4LData.height = 21;
			label4.setLayoutData(label4LData);
			label4.setFont(SWTResourceManager.getFont("Segoe UI", 10, 0, false,
					false));
			label3 = new Label(group4, SWT.NONE);
			label3.setText("Username");
			FormData label3LData = new FormData();
			label3LData.left = new FormAttachment(0, 1000, 9);
			label3LData.top = new FormAttachment(0, 1000, 103);
			label3LData.width = 59;
			label3LData.height = 21;
			label3.setLayoutData(label3LData);
			label3.setFont(SWTResourceManager.getFont("Segoe UI", 10, 0, false,
					false));
			userName = new Text(group4, SWT.NONE);
			userName.setFont(SWTResourceManager.getFont("Segoe UI", 10, 0,
					false, false));
			FormData text1LData1 = new FormData();
			text1LData1.left = new FormAttachment(0, 1000, 74);
			text1LData1.top = new FormAttachment(0, 1000, 103);
			text1LData1.width = 160;
			text1LData1.height = 21;
			userName.setLayoutData(text1LData1);
			password = new Text(group4, SWT.NONE);
			password.setFont(SWTResourceManager.getFont("Segoe UI", 10, 0,
					false, false));
			FormData text2LData1 = new FormData();
			text2LData1.left = new FormAttachment(0, 1000, 74);
			text2LData1.top = new FormAttachment(0, 1000, 136);
			text2LData1.width = 160;
			text2LData1.height = 21;
			password.setLayoutData(text2LData1);
			label5 = new Label(group4, SWT.NONE);
			label5.setText("Password");
			FormData label5LData = new FormData();
			label5LData.left = new FormAttachment(0, 1000, 9);
			label5LData.top = new FormAttachment(0, 1000, 136);
			label5LData.width = 59;
			label5LData.height = 21;
			label5.setLayoutData(label5LData);
			label5.setFont(SWTResourceManager.getFont("Segoe UI", 10, 0, false,
					false));
			button1 = new Button(group4, SWT.PUSH | SWT.CENTER);
			FormData button1LData = new FormData();
			button1LData.left = new FormAttachment(0, 1000, 494);
			button1LData.top = new FormAttachment(0, 1000, 150);
			button1LData.width = 71;
			button1LData.height = 33;
			button1.setLayoutData(button1LData);
			button1.setText("Adicionar");

			button1.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent arg0) {

					getAction().addStockBroker(getNome().getText(),
							getEmail().getText(), getTel().getText(),
							getUserName().getText(), getPassword().getText());

				}
			});
			button2 = new Button(group4, SWT.PUSH | SWT.CENTER);
			button2.setText("Cancelar");
			button2.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent arg0) {
					dialogShell.close();
				}
			});

			FormData button2LData = new FormData();
			button2LData.left = new FormAttachment(0, 1000, 571);
			button2LData.top = new FormAttachment(0, 1000, 150);
			button2LData.width = 71;
			button2LData.height = 33;
			button2.setLayoutData(button2LData);
			group2 = new Group(composite1, SWT.NONE);
			FillLayout group2Layout = new FillLayout(
					org.eclipse.swt.SWT.HORIZONTAL);
			group2.setLayout(group2Layout);
			group2.setText("Lista de Corretores");
			tableClient = new Table(group2, SWT.NONE);
			tableClient.setLinesVisible(true);
			tableClient.setHeaderVisible(true);

			tableColumn5 = new TableColumn(tableClient, SWT.NONE);
			// tableColumn5.setText("");
			tableColumn5.setWidth(25);
			tableColumn2 = new TableColumn(tableClient, SWT.NONE);
			tableColumn2.setText("Nome");
			tableColumn2.setWidth(250);
			tableColumn3 = new TableColumn(tableClient, SWT.NONE);
			tableColumn3.setText("Email");
			tableColumn3.setWidth(200);
			tableColumn4 = new TableColumn(tableClient, SWT.NONE);
			tableColumn4.setText("Telefone");
			tableColumn4.setWidth(80);
			userNameColumn = new TableColumn(tableClient, SWT.NONE);
			userNameColumn.setText("Username");
			userNameColumn.setWidth(86);
			tableClient.addListener(SWT.MouseDown, new Listener() {
				public void handleEvent(Event event) {
					Point pt = new Point(event.x, event.y);
					TableItem item = tableClient.getItem(pt);

					if (item == null)
						return;
					for (int i = 0; i < tableClient.getColumnCount(); i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							int index = tableClient.indexOf(item);

							deleteItemSelectedOnTable(index, item, i);

							break;
						}
					}
				}
			});
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			nome.setFocus();
			// int positionX=parent.getBounds().x+(parent.getBounds().width /4);
			// Move the dialog to the center of the top level shell.
			Rectangle shellBounds = getParent().getBounds();
			Point dialogSize = dialogShell.getSize();

			dialogShell.setLocation(shellBounds.x
					+ (shellBounds.width - dialogSize.x) / 2, shellBounds.y
					+ (shellBounds.height - dialogSize.y) / 2);

			getAction();

			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Text getTel() {
		return tel;
	}

	public Text getEmail() {
		return email;
	}

	public Text getNome() {
		return nome;
	}

	public Table getTableClient() {
		return tableClient;
	}

	public ManageStockBrokerAction getAction() {

		if (action == null) {
			action = new ManageStockBrokerAction(this);
		}

		return action;
	}

	protected void deleteItemSelectedOnTable(int index, TableItem item, int col) {

		boolean showDialogConfirmation = DialogUtil
				.showRemoveConfirmation(getParent());

		if (showDialogConfirmation) {
			getAction().deleteItemSelectedOnTable(index, item, col);
		}

	}

	public Text getPassword() {
		return password;
	}

	public Text getUserName() {
		return userName;
	}
}
