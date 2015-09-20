package com.twocents.ui.client.components.wizard;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.swtdesigner.SWTResourceManager;
import com.twocents.context.ContextHolder;
import com.twocents.core.populate.PopulateData;
import com.twocents.core.util.StringCheck;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Broker;
import com.twocents.model.FixedTax;
import com.twocents.model.StockBroker;
import com.twocents.model.TwUser;
import com.twocents.ui.client.components.facade.AccountFacade;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.constant.UIDefault;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.util.DialogUtil;

public class TwWizard extends Dialog {

    private WizardAction action;
    private Text nomeTxt;
    private Text emailTxt;
    private Text username;
    private Text passwd;

    private NumberFormat currencyFormatter;
    private NumberFormat numberFormatter;
    private Shell dialogShell;
    private CloseShellListener closeListener;
    private Text txtCorretora;
    private Text txtFixa;

    public void open() {

        final Shell parent = getParent();
        dialogShell = new Shell(parent, SWT.CLOSE
                | SWT.TITLE
                | SWT.APPLICATION_MODAL);

        com.cloudgarden.resource.SWTResourceManager
        .registerResourceUser(dialogShell);

        closeListener = new CloseShellListener(dialogShell);
        dialogShell.addListener(SWT.Close, closeListener);

        dialogShell.layout();
        dialogShell.pack();
        dialogShell.setText("TwoCents Wizard");
        dialogShell.setSize(394, 596);
        dialogShell.setLocation(300, 200);

        dialogShell.setLocation(getParent().toDisplay(100, 100));
        // int positionX=parent.getBounds().x+(parent.getBounds().width /4);
        // Move the dialog to the center of the top level shell.
        Rectangle shellBounds = getParent().getBounds();
        Point dialogSize = dialogShell.getSize();

        dialogShell.setLocation(shellBounds.x
                + (shellBounds.width - dialogSize.x) / 2, shellBounds.y
                + (shellBounds.height - dialogSize.y) / 2);

        dialogShell.setBackgroundImage(SWTResourceManager.getImage(
                TwWizard.class,
                UIDefault.COM_TWOCENTS_UI_RESOURCES_IMAGES_LOGIN_SCREEN_PNG));

        dialogShell.setBackgroundMode(SWT.INHERIT_DEFAULT);

        createWizardNoticeScreen(dialogShell);

        Label lblNewLabel_3 = new Label(dialogShell, SWT.NONE);
        lblNewLabel_3.setFont(org.eclipse.wb.swt.SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
        lblNewLabel_3.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblNewLabel_3.setText("Informa\u00E7\u00F5es sobre corretora :");

        Group groupBroker = new Group(dialogShell, SWT.NONE);
        groupBroker.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));
        groupBroker.setLayout(new GridLayout(2, false));
        groupBroker.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        Label lblNewLabel_1 = new Label(groupBroker, SWT.NONE);
        lblNewLabel_1.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblNewLabel_1.setFont(org.eclipse.wb.swt.SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
        lblNewLabel_1.setText("Nome :");

        txtCorretora = new Text(groupBroker, SWT.BORDER);
        txtCorretora.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Label lblNewLabel_2 = new Label(groupBroker, SWT.NONE);
        lblNewLabel_2.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblNewLabel_2.setFont(org.eclipse.wb.swt.SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
        lblNewLabel_2.setText("Corretagem :");

        txtFixa = new Text(groupBroker, SWT.BORDER);
        txtFixa.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        new Label(dialogShell, SWT.NONE);

        final Button nextBtn = new Button(dialogShell, SWT.NONE);
        nextBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        nextBtn.setText("Cadastrar");

        nextBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent arg0) {

                try {

                    fillAndRegisterStockBroker();

                    Broker broker = new Broker();

                    StockBroker stockBrokeradded=fillAndRegisterBroker(broker);

                    TwUser user = new PopulateData().populateUser(
                            stockBrokeradded
                            .getName(),
                            stockBrokeradded.getPassword(),
                            "",
                            stockBrokeradded
                            .getEmail(),
                            stockBrokeradded.getTelefone());

                    new AccountFacade().addAccount(user, stockBrokeradded, broker
                            .getName(), "1");

                    dialogShell.removeListener(SWT.Close, closeListener);
                    dialogShell.close();

                } catch (Exception e) {
                    DialogUtil.errorMessage(dialogShell, e.getMessage(), e
                            .getMessage());
                }

            }


        });

        dialogShell.open();
        Display display = dialogShell.getDisplay();
        while (!dialogShell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }

    }

    private StockBroker fillAndRegisterBroker(Broker broker) throws CoreException {
        if (StringCheck.isEmpty(txtCorretora.getText())) {
            throw new UIException(9300);
        }

        StockBroker logged = ContextHolderUI.getStockBrokerLogged();
        StockBroker added = action.addStockBroker(logged);
        ContextHolderUI.setStockBrokerSelected(added);

        broker.setStockBroker(added);

        /* Set<Broker> brokers = added.getBrokers();

        if (logged.getBrokers() == null) {
            brokers = new TreeSet<Broker>();
        }

        if (!brokers.contains(broker)) {
            brokers.add(broker);
        }*/

        broker.setName(txtCorretora.getText());

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
            .errorMessage(
                    dialogShell,
                    "Dados Inválidos",
                    "O formato dos valores está incorreto. Favor corrigir e tentar novamente.");
            return null;
        }
        broker.setTax(tax);

        action.addBroker(broker);

        return added;
    }

    private void fillAndRegisterStockBroker() {
        StockBroker stb = new StockBroker();
        stb.setName(nomeTxt.getText());
        stb.setEmail(emailTxt.getText());
        stb.setUsername(username.getText());
        stb.setPassword(passwd.getText());
        ContextHolder.setStockBrokerSelected(stb);
    }

    private void createWizardNoticeScreen(final Shell dialogShell) {
        dialogShell.setLayout(new GridLayout(1, false));

        Label lblNewLabel = new Label(dialogShell, SWT.NONE);
        lblNewLabel.setFont(org.eclipse.wb.swt.SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
        lblNewLabel.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblNewLabel.setText("TwoCents Cadastro");

        Label lblNewLabel_4 = new Label(dialogShell, SWT.NONE);
        lblNewLabel_4.setFont(org.eclipse.wb.swt.SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
        lblNewLabel_4.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblNewLabel_4.setText("Informa\u00E7\u00F5es sobre usu\u00E1rio :");

        Group group = new Group(dialogShell, SWT.NONE);
        group.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));
        group.setLayout(new GridLayout(2, false));
        group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));


        Label nomeLbl = new Label(group, SWT.NONE);
        nomeLbl.setFont(org.eclipse.wb.swt.SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
        nomeLbl.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));
        nomeLbl.setText("Nome :");

        nomeTxt = new Text(group, SWT.BORDER);
        GridData gd_nomeTxt = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_nomeTxt.widthHint = 244;
        nomeTxt.setLayoutData(gd_nomeTxt);

        Label lblEmail = new Label(group, SWT.NONE);
        lblEmail.setFont(org.eclipse.wb.swt.SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
        lblEmail.setText("E-mail :");
        lblEmail.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));

        emailTxt = new Text(group, SWT.BORDER);
        GridData gd_emailTxt = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        gd_emailTxt.widthHint = 236;
        emailTxt.setLayoutData(gd_emailTxt);

        Label lblLogin = new Label(group, SWT.NONE);
        lblLogin.setFont(org.eclipse.wb.swt.SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
        lblLogin.setText("Login :");
        lblLogin.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));

        username = new Text(group, SWT.BORDER);
        username.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        Label lblSenha = new Label(group, SWT.NONE);
        lblSenha.setFont(org.eclipse.wb.swt.SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
        lblSenha.setText("Senha :");
        lblSenha.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));

        passwd = new Text(group, SWT.BORDER | SWT.PASSWORD);
        passwd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    }

    public TwWizard(final Shell parent) {
        super(parent);
        action = new WizardAction();

        currencyFormatter = DecimalFormat.getCurrencyInstance(Locale
                .getDefault());

        numberFormatter = DecimalFormat.getInstance(Locale.getDefault());
        ((DecimalFormat) numberFormatter).setDecimalSeparatorAlwaysShown(true);
        ((DecimalFormat) numberFormatter).setMinimumIntegerDigits(1);
        ((DecimalFormat) numberFormatter).setMinimumFractionDigits(2);
        ((DecimalFormat) numberFormatter).setParseBigDecimal(true);

    }
}
