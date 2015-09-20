package com.twocents.ui.client.components.wizard;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.swtdesigner.SWTResourceManager;
import com.twocents.context.ContextHolder;
import com.twocents.model.StockBroker;
import com.twocents.ui.constant.UIDefault;
import com.twocents.ui.util.DialogUtil;

public class TwWizard extends Dialog {

    private WizardAction action;
    private Text nomeTxt;
    private Text emailTxt;
    private Text username;
    private Text passwd;
    private Label lblPasso;

    private NumberFormat currencyFormatter;
    private NumberFormat numberFormatter;
    private Shell dialogShell;
    private CloseShellListener closeListener;

    public void open() {

        final Shell parent = getParent();
        dialogShell = new Shell(parent, SWT.CLOSE | SWT.TITLE
                | SWT.APPLICATION_MODAL);

        com.cloudgarden.resource.SWTResourceManager
        .registerResourceUser(dialogShell);

        closeListener = new CloseShellListener(dialogShell);
        dialogShell.addListener(SWT.Close, closeListener);

        dialogShell.layout();
        dialogShell.pack();
        dialogShell.setText("TwoCents Wizard");
        dialogShell.setSize(557, 596);
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
        //createBrokerComponents(dialogShell);

        dialogShell.open();
        Display display = dialogShell.getDisplay();
        while (!dialogShell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }

    }

    private void createWizardNoticeScreen(final Shell dialogShell) {
        final Composite group = new Composite(dialogShell, SWT.NONE);
        group.setBounds(10, 82, 531, 344);

        Label lblParabnsEObrigado = new Label(group, SWT.CENTER);
        lblParabnsEObrigado.setAlignment(SWT.LEFT);
        lblParabnsEObrigado
        .setText("Parab\u00E9ns e obrigado por utilizar o TwoCents!");
        lblParabnsEObrigado.setForeground(SWTResourceManager
                .getColor(SWT.COLOR_WHITE));
        lblParabnsEObrigado.setFont(SWTResourceManager.getFont("Tahoma", 11,
                SWT.NORMAL));
        lblParabnsEObrigado.setBounds(10, 10, 499, 27);

        Label lblASeguirSero = new Label(group, SWT.CENTER);
        lblASeguirSero
        .setText("A seguir ser\u00E3o solicitados a cria\u00E7\u00E3o de:");
        lblASeguirSero.setForeground(SWTResourceManager
                .getColor(SWT.COLOR_WHITE));
        lblASeguirSero.setFont(SWTResourceManager.getFont("Tahoma", 11,
                SWT.NORMAL));
        lblASeguirSero.setAlignment(SWT.LEFT);
        lblASeguirSero.setBounds(10, 43, 499, 27);

        Label lblUmaConta = new Label(group, SWT.CENTER);
        lblUmaConta.setText("1- Uma conta padr\u00E3o de usu\u00E1rio");
        lblUmaConta.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblUmaConta.setFont(SWTResourceManager.getFont("Tahoma", 12, SWT.BOLD));
        lblUmaConta.setAlignment(SWT.LEFT);
        lblUmaConta.setBounds(32, 83, 499, 48);

        Label lblUmaCorretora = new Label(group, SWT.CENTER);
        lblUmaCorretora.setText("2- Uma corretora de valores");
        lblUmaCorretora.setForeground(SWTResourceManager
                .getColor(SWT.COLOR_WHITE));
        lblUmaCorretora.setFont(SWTResourceManager.getFont("Tahoma", 12,
                SWT.BOLD));
        lblUmaCorretora.setAlignment(SWT.LEFT);
        lblUmaCorretora.setBounds(32, 182, 499, 51);

        Label lblEstaContaSer = new Label(group, SWT.WRAP | SWT.CENTER);
        lblEstaContaSer
        .setText("Esta conta ser\u00E1 utilizada para exibi\u00E7\u00E3o e monitoramento de suas opera\u00E7\u00F5es na bolsa de valores");
        lblEstaContaSer.setForeground(SWTResourceManager
                .getColor(SWT.COLOR_WHITE));
        lblEstaContaSer.setFont(SWTResourceManager.getFont("Tahoma", 11,
                SWT.NORMAL));
        lblEstaContaSer.setAlignment(SWT.LEFT);
        lblEstaContaSer.setBounds(42, 137, 428, 35);

        Label lblNaCorretoraSer = new Label(group, SWT.WRAP | SWT.CENTER);
        lblNaCorretoraSer
        .setText("Na corretora ser\u00E1 definida a corretagem a ser paga por opera\u00E7\u00E3o");
        lblNaCorretoraSer.setForeground(SWTResourceManager
                .getColor(SWT.COLOR_WHITE));
        lblNaCorretoraSer.setFont(SWTResourceManager.getFont("Tahoma", 11,
                SWT.NORMAL));
        lblNaCorretoraSer.setAlignment(SWT.LEFT);
        lblNaCorretoraSer.setBounds(42, 233, 428, 35);

        lblPasso = new Label(dialogShell, SWT.CENTER);
        lblPasso.setFont(SWTResourceManager.getFont("Tahoma", 13, SWT.NORMAL));
        lblPasso.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblPasso.setBounds(27, 10, 499, 66);
        lblPasso
        .setText("Configura\u00E7\u00E3o de Primeiro Acceso ao TwoCents!");

        final Button nextBtn = new Button(dialogShell, SWT.NONE);
        nextBtn.setBounds(434, 507, 107, 33);
        nextBtn.setText("Pr\u00F3ximo");

        nextBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent arg0) {

                try {
                    nextBtn.setVisible(false);
                    group.setVisible(false);
                    createAddAccount(dialogShell);
                } catch (Exception e) {
                    DialogUtil.errorMessage(group, e.getMessage(), e
                            .getMessage());
                }

            }
        });
    }

    private void createAddAccount(final Shell dialogShell) {
        final Composite group = new Composite(dialogShell, SWT.NONE);
        group.setBounds(10, 111, 531, 194);

        lblPasso.setText("Passo 1 - Adicionar Conta Padr\u00E3o");

        Label nomeLbl = new Label(group, SWT.NONE);
        nomeLbl.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        nomeLbl.setBounds(24, 34, 55, 15);
        nomeLbl.setText("Nome");

        Label lblEmail = new Label(group, SWT.NONE);
        lblEmail.setText("E-mail");
        lblEmail.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblEmail.setBounds(24, 76, 55, 15);

        Label lblLogin = new Label(group, SWT.NONE);
        lblLogin.setText("Login");
        lblLogin.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblLogin.setBounds(24, 116, 55, 15);

        Label lblSenha = new Label(group, SWT.NONE);
        lblSenha.setText("Senha");
        lblSenha.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblSenha.setBounds(24, 151, 55, 15);

        nomeTxt = new Text(group, SWT.BORDER);
        nomeTxt.setBounds(87, 34, 208, 21);

        emailTxt = new Text(group, SWT.BORDER);
        emailTxt.setBounds(87, 73, 208, 21);

        username = new Text(group, SWT.BORDER);
        username.setBounds(85, 113, 120, 21);

        passwd = new Text(group, SWT.BORDER | SWT.PASSWORD);
        passwd.setBounds(85, 148, 120, 21);

        final Button nextBtn = new Button(dialogShell, SWT.NONE);
        nextBtn.setBounds(451, 522, 75, 25);
        nextBtn.setText("Pr\u00F3ximo");

        nextBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent arg0) {

                try {

                    StockBroker stb = new StockBroker();
                    stb.setName(nomeTxt.getText());
                    stb.setEmail(emailTxt.getText());
                    stb.setUsername(username.getText());
                    stb.setPassword(passwd.getText());

                    group.setVisible(false);
                    nextBtn.setVisible(false);

                    ContextHolder.setStockBrokerSelected(stb);

                    //createBrokerComponents(dialogShell);

                    TwAddBrokerInfo twAddBroker=new TwAddBrokerInfo(dialogShell, SWT.NONE,action,currencyFormatter,numberFormatter,lblPasso,closeListener);

                } catch (Exception e) {
                    DialogUtil.errorMessage(group, e.getMessage(), e
                            .getMessage());
                }

            }
        });
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
