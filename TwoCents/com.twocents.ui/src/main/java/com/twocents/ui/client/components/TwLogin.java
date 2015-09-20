package com.twocents.ui.client.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.swtdesigner.SWTResourceManager;
import com.twocents.ui.constant.UIDefault;

import swing2swt.layout.BorderLayout;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class TwLogin extends Composite {

    public Text getUserTxt() {
        return userTxt;
    }

    public Text getPwdTxt() {
        return pwdTxt;
    }

    private Text userTxt;
    private Text pwdTxt;
    private Button moreStockBroker;
    private Button button1;
    private Button loginBtn;

    /**
     * Create the composite.
     *
     * @param parent
     * @param style
     */
    public TwLogin(Composite parent, int style) {
        super(parent, style);
        setLayout(new BorderLayout(0, 0));

        Canvas canvas = new Canvas(this, SWT.NONE);
        canvas.setBackgroundMode(SWT.INHERIT_DEFAULT);
        canvas.setLayoutData(BorderLayout.CENTER);
        canvas.setBackgroundImage(SWTResourceManager.getImage(TwLogin.class,
                UIDefault.COM_TWOCENTS_UI_RESOURCES_IMAGES_LOGIN_SCREEN_PNG));
        canvas.setLayout(new GridLayout(6, false));

        int x = (getDisplay().getClientArea().width - 300) / 2;
        int y = (getDisplay().getClientArea().height - 500) / 2;
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        new Label(canvas, SWT.NONE);
        Canvas canvas_1 = new Canvas(canvas, SWT.NONE);

        Label Login = new Label(canvas_1, SWT.NONE);
        Login.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.BOLD));
        Login.setBounds(31, 22, 49, 13);
        Login.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
        Login.setText("Usu\u00E1rio:");

        setUserTxt(new Text(canvas_1, SWT.BORDER));
        getUserTxt().setBounds(86, 22, 174, 19);

        getUserTxt().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                switch (event.keyCode) {
                    case SWT.CR:
                        logar(null);
                        break;
                }
            }
        });

        Label lblSenha = new Label(canvas_1, SWT.NONE);
        lblSenha.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.BOLD));
        lblSenha.setBounds(31, 56, 49, 13);
        lblSenha.setText("Senha:");
        lblSenha.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
        pwdTxt = new Text(canvas_1, SWT.BORDER | SWT.PASSWORD);
        pwdTxt.setBounds(86, 50, 174, 19);

        pwdTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                switch (event.keyCode) {
                    case SWT.CR:
                        logar(null);
                        break;
                }
            }
        });

        loginBtn = new Button(canvas_1, SWT.NONE);
        loginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent arg0) {
                logar(arg0);
            }
        });
        loginBtn.setBounds(106, 107, 75, 25);
        loginBtn.setText("Logar");

        Button pwdBtn = new Button(canvas_1, SWT.NONE);
        pwdBtn.setBounds(187, 107, 75, 25);
        pwdBtn.setText("Cancelar");
        pwdBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent arg0) {
                getDisplay().close();
                dispose();
            }
        });

        button1 = new Button(canvas_1, SWT.CHECK | SWT.LEFT);
        button1.setText("Salvar credenciais");
        button1.setBounds(86, 71, 116, 30);

        moreStockBroker = new Button(canvas_1, SWT.PUSH | SWT.CENTER);
        moreStockBroker.setText("+");
        moreStockBroker.setBounds(31, 107, 32, 25);
        moreStockBroker.setToolTipText("Criar um novo corretor");
        moreStockBroker.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent arg0) {
                createStockBroker(arg0);
            }
        });

        //getUserTxt().setText("gonella");

    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

    public void logar(MouseEvent event) {
    }

    public void createStockBroker(MouseEvent event) {
    }

    public void setUserTxt(Text userTxt) {
        this.userTxt = userTxt;
    }

}
