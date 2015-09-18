package com.twocents.ui.client.action;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.Collator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.swtdesigner.SWTResourceManager;
import com.twocents.core.populate.PopulateOperationData;
import com.twocents.core.util.CoreUtil;
import com.twocents.core.util.FormatUtil;
import com.twocents.core.util.ResourceUtil;
import com.twocents.core.util.StringCheck;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Broker;
import com.twocents.model.CommentPattern;
import com.twocents.model.CustodyView;
import com.twocents.model.Document;
import com.twocents.model.Operation;
import com.twocents.model.OperationType;
import com.twocents.model.OperationView;
import com.twocents.ui.client.TwOperationUI;
import com.twocents.ui.client.common.action.UIAction;
import com.twocents.ui.client.components.DateIntervalComposite;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.constant.UIDefault;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.resources.UIImages;

public class TwOperationUIAction extends UIAction {

	private Logger logger = Logger.getLogger(TwOperationUIAction.class);

	private final PopulateOperationData populateOperationData = new PopulateOperationData();

	private final TwOperationUI twOperationUI;

	Color red = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
	Color green = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);

	public TwOperationUI getOperationUI() {
		return twOperationUI;
	}

	public TwOperationUIAction(TwOperationUI twOperationUI) {
		super();
		this.twOperationUI = twOperationUI;
		setTwOperationUI(twOperationUI);
	}

	/**
	 * Set up the environment for Operation Tab
	 */
	public void init() {

		Account accountSelected = null;
		try {
			accountSelected = ContextHolderUI.getAccountSelected();
		} catch (CoreException e) {
			logger.debug("Não foi selecionado uma conta de cliente");
		}

		if (accountSelected == null) {
			getOperationUI().setPanelDisable();
		} else {
			getOperationUI().setPanelEnable();
			// populateReport(reports);
		}
	}

	public void initializeCommentConfiguration() throws UIException {

		List<CommentPattern> listPattern = getCommentConfigurationFacade()
				.listAllCommentPattern();

		if (!CollectionUtils.isEmpty(listPattern)) {

			for (int i = 0; i < listPattern.size(); i++) {
				CommentPattern pattern = listPattern.get(i);

				if (pattern.isDefaultComment()) {
					// getOperationUI().getCommentaryText().setText(pattern.getCommentPattern());
				}
			}
		}

	}

	private void initializeAllOperations(Timestamp startDate, Timestamp endDate) {
		try {
			List<OperationView> list = getOperationFacade()
					.findOperationViewByAccount(
							ContextHolderUI.getAccountSelected(), startDate,
							endDate);
			popuplateOperationMapTable(list);
		} catch (CoreException e) {
			logger.error(e);
		}
	}

	/**
	 * Usado para popula a ABA de operaçoes de COMPRA
	 */
	private void initializeBuyOperations(Timestamp startDate, Timestamp endDate) {
		try {
			List<Map<String, Object>> list = getOperationFacade()
					.findBuyMapByAccount(ContextHolderUI.getAccountSelected(),
							startDate, endDate);
			popuplateBuyOperationTable(list);
		} catch (CoreException e) {
			logger.error(e);
		}
	}

	private void initializeSellOperations(Timestamp startDate, Timestamp endDate) {
		try {
			List<Map<String, Object>> list = getOperationFacade()
					.findSellMapByAccount(ContextHolderUI.getAccountSelected(),
							startDate, endDate);
			popuplateSellOperationTable(list);
		} catch (CoreException e) {
			logger.error(e);
		}
	}

	private void initializeCallSellOperations(Timestamp startDate,
			Timestamp endDate) {
		try {
			List<Map<String, Object>> list = getOperationFacade()
					.findCallSellMapByAccount(
							ContextHolderUI.getAccountSelected(), startDate,
							endDate);
			popuplateCallSellOperationTable(list);
		} catch (CoreException e) {
			logger.error(e);
		}
	}

	public void initializeCustodyTableOnOperationUI() {
		try {
			logger.debug("Populando tabela de custodia");
			List<CustodyView> list = getOperationFacade().findCustodyByAccount(
					ContextHolderUI.getAccountSelected());
			populateCustodyTable(list);
		} catch (CoreException e) {
			logger.error(e);
		}
	}

	public void updateOperation(OperationView operationView, OperationType operationType, Date date,
			String ativo, Integer quantidade, Double preco) {

		try {
			logger.info("# Atualizando a operação");

			Account ac = ContextHolderUI.getAccountSelected();
			Broker broker = ac.getBroker();
			
			getPopulateOperationData().registerOperation(operationType, date,ativo, quantidade, preco,ContextHolderUI.getAccountSelected(), broker);
			refresh();

			logger.debug("* Operação atualizada");
			// logger.info("Total memory after insert: " +
			// Runtime.getRuntime().totalMemory());
		} catch (Exception e) {
			logger.error("## Não foi possível registrar a operação", e);
		}
	}
	
	public void registerOperation(OperationType operationType, Date date,
			String ativo, Integer quantidade, Double preco) throws CoreException {

		logger.info("# Registrando a operação");

		Account ac = ContextHolderUI.getAccountSelected();
		Broker broker = ac.getBroker();
		getPopulateOperationData().registerOperation(operationType, date,ativo, quantidade, preco, ContextHolderUI.getAccountSelected(),broker);
		refresh();

		logger.debug("* Operação registrada");
		// logger.info("Total memory after insert: " +
		// Runtime.getRuntime().totalMemory());
	}

	private void addDocument(Operation operation, Text pathFile)
			throws IOException {
		String path = pathFile.getText();
		if (StringCheck.isNotBlank(path)) {
			logger.info("Adding file: " + path);
			Document doc = new Document();
			File file = new File(path);
			doc.setName(file.getName());
			doc.setContent(ResourceUtil.getBytesFromFile(file));
			doc.setOperation(operation);
			doc.setMimeType(ResourceUtil.getMimeType(doc.getContent()));
			operation.getDocuments().add(doc);
		} else {
			logger.info("File name empty.");
		}

	}

	/**
	 * Add an operation list on Table View
	 * 
	 * @param operationViewList
	 */
	public void popuplateOperationMapTable(List<OperationView> operationViewList) {
		getOperationUI().getOperationTable().removeAll();
		TableItem item;
		for (OperationView operation : operationViewList) {
			item = new TableItem(getOperationUI().getOperationTable(), SWT.NONE);
			item.setData(operation);
			operationMapToRowString(operation, item);
		}
	}

	private void popuplateBuyOperationTable(List<Map<String, Object>> list) {
		getOperationUI().getOperationBuyTable().removeAll();
		TableItem item;
		for (Map<String, Object> operation : list) {
			item = new TableItem(getOperationUI().getOperationBuyTable(),
					SWT.NONE);
			buyOperationMapToRowString(operation, item);
		}
	}

	private void popuplateSellOperationTable(List<Map<String, Object>> list) {
		getOperationUI().getOperationSellTable().removeAll();
		TableItem item;
		for (Map<String, Object> operation : list) {
			item = new TableItem(getOperationUI().getOperationSellTable(),
					SWT.NONE);
			sellOperationMapToRowString(operation, item);
		}
	}

	private void popuplateCallSellOperationTable(List<Map<String, Object>> list) {
		getOperationUI().getOperationCallOptionTable().removeAll();
		TableItem item;
		for (Map<String, Object> operation : list) {
			item = new TableItem(
					getOperationUI().getOperationCallOptionTable(), SWT.NONE);
			callSelloperationMapToRowString(operation, item);
		}
	}

	/**
	 * Add an custody list on Table View
	 * 
	 * @param list
	 */
	public void populateCustodyTable(List<CustodyView> list) {
		if (getOperationUI() == null) {
			return;
		}
		getOperationUI().getCustodyTable().removeAll();
		TableItem item;
		for (CustodyView custody : list) {
			item = new TableItem(getOperationUI().getCustodyTable(), SWT.NONE);
			custodyToRowString(custody, item);
		}

	}

	public void operationMapToRowString(OperationView op, TableItem item) {
		String stock = op.getStockCode();
		Double price = op.getPrice();
		Integer amount = op.getAmount();
		Double total = new Double(0.0);
		if (price != null && amount != null) {
			total = price * amount;
		}
		Double totalGeral = null;
		boolean buy = "Buy".equalsIgnoreCase((String) op.getOperationType());
		// Double brokerage = (Double)op.get("brokerage");
		// Double bovespaTax = (Double)op.get("bovespaTax");
		if (buy) {
			totalGeral = total;// + brokerage + bovespaTax;
		} else {
			totalGeral = total;// - brokerage - bovespaTax;
		}
		// String desc=op.get("description").toString();
		// [DELETE] [UPDATE] |ID|DATA|ATIVO|OPERACAO|PREÇO|QTDE|TOTAL|DESC
		item.setText(new String[] { "","", op.getId().toString(),
				FormatUtil.formatDateAndTime(op.getOperationDate()),
				FormatUtil.toReal(price), amount.toString(),
				FormatUtil.toReal(total), stock,
				CoreUtil.getOperationType(op.getOperationType()), "0.0",// FormatUtil.formatCurrency(brokerage),
				"0.0",// FormatU til.formatCurrency(bovespaTax),
				FormatUtil.toReal(totalGeral), "" });

		item.setImage(0,SWTResourceManager.getImage(this.getClass(),
				UIImages.DELETE_ICON_16));
		item.setImage(1,SWTResourceManager.getImage(this.getClass(),
				UIImages.EDIT_OPERATION_ICON_16x16_1));

	}

	public void sellOperationMapToRowString(Map<String, Object> op,
			TableItem item) {
		String stock = op.get("stock") != null ? op.get("stock").toString()
				: "";
		Double price = op.get("price") != null ? (Double) op.get("price")
				: null;
		Integer amount = op.get("amount") != null ? (Integer) op.get("amount")
				: 0;
		Double total = null;
		if (price != null && amount != null) {
			total = price * amount;
		}
		Double brokerage = (Double) op.get("brokerage");
		Double bovespaTax = (Double) op.get("bovespaTax");
		Double totalGeral = total - brokerage - bovespaTax;
		Double profit = (Double) op.get("profit");
		Double profitPerc = (((totalGeral / (totalGeral - profit)) * 100) - 100) / 100;
		// X|ID|DATA|ATIVO|PREÇO|QTDE|TOTAL|CORRET|BOVESPA|TGERAL|GANHO|GANHO%|DESC
		item.setText(new String[] { "", op.get("id").toString(),
				FormatUtil.formatDate(op.get("operationDate")), stock,
				FormatUtil.toReal(price), amount.toString(),
				FormatUtil.toReal(total),
				FormatUtil.toReal(brokerage),
				FormatUtil.toReal(bovespaTax),
				FormatUtil.toReal(totalGeral),
				FormatUtil.toReal(profit),
				FormatUtil.toReal(profitPerc),
				op.get("description").toString() });
		item.setImage(SWTResourceManager.getImage(this.getClass(),
				UIImages.DELETE_ICON_16));
		Color c = profit < 0 ? red : green;
		item.setForeground(10, c);
		item.setFont(10, SWTResourceManager.getBoldFont(item.getFont()));
		item.setForeground(11, c);
		item.setFont(11, SWTResourceManager.getBoldFont(item.getFont()));
	}

	public void buyOperationMapToRowString(Map<String, Object> op,
			TableItem item) {
		String stock = op.get("stock") != null ? op.get("stock").toString()
				: "";
		Double price = op.get("price") != null ? (Double) op.get("price")
				: null;
		Integer amount = op.get("amount") != null ? (Integer) op.get("amount")
				: 0;
		Double total = null;
		if (price != null && amount != null) {
			total = price * amount;
		}
		Double brokerage = (Double) op.get("brokerage");
		Double bovespaTax = (Double) op.get("bovespaTax");
		Double totalGeral = total + brokerage + bovespaTax;
		Double averagePrice = totalGeral / amount;
		Double currentPrice = op.get("quotePrice") != null ? (Double) op
				.get("quotePrice") : averagePrice;
		Double variation = (currentPrice / averagePrice * 100) - 100;
		// X|ID|DATA|ATIVO|PREÇO|QTDE|TOTAL|CORRET|BOVESPA|TGERAL|MEDIO|GANHO|GANHO%|DESC
		item.setText(new String[] { "", op.get("id").toString(),
				FormatUtil.formatDate(op.get("operationDate")), stock,
				FormatUtil.toReal(price), amount.toString(),
				FormatUtil.toReal(total),
				FormatUtil.toReal(brokerage),
				FormatUtil.toReal(bovespaTax),
				FormatUtil.toReal(totalGeral),
				FormatUtil.toReal(averagePrice),
				FormatUtil.toReal(currentPrice),
				FormatUtil.toPercentage(variation),
				op.get("description").toString() });
		item.setImage(SWTResourceManager.getImage(this.getClass(),
				UIImages.DELETE_ICON_16));
		Color c = currentPrice < averagePrice ? red : green;
		item.setForeground(11, c);
		item.setFont(11, SWTResourceManager.getBoldFont(item.getFont()));
		item.setForeground(12, c);
		item.setFont(12, SWTResourceManager.getBoldFont(item.getFont()));
	}

	private void callSelloperationMapToRowString(Map<String, Object> op,
			TableItem item) {
		// X|ID|DATA|ATIVO|PREÇO|QTDE|TOTAL|CORRET|BOVESPA|TGERAL|MEDIO|COT|LUCR%|DESC
		String stock = op.get("stock") != null ? op.get("stock").toString()
				: "";
		Double price = op.get("price") != null ? (Double) op.get("price")
				: null;
		Integer amount = op.get("amount") != null ? (Integer) op.get("amount")
				: 0;
		Double total = null;
		if (price != null && amount != null) {
			total = price * amount;
		}
		Double brokerage = (Double) op.get("brokerage");
		Double bovespaTax = (Double) op.get("bovespaTax");
		Double totalGeral = total - brokerage - bovespaTax;
		Double averagePrice = totalGeral / amount;
		Double currentPrice = op.get("quotePrice") != null ? (Double) op
				.get("quotePrice") : averagePrice;
		// Double variation = (currentPrice/averagePrice * 100) - 100;
		Double variation = ((averagePrice - currentPrice) / averagePrice) * 100;
		// X|ID|DATA|ATIVO|PREÇO|QTDE|TOTAL|CORRET|BOVESPA|TGERAL|MEDIO|GANHO|GANHO%|DESC
		item.setText(new String[] { "", op.get("id").toString(),
				FormatUtil.formatDate(op.get("operationDate")), stock,
				FormatUtil.toReal(price), amount.toString(),
				FormatUtil.toReal(total),
				FormatUtil.toReal(brokerage),
				FormatUtil.toReal(bovespaTax),
				FormatUtil.toReal(totalGeral),
				FormatUtil.toReal(averagePrice),
				FormatUtil.toReal(currentPrice),
				FormatUtil.toPercentage(variation),
				op.get("description").toString() });
		item.setImage(SWTResourceManager.getImage(this.getClass(),
				UIImages.DELETE_ICON_16));
		Color c = currentPrice > averagePrice ? red : green;
		item.setForeground(11, c);
		item.setFont(11, SWTResourceManager.getBoldFont(item.getFont()));
		item.setForeground(12, c);
		item.setFont(12, SWTResourceManager.getBoldFont(item.getFont()));

	}

	public void custodyToRowString(final CustodyView custody, TableItem item) {
		Double averagePrice = custody.getAveragePrice();
		Integer amount = custody.getAmount();
		Integer amountBloqued = custody.getAmountBlocked() != null ? custody
				.getAmountBlocked() : 0;
		Double currentPrice = custody.getCurrentPrice() != null ? custody
				.getCurrentPrice() : averagePrice;
		Double variation = (currentPrice / averagePrice * 100) - 100;
		item.setText(new String[] { "", custody.getStock(), amount.toString(),
				amountBloqued.toString(),
				FormatUtil.toReal(averagePrice),
				FormatUtil.toReal(averagePrice * amount),
				FormatUtil.toReal(currentPrice),
				FormatUtil.toPercentage(variation) });
		Color c = currentPrice < averagePrice ? red : green;
		Image i = currentPrice < averagePrice ? SWTResourceManager.getImage(
				this.getClass(), UIImages.DOWN_ARROW_ICON_16)
				: SWTResourceManager.getImage(this.getClass(),
						UIImages.UP_ARROW_ICON_16);

		item.setImage(i);
		item.setForeground(6, c);
		item.setFont(6, SWTResourceManager.getBoldFont(item.getFont()));
		item.setForeground(7, c);
		item.setFont(7, SWTResourceManager.getBoldFont(item.getFont()));

		item.addListener(SWT.MouseDoubleClick, new Listener() {
			public void handleEvent(Event event) {
				logger.info("Double click over: " + custody.getStock());
			}
		});
	}

	public void displayFloatingBoxWithOperationDetail(Event event) {

		TableItem item = (TableItem) event.item;
		logger.info("Selected Table Item ID: " + item.getText());
		Long id = new Long(item.getText());

		Operation operation;
		try {
			operation = getOperationFacade().findById(id);
			logger.info("Found the operation ID:" + operation.getId());

		} catch (UIException e) {
			logger.error(e);
		}
	}

	public void displayToolTipWithOperationDetail(Event event,
			Listener labelListener, Shell tip, Label label, Table table,
			Shell shell, Display display) {

		TableItem item = table.getItem(new Point(event.x, event.y));
		if (item != null) {
			if (tip != null && !tip.isDisposed())
				tip.dispose();
			tip = new Shell(shell, SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL);
			tip
					.setBackground(display
							.getSystemColor(SWT.COLOR_INFO_BACKGROUND));
			FillLayout layout = new FillLayout();
			layout.marginWidth = 2;
			tip.setLayout(layout);
			label = new Label(tip, SWT.NONE);
			label.setForeground(display
					.getSystemColor(SWT.COLOR_INFO_FOREGROUND));
			label.setBackground(display
					.getSystemColor(SWT.COLOR_INFO_BACKGROUND));
			label.setData("_TABLEITEM", item);
			label.setText(item.getText());
			label.setImage(display.getSystemImage(SWT.ICON_QUESTION));
			label.addListener(SWT.MouseExit, labelListener);

			label.addListener(SWT.MouseDown, labelListener);
			Point size = tip.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			Rectangle rect = item.getBounds(0);
			Point pt = table.toDisplay(rect.x, rect.y);

			int newX = pt.x; // getOperationUI().getBounds().x+getOperationUI().getBounds().width;
			int newY = pt.y; // getOperationUI().getBounds().y+getOperationUI().getBounds().height-size.y;

			tip.setBounds(newX, newY, size.x, size.y);
			tip.setVisible(true);
		}

	}

	public void openDialogToAttachTheFileForOperation() {

		FileDialog dialog = new FileDialog(getOperationUI().getShell(),
				SWT.OPEN);
		// dialog.setFilterNames (new String [] {"Imagens", "Documentos(Doc)"});
		// dialog.setFilterExtensions (new String [] {"*.jpg", "*.doc"});
		// //Windows wild cards
		dialog.setFilterPath(UIDefault.DEFAULT_PATH_ATTACH_FILE); // Windows
		// path
		// dialog.setFileName ("fred.bat");

		String fileSelected = dialog.open();
		if (fileSelected != null) {
			logger.info("File selected " + fileSelected);
			UIDefault.DEFAULT_PATH_ATTACH_FILE = fileSelected;

			// getOperationUI().getPathFile().setText(fileSelected);

		}

	}

	public void sortingTableByDate(final Table table, TableColumn column1) {
		TableItem[] items = table.getItems();
		Collator collator = Collator.getInstance(Locale.getDefault());
		// TableColumn column = (TableColumn)e.widget;
		int index = 0; // column == column1 ? 0 : 1;

		for (int i = 1; i < items.length; i++) {
			String value1 = items[i].getText(index);
			for (int j = 0; j < i; j++) {
				String value2 = items[j].getText(index);
				if (collator.compare(value1, value2) < 0) {
					String[] values = { items[i].getText(0),
							items[i].getText(1) };
					items[i].dispose();
					TableItem item = new TableItem(table, SWT.NONE, j);
					item.setText(values);
					items = table.getItems();
					break;
				}
			}
		}
		table.setSortColumn(column1);
		table.setSortDirection(SWT.UP);

	}

	public void updateOperationInAddOperationComposite(TableItem item){
		
		logger.info("Abrindo operação para ser editada");
		
		try {
			
			if (item==null || (item!=null && item.getData()==null)) {
				throw new UIException(5017);
			}
			
			Object data = item.getData();
			
			if(!(data instanceof OperationView)){
				throw new UIException(5018);
			}
			
			OperationView opView=(OperationView)data;
			
			getTwOperationUI().getAddOperationComposite().editMode(opView);
				
			//refresh();
		
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public void deleteOperation(int index, TableItem item, int col) {
		
		logger.info("Removendo operação selecionada");
		
		try {
			if (col != 0){
				return;
			}
			String idStr = item.getText(2);
			logger.info("Removendo Operação com ID["+idStr+"] " + idStr);
			if (!StringCheck.isEmpty(idStr) && StringCheck.isNumeric(idStr)) {
				Operation operation = new Operation();
				operation.setId(new Long(idStr));
				getOperationFacade().removeOperation(operation);
				refresh();

			} else {
				throw new Exception("O ID não é um numero, não é possível remover");
			}

		} catch (Exception e) {
			logger.error("Não foi possível remover a operação",e);
		}
	}

	public void displayAccountSelected() {
				
		getTwOperationUI().getAddOperationComposite().cancelButton();//Seta default nos campos
		
		initializeCustodyTableOnOperationUI();
		CTabFolder tabFolder = getOperationUI().getOperationTabFolder();
		CTabItem tabItem = tabFolder.getItem(tabFolder.getSelectionIndex());
		updateOperationsTable(tabItem);
	}

	public void refresh() {
		logger.debug("Atualizando componentes da OperationUI");
		displayAccountSelected();
	}

	public void updateOperationsTable(CTabItem tabItem) {
		DateIntervalComposite interval = (DateIntervalComposite) tabItem
				.getData("dateInterval");
		if (interval != null) {
			Timestamp startDate = interval.getStartDate();
			Timestamp endDate = interval.getEndDate();
			OperationType operationType = (OperationType) tabItem.getData("operationType");
			
			if (OperationType.BUY.equals(operationType)) {
				initializeBuyOperations(startDate, endDate);
			} else if (OperationType.SELL.equals(operationType)) {
				initializeSellOperations(startDate, endDate);
			} else if (OperationType.SELL_CALL_OPTION.equals(operationType)) {
				initializeCallSellOperations(startDate, endDate);
			} else {
				initializeAllOperations(startDate, endDate);
			}
		}
	}

	public PopulateOperationData getPopulateOperationData() {
		return populateOperationData;
	}

	
	public void doCollectData() {
		
	}

	

}
