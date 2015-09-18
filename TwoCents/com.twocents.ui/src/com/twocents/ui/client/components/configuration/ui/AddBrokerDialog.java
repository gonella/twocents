package com.twocents.ui.client.components.configuration.ui;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.twocents.core.util.StringCheck;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Broker;
import com.twocents.model.FixedTax;
import com.twocents.model.StockBroker;
import com.twocents.model.VariableTax;
import com.twocents.ui.client.components.configuration.action.BrokerConfigurationAction;
import com.twocents.ui.client.components.configuration.event.BrokerAddedEvent;
import com.twocents.ui.client.components.configuration.event.BrokerOperationListener;
import com.twocents.ui.client.components.configuration.event.BrokerUpdatedEvent;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.util.DialogUtil;

public class AddBrokerDialog extends Dialog {

	protected Object result;
	protected Shell shlAdicionarCorretora;
	private Text txtCorretora;
	private Text txtFixa;
	private Text minor1;
	private Text max1;
	private Text fixed1;
	private Text percent1;
	private Text minor2;
	private Text max2;
	private Text fixed2;
	private Text percent2;
	private Text minor3;
	private Text max3;
	private Text fixed3;
	private Text percent3;

	private Label lblFaixa_1;
	private Label lblDe;
	private Label lblAt;
	private Label lblR;
	private Label label_1;
	private Label lblFaixa_2;
	private Label label_2;
	private Label label_3;
	private Label lblFaixa_3;
	private Label label_7;
	private Label label_8;
	private Label label_10;
	private Label label_5;
	private Label lblCorretagemFixa;
	private Label lblValorFixo;
	private Button chkTabelaDefault;
	private Group fixedGrp;
	private Group variableGrp;
	private Button rdbFixa;
	private Button rdbVarivel;
	private Button btnAdicionar;
	private BrokerConfigurationAction action;
	private List<BrokerOperationListener> listeners;
	private Label lblFaixa_5;
	private Text from5;
	private Text fixed5;
	private Text percent5;
	private Label lblAPartirDe;
	private Label label_11;
	private Label lblFaixa_4;
	private Text minor4;
	private Label label_4;
	private Text max4;
	private Label label_12;
	private Text fixed4;
	private Text percent4;
	private Label label;
	private Label label_15;
	private Label label_9;
	private Label label_16;
	private Label label_6;

	private NumberFormat currencyFormatter;
	private NumberFormat numberFormatter;

	private Broker broker;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public AddBrokerDialog(Shell parent, int style,
			BrokerConfigurationAction action, Broker broker,
			List<BrokerOperationListener> listeners) {
		super(parent, style);
		this.action = action;
		this.listeners = listeners;
		this.broker = broker;
		setText("Adicionar/Atualizar Broker");

		currencyFormatter = DecimalFormat.getCurrencyInstance(Locale.getDefault());

		numberFormatter = DecimalFormat.getInstance(Locale.getDefault());
		((DecimalFormat) numberFormatter).setDecimalSeparatorAlwaysShown(true);
		((DecimalFormat) numberFormatter).setMinimumIntegerDigits(1);
		((DecimalFormat) numberFormatter).setMinimumFractionDigits(2);
		((DecimalFormat) numberFormatter).setParseBigDecimal(true);

	}

	/**
	 * @wbp.parser.constructor
	 */
	public AddBrokerDialog(Shell parent, int style,
			BrokerConfigurationAction action, Broker broker) {
		this(parent, style, action, broker, null);
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents(broker);
		shlAdicionarCorretora.open();
		shlAdicionarCorretora.layout();
		Display display = getParent().getDisplay();
		while (!shlAdicionarCorretora.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	private void close() {
		shlAdicionarCorretora.close();
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents(Broker broker) {
		shlAdicionarCorretora = new Shell(getParent(), getStyle());
		shlAdicionarCorretora.setLocation(250, 300);
		shlAdicionarCorretora.setSize(557, 594);
		shlAdicionarCorretora.setText("Adicionar Corretora");

		Label lblNomeDaCorretora = new Label(shlAdicionarCorretora, SWT.NONE);
		lblNomeDaCorretora.setBounds(10, 18, 113, 15);
		lblNomeDaCorretora.setText("Nome da Corretora");

		txtCorretora = new Text(shlAdicionarCorretora, SWT.BORDER);
		txtCorretora.setBounds(129, 18, 169, 21);

		Label lblCorretagem = new Label(shlAdicionarCorretora, SWT.NONE);
		lblCorretagem.setBounds(10, 64, 82, 15);
		lblCorretagem.setText("Corretagem");

		rdbFixa = new Button(shlAdicionarCorretora, SWT.RADIO);
		rdbFixa.setBounds(10, 89, 57, 16);
		rdbFixa.setText("Fixa");

		rdbVarivel = new Button(shlAdicionarCorretora, SWT.RADIO);
		rdbVarivel.setBounds(83, 89, 90, 16);
		rdbVarivel.setText("Vari\u00E1vel");

		fixedGrp = new Group(shlAdicionarCorretora, SWT.NONE);
		fixedGrp.setBounds(10, 128, 531, 82);

		lblCorretagemFixa = new Label(fixedGrp, SWT.NONE);
		lblCorretagemFixa.setBounds(10, 0, 93, 15);
		lblCorretagemFixa.setText("Corretagem Fixa");

		lblValorFixo = new Label(fixedGrp, SWT.NONE);
		lblValorFixo.setEnabled(false);
		lblValorFixo.setText("Valor Fixo por opera\u00E7\u00E3o ("
				+ ((DecimalFormat) currencyFormatter).getCurrency().getSymbol() + ") ");
		lblValorFixo.setBounds(10, 31, 146, 15);

		txtFixa = new Text(fixedGrp, SWT.BORDER);
		txtFixa.setEnabled(false);
		txtFixa.setBounds(162, 28, 81, 21);

		variableGrp = new Group(shlAdicionarCorretora, SWT.NONE);
		variableGrp.setBounds(10, 235, 531, 257);

		Label lblCorretagemVarivel = new Label(variableGrp, SWT.NONE);
		lblCorretagemVarivel.setBounds(10, 0, 113, 15);
		lblCorretagemVarivel.setText("Corretagem Vari\u00E1vel");

		chkTabelaDefault = new Button(variableGrp, SWT.CHECK);
		chkTabelaDefault.setEnabled(false);
		chkTabelaDefault.setBounds(10, 25, 135, 16);
		chkTabelaDefault.setText("Usar a tabela Bovespa");

		minor1 = new Text(variableGrp, SWT.BORDER);
		minor1.setEnabled(false);
		minor1.setBounds(87, 66, 85, 21);

		lblDe = new Label(variableGrp, SWT.NONE);
		lblDe.setBounds(121, 51, 29, 15);
		lblDe.setText("De");

		lblAt = new Label(variableGrp, SWT.NONE);
		lblAt.setText("At\u00E9");
		lblAt.setBounds(212, 51, 29, 15);

		max1 = new Text(variableGrp, SWT.BORDER);
		max1.setEnabled(false);
		max1.setBounds(180, 66, 85, 21);

		lblR = new Label(variableGrp, SWT.NONE);
		lblR.setText("Valor Fixo");
		lblR.setBounds(285, 51, 61, 15);

		fixed1 = new Text(variableGrp, SWT.BORDER);
		fixed1.setEnabled(false);
		fixed1.setBounds(271, 66, 85, 21);

		lblFaixa_1 = new Label(variableGrp, SWT.NONE);
		lblFaixa_1.setEnabled(false);
		lblFaixa_1.setBounds(10, 69, 71, 15);
		lblFaixa_1.setText("Faixa 1 (" + ((DecimalFormat) currencyFormatter).getCurrency().getSymbol() + ")");
		label_1 = new Label(variableGrp, SWT.NONE);
		label_1.setText("%");
		label_1.setBounds(390, 51, 29, 15);

		percent1 = new Text(variableGrp, SWT.BORDER);
		percent1.setEnabled(false);
		percent1.setBounds(362, 66, 85, 21);

		lblFaixa_2 = new Label(variableGrp, SWT.NONE);
		lblFaixa_2.setEnabled(false);
		lblFaixa_2.setText("Faixa 2 (" + ((DecimalFormat) currencyFormatter).getCurrency().getSymbol() + ")");
		lblFaixa_2.setBounds(10, 111, 71, 15);

		label_2 = new Label(variableGrp, SWT.NONE);
		label_2.setText("De");
		label_2.setBounds(121, 93, 29, 15);

		minor2 = new Text(variableGrp, SWT.BORDER);
		minor2.setEnabled(false);
		minor2.setBounds(87, 108, 85, 21);

		label_3 = new Label(variableGrp, SWT.NONE);
		label_3.setText("At\u00E9");
		label_3.setBounds(212, 93, 29, 15);

		max2 = new Text(variableGrp, SWT.BORDER);
		max2.setEnabled(false);
		max2.setBounds(180, 108, 85, 21);

		fixed2 = new Text(variableGrp, SWT.BORDER);
		fixed2.setEnabled(false);
		fixed2.setBounds(271, 108, 85, 21);

		label_5 = new Label(variableGrp, SWT.NONE);
		label_5.setText("%");
		label_5.setBounds(390, 93, 29, 15);

		percent2 = new Text(variableGrp, SWT.BORDER);
		percent2.setEnabled(false);
		percent2.setBounds(362, 108, 85, 21);

		lblFaixa_3 = new Label(variableGrp, SWT.NONE);
		lblFaixa_3.setEnabled(false);
		lblFaixa_3.setText("Faixa 3 (" + ((DecimalFormat) currencyFormatter).getCurrency().getSymbol() + ")");
		lblFaixa_3.setBounds(10, 146, 71, 15);

		label_7 = new Label(variableGrp, SWT.NONE);
		label_7.setText("De");
		label_7.setBounds(121, 132, 29, 15);

		minor3 = new Text(variableGrp, SWT.BORDER);
		minor3.setEnabled(false);
		minor3.setBounds(87, 147, 85, 21);

		label_8 = new Label(variableGrp, SWT.NONE);
		label_8.setText("At\u00E9");
		label_8.setBounds(212, 132, 29, 15);

		max3 = new Text(variableGrp, SWT.BORDER);
		max3.setEnabled(false);
		max3.setBounds(180, 147, 85, 21);

		fixed3 = new Text(variableGrp, SWT.BORDER);
		fixed3.setEnabled(false);
		fixed3.setBounds(271, 147, 85, 21);

		label_10 = new Label(variableGrp, SWT.NONE);
		label_10.setText("%");
		label_10.setBounds(390, 132, 29, 15);

		percent3 = new Text(variableGrp, SWT.BORDER);
		percent3.setEnabled(false);
		percent3.setBounds(362, 147, 85, 21);

		lblFaixa_5 = new Label(variableGrp, SWT.NONE);
		lblFaixa_5.setText("Faixa 5 ($)");
		lblFaixa_5.setEnabled(false);
		lblFaixa_5.setBounds(10, 229, 71, 15);

		from5 = new Text(variableGrp, SWT.BORDER);
		from5.setEnabled(false);
		from5.setBounds(180, 226, 85, 21);

		fixed5 = new Text(variableGrp, SWT.BORDER);
		fixed5.setEnabled(false);
		fixed5.setBounds(271, 226, 85, 21);

		percent5 = new Text(variableGrp, SWT.BORDER);
		percent5.setEnabled(false);
		percent5.setBounds(362, 226, 85, 21);

		lblAPartirDe = new Label(variableGrp, SWT.NONE);
		lblAPartirDe.setText("A partir de");
		lblAPartirDe.setBounds(190, 210, 65, 15);

		label_11 = new Label(variableGrp, SWT.NONE);
		label_11.setText("%");
		label_11.setBounds(390, 171, 29, 15);

		lblFaixa_4 = new Label(variableGrp, SWT.NONE);
		lblFaixa_4.setText("Faixa 4 (" + ((DecimalFormat) currencyFormatter).getCurrency().getSymbol() + ")");
		lblFaixa_4.setEnabled(false);
		lblFaixa_4.setBounds(10, 189, 71, 15);

		minor4 = new Text(variableGrp, SWT.BORDER);
		minor4.setEnabled(false);
		minor4.setBounds(87, 186, 85, 21);

		label_4 = new Label(variableGrp, SWT.NONE);
		label_4.setText("De");
		label_4.setBounds(121, 171, 29, 15);

		max4 = new Text(variableGrp, SWT.BORDER);
		max4.setEnabled(false);
		max4.setBounds(180, 186, 85, 21);

		label_12 = new Label(variableGrp, SWT.NONE);
		label_12.setText("At\u00E9");
		label_12.setBounds(212, 171, 29, 15);

		fixed4 = new Text(variableGrp, SWT.BORDER);
		fixed4.setEnabled(false);
		fixed4.setBounds(271, 186, 85, 21);

		percent4 = new Text(variableGrp, SWT.BORDER);
		percent4.setEnabled(false);
		percent4.setBounds(362, 186, 85, 21);

		label = new Label(variableGrp, SWT.NONE);
		label.setText("%");
		label.setBounds(390, 210, 29, 15);

		label_15 = new Label(variableGrp, SWT.NONE);
		label_15.setText("Valor Fixo");
		label_15.setBounds(285, 93, 61, 15);

		label_9 = new Label(variableGrp, SWT.NONE);
		label_9.setText("Valor Fixo");
		label_9.setBounds(285, 132, 61, 15);

		label_16 = new Label(variableGrp, SWT.NONE);
		label_16.setText("Valor Fixo");
		label_16.setBounds(285, 171, 61, 15);

		label_6 = new Label(variableGrp, SWT.NONE);
		label_6.setText("Valor Fixo");
		label_6.setBounds(285, 210, 61, 15);

		btnAdicionar = new Button(shlAdicionarCorretora, SWT.NONE);

		if (broker == null) {
			btnAdicionar.setText("Adicionar");
		} else {
			btnAdicionar.setText("Atualizar");
		}

		btnAdicionar.setEnabled(false);
		btnAdicionar.setBounds(398, 519, 73, 21);

		Button btnCancelar = new Button(shlAdicionarCorretora, SWT.NONE);
		btnCancelar.setText("Cancelar");
		btnCancelar.setBounds(477, 519, 64, 21);

		btnAdicionar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					addBroker();
				} catch (UIException e) {
					DialogUtil.errorMessage(shlAdicionarCorretora, e
							.getMessage(), e.getMessage());
				}
			}
		});

		btnCancelar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				close();
			}
		});

		rdbVarivel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				displayVariableBrokerFields(true);
			}

		});

		rdbFixa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				displayFixedBrokerFields(true);
			}
		});

		chkTabelaDefault.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setDefaultValues();
			}
		});

		if (broker != null) {
			txtCorretora.setText(broker.getName());

			if (broker.getTax() instanceof FixedTax) {
				displayFixedBrokerFields(true);
			} else {
				displayVariableBrokerFields(true);

			}

		}

	}

	private void displayFixedBrokerFields(boolean isEnabled) {
		lblValorFixo.setEnabled(isEnabled);
		txtFixa.setEnabled(isEnabled);

		if (broker != null && isEnabled) {
			rdbFixa.setSelection(true);
			rdbVarivel.setSelection(false);
			if (broker.getTax() instanceof FixedTax) {
				FixedTax tax = (FixedTax) broker.getTax();
				txtFixa.setText(numberFormatter.format(tax.getValue()));
			}
		}

		if (isEnabled) {
			displayVariableBrokerFields(false);
		}

		btnAdicionar.setEnabled(true);

	}

	private void displayVariableBrokerFields(boolean isEnabled) {
		chkTabelaDefault.setEnabled(isEnabled);
		lblFaixa_1.setEnabled(isEnabled);
		lblFaixa_2.setEnabled(isEnabled);
		lblFaixa_3.setEnabled(isEnabled);
		lblFaixa_4.setEnabled(isEnabled);
		lblFaixa_5.setEnabled(isEnabled);

		minor1.setEnabled(isEnabled);
		max1.setEnabled(isEnabled);
		fixed1.setEnabled(isEnabled);
		percent1.setEnabled(isEnabled);

		minor2.setEnabled(isEnabled);
		max2.setEnabled(isEnabled);
		fixed2.setEnabled(isEnabled);
		percent2.setEnabled(isEnabled);

		minor3.setEnabled(isEnabled);
		max3.setEnabled(isEnabled);
		fixed3.setEnabled(isEnabled);
		percent3.setEnabled(isEnabled);

		minor4.setEnabled(isEnabled);
		max4.setEnabled(isEnabled);
		fixed4.setEnabled(isEnabled);
		percent4.setEnabled(isEnabled);

		from5.setEnabled(isEnabled);
		fixed5.setEnabled(isEnabled);
		percent5.setEnabled(isEnabled);

		if (broker != null && isEnabled) {
			rdbVarivel.setSelection(true);
			rdbFixa.setSelection(false);
			
			if (broker.getTax() instanceof VariableTax) {
				VariableTax tax = (VariableTax) broker.getTax();

				minor1.setText(numberFormatter.format(tax.getMinor1()));
				max1.setText(numberFormatter.format(tax.getMax1()));
				fixed1.setText(numberFormatter.format(tax.getFixed1()));
				percent1.setText(numberFormatter.format(tax.getPercent1()));

				minor2.setText(numberFormatter.format(tax.getMinor2()));
				max2.setText(numberFormatter.format(tax.getMax2()));
				fixed2.setText(numberFormatter.format(tax.getFixed2()));
				percent2.setText(numberFormatter.format(tax.getPercent2()));

				minor3.setText(numberFormatter.format(tax.getMinor3()));
				max3.setText(numberFormatter.format(tax.getMax3()));
				fixed3.setText(numberFormatter.format(tax.getFixed3()));
				percent3.setText(numberFormatter.format(tax.getPercent3()));

				minor4.setText(numberFormatter.format(tax.getMinor4()));
				max4.setText(numberFormatter.format(tax.getMax4()));
				fixed4.setText(numberFormatter.format(tax.getFixed4()));
				percent4.setText(numberFormatter.format(tax.getPercent4()));

				from5.setText(numberFormatter.format(tax.getFrom5()));
				fixed5.setText(numberFormatter.format(tax.getFixed5()));
				percent5.setText(numberFormatter.format(tax.getPercent5()));

			}			
		}

		if (isEnabled) {
			displayFixedBrokerFields(false);
		}

		btnAdicionar.setEnabled(true);

	}

	private void addBroker() throws UIException {
		if (StringCheck.isEmpty(txtCorretora.getText())) {
			throw new UIException(9300);
		}

		if (broker == null) {
			broker = new Broker();
		}

		try {
			StockBroker logged = ContextHolderUI.getStockBrokerLogged();
			broker.setStockBroker(logged);

			Set<Broker> brokers = logged.getBrokers();

			if (logged.getBrokers() == null) {
				brokers = new TreeSet<Broker>();
			}

			if (!brokers.contains(broker)) {
				brokers.add(broker);
			}

		} catch (CoreException e1) {
			e1.printStackTrace();
		}

		broker.setName(txtCorretora.getText());

		if (rdbFixa.getSelection()) {
			String valueFixed = txtFixa.getText();

			if (StringCheck.isEmpty(valueFixed)) {
				throw new UIException(9304);
			}
			FixedTax tax = new FixedTax();
			tax.setFixed(true);
			try {
				tax.setValue(numberFormatter.parse(valueFixed.trim()).doubleValue());
			} catch (Exception e) {
				DialogUtil
						.errorMessage(shlAdicionarCorretora, "Dados Inválidos",
								"O formato dos valores está incorreto. Favor corrigir e tentar novamente.");
				return;
			}
			broker.setTax(tax);

		} else {

			VariableTax tax = new VariableTax();
			tax.setFixed(false);
			try {
				tax.setFixed1(numberFormatter.parse(fixed1.getText()).floatValue());
				tax.setMax1(numberFormatter.parse(max1.getText()).floatValue());
				tax.setMinor1(numberFormatter.parse(minor1.getText()).floatValue());

				tax.setFixed2(numberFormatter.parse(fixed2.getText()).floatValue());
				tax.setMax2(numberFormatter.parse(max2.getText()).floatValue());
				tax.setMinor2(numberFormatter.parse(minor2.getText()).floatValue());

				tax.setFixed3(numberFormatter.parse(fixed3.getText()).floatValue());
				tax.setMax3(numberFormatter.parse(max3.getText()).floatValue());
				tax.setMinor3(numberFormatter.parse(minor3.getText()).floatValue());

				tax.setFixed4(numberFormatter.parse(fixed4.getText()).floatValue());
				tax.setMax4(numberFormatter.parse(max4.getText()).floatValue());
				tax.setMinor4(numberFormatter.parse(minor4.getText()).floatValue());

				tax.setFrom5(numberFormatter.parse(from5.getText()).floatValue());
				tax.setFixed5(numberFormatter.parse(fixed5.getText()).floatValue());

				tax.setPercent1(numberFormatter.parse(percent1.getText())
						.floatValue());
				tax.setPercent2(numberFormatter.parse(percent2.getText())
						.floatValue());
				tax.setPercent3(numberFormatter.parse(percent3.getText())
						.floatValue());
				tax.setPercent4(numberFormatter.parse(percent4.getText())
						.floatValue());
				tax.setPercent5(numberFormatter.parse(percent5.getText())
						.floatValue());

			} catch (Exception e) {
				DialogUtil
						.errorMessage(shlAdicionarCorretora, "Dados Inválidos",
								"O formato dos valores está incorreto. Favor corrigir e tentar novamente.");
				return;
			}

			broker.setTax(tax);

		}

		if (broker.getId() == null) {
			action.addBroker(broker);
			if (listeners != null && !listeners.isEmpty()) {
				for (BrokerOperationListener listener : listeners) {
					listener
							.onBrokerUpdated(new BrokerAddedEvent(this, broker));
				}
			}
		} else {
			action.update(broker);
			if (listeners != null && !listeners.isEmpty()) {
				for (BrokerOperationListener listener : listeners) {
					listener.onBrokerUpdated(new BrokerUpdatedEvent(this,
							broker));
				}
			}
		}

		close();
	}

	private void setDefaultValues() {

		minor1.setText(numberFormatter.format(0.01));
		max1.setText(numberFormatter.format(135.07));
		fixed1.setText(numberFormatter.format(2.70));
		percent1.setText(numberFormatter.format(0.00));

		minor2.setText(numberFormatter.format(135.08));
		max2.setText(numberFormatter.format(498.62));
		fixed2.setText(numberFormatter.format(0.00));
		percent2.setText(numberFormatter.format(2.0));

		minor3.setText(numberFormatter.format(498.63));
		max3.setText(numberFormatter.format(1514.69));
		fixed3.setText(numberFormatter.format(2.49));
		percent3.setText(numberFormatter.format(1.5));

		minor4.setText(numberFormatter.format(1514.70));
		max4.setText(numberFormatter.format(3029.38));
		fixed4.setText(numberFormatter.format(10.06));
		percent4.setText(numberFormatter.format(1.0));

		from5.setText(numberFormatter.format(3029.39));
		fixed5.setText(numberFormatter.format(25.21));
		percent5.setText(numberFormatter.format(0.5));

	}
}
