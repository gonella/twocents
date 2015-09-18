package com.twocents.ui.client.components.configuration.ui;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.swtdesigner.SWTResourceManager;
import com.twocents.model.Broker;
import com.twocents.ui.client.components.configuration.action.BrokerConfigurationAction;
import com.twocents.ui.client.components.configuration.event.BrokerAddedEvent;
import com.twocents.ui.client.components.configuration.event.BrokerEvent;
import com.twocents.ui.client.components.configuration.event.BrokerOperationListener;
import com.twocents.ui.client.components.configuration.event.BrokerUpdatedEvent;
import com.twocents.ui.resources.UIMessages;
import com.twocents.ui.util.DialogUtil;

public class BrokerConfigurationUI extends Composite implements
		BrokerOperationListener {

	private Logger log = Logger.getLogger(BrokerConfigurationUI.class);
	private BrokerConfigurationAction action;

	public void setAction(BrokerConfigurationAction action) {
		this.action = action;
	}

	private List list;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public BrokerConfigurationUI(final Composite parent, final int style) {
		super(parent, SWT.NONE);
	}

	public void createComponents() {

		final Button btnAdicionar = new Button(this, SWT.NONE);
		btnAdicionar.setBounds(188, 27, 75, 25);
		btnAdicionar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				openAddBroker(null);
			}
		});
		btnAdicionar.setText("Adicionar");

		final Button btnEditar = new Button(this, SWT.NONE);
		btnEditar.setBounds(188, 58, 75, 25);
		btnEditar.setEnabled(false);
		btnEditar.setText("Editar");

		btnEditar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				openAddBroker(action.getBrokerByBrokerName(list.getItem(list
						.getSelectionIndex())));
			}
		});

		final Button btnRemover = new Button(this, SWT.NONE);
		btnRemover.setBounds(188, 89, 75, 25);
		btnRemover.setEnabled(false);
		btnRemover.setText("Remover");

		list = new List(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		list.setBounds(24, 27, 149, 118);
		list.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));

		btnRemover.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				remove();
			}

		});

		setListItems();

		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				btnEditar.setEnabled(true);
				btnRemover.setEnabled(true);

			}
		});
	}

	private void setListItems() {
		java.util.List<Broker> brokers = action.listBrokerByStockBroker();
		java.util.List<String> names = new java.util.ArrayList<String>();

		for (Broker brk : brokers) {
			names.add(brk.getName());
		}

		list.setItems(names.toArray(new String[names.size()]));
	}

	private void openAddBroker(Broker broker) {
		Display display = getDisplay();
		Shell shell = new Shell(display);

		java.util.List<BrokerOperationListener> listeners = new ArrayList<BrokerOperationListener>();
		listeners.add(this);

		AddBrokerDialog dialog = new AddBrokerDialog(shell,
				SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM, action, broker,
				listeners);
		dialog.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
	}

	@Override
	public void onBrokerUpdated(BrokerEvent event) {
		if (event instanceof BrokerAddedEvent) {
			Broker b = ((BrokerEvent) event).getBroker();
			list.add(b.getName());
			setListItems();
		} else if (event instanceof BrokerUpdatedEvent) {
			setListItems();
		}
	}

	private void remove() {
		try {
			boolean isDeleteConfirmedDialog = DialogUtil
					.showRemoveConfirmation(UIMessages
							.getMessage("DIALOG_CONFIRMATION"), UIMessages
							.getMessage("DIALOG_CONFIRMATION_REMOVE_BROKER"),
							getShell());

			if (isDeleteConfirmedDialog) {
				String brkName = list.getItem(list.getSelectionIndex());
				log.debug("Removing broker: " + brkName);
				action.removeBroker(brkName);
				list.remove(brkName);
			}

		} catch (Exception e) {
			DialogUtil
					.errorMessage(getShell(), "ERRO",
							"Ocorreu um erro durante a remoção da corretora. Favor tentar novamente.");
		}
	}

}
