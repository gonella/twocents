package com.twocents.ui.client.components.wizard;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.swtdesigner.SWTResourceManager;
import com.twocents.core.populate.PopulateData;
import com.twocents.core.util.StringCheck;
import com.twocents.model.Broker;
import com.twocents.model.FixedTax;
import com.twocents.model.StockBroker;
import com.twocents.model.TwUser;
import com.twocents.model.VariableTax;
import com.twocents.ui.client.components.facade.AccountFacade;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.util.DialogUtil;

public class TwAddBrokerInfo extends Composite{


    private WizardAction action;
    private NumberFormat currencyFormatter;
    private NumberFormat numberFormatter;
    private Label lblPasso;
    private CloseShellListener closeListener;

    public TwAddBrokerInfo(final Shell dialogShell, int arg1, final WizardAction action, final NumberFormat currencyFormatter, final NumberFormat numberFormatter, Label lblPasso, final CloseShellListener closeListener) {
        super(dialogShell, arg1);
        this.action = action;
        this.currencyFormatter = currencyFormatter;
        this.numberFormatter = numberFormatter;
        this.lblPasso = lblPasso;
        this.closeListener = closeListener;

        /*createBrokerComponents(arg0);
    }

    private void createBrokerComponents(final Shell dialogShell) {
         */
        lblPasso.setText("Passo 2 - Configurar Corretora");

        final Composite group = new Composite(dialogShell, SWT.NONE);

        Label lblNomeDaCorretora = new Label(dialogShell, SWT.NONE);
        lblNomeDaCorretora.setBounds(10, 45, 113, 15);
        lblNomeDaCorretora.setText("Nome da Corretora");
        lblNomeDaCorretora.setForeground(SWTResourceManager
                .getColor(SWT.COLOR_WHITE));

        final Text txtCorretora = new Text(dialogShell, SWT.BORDER);
        txtCorretora.setBounds(129, 45, 169, 21);

        Label lblCorretagem = new Label(dialogShell, SWT.NONE);
        lblCorretagem.setBounds(10, 75, 82, 15);
        lblCorretagem.setText("Corretagem");
        lblCorretagem.setForeground(SWTResourceManager
                .getColor(SWT.COLOR_WHITE));

        final Button rdbFixa = new Button(dialogShell, SWT.RADIO);
        rdbFixa.setBounds(20, 96, 16, 16);
        rdbFixa.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        Label lblFixa = new Label(dialogShell, SWT.NONE);
        lblFixa.setText("Fixa");
        lblFixa.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblFixa.setBounds(42, 97, 34, 15);

        final Button rdbVarivel = new Button(dialogShell, SWT.RADIO);
        rdbVarivel.setBounds(20, 118, 16, 16);
        rdbVarivel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        Label lblVarivel = new Label(dialogShell, SWT.NONE);
        lblVarivel.setText("Vari\u00E1vel");
        lblVarivel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblVarivel.setBounds(42, 119, 50, 15);

        final Group fixedGrp = new Group(dialogShell, SWT.NONE);
        fixedGrp.setBounds(10, 147, 531, 82);

        final Label lblCorretagemFixa = new Label(fixedGrp, SWT.NONE);
        lblCorretagemFixa.setBounds(10, 0, 93, 15);
        lblCorretagemFixa.setText("Corretagem Fixa");
        lblCorretagemFixa.setForeground(SWTResourceManager
                .getColor(SWT.COLOR_WHITE));

        final Label lblValorFixo = new Label(fixedGrp, SWT.NONE);
        lblValorFixo.setEnabled(false);
        lblValorFixo.setText("Valor Fixo por opera\u00E7\u00E3o ("
                + ((DecimalFormat) currencyFormatter).getCurrency().getSymbol()
                + ") ");
        lblValorFixo.setBounds(10, 31, 146, 15);
        lblValorFixo
        .setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text txtFixa = new Text(fixedGrp, SWT.BORDER);
        txtFixa.setEnabled(false);
        txtFixa.setBounds(162, 28, 81, 21);

        final Group variableGrp = new Group(dialogShell, SWT.NONE);
        variableGrp.setBounds(10, 235, 531, 257);

        Label lblCorretagemVarivel = new Label(variableGrp, SWT.NONE);
        lblCorretagemVarivel.setBounds(10, 0, 113, 15);
        lblCorretagemVarivel.setText("Corretagem Vari\u00E1vel");
        lblCorretagemVarivel.setForeground(SWTResourceManager
                .getColor(SWT.COLOR_WHITE));

        final Button chkTabelaDefault = new Button(variableGrp, SWT.CHECK);
        chkTabelaDefault.setEnabled(false);
        chkTabelaDefault.setBounds(10, 25, 135, 16);
        chkTabelaDefault.setText("Usar a tabela Bovespa");

        final Text minor1 = new Text(variableGrp, SWT.BORDER);
        minor1.setEnabled(false);
        minor1.setBounds(87, 66, 85, 21);

        final Label lblDe = new Label(variableGrp, SWT.NONE);
        lblDe.setBounds(121, 51, 29, 15);
        lblDe.setText("De");
        lblDe.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Label lblAt = new Label(variableGrp, SWT.NONE);
        lblAt.setText("At\u00E9");
        lblAt.setBounds(212, 51, 29, 15);
        lblAt.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text max1 = new Text(variableGrp, SWT.BORDER);
        max1.setEnabled(false);
        max1.setBounds(180, 66, 85, 21);

        final Label lblR = new Label(variableGrp, SWT.NONE);
        lblR.setText("Valor Fixo");
        lblR.setBounds(285, 51, 61, 15);
        lblR.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text fixed1 = new Text(variableGrp, SWT.BORDER);
        fixed1.setEnabled(false);
        fixed1.setBounds(271, 66, 85, 21);

        final Label lblFaixa_1 = new Label(variableGrp, SWT.NONE);
        lblFaixa_1.setEnabled(false);
        lblFaixa_1.setBounds(10, 69, 71, 15);
        lblFaixa_1.setText("Faixa 1 ("
                + ((DecimalFormat) currencyFormatter).getCurrency().getSymbol()
                + ")");
        lblFaixa_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Label label_1 = new Label(variableGrp, SWT.NONE);
        label_1.setText("%");
        label_1.setBounds(390, 51, 29, 15);
        label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text percent1 = new Text(variableGrp, SWT.BORDER);
        percent1.setEnabled(false);
        percent1.setBounds(362, 66, 85, 21);

        final Label lblFaixa_2 = new Label(variableGrp, SWT.NONE);
        lblFaixa_2.setEnabled(false);
        lblFaixa_2.setText("Faixa 2 ("
                + ((DecimalFormat) currencyFormatter).getCurrency().getSymbol()
                + ")");
        lblFaixa_2.setBounds(10, 111, 71, 15);
        lblFaixa_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Label label_2 = new Label(variableGrp, SWT.NONE);
        label_2.setText("De");
        label_2.setBounds(121, 93, 29, 15);
        label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text minor2 = new Text(variableGrp, SWT.BORDER);
        minor2.setEnabled(false);
        minor2.setBounds(87, 108, 85, 21);

        final Label label_3 = new Label(variableGrp, SWT.NONE);
        label_3.setText("At\u00E9");
        label_3.setBounds(212, 93, 29, 15);
        label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text max2 = new Text(variableGrp, SWT.BORDER);
        max2.setEnabled(false);
        max2.setBounds(180, 108, 85, 21);

        final Text fixed2 = new Text(variableGrp, SWT.BORDER);
        fixed2.setEnabled(false);
        fixed2.setBounds(271, 108, 85, 21);

        final Label label_5 = new Label(variableGrp, SWT.NONE);
        label_5.setText("%");
        label_5.setBounds(390, 93, 29, 15);
        label_5.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text percent2 = new Text(variableGrp, SWT.BORDER);
        percent2.setEnabled(false);
        percent2.setBounds(362, 108, 85, 21);

        final Label lblFaixa_3 = new Label(variableGrp, SWT.NONE);
        lblFaixa_3.setEnabled(false);
        lblFaixa_3.setText("Faixa 3 ("
                + ((DecimalFormat) currencyFormatter).getCurrency().getSymbol()
                + ")");
        lblFaixa_3.setBounds(10, 146, 71, 15);
        lblFaixa_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Label label_7 = new Label(variableGrp, SWT.NONE);
        label_7.setText("De");
        label_7.setBounds(121, 132, 29, 15);
        label_7.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text minor3 = new Text(variableGrp, SWT.BORDER);
        minor3.setEnabled(false);
        minor3.setBounds(87, 147, 85, 21);

        final Label label_8 = new Label(variableGrp, SWT.NONE);
        label_8.setText("At\u00E9");
        label_8.setBounds(212, 132, 29, 15);
        label_8.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text max3 = new Text(variableGrp, SWT.BORDER);
        max3.setEnabled(false);
        max3.setBounds(180, 147, 85, 21);

        final Text fixed3 = new Text(variableGrp, SWT.BORDER);
        fixed3.setEnabled(false);
        fixed3.setBounds(271, 147, 85, 21);

        final Label label_10 = new Label(variableGrp, SWT.NONE);
        label_10.setText("%");
        label_10.setBounds(390, 132, 29, 15);
        label_10.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text percent3 = new Text(variableGrp, SWT.BORDER);
        percent3.setEnabled(false);
        percent3.setBounds(362, 147, 85, 21);

        final Label lblFaixa_5 = new Label(variableGrp, SWT.NONE);
        lblFaixa_5.setText("Faixa 5 ($)");
        lblFaixa_5.setEnabled(false);
        lblFaixa_5.setBounds(10, 229, 71, 15);
        lblFaixa_5.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text from5 = new Text(variableGrp, SWT.BORDER);
        from5.setEnabled(false);
        from5.setBounds(180, 226, 85, 21);

        final Text fixed5 = new Text(variableGrp, SWT.BORDER);
        fixed5.setEnabled(false);
        fixed5.setBounds(271, 226, 85, 21);

        final Text percent5 = new Text(variableGrp, SWT.BORDER);
        percent5.setEnabled(false);
        percent5.setBounds(362, 226, 85, 21);

        final Label lblAPartirDe = new Label(variableGrp, SWT.NONE);
        lblAPartirDe.setText("A partir de");
        lblAPartirDe.setBounds(190, 210, 65, 15);
        lblAPartirDe
        .setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Label label_11 = new Label(variableGrp, SWT.NONE);
        label_11.setText("%");
        label_11.setBounds(390, 171, 29, 15);
        label_11.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Label lblFaixa_4 = new Label(variableGrp, SWT.NONE);
        lblFaixa_4.setText("Faixa 4 ("
                + ((DecimalFormat) currencyFormatter).getCurrency().getSymbol()
                + ")");
        lblFaixa_4.setEnabled(false);
        lblFaixa_4.setBounds(10, 189, 71, 15);
        lblFaixa_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text minor4 = new Text(variableGrp, SWT.BORDER);
        minor4.setEnabled(false);
        minor4.setBounds(87, 186, 85, 21);

        final Label label_4 = new Label(variableGrp, SWT.NONE);
        label_4.setText("De");
        label_4.setBounds(121, 171, 29, 15);
        label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text max4 = new Text(variableGrp, SWT.BORDER);
        max4.setEnabled(false);
        max4.setBounds(180, 186, 85, 21);

        final Label label_12 = new Label(variableGrp, SWT.NONE);
        label_12.setText("At\u00E9");
        label_12.setBounds(212, 171, 29, 15);
        label_12.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Text fixed4 = new Text(variableGrp, SWT.BORDER);
        fixed4.setEnabled(false);
        fixed4.setBounds(271, 186, 85, 21);

        final Text percent4 = new Text(variableGrp, SWT.BORDER);
        percent4.setEnabled(false);
        percent4.setBounds(362, 186, 85, 21);

        final Label label = new Label(variableGrp, SWT.NONE);
        label.setText("%");
        label.setBounds(390, 210, 29, 15);
        label.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Label label_15 = new Label(variableGrp, SWT.NONE);
        label_15.setText("Valor Fixo");
        label_15.setBounds(285, 93, 61, 15);
        label_15.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Label label_9 = new Label(variableGrp, SWT.NONE);
        label_9.setText("Valor Fixo");
        label_9.setBounds(285, 132, 61, 15);
        label_9.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Label label_16 = new Label(variableGrp, SWT.NONE);
        label_16.setText("Valor Fixo");
        label_16.setBounds(285, 171, 61, 15);
        label_16.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        final Label label_6 = new Label(variableGrp, SWT.NONE);
        label_6.setText("Valor Fixo");
        label_6.setBounds(285, 210, 61, 15);
        label_6.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        Button nextBtn = new Button(dialogShell, SWT.NONE);
        nextBtn.setBounds(451, 522, 75, 25);
        nextBtn.setText("Pr\u00F3ximo");
        nextBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent arg0) {

                try {
                    if (StringCheck.isEmpty(txtCorretora.getText())) {
                        throw new UIException(9300);
                    }

                    Broker broker = new Broker();

                    StockBroker logged = ContextHolderUI.getStockBrokerLogged();
                    StockBroker added = action.addStockBroker(logged);
                    ContextHolderUI.setStockBrokerSelected(added);

                    broker.setStockBroker(added);

                    Set<Broker> brokers = added.getBrokers();

                    if (logged.getBrokers() == null) {
                        brokers = new TreeSet<Broker>();
                    }

                    if (!brokers.contains(broker)) {
                        brokers.add(broker);
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
                            tax.setValue(numberFormatter.parse(
                                    valueFixed.trim()).doubleValue());
                        } catch (Exception e) {
                            DialogUtil
                            .errorMessage(group, "Dados Inválidos",
                                    "O formato dos valores está incorreto. Favor corrigir e tentar novamente.");
                            return;
                        }
                        broker.setTax(tax);

                    } else {

                        VariableTax tax = new VariableTax();
                        tax.setFixed(false);
                        try {
                            tax.setFixed1(numberFormatter.parse(
                                    fixed1.getText()).floatValue());
                            tax.setMax1(numberFormatter.parse(max1.getText())
                                    .floatValue());
                            tax.setMinor1(numberFormatter.parse(
                                    minor1.getText()).floatValue());

                            tax.setFixed2(numberFormatter.parse(
                                    fixed2.getText()).floatValue());
                            tax.setMax2(numberFormatter.parse(max2.getText())
                                    .floatValue());
                            tax.setMinor2(numberFormatter.parse(
                                    minor2.getText()).floatValue());

                            tax.setFixed3(numberFormatter.parse(
                                    fixed3.getText()).floatValue());
                            tax.setMax3(numberFormatter.parse(max3.getText())
                                    .floatValue());
                            tax.setMinor3(numberFormatter.parse(
                                    minor3.getText()).floatValue());

                            tax.setFixed4(numberFormatter.parse(
                                    fixed4.getText()).floatValue());
                            tax.setMax4(numberFormatter.parse(max4.getText())
                                    .floatValue());
                            tax.setMinor4(numberFormatter.parse(
                                    minor4.getText()).floatValue());

                            tax.setFrom5(numberFormatter.parse(from5.getText())
                                    .floatValue());
                            tax.setFixed5(numberFormatter.parse(
                                    fixed5.getText()).floatValue());

                            tax.setPercent1(numberFormatter.parse(
                                    percent1.getText()).floatValue());
                            tax.setPercent2(numberFormatter.parse(
                                    percent2.getText()).floatValue());
                            tax.setPercent3(numberFormatter.parse(
                                    percent3.getText()).floatValue());
                            tax.setPercent4(numberFormatter.parse(
                                    percent4.getText()).floatValue());
                            tax.setPercent5(numberFormatter.parse(
                                    percent5.getText()).floatValue());

                        } catch (Exception e) {
                            DialogUtil
                            .errorMessage(group,
                                    "Dados inválidos ou incompletos.",
                                    " Os valores estão incorretos. Favor corrigir e tentar novamente.");
                            return;
                        }

                        broker.setTax(tax);

                    }

                    action.addBroker(broker);

                    TwUser user = new PopulateData().populateUser(added
                            .getName(), added.getPassword(), "", added
                            .getEmail(), added.getTelefone());

                    new AccountFacade().addAccount(user, added, broker
                            .getName(), "1");

                    dialogShell.removeListener(SWT.Close, closeListener);
                    dialogShell.close();

                } catch (Exception e) {
                    DialogUtil.errorMessage(group, e.getMessage(), e
                            .getMessage());
                }

            }
        });

        chkTabelaDefault.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
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
        });
        rdbVarivel.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                chkTabelaDefault.setEnabled(true);
                lblFaixa_1.setEnabled(true);
                lblFaixa_2.setEnabled(true);
                lblFaixa_3.setEnabled(true);
                lblFaixa_4.setEnabled(true);
                lblFaixa_5.setEnabled(true);

                minor1.setEnabled(true);
                max1.setEnabled(true);
                fixed1.setEnabled(true);
                percent1.setEnabled(true);

                minor2.setEnabled(true);
                max2.setEnabled(true);
                fixed2.setEnabled(true);
                percent2.setEnabled(true);

                minor3.setEnabled(true);
                max3.setEnabled(true);
                fixed3.setEnabled(true);
                percent3.setEnabled(true);

                minor4.setEnabled(true);
                max4.setEnabled(true);
                fixed4.setEnabled(true);
                percent4.setEnabled(true);

                from5.setEnabled(true);
                fixed5.setEnabled(true);
                percent5.setEnabled(true);

                lblValorFixo.setEnabled(false);
                txtFixa.setEnabled(false);
            }

        });

        rdbFixa.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                lblValorFixo.setEnabled(true);
                txtFixa.setEnabled(true);

                chkTabelaDefault.setEnabled(false);
                lblFaixa_1.setEnabled(false);
                lblFaixa_2.setEnabled(false);
                lblFaixa_3.setEnabled(false);
                lblFaixa_4.setEnabled(false);
                lblFaixa_5.setEnabled(false);

                minor1.setEnabled(false);
                max1.setEnabled(false);
                fixed1.setEnabled(false);
                percent1.setEnabled(false);

                minor2.setEnabled(false);
                max2.setEnabled(false);
                fixed2.setEnabled(false);
                percent2.setEnabled(false);

                minor3.setEnabled(false);
                max3.setEnabled(false);
                fixed3.setEnabled(false);
                percent3.setEnabled(false);

                minor4.setEnabled(false);
                max4.setEnabled(false);
                fixed4.setEnabled(false);
                percent4.setEnabled(false);

                from5.setEnabled(false);
                fixed5.setEnabled(false);
                percent5.setEnabled(false);

            }
        });

    }

}
