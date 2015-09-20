package com.twocents.ui.client;

import org.eclipse.nebula.widgets.datechooser.DateChooserCombo;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.model.OperationType;
import com.twocents.ui.client.action.TwOperationUIAction;
import com.twocents.ui.client.components.AddOperationComposite;
import com.twocents.ui.client.components.DateIntervalComposite;
import com.twocents.ui.client.components.WindowsAdvertisingInOperation;
import com.twocents.ui.client.components.google.GoogleComposite;
import com.twocents.ui.client.table.CustodyTable;
import com.twocents.ui.util.DialogUtil;

public class TwOperationUI extends TwBase {

	private static final String _ADICIONAR_UMA_OPERACAO = "Adicionar uma operação";
	private static final String _OPERACOES_REALIZADAS = "Operações Realizadas";
	private Table operationCallOptionTable;
	private CustodyTable custodyTable;
	private TableColumn dateColumn;

	private Table operationSellTable;
	private Table operationBuyTable;

	private CTabFolder operationTabFolder;

	private TwOperationUIAction action;
	private DateIntervalComposite compositeIntervalOperation;

	private Color colorBackGround;
	private PGroup groupAddOperation;
	private AddOperationComposite addOperationComposite;
	private Composite compositeAddOperation;
	private Composite compositeTableOperation;
	private Table operationTable;
	private final Composite containerComponents;

	private WindowsAdvertisingInOperation windowsAdvertisingInOperation;
	private GoogleComposite googleDocComposite;

	
	public AddOperationComposite getAddOperationComposite() {
		return addOperationComposite;
	}
	
	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param compositeCenter 
	 * @param compositeLeftBotton 
	 * @param style
	 */
	public TwOperationUI(Composite containerComponents, Composite compositeCenter, Composite compositeLeftBotton, int style, String title) {
		super(compositeCenter, style, title);
		this.containerComponents = containerComponents;

		createComponents(getCompositeCenter());

		//setGoogleDocComposite(new GoogleComposite(compositeLeftBotton,SWT.NONE));

		setWindowsAdvertisingInOperation(new WindowsAdvertisingInOperation(compositeLeftBotton));
		
		//initAction();
	}
	
	public void setWindowsAdvertisingInOperation(
			WindowsAdvertisingInOperation windowsAdvertisingInOperation) {
		this.windowsAdvertisingInOperation = windowsAdvertisingInOperation;
	}

	public WindowsAdvertisingInOperation getWindowsAdvertisingInOperation() {
		return windowsAdvertisingInOperation;
	}
	public void setGoogleDocComposite(GoogleComposite googleDocComposite) {
		this.googleDocComposite = googleDocComposite;
	}

	public GoogleComposite getGoogleDocComposite() {
		return googleDocComposite;
	}

	protected void createComponents(Composite center) {

		Composite main = new Composite(center, SWT.NONE);

		main.setLayout(new FillLayout(SWT.VERTICAL));

		final Composite composite_1 = new Composite(main, SWT.NONE);
		composite_1.setLayout(new FillLayout());
		composite_1.setBackground(SWTResourceManager.getColor(255, 255, 255));

		final Composite composite_2 = new Composite(composite_1, SWT.NONE);
		composite_2.setLayout(new BorderLayout(0, 0));
		composite_2.setBackground(SWTResourceManager.getColor(255, 255, 255));

		final Composite composite_3 = new Composite(composite_2, SWT.NONE);
		final FillLayout fillLayout_1 = new FillLayout();
		fillLayout_1.marginHeight = 3;
		fillLayout_1.spacing = 5;
		fillLayout_1.marginWidth = 10;
		composite_3.setLayout(fillLayout_1);
		composite_3.setLayoutData(BorderLayout.NORTH);
		composite_3.setBackground(SWTResourceManager.getColor(255, 255, 255));

		final Composite compositeCustody = new Composite(composite_2, SWT.NONE);
		final FillLayout fillLayout = new FillLayout();
		fillLayout.marginHeight = 3;
		fillLayout.marginWidth = 10;
		fillLayout.spacing = 5;
		compositeCustody.setLayout(fillLayout);
		compositeCustody.setBackground(SWTResourceManager.getColor(255, 255, 255));
		compositeCustody.setLayoutData(BorderLayout.CENTER);

		setCompositeAddOperation(new Composite(compositeCustody, SWT.NONE));
		getCompositeAddOperation().setLayout(new FillLayout());
		getCompositeAddOperation().setBackground(SWTResourceManager.getColor(255, 255, 255));
		//http://www.ibm.com/developerworks/opensource/tutorials/os-eclipse-nebula/section16.html
		
		createPanelAddOperation(getCompositeAddOperation());
		
		createPanelCustody(compositeCustody);

		createPanelOperationTable(main);

	}

	public void createPanelCustody(final Composite compositeCustody) {
		final Composite composite_6 = new Composite(compositeCustody, SWT.NONE);
		final FillLayout fillLayout_2 = new FillLayout(SWT.VERTICAL);
		composite_6.setLayout(fillLayout_2);
		composite_6.setBackground(SWTResourceManager.getColor(255, 255, 255));

		final PGroup custodyGroup = new PGroup(composite_6, SWT.NONE);
		custodyGroup.setToggleRenderer(null);
		custodyGroup.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));
		final FillLayout fillLayout_3 = new FillLayout(SWT.VERTICAL);
		fillLayout_3.spacing = 5;
		fillLayout_3.marginWidth = 10;
		fillLayout_3.marginHeight = 10;
		custodyGroup.setLayout(fillLayout_3);
		custodyGroup.setText("Custódia");

		custodyTable = new CustodyTable(custodyGroup, SWT.BORDER);
	}

	public void createPanelAddOperation(Composite compositeAddOperation) {
		groupAddOperation = new PGroup(compositeAddOperation, SWT.NONE);
		// groupAddOperation = new Group(composite_4, SWT.NONE);
		groupAddOperation.setText(_ADICIONAR_UMA_OPERACAO);
		groupAddOperation.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		groupAddOperation.setLayout(new FillLayout());

		groupAddOperation.setBackground(SWTResourceManager.getColor(0, 96, 191));

		groupAddOperation.setForeground(SWTResourceManager.getColor(255, 255,255));
		//groupAddOperation.setToggleRenderer(new TwisteToggleRenderer());
		groupAddOperation.setToggleRenderer(null);

		addOperationComposite = new AddOperationComposite(groupAddOperation,SWT.NONE, this);

		MouseAdapter listener = new MouseAdapter(){
			public void mouseEntered(MouseEvent e)
			{
				Display display = new Display();
				groupAddOperation.setForeground(display.getSystemColor(SWT.COLOR_YELLOW));
				//System.out.println("Mouse Over!");
			}
		};
		groupAddOperation.addMouseListener(listener);
	}
	public void createPanelOperationTable(Composite main) {
		final Composite composite = new Composite(main, SWT.NONE);
		final FillLayout fillLayout_4 = new FillLayout();
		fillLayout_4.marginWidth = 10;
		fillLayout_4.marginHeight = 3;
		composite.setLayout(fillLayout_4);
		composite.setBackground(SWTResourceManager.getColor(255, 255, 255));

		final PGroup operationDone = new PGroup(composite, SWT.NONE);
		// final Group operationDone = new Group(composite, SWT.NONE);
		operationDone.setText(_OPERACOES_REALIZADAS);
		operationDone.setToggleRenderer(null);
		operationDone.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));
		final FillLayout fillLayout_6 = new FillLayout();
		fillLayout_6.marginHeight = 10;
		operationDone.setLayout(fillLayout_6);

		operationTabFolder = new CTabFolder(operationDone, SWT.NONE);
		operationTabFolder.setSelectionBackground(new Color[] {
				SWTResourceManager.getColor(225, 240, 255),
				SWTResourceManager.getColor(149, 202, 255),
				Display.getCurrent().getSystemColor(SWT.COLOR_WHITE) },
				new int[] { 50, 100 }, true);
		operationTabFolder.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));
		operationTabFolder.setSimple(false);
		operationTabFolder.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {
				updateOperationsTable(event);

			}

			public void widgetSelected(SelectionEvent event) {
				updateOperationsTable(event);

			}
		});

		final CTabItem tabItem = new CTabItem(operationTabFolder, SWT.NONE);
		tabItem.setText("Todas Operações    ");
		operationTabFolder.setSelection(tabItem);

		setCompositeTableOperation(createCompositeInterval(tabItem,compositeIntervalOperation, null));
		
		setOperationTable(createBaseOperationTable(getCompositeTableOperation()));
	}
	
	private Composite createCompositeInterval(final CTabItem tabItem,
			DateIntervalComposite compositeInterval, OperationType type) {
		final Composite composite = new Composite(operationTabFolder, SWT.NONE);
		composite.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));
		composite.setLayout(new BorderLayout(0, 0));
		compositeInterval = new DateIntervalComposite(composite, SWT.NONE);
		compositeInterval.addActionButtonListener(new Listener() {
			public void handleEvent(Event event) {
				updateOperationsTable(tabItem);
			}
		});
		compositeInterval.setLayoutData(BorderLayout.NORTH);
		tabItem.setData("dateInterval", compositeInterval);
		tabItem.setData("operationType", type);
		tabItem.setControl(composite);

		return composite;
	}
	private Table createBaseOperationTable(final Composite composite) {
		final Table operationTable = new Table(composite, SWT.BORDER
				| SWT.FULL_SELECTION);
		operationTable.setLinesVisible(true);
		operationTable.setHeaderVisible(true);

		// Disable native tooltip
		operationTable.setToolTipText("");
		Listener tableListener = createListenerForToolTip(operationTable,
				operationTable.getShell(), operationTable.getShell()
						.getDisplay());
		operationTable.addListener(SWT.MouseHover, tableListener);

		operationTable.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				Point pt = new Point(event.x, event.y);
				TableItem item = operationTable.getItem(pt);

				if (item == null)
					return;
				for (int col = 0; col < operationTable.getColumnCount(); col++) {

					// DELETE ACTION
					if (col == 0) {
						Rectangle rect = item.getBounds(col);
						if (rect.contains(pt)) {
							int index = operationTable.indexOf(item);

							deleteItemSelectedOnTable(index, item, col);

							break;
						}
					}
					
					// DELETE ACTION
					else if (col == 1) {
						Rectangle rect = item.getBounds(col);
						if (rect.contains(pt)) {
							int index = operationTable.indexOf(item);

							//DialogUtil.showDialogQuestion(composite.getShell(), "Test", "Update");
							updateItemSelectedOnTable(item);

							break;
						}
					}
				}
			}
		});

		// [CHANGE] [DELETE] |ID|DATA|ATIVO|OPERACAO|PREÇO|QTDE|TOTAL|DESC
		final TableColumn newColumnTableColumn_6 = new TableColumn(
				operationTable, SWT.NONE);
		newColumnTableColumn_6.setWidth(25);

		final TableColumn newColumnTableColumn_7 = new TableColumn(
				operationTable, SWT.NONE);
		newColumnTableColumn_7.setWidth(25);


		final TableColumn newColumnTableColumn = new TableColumn(
				operationTable, SWT.NONE);
		newColumnTableColumn.setWidth(35);
		newColumnTableColumn.setText("Id");

		final TableColumn newColumnTableColumn_1 = new TableColumn(
				operationTable, SWT.NONE);
		newColumnTableColumn_1.setWidth(120);
		newColumnTableColumn_1.setText("Data");
		
		final TableColumn newColumnTableColumnOp_1 = new TableColumn(
				operationTable, SWT.NONE);
		newColumnTableColumnOp_1.setWidth(70);
		newColumnTableColumnOp_1.setText("Preço");

		final TableColumn newColumnTableColumnOp_2 = new TableColumn(
				operationTable, SWT.NONE);
		newColumnTableColumnOp_2.setWidth(70);
		newColumnTableColumnOp_2.setText("Qtde");

		final TableColumn newColumnTableColumnOp_3 = new TableColumn(
				operationTable, SWT.NONE);
		newColumnTableColumnOp_3.setWidth(95);
		newColumnTableColumnOp_3.setText("Total");

		final TableColumn newColumnTableColumn_4 = new TableColumn(
				operationTable, SWT.NONE);
		newColumnTableColumn_4.setWidth(70);
		newColumnTableColumn_4.setText("Ativo");

		final TableColumn newColumnTableColumn_3 = new TableColumn(
				operationTable, SWT.NONE);
		newColumnTableColumn_3.setWidth(70);
		newColumnTableColumn_3.setText("Operação");

		final TableColumn newColumnTableColumnOp_6 = new TableColumn(
				operationTable, SWT.NONE);
		newColumnTableColumnOp_6.setWidth(70);
		newColumnTableColumnOp_6.setText("Corretagem");

		final TableColumn newColumnTableColumnOp_8 = new TableColumn(
				operationTable, SWT.NONE);
		newColumnTableColumnOp_8.setWidth(70);
		newColumnTableColumnOp_8.setText("Bovespa");

		final TableColumn newColumnTableColumnOp_7 = new TableColumn(
				operationTable, SWT.NONE);
		newColumnTableColumnOp_7.setWidth(95);
		newColumnTableColumnOp_7.setText("Total Geral");

		final TableColumn newColumnTableColumn_2 = new TableColumn(
				operationTable, SWT.NONE);
		newColumnTableColumn_2.setWidth(180);
		newColumnTableColumn_2.setText("Comentários");

		operationTable.setLayoutData(BorderLayout.CENTER);
		
		
		return operationTable;
		
	}

	protected void updateItemSelectedOnTable(TableItem item) {
		getAction().updateOperationInAddOperationComposite(item);
	}

	private Listener createListenerForToolTip(final Table table,
			final Shell shell, final Display display) {

		// Implement a "fake" tooltip
		new Listener() {
			public void handleEvent(Event event) {
				Label label = (Label) event.widget;
				Shell shell = label.getShell();
				switch (event.type) {
				case SWT.MouseDown:
					Event e = new Event();
					e.item = (TableItem) label.getData("_TABLEITEM");
					// Assuming table is single select, set the selection as if
					// the mouse down event went through to the table
					table.setSelection(new TableItem[] { (TableItem) e.item });
					table.notifyListeners(SWT.Selection, e);
					shell.dispose();
					table.setFocus();
					break;
				case SWT.MouseExit:
					shell.dispose();
					break;
				}
			}
		};

		Listener tableListener = new Listener() {
			Shell tip = null;

			// Label label = null;

			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.Dispose:
				case SWT.KeyDown:
				case SWT.MouseMove: {
					if (tip == null)
						break;
					tip.dispose();
					tip = null;
					// label = null;
					break;
				}
				case SWT.MouseHover: {
					// displayToolTipWithOperationDetail(event, labelListener,
					// tip, label, table, shell, display);
				}
				}
			}
		};

		return tableListener;
	}

	protected void deleteItemSelectedOnTable(int index, TableItem item, int col) {

		boolean showDialogConfirmation = DialogUtil.showRemoveConfirmation(this.getShell());

		if (showDialogConfirmation) {
			new TwOperationUIAction(this).deleteOperation(index, item, col);
		}

	}
	
	protected void updateOperationsTable(CTabItem tabItem) {
		action.updateOperationsTable(tabItem);
	}

	protected void updateOperationsTable(SelectionEvent event) {
		CTabItem itemSelected = (CTabItem) event.item;
		updateOperationsTable(itemSelected);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	protected void displayFloatingBoxWithOperationDetail(Event event) {
		// getAction().displayFloatingBoxWithOperationDetail(event);
	}

	protected void displayToolTipWithOperationDetail(Event event,
			Listener labelListener, Shell tip, Label label, final Table table,
			final Shell shell, final Display display) {
		// getAction().displayToolTipWithOperationDetail(event,labelListener,tip,label,table,shell,display);
	}

	public void openDialogToAttachTheFileForOperation() {
		getAction().openDialogToAttachTheFileForOperation();

	}

	protected void deleteTheOperationSelectedOnTable(int index, TableItem item,
			int col) {
		getAction().deleteOperation(index, item, col);
	}

	public Table getOperationCallOptionTable() {
		return operationCallOptionTable;
	}

	public Table getOperationSellTable() {
		return operationSellTable;
	}

	public Table getOperationBuyTable() {
		return operationBuyTable;
	}

	public TwOperationUIAction getAction() {
		if(action==null){
			action = new TwOperationUIAction(this);
		}

		return action;
	}

	public Table getCustodyTable() {
		return custodyTable.getCustodyTable();
	}

	public TableColumn getDateColumn() {
		return dateColumn;
	}

	public CTabFolder getOperationTabFolder() {
		return operationTabFolder;
	}

	protected void setColorBackGround(Color colorBackGround) {
		this.colorBackGround = colorBackGround;

		changeColor(groupAddOperation);
	}

	protected Color getColorBackGround() {
		return colorBackGround;
	}

	private void changeColor(Control cont) {

		cont.setBackground(getColorBackGround());

		if (cont instanceof Composite) {
			Composite composite = (Composite) cont;
			if (composite.getChildren().length == 0) {
				return;
			} else {
				Control[] control = composite.getChildren();
				for (Control control2 : control) {
					if (control2 instanceof DateChooserCombo) {
						continue;
					}
					if (control2 instanceof Text) {
						continue;
					}
					if (control2 instanceof StyledText) {
						continue;
					}
					// if(control2 instanceof Button) {continue;}

					try {
						changeColor(control2);
					} catch (Exception e) {
						e.printStackTrace();
						control2.setBackground(getColorBackGround());
					}
				}
			}
		}

	}

	public void setPanelDisable() {
		getAddOperationComposite().getRegistrar().setVisible(false);
	}

	public void setPanelEnable() {

		// this.setEnabled(true);

		/*getAddOperationComposite().getRegistrar().setEnabled(true);
		getAddOperationComposite().getRegistrar().setToolTipText(UIMessages.getMessage("REGISTRAR_BUTTON_WITH_ACCOUNT"));
*/
		getAddOperationComposite().getRegistrar().setVisible(true);
	}

	public void setCompositeAddOperation(Composite compositeAddOperation) {
		this.compositeAddOperation = compositeAddOperation;
	}

	public Composite getCompositeAddOperation() {
		return compositeAddOperation;
	}

	public void setOperationTable(Table operationTable) {
		this.operationTable = operationTable;
	}

	public Table getOperationTable() {
		return operationTable;
	}

	public Composite getContainerComponents() {
		return containerComponents;
	}

	public void setCompositeTableOperation(Composite compositeTableOperation) {
		this.compositeTableOperation = compositeTableOperation;
	}

	public Composite getCompositeTableOperation() {
		return compositeTableOperation;
	}

}
