package com.twocents.ui.client.components.configuration.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.twocents.ui.client.components.configuration.BrokerConfiguration;

public class AddBrokerUI extends Composite {

	private Text range3Percentage;
	private Text range3Cash;
	private Text range3Until;
	private Text range3From;
	private Label range3Label;
	private Text range2Percentage;
	private Text range2Cash;
	private Text range2Until;
	private Text range2From;
	private Label range2Label;
	private Text range1Percentage;
	private Text range1Cash;
	private Text range1Until;
	private Label percentageLabel;
	private Label cashLabel;
	private Label untilLabel;
	private Label fromLabel;
	private Text range1From;
	private Label range1Label;
	private Group corretagemFixaGroup_1;
	private Button addButton;
	private Text valueFixedText;
	private Label valueFixedPerOperation;
	private Button brokageVariableRadio;
	private Button brokageFixedRadio;
	private Text brokerName;
	private Label lblCorretagem;
	private Button btnCancelar;

	public BrokerConfiguration rebuild(Composite composite) {
		return new BrokerConfiguration(composite);
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public AddBrokerUI(Composite parent, int style) {
		super(parent, style);

		setLayout(new FormLayout());

		final Group corretorasGroup = new Group(this, SWT.NONE);
		final FormData fd_corretorasGroup = new FormData();
		fd_corretorasGroup.bottom = new FormAttachment(100, -10);
		fd_corretorasGroup.right = new FormAttachment(100);
		fd_corretorasGroup.top = new FormAttachment(0, 31);
		fd_corretorasGroup.left = new FormAttachment(0, 10);
		corretorasGroup.setLayoutData(fd_corretorasGroup);
		corretorasGroup.setLayout(new FormLayout());

		brokerName = new Text(corretorasGroup, SWT.BORDER);
		final FormData fd_brokerName = new FormData();
		fd_brokerName.right = new FormAttachment(100, -336);
		fd_brokerName.left = new FormAttachment(0, 5);
		brokerName.setLayoutData(fd_brokerName);

		addButton = new Button(corretorasGroup, SWT.NONE);
		final FormData fd_addButton = new FormData();
		fd_addButton.left = new FormAttachment(0, 301);
		addButton.setLayoutData(fd_addButton);
		addButton.setText("Adicionar");

		addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// addBroker();
			}
		});

		Group corretagemFixaGroup;
		corretagemFixaGroup = new Group(corretorasGroup, SWT.NONE);
		final FormData fd_corretagemFixaGroup = new FormData();
		fd_corretagemFixaGroup.right = new FormAttachment(100, -22);
		fd_corretagemFixaGroup.left = new FormAttachment(0, 5);
		corretagemFixaGroup.setLayoutData(fd_corretagemFixaGroup);
		corretagemFixaGroup.setLayout(new FormLayout());
		corretagemFixaGroup.setText("Corretagem Fixa");

		valueFixedPerOperation = new Label(corretagemFixaGroup, SWT.NONE);
		valueFixedPerOperation.setEnabled(false);
		final FormData fd_valueFixedPerOperation = new FormData();
		fd_valueFixedPerOperation.bottom = new FormAttachment(0, 23);
		fd_valueFixedPerOperation.top = new FormAttachment(0, 10);
		fd_valueFixedPerOperation.right = new FormAttachment(0, 128);
		fd_valueFixedPerOperation.left = new FormAttachment(0, 5);
		valueFixedPerOperation.setLayoutData(fd_valueFixedPerOperation);
		valueFixedPerOperation.setText("Valor Fixo por operação");

		valueFixedText = new Text(corretagemFixaGroup, SWT.BORDER);
		valueFixedText.setEnabled(false);
		final FormData fd_valueFixedText = new FormData();
		fd_valueFixedText.bottom = new FormAttachment(valueFixedPerOperation,
				16, SWT.TOP);
		fd_valueFixedText.top = new FormAttachment(valueFixedPerOperation, -3,
				SWT.TOP);
		fd_valueFixedText.right = new FormAttachment(valueFixedPerOperation,
				264, SWT.LEFT);
		fd_valueFixedText.left = new FormAttachment(valueFixedPerOperation,
				125, SWT.LEFT);
		valueFixedText.setLayoutData(fd_valueFixedText);

		corretagemFixaGroup_1 = new Group(corretorasGroup, SWT.NONE);
		fd_corretagemFixaGroup.bottom = new FormAttachment(
				corretagemFixaGroup_1, -1);
		final FormData fd_corretagemFixaGroup_1 = new FormData();
		fd_corretagemFixaGroup_1.bottom = new FormAttachment(100, -71);
		fd_corretagemFixaGroup_1.top = new FormAttachment(0, 188);
		fd_corretagemFixaGroup_1.right = new FormAttachment(100, -19);
		fd_corretagemFixaGroup_1.left = new FormAttachment(0, 5);
		corretagemFixaGroup_1.setLayoutData(fd_corretagemFixaGroup_1);
		corretagemFixaGroup_1.setText("Corretagem Variável");

		range1Label = new Label(corretagemFixaGroup_1, SWT.NONE);
		range1Label.setEnabled(false);
		range1Label.setBounds(10, 35, 42, 13);
		range1Label.setText("Faixa 1");

		range1From = new Text(corretagemFixaGroup_1, SWT.BORDER);
		range1From.setEnabled(false);
		range1From.setBounds(60, 35, 46, 19);

		fromLabel = new Label(corretagemFixaGroup_1, SWT.NONE);
		fromLabel.setEnabled(false);
		fromLabel.setBounds(75, 20, 21, 13);
		fromLabel.setText("De");

		untilLabel = new Label(corretagemFixaGroup_1, SWT.NONE);
		untilLabel.setEnabled(false);
		untilLabel.setBounds(137, 20, 29, 13);
		untilLabel.setText("Até");

		cashLabel = new Label(corretagemFixaGroup_1, SWT.NONE);
		cashLabel.setEnabled(false);
		cashLabel.setBounds(205, 20, 21, 13);
		cashLabel.setText("R$");

		percentageLabel = new Label(corretagemFixaGroup_1, SWT.NONE);
		percentageLabel.setEnabled(false);
		percentageLabel.setBounds(270, 20, 21, 13);
		percentageLabel.setText("%");

		range1Until = new Text(corretagemFixaGroup_1, SWT.BORDER);
		range1Until.setEnabled(false);
		range1Until.setBounds(125, 35, 46, 19);

		range1Cash = new Text(corretagemFixaGroup_1, SWT.BORDER);
		range1Cash.setEnabled(false);
		range1Cash.setBounds(191, 35, 46, 19);

		range1Percentage = new Text(corretagemFixaGroup_1, SWT.BORDER);
		range1Percentage.setEnabled(false);
		range1Percentage.setBounds(255, 35, 46, 19);

		range2Label = new Label(corretagemFixaGroup_1, SWT.NONE);
		range2Label.setEnabled(false);
		range2Label.setBounds(10, 60, 42, 13);
		range2Label.setText("Faixa 2");

		range2From = new Text(corretagemFixaGroup_1, SWT.BORDER);
		range2From.setEnabled(false);
		range2From.setBounds(60, 60, 46, 19);

		range2Until = new Text(corretagemFixaGroup_1, SWT.BORDER);
		range2Until.setEnabled(false);
		range2Until.setBounds(125, 60, 46, 19);

		range2Cash = new Text(corretagemFixaGroup_1, SWT.BORDER);
		range2Cash.setEnabled(false);
		range2Cash.setBounds(191, 60, 46, 19);

		range2Percentage = new Text(corretagemFixaGroup_1, SWT.BORDER);
		range2Percentage.setEnabled(false);
		range2Percentage.setBounds(255, 60, 46, 19);

		range3Label = new Label(corretagemFixaGroup_1, SWT.NONE);
		range3Label.setEnabled(false);
		range3Label.setBounds(10, 85, 42, 13);
		range3Label.setText("Faixa 3");

		range3From = new Text(corretagemFixaGroup_1, SWT.BORDER);
		range3From.setEnabled(false);
		range3From.setBounds(60, 85, 46, 19);

		range3Until = new Text(corretagemFixaGroup_1, SWT.BORDER);
		range3Until.setEnabled(false);
		range3Until.setBounds(125, 85, 46, 19);

		range3Cash = new Text(corretagemFixaGroup_1, SWT.BORDER);
		range3Cash.setEnabled(false);
		range3Cash.setBounds(191, 85, 46, 19);

		range3Percentage = new Text(corretagemFixaGroup_1, SWT.BORDER);
		range3Percentage.setEnabled(false);
		range3Percentage.setBounds(255, 85, 46, 19);

		final Label nomeDaCorretoraLabel = new Label(corretorasGroup, SWT.NONE);
		final FormData fd_nomeDaCorretoraLabel = new FormData();
		fd_nomeDaCorretoraLabel.top = new FormAttachment(0, 10);
		fd_nomeDaCorretoraLabel.left = new FormAttachment(brokerName, 0,
				SWT.LEFT);
		fd_nomeDaCorretoraLabel.right = new FormAttachment(100, -342);
		nomeDaCorretoraLabel.setLayoutData(fd_nomeDaCorretoraLabel);
		nomeDaCorretoraLabel.setText("Nome da Corretora");

		lblCorretagem = new Label(corretorasGroup, SWT.NONE);
		fd_brokerName.bottom = new FormAttachment(lblCorretagem, -6);
		lblCorretagem.setText("Corretagem");
		FormData fd_lblCorretagem = new FormData();
		fd_lblCorretagem.left = new FormAttachment(0, 5);
		lblCorretagem.setLayoutData(fd_lblCorretagem);

		brokageFixedRadio = new Button(corretorasGroup, SWT.RADIO);
		fd_lblCorretagem.bottom = new FormAttachment(brokageFixedRadio, -6);
		fd_corretagemFixaGroup.top = new FormAttachment(0, 121);
		FormData fd_brokageFixedRadio = new FormData();
		fd_brokageFixedRadio.left = new FormAttachment(0, 5);
		fd_brokageFixedRadio.bottom = new FormAttachment(corretagemFixaGroup,
				-22);
		brokageFixedRadio.setLayoutData(fd_brokageFixedRadio);

		brokageFixedRadio.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				displayFixedBrokerFields();
			}
		});

		brokageFixedRadio.setText("Fixa");

		brokageVariableRadio = new Button(corretorasGroup, SWT.RADIO);
		FormData fd_brokageVariableRadio = new FormData();
		fd_brokageVariableRadio.top = new FormAttachment(brokageFixedRadio, 0,
				SWT.TOP);
		fd_brokageVariableRadio.left = new FormAttachment(brokageFixedRadio, 4);
		brokageVariableRadio.setLayoutData(fd_brokageVariableRadio);
		brokageVariableRadio.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				// displayBrokerFields();
			}
		});
		brokageVariableRadio.setText("Variável");

		btnCancelar = new Button(corretorasGroup, SWT.NONE);
		fd_addButton.bottom = new FormAttachment(btnCancelar, 21);
		fd_addButton.top = new FormAttachment(btnCancelar, 0, SWT.TOP);
		fd_addButton.right = new FormAttachment(btnCancelar, -6);
		btnCancelar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnCancelar.setText("Cancelar");
		FormData fd_btnCancelar = new FormData();
		fd_btnCancelar.right = new FormAttachment(corretagemFixaGroup, 0,
				SWT.RIGHT);
		fd_btnCancelar.bottom = new FormAttachment(corretagemFixaGroup_1, 47,
				SWT.BOTTOM);
		fd_btnCancelar.top = new FormAttachment(corretagemFixaGroup_1, 26);
		fd_btnCancelar.left = new FormAttachment(0, 380);
		btnCancelar.setLayoutData(fd_btnCancelar);

		Label lblAdicionarCorretora = new Label(this, SWT.NONE);
		FormData fd_lblAdicionarCorretora = new FormData();
		fd_lblAdicionarCorretora.bottom = new FormAttachment(corretorasGroup,
				-6);
		fd_lblAdicionarCorretora.left = new FormAttachment(0, 10);
		lblAdicionarCorretora.setLayoutData(fd_lblAdicionarCorretora);
		lblAdicionarCorretora.setText("Adicionar Corretora");
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void displayFixedBrokerFields() {
		this.valueFixedPerOperation.setEnabled(true);
		this.valueFixedText.setEnabled(true);
	}

}
