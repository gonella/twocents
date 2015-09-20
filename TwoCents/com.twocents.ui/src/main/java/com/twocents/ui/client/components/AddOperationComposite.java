package com.twocents.ui.client.components;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.nebula.widgets.datechooser.DateChooserCombo;
import org.eclipse.nebula.widgets.datechooser.DateChooserTheme;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import swing2swt.layout.BorderLayout;

import com.cloudgarden.resource.SWTResourceManager;
import com.twocents.bovespa.stock.ListStockInBovespa;
import com.twocents.bovespa.stock.StockSuggestion;
import com.twocents.core.search.LuceneSearch;
import com.twocents.core.util.DateUtil;
import com.twocents.core.util.FormatUtil;
import com.twocents.core.util.StringCheck;
import com.twocents.model.OperationType;
import com.twocents.model.OperationView;
import com.twocents.ui.client.TwOperationUI;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.resources.UIImages;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.FocusAdapter;

public class AddOperationComposite extends Composite {

	private final Color COR_PADRAO_PARA_VENDER = com.swtdesigner.SWTResourceManager
			.getColor(SWT.COLOR_RED);
	private final Color COR_PADRAO_PARA_COMPRAR = com.swtdesigner.SWTResourceManager
			.getColor(SWT.COLOR_DARK_GREEN);
	private Color COR_PADRAO_SELECIONADA = COR_PADRAO_PARA_COMPRAR;

	private static final int LIMIT_TEXT = 10;

	{
		// Register as a resource user - SWTResourceManager will
		// handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	private Logger logger = Logger.getLogger(AddOperationComposite.class);
	private Button checkboxVender;
	private Text preco;
	// private Text valorFinanceiro;
	private Label valorFinanceiro;
	private Text quantidade;
	private Text ativo;
	private Group group6;
	private Group groupPreco;
	private Group groupQuantidade;
	private Group groupAtivo;
	private Button checkboxComprar;
	private Composite compositeAddOperation;
	private final TwOperationUI operationUI;
	private DateChooserCombo dateOperation;
	private CLabel lblComprar;
	private CLabel bVender;
	private CLabel lblVender;
	private CLabel bComprar;
	private Composite composite_12;
	private Composite composite_13;
	private Composite composite_14;
	private Composite composite_15;
	private Composite composite_16;
	private Composite composite_17;
	private Composite composite_18;
	private Composite composite_19;
	private Composite composite_20;
	private Composite composite;
	private Composite composite_1;
	private Composite composite_4;
	private CLabel registrar;
	private Composite composite_5;
	private Composite composite_2;
	private Label btnCancelar;
	private boolean isEditMode = false;
	private OperationView opView;

	private boolean checkboxComprarSelected = true;
	private boolean checkboxVenderSelected = false;
	private Map<String, StockSuggestion> mapBovespaStocks;
	private CLabel lblNewLabel;
	private Label dateSelected;
	private TwDateTime twDateTime;

	public TwOperationUI getOperationUI() {
		return operationUI;
	}

	public AddOperationComposite(Composite arg0, int arg1,
			TwOperationUI operationUI) {
		super(arg0, arg1);
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		// setBackground(SWTResourceManager.getColor(223,240,255));

		this.operationUI = operationUI;
		setLayout(new BorderLayout(0, 0));

		int widthItem = 10;
		int heightItem = 10;

		Image IMAGE_BLUE = new Image(getDisplay(), widthItem, heightItem);
		Color color = getDisplay().getSystemColor(SWT.COLOR_BLUE);
		GC gc = new GC(IMAGE_BLUE);
		gc.setBackground(color);
		gc.fillRectangle(IMAGE_BLUE.getBounds());
		gc.dispose();

		Image IMAGE_RED = new Image(getDisplay(), widthItem, heightItem);
		color = getDisplay().getSystemColor(SWT.COLOR_RED);
		gc = new GC(IMAGE_RED);
		gc.setBackground(color);
		gc.fillRectangle(IMAGE_RED.getBounds());
		gc.dispose();

		Image IMAGE_GREEN = new Image(getDisplay(), widthItem, heightItem);
		color = getDisplay().getSystemColor(SWT.COLOR_GREEN);
		gc = new GC(IMAGE_GREEN);
		gc.setBackground(color);
		gc.fillRectangle(IMAGE_GREEN.getBounds());
		gc.dispose();

		createComponents();
		
		
		/*groupAtivo.setTabList(new Control[]{ativo});
		groupQuantidade.setTabList(new Control[]{quantidade});
		groupPreco.setTabList(new Control[]{preco});
		
		composite_14.setTabList(new Control[]{groupAtivo,groupQuantidade,groupPreco});
	    composite_5.setTabList(new Control[]{getRegistrar()});
	   
	    setTabList(new Control[]{composite_14,composite_5});
	   */
	}

	public void editMode(OperationView opView) {

		setOpView(opView);
		setEditMode(true);

		// http://www.tayloredmktg.com/rgb/

		// Color color = com.swtdesigner.SWTResourceManager.getColor(250, 128,
		// 114); //vermelho claro
		// Color color =
		// com.swtdesigner.SWTResourceManager.getColor(COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT);

		/*
		 * setBackground(color); getAtivo().setBackground(color);
		 * getQuantidade().setBackground(color);
		 * getPreco().setBackground(color);
		 * getDateOperation().setBackground(color);
		 */

		OperationType operationType = OperationType.toType(opView
				.getOperationType());
		if (OperationType.BUY.equals(operationType)) {
			clickComprar();
		} else if (OperationType.SELL.equals(operationType)) {
			clickVender();
		}

		getRegistrar()
				.setImage(
						com.swtdesigner.SWTResourceManager
								.getImage(
										AddOperationComposite.class,
										UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_BOTAO_EDITAR01_PNG));
		getBtnCancelar().setVisible(true);

		getAtivo().setText(opView.getStockCode());
		getQuantidade().setText(opView.getAmount().toString());
		//getDateOperation().setValue(opView.getOperationDate());

		getPreco().setText(FormatUtil.toReal(opView.getPrice()));

		Double valorFinanceiro = (opView.getAmount() * opView.getPrice());
		getValorFinanceiro().setText(FormatUtil.toReal(valorFinanceiro));

	}

	public void registerMode() {

		// Seta a operação selecionada para null, ou seja, nenhuma operacao para
		// editar
		setOpView(null);

		setBackground(null);
		getAtivo().setBackground(null);
		getQuantidade().setBackground(null);
		getPreco().setBackground(null);
		//getDateOperation().setBackground(null);

		if (isCheckboxComprarSelected()) {
			getRegistrar()
					.setImage(
							com.swtdesigner.SWTResourceManager
									.getImage(
											AddOperationComposite.class,
											UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_BOTAO_COMPRAR01_PNG));
		} else if (isCheckboxVenderSelected()) {
			getRegistrar()
					.setImage(
							com.swtdesigner.SWTResourceManager
									.getImage(
											AddOperationComposite.class,
											UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_BOTAO_VENDER01_PNG));
		} else {
			return;
		}

		getBtnCancelar().setVisible(false);
		setEditMode(false);
		// getRegistrar().setText(_COMPRAR);
		// mainPanel.getAddOperationComposite().setBackground(com.swtdesigner.SWTResourceManager.getColor(SWT.COLOR_GRAY));
		// new OperationTabAction(mainPanel).updateOperation(index, item, col);
	}

	public void clickVender() {
		COR_PADRAO_SELECIONADA = COR_PADRAO_PARA_VENDER;

		bComprar.setImage(com.swtdesigner.SWTResourceManager.getImage(
				AddOperationComposite.class, UIImages.CHECKBOX_EMPTY));
		bVender.setImage(com.swtdesigner.SWTResourceManager
				.getImage(AddOperationComposite.class,
						UIImages.CHECKBOX_CHECKED_RED));
		setCheckboxComprarSelected(false);
		setCheckboxVenderSelected(true);

		getAtivo().setForeground(COR_PADRAO_SELECIONADA);
		getQuantidade().setForeground(COR_PADRAO_SELECIONADA);
		getPreco().setForeground(COR_PADRAO_SELECIONADA);
		getValorFinanceiro().setForeground(COR_PADRAO_SELECIONADA);

		getRegistrar()
				.setImage(
						com.swtdesigner.SWTResourceManager
								.getImage(
										AddOperationComposite.class,
										UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_BOTAO_VENDER01_PNG));

		// getRegistrar().setText(_VENDER);
	}

	public void clickComprar() {

		COR_PADRAO_SELECIONADA = COR_PADRAO_PARA_COMPRAR;

		bComprar.setImage(com.swtdesigner.SWTResourceManager.getImage(
				AddOperationComposite.class,
				UIImages.CHECKBOX_CHECKED_GREEN));
		bVender.setImage(com.swtdesigner.SWTResourceManager.getImage(
				AddOperationComposite.class, UIImages.CHECKBOX_EMPTY));
		setCheckboxComprarSelected(true);
		setCheckboxVenderSelected(false);

		getAtivo().setForeground(COR_PADRAO_SELECIONADA);
		getQuantidade().setForeground(COR_PADRAO_SELECIONADA);
		getPreco().setForeground(COR_PADRAO_SELECIONADA);
		getValorFinanceiro().setForeground(COR_PADRAO_SELECIONADA);

		getRegistrar()
				.setImage(
						com.swtdesigner.SWTResourceManager
								.getImage(
										AddOperationComposite.class,
										UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_BOTAO_COMPRAR01_PNG));

		// getOperationUI().getAddOperationComposite()

	}

	protected void createComponents() {

		// setBackground(SWTResourceManager.getColor(240,240,240));

		// Green
		// setBackground(SWTResourceManager.getColor(221,255,187));

		// Red
		// setBackground(SWTResourceManager.getColor(255,210,210));

		FocusListener listener = new FocusListener() {
			public void focusGained(FocusEvent event) {

			}

			public void focusLost(FocusEvent event) {

				Double preco = 0.0;
				Integer quant = 0;

				boolean fillAmount = !StringCheck.isEmpty(getQuantidade()
						.getText());
				if (fillAmount) {
					quant = new Integer(getQuantidade().getText().toString());
				}
				boolean fillPrice = !StringCheck.isEmpty(getPreco().getText());
				if (fillPrice) {
					try {
						preco = new Double(FormatUtil.fromReal(getPreco().getText().toString()));
					}
					catch (ParseException e) {
						logger.error(e);
						return;
					}
				}

				if (fillAmount && fillPrice) {
					Double value = preco * quant;
					getValorFinanceiro().setText(FormatUtil.toReal(value));
				}

			}
		};

		this.setSize(546, 203);
		{
			compositeAddOperation = new Composite(this, SWT.NONE);
			FillLayout fl_compositeAddOperation = new FillLayout(
					org.eclipse.swt.SWT.HORIZONTAL);
			compositeAddOperation.setLayout(fl_compositeAddOperation);
			{
				{
					{
						{
							/*
							 * checkboxComprar = new Button(group2, SWT.RADIO |
							 * SWT.CENTER); checkboxComprar.setText("Comprar");
							 * checkboxComprar
							 * .setFont(SWTResourceManager.getFont("Verdana",
							 * 12, 1, false, false));
							 * checkboxComprar.setFocus();
							 */
						}
						{
							/*
							 * checkboxVender = new Button(group2, SWT.RADIO |
							 * SWT.CENTER); checkboxVender.setText("Vender");
							 * checkboxVender
							 * .setFont(SWTResourceManager.getFont("Verdana",
							 * 12, 1, false, false));
							 */
						}
						// bComprar.setImage(com.swtdesigner.SWTResourceManager.getImage(AddOperationComposite.class,UIImageUtil.CHECKBOX_CHECKED_GREEN));
						{
							{
								GridData dataOperationLData = new GridData();

							}
						}
					}
				}
				{
					{
						{
							{
								{
									{
										{
											{
												{
													{
														{
															{
																{

																}
															}
														}
													}
													{
														{

															composite_12 = new Composite(
																	compositeAddOperation,
																	SWT.NONE);
															composite_12
																	.setLayout(new FillLayout(
																			SWT.VERTICAL));

															composite_13 = new Composite(
																	composite_12,
																	SWT.NONE);
															composite_13
																	.setLayout(new FillLayout(
																			SWT.HORIZONTAL));

															composite_17 = new Composite(
																	composite_13,
																	SWT.NONE);
															composite_17
																	.setLayout(new GridLayout(
																			4,
																			false));

															bComprar = new CLabel(
																	composite_17,
																	SWT.NONE);
															bComprar
																	.addMouseListener(new MouseAdapter() {

																		public void mouseDown(
																				MouseEvent arg0) {
																			clickComprar();
																		}

																	});
															bComprar
																	.setText("");
															bComprar
																	.setImage(com.swtdesigner.SWTResourceManager
																			.getImage(
																					AddOperationComposite.class,
																					UIImages.CHECKBOX_CHECKED_GREEN));

															{
																lblComprar = new CLabel(
																		composite_17,
																		SWT.NONE);
																lblComprar.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
																lblComprar
																		.setFont(com.swtdesigner.SWTResourceManager
																				.getFont(
																						"Segoe UI",
																						12,
																						SWT.BOLD));
																lblComprar
																		.setText("Comprar");
															}
															bVender = new CLabel(
																	composite_17,
																	SWT.NONE);
															bVender.setText("");
															bVender
																	.setImage(com.swtdesigner.SWTResourceManager
																			.getImage(
																					AddOperationComposite.class,
																					UIImages.CHECKBOX_EMPTY));
															bVender
																	.addMouseListener(new MouseAdapter() {

																		public void mouseDown(
																				MouseEvent arg0) {

																			clickVender();
																		}

																	});
															{
																lblVender = new CLabel(
																		composite_17,
																		SWT.NONE);
																lblVender.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
																lblVender
																		.setFont(com.swtdesigner.SWTResourceManager
																				.getFont(
																						"Segoe UI",
																						12,
																						SWT.BOLD));
																lblVender
																		.setText("Vender");
															}

															composite_18 = new Composite(
																	composite_13,
																	SWT.NONE);
															composite_18
																	.setLayout(new FillLayout(
																			SWT.HORIZONTAL));

															composite = new Composite(
																	composite_18,
																	SWT.NONE);
															composite
																	.setLayout(new FillLayout(
																			SWT.HORIZONTAL));

															composite_1 = new Composite(
																	composite_18,
																	SWT.NONE);
															composite_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
															
															setDateSelected(new Label(composite_1, SWT.RIGHT));
															getDateSelected().setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
															getDateSelected().setFont(org.eclipse.wb.swt.SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
															getDateSelected().setAlignment(SWT.CENTER);
															getDateSelected().setText(FormatUtil.formatDate(new Date()));
															
															lblNewLabel = new CLabel(composite_1, SWT.NONE);
															lblNewLabel.addMouseListener(new MouseAdapter() {

																@Override
																public void mouseDown(MouseEvent arg0) {
																	
																	Point location = lblNewLabel.getShell().getDisplay().getCursorLocation();
																	int x=location.x;
																	int y=location.y;
																	
																	if(twDateTime!=null && !twDateTime.isDisposed()){
																		
																		twDateTime.close();
																	}
																	
																	twDateTime = new TwDateTime(lblNewLabel.getShell(),x,y,getDateSelected()); 
																	
																	
																	
																}
															});
															lblNewLabel.setImage(org.eclipse.wb.swt.SWTResourceManager.getImage(AddOperationComposite.class, "/com/twocents/ui/resources/images/DateIcon01_40x40.png"));
															lblNewLabel.setText("");
															{
																/*setDateOperation(new DateChooserCombo(
																		composite_1,
																		SWT.BORDER));
																getDateOperation()
																		.setFont(
																				com.swtdesigner.SWTResourceManager
																						.getFont(
																								"Segoe UI Light",
																								12,
																								SWT.NORMAL));
																getDateOperation()
																		.setValue(
																				new Date());
*/
															}

															composite_14 = new Composite(
																	composite_12,
																	SWT.NONE);
															composite_14
																	.setLayout(new FillLayout(
																			SWT.HORIZONTAL));
															groupAtivo = new Group(
																	composite_14,
																	SWT.NONE);
															//groupAtivo.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
															FillLayout group3Layout = new FillLayout(
																	org.eclipse.swt.SWT.HORIZONTAL);
															groupAtivo
																	.setLayout(group3Layout);
															groupAtivo
																	.setText("Ativo");
															ativo = new Text(
																	groupAtivo,
																	SWT.NONE);
															ativo.addTraverseListener(new TraverseListener() {
																public void keyTraversed(TraverseEvent arg0) {
																	if(arg0.keyCode == SWT.TAB) { 
																		logger.debug("Indo para a Quantidade. :"+quantidade.forceFocus());															            
															         } 
																}
															});
															
															ativo
																	.setForeground(COR_PADRAO_PARA_COMPRAR);
															ativo
																	.setFont(SWTResourceManager
																			.getFont(
																					"Segoe UI",
																					16,
																					1,
																					false,
																					false));
															ativo
																	.setOrientation(SWT.HORIZONTAL);
															ativo
																	.setTextLimit(10);
															groupAtivo.setTabList(new Control[]{ativo});

															createSugestionBox();

															groupQuantidade = new Group(
																	composite_14,
																	SWT.NONE);
															FillLayout group4Layout = new FillLayout(
																	org.eclipse.swt.SWT.HORIZONTAL);
															groupQuantidade
																	.setLayout(group4Layout);
															groupQuantidade
																	.setText("Quantidade");
															quantidade = new Text(
																	groupQuantidade,
																	SWT.NONE);
															quantidade.addTraverseListener(new TraverseListener() {
																public void keyTraversed(TraverseEvent arg0) {
																	if(arg0.keyCode == SWT.TAB) { 
																		logger.debug("Indo para o preço. :"+preco.forceFocus());															            
															         } 
																}
															});
															quantidade
																	.setForeground(COR_PADRAO_PARA_COMPRAR);
															quantidade
																	.setFont(SWTResourceManager
																			.getFont(
																					"Segoe UI",
																					16,
																					1,
																					false,
																					false));
															quantidade
																	.setTextLimit(LIMIT_TEXT);
															groupQuantidade.setTabList(new Control[]{quantidade});
															groupPreco = new Group(
																	composite_14,
																	SWT.NONE);
															FillLayout group5Layout = new FillLayout(
																	org.eclipse.swt.SWT.HORIZONTAL);
															groupPreco
																	.setLayout(group5Layout);
															groupPreco
																	.setText("Preço");
															preco = new Text(
																	groupPreco,
																	SWT.NONE);
															preco.addTraverseListener(new TraverseListener() {
																public void keyTraversed(TraverseEvent arg0) {
																	if(arg0.keyCode == SWT.TAB) { 
																		logger.debug("Indo para o Botão Registrar. :"+registrar.forceFocus());															            
															         } 
																}
															});
															preco
																	.setForeground(COR_PADRAO_PARA_COMPRAR);
															preco
																	.setFont(SWTResourceManager
																			.getFont(
																					"Segoe UI",
																					16,
																					1,
																					false,
																					false));
															preco
																	.setTextLimit(LIMIT_TEXT);
															preco
																	.addFocusListener(listener);

															// Ao pressionar o
															// ENTER vai para o
															// proximo campo
															preco
																	.addKeyListener(new KeyAdapter() {
																		public void keyPressed(
																				KeyEvent event) {
																			switch (event.keyCode) {
																			case SWT.CR:

																				// registrar.setFocus();
																				// break;
																			}
																		}
																	});

															preco
																	.addListener(
																			SWT.Verify,
																			new Listener() {
																				public void handleEvent(
																						Event e) {
																					String string = e.text;
																					char[] chars = new char[string
																							.length()];

																					string
																							.getChars(
																									0,
																									chars.length,
																									chars,
																									0);
																					for (int i = 0; i < chars.length; i++) {
																						if (!('0' <= chars[i] && chars[i] <= '9')
																								&& !String
																										.valueOf(
																												(chars[i]))
																										.equals(
																												",")
																						// &&
																						// !String.valueOf((chars[i])).equals(".")

																						) {
																							e.doit = false;
																							return;
																						}
																					}
																				}
																			});
															quantidade
																	.addFocusListener(listener);

															// Ao pressionar o
															// ENTER vai para o
															// proximo campo
															quantidade
																	.addKeyListener(new KeyAdapter() {
																		public void keyPressed(
																				KeyEvent event) {
																			switch (event.keyCode) {
																			case SWT.CR:

																				preco
																						.setFocus();
																				break;
																			}
																		}
																	});

															quantidade
																	.addListener(
																			SWT.Verify,
																			new Listener() {
																				public void handleEvent(
																						Event e) {
																					String string = e.text;
																					char[] chars = new char[string
																							.length()];
																					string
																							.getChars(
																									0,
																									chars.length,
																									chars,
																									0);
																					for (int i = 0; i < chars.length; i++) {
																						if (!('0' <= chars[i] && chars[i] <= '9')) {
																							e.doit = false;
																							return;
																						}
																					}
																				}
																			});

															// RoundedBox.addRoundedBoxOnResize(ativo,
															// 15,
															// RoundedBox.EDGES_ALL);

															// Ao pressionar o
															// ENTER vai para o
															// proximo campo
															ativo
																	.addKeyListener(new KeyAdapter() {
																		public void keyPressed(
																				KeyEvent event) {
																			switch (event.keyCode) {
																			case SWT.CR:

																				quantidade
																						.setFocus();
																				break;
																			}
																		}
																	});
															ativo
																	.addVerifyListener(new VerifyListener() {
																		public void verifyText(
																				VerifyEvent e) {
																			if (e.text
																					.startsWith("1")) {
																				e.doit = false;
																			} else {
																				e.text = e.text
																						.toUpperCase();
																			}
																		}
																	});
															ativo.setFocus();

															composite_15 = new Composite(
																	composite_12,
																	SWT.NONE);
															composite_15
																	.setLayout(new FillLayout(
																			SWT.HORIZONTAL));
															{
																group6 = new Group(
																		composite_15,
																		SWT.NONE);
																FillLayout group6Layout = new FillLayout(
																		org.eclipse.swt.SWT.HORIZONTAL);
																group6
																		.setLayout(group6Layout);
																group6
																		.setText("Valor Financeiro");
																{
																	valorFinanceiro = new Label(
																			group6,
																			SWT.NONE);
																	valorFinanceiro
																			.setForeground(COR_PADRAO_PARA_COMPRAR);
																	valorFinanceiro
																			.setFont(SWTResourceManager
																					.getFont(
																							"Segoe UI",
																							16,
																							0,
																							false,
																							false));
																	// valorFinanceiro.setEditable(false);
																	// valorFinanceiro.setEnabled(false);
																	// valorFinanceiro.setBackground(SWTResourceManager.getColor(240,
																	// 240,
																	// 240));

																}
															}

															composite_16 = new Composite(
																	composite_12,
																	SWT.NONE);
															composite_16
																	.setLayout(new FillLayout(
																			SWT.HORIZONTAL));

															composite_19 = new Composite(
																	composite_16,
																	SWT.NONE);
															composite_19
																	.setLayout(new FillLayout(
																			SWT.HORIZONTAL));

															/*
															 * registrar.addSelectionListener
															 * (new
															 * SelectionAdapter
															 * () { public void
															 * widgetSelected
															 * (final
															 * SelectionEvent
															 * arg0) {
															 * registrarButton
															 * (); } });
															 */

															composite_20 = new Composite(
																	composite_16,
																	SWT.NONE);
															composite_20
																	.setLayout(new FillLayout(
																			SWT.HORIZONTAL));

															composite_4 = new Composite(
																	composite_20,
																	SWT.NONE);
															composite_4
																	.setLayout(new BorderLayout(
																			0,
																			0));

															composite_5 = new Composite(
																	composite_4,
																	SWT.NONE);
															composite_5
																	.setLayoutData(BorderLayout.EAST);
															FillLayout fl_composite_5 = new FillLayout(
																	SWT.HORIZONTAL);
															fl_composite_5.marginWidth = 5;
															composite_5
																	.setLayout(fl_composite_5);

															setRegistrar(new CLabel(
																	composite_5,
																	SWT.NONE));
															getRegistrar()
																	.setImage(
																			com.swtdesigner.SWTResourceManager
																					.getImage(
																							AddOperationComposite.class,
																							UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_BOTAO_COMPRAR01_PNG));

															composite_2 = new Composite(
																	composite_4,
																	SWT.NONE);
															composite_2
																	.setLayoutData(BorderLayout.CENTER);
															composite_2
																	.setLayout(new BorderLayout(
																			0,
																			0));

															setBtnCancelar(new Label(
																	composite_2,
																	SWT.NONE));
															getBtnCancelar()
																	.setLayoutData(
																			BorderLayout.EAST);
															getBtnCancelar()
																	.setImage(
																			com.swtdesigner.SWTResourceManager
																					.getImage(
																							AddOperationComposite.class,
																							UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_BOTAO_CANCELAR01_PNG));
															getBtnCancelar()
																	.setVisible(
																			false);
															getBtnCancelar()
																	.addMouseListener(
																			new MouseAdapter() {

																				public void mouseDown(
																						MouseEvent arg0) {
																					cancelButton();
																				}

																			});

															getRegistrar()
																	.addMouseListener(
																			new MouseAdapter() {

																				public void mouseDown(
																						MouseEvent arg0) {
																					registerAndUpdateButton();
																				}

																			});

														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	  
	    // Blue
		// setBackground(SWTResourceManager.getColor(223,240,255));

	}

	
	public void createSugestionBox() {

		/*//Preparando o suggestion box
		setMapBovespaStocks(null);
		try {
			setMapBovespaStocks(ContextHolderUI.getEnvironmentAction().getBovespaStocks().getList());
		} catch (UIException e) {
			logger.error(e.getClass().getName(),e);
		}*/
		
		// Recupera todas as ações cadastradas da bovespa armazenada em planilha
		ListStockInBovespa bovespaStocks = new ListStockInBovespa();
		setMapBovespaStocks(bovespaStocks.getList());
		
		
		// Indexa os ativos para melhor procura
		final LuceneSearch luceneSearch = LuceneSearch.getInstance(getMapBovespaStocks());

		final Shell popupShell = new Shell(getDisplay(), SWT.ON_TOP);
		popupShell.setLayout(new FillLayout());
		final Table table = new Table(popupShell, SWT.SINGLE);

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent event) {

				if (popupShell.isVisible() && table.getSelectionIndex() != -1) {
					ativo.setText(table.getSelection()[0].getText().substring(
							0, 5));
					popupShell.setVisible(false);
					getQuantidade().setFocus();
				}

			}

		});

		ativo.addListener(SWT.KeyDown, new Listener() {
			public void handleEvent(Event event) {
				switch (event.keyCode) {
				case SWT.ARROW_DOWN:
					int index = (table.getSelectionIndex() + 1)
							% table.getItemCount();
					table.setSelection(index);
					event.doit = false;
					break;
				case SWT.ARROW_UP:
					index = table.getSelectionIndex() - 1;
					if (index < 0)
						index = table.getItemCount() - 1;
					table.setSelection(index);
					event.doit = false;
					break;
				case SWT.CR:
					if (popupShell.isVisible()
							&& table.getSelectionIndex() != -1) {

						StockSuggestion stockSuggestion = (StockSuggestion) table
								.getSelection()[0].getData();
						ativo.setText(stockSuggestion.getCode());

						popupShell.setVisible(false);
					}
					break;
				case SWT.ESC:
					popupShell.setVisible(false);
					break;
				}
			}
		});
		ativo.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				String stringTosearch = ativo.getText();
				
				if (stringTosearch!=null && (StringCheck.isEmpty(stringTosearch.trim()))) {
					popupShell.setVisible(false);
				} else {

					String[] search = luceneSearch
							.searchAllOccurrences(stringTosearch);

					if (search == null
							|| (search != null && search.length == 0)) {
						popupShell.setVisible(false);
						return;
					}

					table.removeAll();

					TableItem tableItem = null;

					int sizeOfName = 0;

					for (int i = 0; i < search.length; i++) {
						tableItem = new TableItem(table, SWT.NONE);
						tableItem.setForeground(COR_PADRAO_SELECIONADA);

						StockSuggestion stockSuggestion = getMapBovespaStocks().get(search[i]);
						String name = stockSuggestion.toString();
						tableItem.setText(name);
						tableItem.setData(stockSuggestion);
						if (name.length() >= sizeOfName) {
							sizeOfName = name.length();
						}
					}

					Rectangle textBoundsAddComposite = getOperationUI()
							.getDisplay().map(compositeAddOperation.getShell(),
									null, compositeAddOperation.getBounds());

					Rectangle textBoundsAtivo = getOperationUI()
							.getDisplay()
							.map(groupAtivo.getShell(), null, ativo.getBounds());
					/*
					 * Rectangle textBoundsQuantidade =
					 * getOperationUI().getDisplay
					 * ().map(groupQuantidade.getShell(), null,
					 * quantidade.getBounds()); Rectangle textBoundsPreco =
					 * getOperationUI().getDisplay().map(groupPreco.getShell(),
					 * null, preco.getBounds());
					 */
					Rectangle toolItemBounds = ativo.getBounds();
					Point point = groupAtivo.toDisplay(new Point(
							toolItemBounds.x, toolItemBounds.y));

					int width = textBoundsAddComposite.width; // textBoundsAtivo.width
					// +
					// textBoundsQuantidade.width
					// +
					// textBoundsPreco.width
					// + 20;

					Rectangle textBoundsTableRow = getOperationUI()
							.getDisplay().map(groupAtivo.getShell(), null,
									tableItem.getBounds());

					/*
					 * GC gc = new GC(groupAtivo); FontMetrics fm =
					 * gc.getFontMetrics();
					 * 
					 * int charWidth = fm.getAverageCharWidth(); int widthChar =
					 * charWidth * sizeOfName; int width=0; if(widthChar <
					 * textBoundsAtivo.width){ width=textBoundsAtivo.width;
					 * }else{ width=widthChar+100; }
					 * 
					 * logger.info(width);
					 */
					int heigh = 0;

					int BOX_LIMIT_ROW_SEARCH = 10;
					if (search.length < BOX_LIMIT_ROW_SEARCH) {
						heigh = textBoundsTableRow.height * search.length;
					} else {
						heigh = textBoundsTableRow.height
								* BOX_LIMIT_ROW_SEARCH;
					}

					popupShell.setBounds(point.x, point.y
							+ textBoundsAtivo.height, width, heigh);
					popupShell.setVisible(true);
				}
			}
		});

		table.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event event) {
				// ativo.setText(table.getSelection()[0].getText());

				StockSuggestion stockSuggestion = (StockSuggestion) table
						.getSelection()[0].getData();
				ativo.setText(stockSuggestion.getCode());

				popupShell.setVisible(false);
			}
		});
		table.addListener(SWT.KeyDown, new Listener() {
			public void handleEvent(Event event) {
				if (event.keyCode == SWT.ESC) {
					popupShell.setVisible(false);
				}
			}
		});

		Listener focusOutListener = new Listener() {
			public void handleEvent(Event event) {
				/* async is needed to wait until focus reaches its new Control */
				getDisplay().asyncExec(new Runnable() {
					public void run() {
						// if (getDisplay().isDisposed()) return;
						// Control control = getDisplay().getFocusControl();
						// if (control == null || (control != ativo && control
						// != table))
						{
							// if(getDisplay()!=null)
							{
								popupShell.setVisible(false);
							}
						}
					}
				});
			}
		};
		table.addListener(SWT.FocusOut, focusOutListener);
		ativo.addListener(SWT.FocusOut, focusOutListener);

		getShell().addListener(SWT.Move, new Listener() {
			public void handleEvent(Event event) {
				popupShell.setVisible(false);
			}
		});
	}

	public void cancelButton() {
		registerMode();

		getAtivo().setText("");
		getQuantidade().setText("");
		getPreco().setText("");
		getValorFinanceiro().setText("");
		//getDateOperation().setValue(new Date());

		// if(isCheckboxComprarSelected())
		{
			clickComprar();
		}
		/*
		 * else { clickVender(); }
		 */

		getAtivo().setFocus();

	}

	private void registerAndUpdateButton() {

		logger.info("Registrando operacão...");
		try{
			
			if (StringCheck.isEmpty(getDateSelected().getText()) 
					|| StringCheck.isEmpty(getAtivo().getText())
					|| StringCheck.isEmpty(getQuantidade().getText())
					|| StringCheck.isEmpty(getPreco().getText())
	
			) {
				throw new Exception("É necessario preencher um dos campos(Ativo,Quantidade,Preço e Data).");
			}
			//Date date = getDateOperation().getValue();
			String dateSelected=getDateSelected().getText();
			
			String formatTime = FormatUtil.formatTime(new Date());
			Date date = FormatUtil.parseDateAndTime(dateSelected + " " + formatTime);
			
			String ativo = getAtivo().getText();
			Integer quantidade = new Integer(getQuantidade().getText());
			Double preco = new Double(FormatUtil.fromReal(getPreco().getText()));
			
			OperationType operationType = null;
			if (isCheckboxComprarSelected()) {
				operationType = OperationType.BUY;
			} else if (isCheckboxVenderSelected()) {
				operationType = OperationType.SELL;
			} else {
				throw new Exception("Tipo de operação não reconhecida");
			}
			logger.info("Valores :"+operationType.name()+" Date :"+date+" Ativo :"+ativo+" Quantidade :"+quantidade+" Preço :"+preco);
			
			if (isEditMode()) {
				logger.info("Atualizando operação");
				if (getOpView() != null) {
					// getOperationUI().getAction().updateOperation(getOpView(),operationType,date, ativo, quantidade, preco);
				} 
			} else {
				logger.info("Adicionando operação");
				getOperationUI().getAction().registerOperation(operationType, date,	ativo, quantidade, preco);
			}
		}catch(Exception e){
			logger.error("## Não foi possível registrar a operação", e);
		}

	}

	public Button getCheckboxVender() {
		return checkboxVender;
	}

	public Text getPreco() {
		return preco;
	}

	public Label getValorFinanceiro() {
		return valorFinanceiro;
	}

	public Text getQuantidade() {
		return quantidade;
	}

	public Text getAtivo() {
		return ativo;
	}

	public Button getCheckboxComprar() {
		return checkboxComprar;
	}

	public void setDateOperation(DateChooserCombo dateOperation) {
		this.dateOperation = dateOperation;
		
		dateOperation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		dateOperation.setTheme(DateChooserTheme.GRAY);
	}

	public void setBtnCancelar(Button btnCancelar) {
	}

	public void setRegistrar(CLabel registrar) {
		this.registrar = registrar;
		registrar.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent arg0) {
				if(arg0.keyCode == SWT.TAB) { 
					logger.info("Indo para o Ativo. :"+ativo.forceFocus());															            
		         } 
			}
		});
		registrar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				logger.info("Botão Registrar Selecionado");
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				
				logger.info("Saiu do Botão Registrar");
				ativo.forceFocus();
			}
		});
	}

	public CLabel getRegistrar() {
		return registrar;
	}

	public void setBtnCancelar(Label btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public Label getBtnCancelar() {
		return btnCancelar;
	}

	public void setEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}

	public boolean isEditMode() {
		return isEditMode;
	}

	public void setCheckboxComprarSelected(boolean checkboxComprarSelected) {
		this.checkboxComprarSelected = checkboxComprarSelected;
	}

	public boolean isCheckboxComprarSelected() {
		return checkboxComprarSelected;
	}

	public void setCheckboxVenderSelected(boolean checkboxVenderSelected) {
		this.checkboxVenderSelected = checkboxVenderSelected;
	}

	public boolean isCheckboxVenderSelected() {
		return checkboxVenderSelected;
	}

	public void setOpView(OperationView opView) {
		this.opView = opView;
	}

	public OperationView getOpView() {
		return opView;
	}

	public void setMapBovespaStocks(Map<String, StockSuggestion> mapBovespaStocks) {
		this.mapBovespaStocks = mapBovespaStocks;
	}

	public Map<String, StockSuggestion> getMapBovespaStocks() {
		return mapBovespaStocks;
	}

	public void setDateSelected(Label dateSelected) {
		this.dateSelected = dateSelected;
	}

	public Label getDateSelected() {
		return dateSelected;
	}

}
