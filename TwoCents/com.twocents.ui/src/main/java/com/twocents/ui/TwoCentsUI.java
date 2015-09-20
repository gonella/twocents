package com.twocents.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

import com.swtdesigner.SWTResourceManager;
import com.twocents.context.ContextHolder;
import com.twocents.environment.Environment;
import com.twocents.exceptions.CoreException;
import com.twocents.ui.client.TwOperationUI;
import com.twocents.ui.client.TwReportUI;
import com.twocents.ui.client.action.TwoCentsUIAction;
import com.twocents.ui.client.common.windows.ConfigurationUIDialog;
import com.twocents.ui.client.common.windows.DefaultDialog;
import com.twocents.ui.client.common.windows.FormBase;
import com.twocents.ui.client.components.StockBrokerAccountComposite;
import com.twocents.ui.client.components.TwButtonText;
import com.twocents.ui.client.components.TwLogin;
import com.twocents.ui.client.components.TwSplashScreen;
import com.twocents.ui.client.components.export.ExportComposite;
import com.twocents.ui.client.components.export.ImportComposite;
import com.twocents.ui.client.components.listener.UpdateUserAccountListener;
import com.twocents.ui.client.components.manage.ManageAccountComposite;
import com.twocents.ui.client.components.manage.ManageStockBrokerDialog;
import com.twocents.ui.client.components.wizard.TwWizard;
import com.twocents.ui.constant.UIDefault;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.handler.progress.threadprogress.ThreadProgress;
import com.twocents.ui.resources.UIImages;
import com.twocents.ui.resources.UIMessages;
import com.twocents.ui.util.DialogUtil;

import swing2swt.layout.BorderLayout;

public class TwoCentsUI extends FormBase {

    public static final int windowsHeight = 720;
    public static final int windowsWitdh = 1280;
    private static final String RELATORIOS_CUTOMIZADOS = "Relatórios customizados";
    private static final String OPERACOES_FINANCEIRAS = "Operações financeiras";

    private Logger logger = Logger.getLogger(this.getClass());

    private static Shell shell;
    private TwoCentsUIAction twoCentsUIAction;
    private TwButtonText buttonOperation;
    private TwButtonText buttonReport;
    private TwButtonText buttonClient;
    private TwButtonText buttonConfig;
    private TwButtonText buttonImport;
    private TwButtonText buttonExport;
    private TwButtonText buttonExit;
    private TwButtonText buttonQuote;
    private Composite containerComponents;
    private Composite mainComposite;
    private Composite compositeLeft;
    private Composite compositeCenter;
    private Composite compositeLeftTop;
    private Composite compositeLeftBotton;

    private TwLogin twLogin;
    private StockBrokerAccountComposite accountPanel;

    private TwReportUI reportUI;
    private TwOperationUI operationUI;
    private final String appVersion;
    private StackLayout stackLayout;
    private StackLayout stackLayoutLeftBotton;
    private TwButtonText buttonAbout;

    public TwoCentsUI(String appVersion) {
        super();
        this.appVersion = appVersion;
        ContextHolder.setTwRunningVersion(appVersion);

        logger.info(appVersion);

        new TwSplashScreen().start(this,
                UIDefault.N_STEP_PROGRESS_FOR__SPLASH_SCREEN);

    }

    public TwoCentsUIAction getEnvironmentAction() {
        return twoCentsUIAction;
    }

    @Override
    public void setEnvironmentAction(TwoCentsUIAction environmentAction) {
        this.twoCentsUIAction = environmentAction;
    }

    /**
     * Open the window.
     */
    @Override
    public void open() {
        Display display = Display.getDefault();

        createContents();

        // setTray(windows);
        // windows.addListener (SWT.KeyDown, listener);

        getShell().setLocation(100, 50);
        getShell().open();
        getShell().layout();

        while (!getShell().isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     *
     * @wbp.parser.entryPoint
     */
    protected void createContents() {

        // SWT.ON_TOP | SWT.SHELL_TRIM | SWT.CLOSE | SWT.TITLE
        setShell(new Shell(SWT.MIN));
        getShell().setLayout(new BorderLayout(0, 0));
        getShell().setSize(windowsWitdh, windowsHeight);
        getShell().setMinimumSize(windowsWitdh, windowsHeight);

        // put in the middle of screen
        Monitor primary = Display.getDefault().getPrimaryMonitor();
        Rectangle bounds = primary.getBounds();
        Rectangle rect = getShell().getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;

        getShell().setLocation(x, y);

        getShell().setText(
                Environment.TWO_CENTS_ENTERPRISE_FINANCE + getAppVersion());
        // shlTwoCents.setImage(SWTResourceManager.getImage(Main.class,"/com/twocents/ui/resources/images/logoTC.png"));
        getShell().setImage(
                SWTResourceManager.getImage(TwoCentsUI.class,
                        UIImages.MAIN_01_ICON_48));

        getShell().addDisposeListener(new DisposeListener() {
            public void widgetDisposed(final DisposeEvent arg0) {
                disposeTheApp();
            }

        });

        setContainerComponents(new Composite(getShell(), SWT.NONE));
        getContainerComponents().setBackgroundMode(SWT.INHERIT_DEFAULT);
        getContainerComponents().setLayout(new FillLayout(SWT.HORIZONTAL));
        getContainerComponents().setBackground(
                SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION_TEXT));
        getContainerComponents().setLayoutData(BorderLayout.CENTER);

        createHeaderComposite();

        this.displayWizard();

        createLoginComposite();
        // createWorkComposite(); //for test in windows builder design
    }

    public void displayWizard() {
        if (getEnvironmentAction().checkFirstAppUsage())
            new TwWizard(getShell()).open();
    }

    public void doLogon() {

        new ThreadProgress(getContainerComponents().getShell(),
                getContainerComponents().getShell()) {
            @Override
            public void execute() {

                getShell().getDisplay().syncExec(new Runnable() {

                    public void run() {

                        /*
                         * try { Thread.sleep(3000); } catch
                         * (InterruptedException e) { // TODO Auto-generated
                         * catch block e.printStackTrace(); }
                         */

                        doInstance();
                        doProcess();
                        doDisplay();

                        // TODO: isto tem que ser melhorado para ser um
                        // parametro de configuracao, pode demorar para executar
                        // FIXME: Esta deixando muito lento a app, deixa o
                        // usuário atualizar por conta propria, não vamos
                        // forçar.
                        /*
                         * try { getEnvironmentAction().doUpdateQuote(); } catch
                         * (Exception e) {logger.error(
                         * "Quotes update attempt failed, proceeding without this update (it must be performed manually"
                         * ); }
                         */

                    }
                });

            }

            @Override
            public void before() {
            }

            @Override
            public void after() {
            }

            @Override
            public void abort() {
            }
        };

    }

    public void doInstance() {

        createWorkComposite();

    }

    public void doProcess() {

        getOperationUI().getAction().doCollectData();
        getReportUI().getAction().doCollectData();
        getAccountPanel().getAction().doCollectData();
        getReportUI().getReportTableComposite().getAction().doCollectData();

    }

    public void doDisplay() {

        getOperationUI().getAction().init();
        getReportUI().getAction().init();
        getAccountPanel().getAction().init();
        getReportUI().getReportTableComposite().getAction().init();

        displayOperationUI();

        getContainerComponents().layout(true);
    }

    public void createWorkComposite() {

        if (getTwLogin() != null) {
            getTwLogin().dispose();
        }

        buttonReport.setEnabled(true);
        buttonOperation.setEnabled(true);
        buttonConfig.setEnabled(true);
        buttonClient.setEnabled(true);
        buttonImport.setEnabled(true);
        buttonExport.setEnabled(true);
        buttonQuote.setEnabled(true);
        buttonAbout.setEnabled(true);

        mainComposite = new Composite(getContainerComponents(), SWT.NONE);
        mainComposite.setLayout(new BorderLayout(0, 0));

        compositeLeft = new Composite(mainComposite, SWT.BORDER);
        compositeLeft.setLayoutData(BorderLayout.WEST);
        compositeLeft.setLayout(new FillLayout(SWT.VERTICAL));

        compositeLeftTop = new Composite(compositeLeft, SWT.NONE);
        compositeLeftTop.setLayout(new FillLayout(SWT.HORIZONTAL));

        compositeLeftBotton = new Composite(compositeLeft, SWT.NONE);
        // compositeLeftBotton.setBounds(0, 200, 200, 200);
        // compositeLeftBotton.setLayout(new FillLayout(SWT.HORIZONTAL));
        stackLayoutLeftBotton = new StackLayout();
        compositeLeftBotton.setLayout(stackLayoutLeftBotton);

        compositeCenter = new Composite(mainComposite, SWT.NONE);
        compositeCenter.setLayoutData(BorderLayout.CENTER);
        // compositeCenter.setLayout(new FillLayout());
        stackLayout = new StackLayout();
        compositeCenter.setLayout(stackLayout);
        addShortcut();

        setOperationUI(new TwOperationUI(containerComponents, compositeCenter,
                compositeLeftBotton, SWT.CENTER, OPERACOES_FINANCEIRAS));

        setReportUI(new TwReportUI(containerComponents, compositeCenter,
                compositeLeftBotton, SWT.CENTER, RELATORIOS_CUTOMIZADOS,
                compositeLeftBotton));

        StockBrokerAccountComposite acPanel = new StockBrokerAccountComposite(
                compositeLeftTop, getOperationUI(), getReportUI(), SWT.NONE);
        setAccountPanel(acPanel);

        getEnvironmentAction().setTwOperationUI(getOperationUI());
        getEnvironmentAction().setTwReportUI(getReportUI());
        getEnvironmentAction().setAccountPanel(acPanel);

    }

    private void displayOperationUI() {
        logger.info("Apresentando Painel de [Operações]");

        getOperationUI().setVisible(true);
        getReportUI().setVisible(false);
        stackLayout.topControl = getOperationUI();

        getReportUI().getReportTableComposite().setVisible(false);
        // getGoogleDocComposite().setVisible(true);
        getOperationUI().getWindowsAdvertisingInOperation().setVisible(true);
        // stackLayoutLeftBotton.topControl = getGoogleDocComposite();
        stackLayoutLeftBotton.topControl = getOperationUI()
                .getWindowsAdvertisingInOperation();

    }

    private void displayReportUI() {
        logger.info("Apresentando Painel de [Relatórios]");

        getOperationUI().setVisible(false);
        getReportUI().setVisible(true);
        stackLayout.topControl = getReportUI();

        getReportUI().getReportTableComposite().setVisible(true);
        // getGoogleDocComposite().setVisible(false);
        getOperationUI().getWindowsAdvertisingInOperation().setVisible(false);

        stackLayoutLeftBotton.topControl = getReportUI()
                .getReportTableComposite();

    }

    public void createHeaderComposite() {
        Canvas canvas = new Canvas(getShell(), SWT.NONE);
        canvas.setLayout(new BorderLayout(0, 0));
        canvas.setLayoutData(BorderLayout.NORTH);
        canvas.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_LIST_SELECTION));
        canvas.setBackgroundImage(SWTResourceManager.getImage(TwoCentsUI.class,
                UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_BANIERE_FOND_PNG));
        canvas.setBackgroundMode(SWT.INHERIT_DEFAULT);

        Canvas canvas_1 = new Canvas(canvas, SWT.NONE);
        canvas_1.setBackgroundMode(SWT.INHERIT_DEFAULT);
        canvas_1.setLayout(null);

        canvas_1.setLayoutData(BorderLayout.CENTER);

        buttonOperation = new TwButtonText(canvas_1, SWT.NONE) {
            @Override
            public void buttonClick() {
                displayOperationUI();
            }
        };

        buttonOperation.setButtonTextColor(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        buttonOperation.setBounds(10, 10, 103, 83);
        buttonOperation.setButtonImage(SWTResourceManager.getImage(
                TwoCentsUI.class,
                UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_STOCK02_48X48_PNG));
        buttonOperation.setButtonText("Operações");

        buttonReport = new TwButtonText(canvas_1, SWT.NONE) {
            @Override
            public void buttonClick() {
                displayReportUI();
            }
        };
        buttonReport.setButtonTextColor(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        buttonReport.setBounds(119, 10, 103, 83);
        buttonReport.setButtonImage(SWTResourceManager.getImage(
                TwoCentsUI.class,
                UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_REPORT01_48X48_PNG));
        buttonReport.setButtonText("Relatórios");

        buttonClient = new TwButtonText(canvas_1, SWT.NONE) {
            @Override
            public void buttonClick() {
                displayManageAccount(getShell());
            }
        };

        buttonClient.setButtonTextColor(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        buttonClient.setButtonText("Contas");
        buttonClient.setBounds(337, 10, 103, 83);
        buttonClient.setButtonImage(SWTResourceManager.getImage(
                TwoCentsUI.class,
                UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_USERS_48X48_PNG));

        buttonConfig = new TwButtonText(canvas_1, SWT.NONE) {
            @Override
            public void buttonClick() {
                displayConfigurationDialog();
            }
        };

        buttonConfig.setButtonTextColor(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        buttonConfig.setBounds(228, 10, 103, 83);
        buttonConfig.setButtonText("Configurações");
        buttonConfig
        .setButtonImage(SWTResourceManager
                .getImage(
                        TwoCentsUI.class,
                        UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_CONFIGURATION01_48X48_PNG));

        buttonImport = new TwButtonText(canvas_1, SWT.NONE) {
            @Override
            public void buttonClick() {
                displayDialogImport(getShell());
            }

        };

        buttonImport.setButtonTextColor(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        buttonImport.setButtonText("Importar");
        buttonImport.setButtonImage(SWTResourceManager.getImage(
                TwoCentsUI.class,
                UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_IMPORT04_48X48_PNG));
        buttonImport.setBounds(446, 10, 103, 83);

        Label label = new Label(canvas_1, SWT.NONE);
        label.setBounds(0, 10, 49, 97);

        buttonExport = new TwButtonText(canvas_1, SWT.NONE) {
            @Override
            public void buttonClick() {
                displayDialogExport(getShell());
            }

        };
        buttonExport.setBounds(555, 10, 103, 83);
        buttonExport.setButtonTextColor(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        buttonExport.setButtonText("Exportar");
        buttonExport
        .setButtonImage(SWTResourceManager
                .getImage(
                        TwoCentsUI.class,
                        UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_EXPORT_DATA03_48X48_PNG));

        buttonQuote = new TwButtonText(canvas_1, SWT.NONE) {
            @Override
            public void buttonClick() {
                displayUpdateQuote();
            }
        };

        buttonQuote.setBounds(664, 10, 103, 83);
        buttonQuote.setButtonTextColor(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        buttonQuote.setButtonText("Atualizar Ativos");
        buttonQuote.setButtonImage(SWTResourceManager.getImage(
                TwoCentsUI.class,
                UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_UPDATE01_48X48_PNG));

        buttonAbout = new TwButtonText(canvas_1, SWT.NONE) {
            @Override
            public void buttonClick() {
                displayAbout();
            }
        };
        buttonAbout.setBounds(786, 10, 103, 83);
        buttonAbout.setButtonTextColor(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        buttonAbout.setButtonText("Sobre");
        buttonAbout.setButtonImage(SWTResourceManager.getImage(
                TwoCentsUI.class,
                UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_ABOUT01_48X48_PNG));

        buttonExit = new TwButtonText(canvas_1, SWT.NONE) {
            @Override
            public void buttonClick() {

                getDisplay().close();
                dispose();
            }
        };

        buttonExit.setBounds(908, 10, 103, 83);
        buttonExit.setButtonTextColor(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        buttonExit.setButtonText("Sair");
        buttonExit.setButtonImage(SWTResourceManager.getImage(TwoCentsUI.class,
                UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_EXIT01_48X48_PNG));

        Canvas canvas_2 = new Canvas(canvas, SWT.NONE);
        canvas_2.setLayout(new FillLayout(SWT.HORIZONTAL));
        canvas_2.setForeground(SWTResourceManager.getColor(230, 230, 250));
        canvas_2.setLayoutData(BorderLayout.SOUTH);
        canvas_2.setBackgroundImage(SWTResourceManager.getImage(
                TwoCentsUI.class,
                UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_MENU_FOND_PNG));

        new ToolBar(canvas_2, SWT.RIGHT);

        buttonReport.setEnabled(false);
        buttonOperation.setEnabled(false);

        buttonConfig.setEnabled(false);
        buttonClient.setEnabled(false);
        buttonImport.setEnabled(false);
        buttonExport.setEnabled(false);
        buttonQuote.setEnabled(false);
        buttonAbout.setEnabled(false);
    }

    protected void displayAbout() {

        final DefaultDialog defaultDialog = new DefaultDialog(getShell(),
                "Sobre o TwoCents", 700, 550, true);
        // AboutComposite aboutComposite = new AboutComposite(defaultDialog
        // .getCompositeBody(), SWT.NONE);

        defaultDialog.open();
    }

    public void createLoginComposite() {
        setTwLogin(new TwLogin(getContainerComponents(), SWT.NONE) {

            @Override
            public void logar(MouseEvent event) {

                if (doLogin(this)) {
                    doLogon();

                } else {
                    logger.info("Credenciais informadas estão invalidas");
                }
            }

            @Override
            public void createStockBroker(MouseEvent event) {
                displayManageStockBroker(getShell());
            }
        });

        // getTwLogin().getUserTxt().setText(MockData.STOCK_BROKER01);
    }

    protected void displayManageAccount(Shell shell) {
        List<UpdateUserAccountListener> list = new ArrayList<UpdateUserAccountListener>();
        list.add(getAccountPanel());

        DefaultDialog defaultDialog = new DefaultDialog(shell,
                "Gerenciar Contas", 700, 550, true);

        ManageAccountComposite manageAccountComposite = new ManageAccountComposite(
                defaultDialog.getCompositeBody(), list);

        // try {
        // exportComposite.getAction().checkAccount();
        defaultDialog.setAction(manageAccountComposite.getAction());
        defaultDialog.open();

        /*
         * } catch (CoreException e) { logger.error(e); }
         */
    }

    protected void displayDialogExport(Shell shell) {
        DefaultDialog defaultDialog = new DefaultDialog(shell,
                "Exportar operações da conta", 550, 350, true);

        ExportComposite exportComposite = new ExportComposite(defaultDialog
                .getCompositeBody());

        try {
            exportComposite.getAction().checkAccount();
            defaultDialog.setAction(exportComposite.getAction());
            defaultDialog.open();
        } catch (CoreException e) {
            logger.error(e);
        }

    }

    protected void displayDialogImport(Shell shell) {
        DefaultDialog defaultDialog = new DefaultDialog(shell,
                "Importar operações externas", 700, 550, true);
        ImportComposite importComposite = new ImportComposite(defaultDialog
                .getCompositeBody());
        try {
            importComposite.getAction().checkAccount();
            defaultDialog.setAction(importComposite.getAction());
            defaultDialog.open();
        } catch (CoreException e) {
            logger.error(e);
        }
    }

    public boolean doLogin(TwLogin composite) {
        String pwd = composite.getPwdTxt().getText();
        String user = composite.getUserTxt().getText();
        try {
            getEnvironmentAction().doLogin(user, pwd);
        } catch (UIException e) {

            String message = UIMessages.getMessage(4011);
            String title = UIMessages.getMessage(4012);
            // http://java.sun.com/docs/books/tutorial/uiswing/components/dialog.html
            DialogUtil.errorMessage(composite, title, message);
            return false;
        }
        return true;
    }

    public void displayUpdateQuote() {

        logger.info("[Atualizando] todos os ativos da aplicação");

        new ThreadProgress(getShell(), getShell()) {

            @Override
            public void execute() {
                try {
                    getEnvironmentAction().doUpdateQuote();
                } catch (final UIException e) {
                    setFinished(true);

                    getShell().getDisplay().syncExec(new Runnable() {
                        public void run() {
                            DialogUtil.errorMessage(getShell(),
                                    "Erro na atualização dos ativos!", e
                                    .getLocalizedMessage());
                        }
                    });
                    getShell().getDisplay().wake();

                } finally {
                    setFinished(true);
                }

            }

            @Override
            public void before() {
            }

            @Override
            public void after() {
            }

            @Override
            public void abort() {
            }
        };

        logger.info("[Atulização] Concluida");
    }

    protected void disposeTheApp() {
        getEnvironmentAction().disposeTheApp();
    }

    protected void displayConfigurationDialog() {
        new ConfigurationUIDialog(this).open();
    }

    protected void displayManageStockBroker(Shell shell) {
        new ManageStockBrokerDialog(shell).open();
    }

    public static Shell getShell() {
        return shell;
    }

    public void setTray(Shell shell) {
        final Tray tray = shell.getDisplay().getSystemTray();
        if (tray == null) {
            System.out.println("The system tray is not available");
        } else {
            final TrayItem item = new TrayItem(tray, SWT.NONE);
            item.setToolTipText("SWT TrayItem");
            item.addListener(SWT.Show, new Listener() {
                public void handleEvent(Event event) {
                    System.out.println("show");
                }
            });
            item.addListener(SWT.Hide, new Listener() {
                public void handleEvent(Event event) {
                    System.out.println("hide");
                }
            });
            item.addListener(SWT.Selection, new Listener() {
                public void handleEvent(Event event) {
                    System.out.println("selection");
                }
            });
            item.addListener(SWT.DefaultSelection, new Listener() {
                public void handleEvent(Event event) {
                    System.out.println("default selection");
                }
            });
            final Menu menu = new Menu(shell, SWT.POP_UP);
            for (int i = 0; i < 8; i++) {
                MenuItem mi = new MenuItem(menu, SWT.PUSH);
                mi.setText("Item" + i);
                mi.addListener(SWT.Selection, new Listener() {
                    public void handleEvent(Event event) {
                        System.out.println("selection " + event.widget);
                    }
                });
                if (i == 0)
                    menu.setDefaultItem(mi);
            }
            item.addListener(SWT.MenuDetect, new Listener() {
                public void handleEvent(Event event) {
                    menu.setVisible(true);
                }
            });

            Image image = shell.getDisplay().getSystemImage(
                    SWT.ICON_INFORMATION);

            item.setImage(image);
        }
    }

    public void setTwLogin(TwLogin twLogin) {
        this.twLogin = twLogin;
    }

    public TwLogin getTwLogin() {
        return twLogin;
    }

    public void setAccountPanel(StockBrokerAccountComposite accountPanel) {
        this.accountPanel = accountPanel;
    }

    public StockBrokerAccountComposite getAccountPanel() {
        return accountPanel;
    }

    public void setReportUI(TwReportUI reportUI) {
        this.reportUI = reportUI;
    }

    public TwReportUI getReportUI() {

        return reportUI;
    }

    public void setOperationUI(TwOperationUI operationUI) {
        this.operationUI = operationUI;
    }

    public TwOperationUI getOperationUI() {

        return operationUI;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setContainerComponents(Composite containerComponents) {
        this.containerComponents = containerComponents;
    }

    public Composite getContainerComponents() {
        return containerComponents;
    }

    private void addShortcut() {
        Listener listener = new Listener() {
            public void handleEvent(Event event) {

                int type = event.type;

                if (type == SWT.KeyUp) {

                } else if (type == SWT.KeyDown) {

                    // logger.info("KEYCODE: "+event.keyCode);
                    if ((event.stateMask & SWT.CTRL) != 0
                            && event.keyCode == 49) {
                        logger.info("Pressionado CTRL+1");

                        displayOperationUI();

                    } else if ((event.stateMask & SWT.CTRL) != 0
                            && event.keyCode == 50) {
                        logger.info("Pressionado CTRL+2");

                        displayReportUI();
                    }

                }

            }
        };
        Display.getDefault().addFilter(SWT.KeyDown, listener);
    }

    public static void setShell(Shell shell) {
        TwoCentsUI.shell = shell;
    }

}
