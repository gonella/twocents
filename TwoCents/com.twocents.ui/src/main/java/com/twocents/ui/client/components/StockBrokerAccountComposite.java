package com.twocents.ui.client.components;

import java.text.DateFormat;
import java.text.Format;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.nebula.widgets.pshelf.RedmondShelfRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import swing2swt.layout.BorderLayout;

import com.cloudgarden.resource.SWTResourceManager;
import com.twocents.model.Account;
import com.twocents.ui.client.TwOperationUI;
import com.twocents.ui.client.TwReportUI;
import com.twocents.ui.client.common.action.PanelCompositeAction;
import com.twocents.ui.client.components.action.StockBrokerAccountCompositeAction;
import com.twocents.ui.client.components.listener.UpdateUserAccountListener;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.util.DialogUtil;

public class StockBrokerAccountComposite extends PanelCompositeAction implements
		UpdateUserAccountListener {

	{
		// Register as a resource user - SWTResourceManager will
		// handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Logger logger = Logger.getLogger(StockBrokerAccountComposite.class);

	StockBrokerAccountCompositeAction action;

	private List listUser;
	private TableColumn profit;
	private TableColumn name;
	private Table table;
	private Composite composite_2;
	private Composite composite_3;
	private Label label;

	final Color BLUE_LIGHT = SWTResourceManager.getColor(173, 198, 225);
	final Color BLUE_STRONG = SWTResourceManager.getColor(155, 205, 255);

	private final TwOperationUI operationUI;

	private final TwReportUI reportUI;

	/**
	 * @wbp.parser.constructor
	 */
	public StockBrokerAccountComposite(Composite parent,
			TwOperationUI operationUI, TwReportUI reportUI) {
		this(parent, operationUI, reportUI, SWT.BORDER);
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param reportUI
	 * @param operationUI
	 * @param style
	 */
	public StockBrokerAccountComposite(Composite parent,
			TwOperationUI operationUI, TwReportUI reportUI, int style) {
		super(parent, style);
		this.operationUI = operationUI;
		this.reportUI = reportUI;

		// AZUL FORTE
		setBackground(SWTResourceManager.getColor(155, 205, 255));

		// AZUL CLARINHO
		// setBackground(SWTResourceManager.getColor(173, 198, 225));

		setBackgroundMode(SWT.INHERIT_DEFAULT);

		setLayout(new BorderLayout(0, 0));
		Composite composite_1 = new Composite(this, SWT.NONE);
		// composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite_1.setLayout(new BorderLayout(0, 0));

		PShelf shelf = new PShelf(composite_1, SWT.NONE);
		shelf.setRenderer(new RedmondShelfRenderer());
		PShelfItem shelfItem = new PShelfItem(shelf, SWT.NONE);
		shelfItem.setText("Contas");
		shelfItem.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		// shelfItem.getBody().setBounds(0, 0, 200, 200);

		table = new Table(shelfItem.getBody(), SWT.FULL_SELECTION | SWT.BORDER);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		// listClient.setLayoutData(new
		// GridData(GridData.FILL_BOTH));

		name = new TableColumn(table, SWT.LEFT);
		name.setText("Nome");
		name.setWidth(125);
		// name.setMoveable(true);
		// name.pack();
		profit = new TableColumn(table, SWT.CENTER);
		profit.setText("Rent(%)");
		profit.setWidth(65);
		// profit.setMoveable(true);
		// profit.pack();

		// listClient.pack();
		table.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				Point pt = new Point(event.x, event.y);
				TableItem item = table.getItem(pt);

				if (item == null)
					return;
				for (int i = 0; i < table.getColumnCount(); i++) {
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt)) {
						int index = table.indexOf(item);

						selectItemOnTable(index, item, i);

						break;
					}
				}
			}
		});
		final Color red = getDisplay().getSystemColor(SWT.COLOR_RED);
		final Color yellow = getDisplay().getSystemColor(SWT.COLOR_YELLOW);

		final Color blue = getDisplay().getSystemColor(SWT.COLOR_CYAN);
		final Color green = getDisplay().getSystemColor(SWT.COLOR_GREEN);

		final Color dark_blue = getDisplay()
				.getSystemColor(SWT.COLOR_DARK_BLUE);
		final Color dark_green = getDisplay().getSystemColor(
				SWT.COLOR_DARK_GREEN);

		table.addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(Event event) {
				event.detail &= ~SWT.HOT;
				if ((event.detail & SWT.SELECTED) == 0)
					return; // / item not
				// selected

				Table table = (Table) event.widget;
				TableItem item = (TableItem) event.item;
				int clientWidth = table.getClientArea().width;

				GC gc = event.gc;
				Color oldForeground = gc.getForeground();
				Color oldBackground = gc.getBackground();

				// gc.setBackground(red);
				// gc.setForeground(yellow);

				gc.setBackground(BLUE_STRONG);
				gc.setForeground(BLUE_STRONG);

				// gc.fillRectangle(0, event.y,
				// clientWidth, event.height);
				gc.fillGradientRectangle(0, event.y, clientWidth, event.height,
						false);

				gc.setForeground(oldForeground);
				gc.setBackground(oldBackground);
				event.detail &= ~SWT.SELECTED;
			}
		});

		// shelf.pack();

		// MOSTRA A DATA EM CIMA DA LISTA DE CONTAS
		/*
		 * { composite_2 = new Composite(composite_1, SWT.NONE);
		 * composite_2.setLayoutData(BorderLayout.NORTH);
		 * composite_2.setLayout(new BorderLayout(0, 0)); { composite_3 = new
		 * Composite(composite_2, SWT.NONE);
		 * composite_3.setLayoutData(BorderLayout.NORTH);
		 * composite_3.setLayout(new FillLayout(SWT.HORIZONTAL)); { setLabel(new
		 * Label(composite_3, SWT.NONE)); getLabel().setFont(
		 * SWTResourceManager.getFont("Arial", 10, SWT.NONE)); //
		 * getLabel().setText("Domingo, 23 de Janeiro de 2011"); Format fullDate
		 * = DateFormat .getDateInstance(DateFormat.FULL);
		 * 
		 * getLabel().setText(fullDate.format(new Date()));
		 * getLabel().setBackground
		 * (com.swtdesigner.SWTResourceManager.getColor(SWT.COLOR_WHITE));
		 * 
		 * //getLabel().setBackground(SWTResourceManager.getColor(3, 16, 48)); }
		 * } }
		 */

	}

	private void initAccountPanelSize() {

		Rectangle rectangle = new Rectangle(0, 60,
				getLabel().getBounds().width, 200);
		setBounds(rectangle);

		pack();

	}

	protected void selectItemOnTable(int index, TableItem item, int i) {
		getAction().displayAccountSelected(item.getData());
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void setListUser(List listClient) {
	}

	public List getListUser() {
		return listUser;
	}

	public StockBrokerAccountCompositeAction getAction() {

		if (action == null) {
			action = new StockBrokerAccountCompositeAction(this);
		}

		return action;
	}

	public Table getListClient() {
		return table;
	}

	public void refresh() throws UIException {
		getAction().init();
	}

	@Override
	public void onAccountUpdate(Account acount) {
		refreshTable();
	}

	@Override
	public void onAccountRemove(Account acount) {
		refreshTable();
		getAction().cleanUpOnDeleteAccount();
	}

	private void refreshTable() {
		try {
			refresh();
		} catch (UIException e) {
			DialogUtil.errorMessage(getShell(), "Erro",
					"Erro durante listagem de contas, favor tentar novamente");
			logger.error(e);
		}
	}

	public void updateAcountPanelComposite() {
		try {
			refresh();
		} catch (UIException e) {
			DialogUtil.errorMessage(getShell(), "Erro",
					"Erro durante listagem de contas, favor tentar novamente");
			logger.error(e);
		}
	}

	public void setLabel(Label label) {
		this.label = label;
		label.setFont(org.eclipse.wb.swt.SWTResourceManager.getFont("Segoe UI",
				12, SWT.NORMAL));
	}

	public Label getLabel() {
		return label;
	}

	public TwOperationUI getOperationUI() {
		return operationUI;
	}

	public TwReportUI getReportUI() {
		return reportUI;
	}

}
