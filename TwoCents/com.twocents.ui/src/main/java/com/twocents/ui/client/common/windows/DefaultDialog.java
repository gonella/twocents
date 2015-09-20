package com.twocents.ui.client.common.windows;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.ui.client.common.action.UIDialogAction;

public class DefaultDialog extends Dialog {

	private static final String FECHAR = 	"    Fechar    ";

	private static final String CANCELAR = 	"  Cancelar  ";

	private static final String OK = 		"        Ok        ";


	private static Logger logger = Logger.getLogger(DefaultDialog.class);

	
	private int windowHeight = 550;
	private int windowWidth = 700;
	private Label header;
	private Button cancelarButton;
	private Button okButton;
	protected Object result;
	private Shell shellDialog;
	private final Shell parent;
	private Composite configurationPanelComposite;
	private Composite compositeBody;
	private Composite mainView;
	private final String title;

	private UIDialogAction action;
	private Composite composite_1;
	private Composite composite_2;
	private Composite composite_3;
	private Composite composite_5;
	private Composite composite_6;
	private Composite composite_4;
	
	public Composite getConfigurationPanelComposite() {
		return configurationPanelComposite;
	}
	

	/**
	 * Create the dialog
	 * @param parent
	 * @param style
	 */
	/*public DefaultDialog(Shell parent, int style) {
		super(parent, style);
		this.parent = parent;

		//setCompositeMain(new Composite(getShellDialog(), SWT.BORDER));

	}*/
	
	public DefaultDialog(Shell parent,String title,int windowWidth,int windowHeight,boolean isCloseWindows) {
		this(parent, title,windowWidth,windowHeight);
		
		if(isCloseWindows){
			getOkButton().setVisible(false);
			getCancelarButton().setText(FECHAR);
		}
		
	}
	
	public DefaultDialog(Shell parent,String title,int windowWidth,int windowHeight) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.parent = parent;
		this.title = title;
		
		this.windowWidth=windowWidth;
		this.windowHeight=windowHeight;
		createContents();

	}

	
	/**
	 * Open the dialog
	 * @return the result
	 */
	public Object open() {

		getShellDialog().open();
		getShellDialog().layout();
		Display display = getParent().getDisplay();
		while (!getShellDialog().isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		setShellDialog(new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL));
		getShellDialog().setLayout(new BorderLayout(0, 0));
		//shell.setSize(676, 500);
		
		/*int positionX=parent.getBounds().x+(parent.getBounds().width /4);
		getShellDialog().setBounds(positionX,parent.getBounds().y+100,windowWidth,windowHeight);
		*/
		int widthTmp=(parent.getBounds().width-windowWidth)/2;
		int heightTmp=(parent.getBounds().height - windowHeight)/2;
		
		int positionX=parent.getBounds().x+(widthTmp);
		int positionY=parent.getBounds().y+(heightTmp);
		getShellDialog().setBounds(positionX,positionY,windowWidth,windowHeight);
		
		//getShellDialog().setText("Configurações");

		Composite composite=new Composite(getShellDialog(), SWT.BORDER);
		composite.setLayoutData(BorderLayout.SOUTH);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.VERTICAL));
		
		composite_3 = new Composite(composite_2, SWT.NONE);
		composite_3.setLayout(new FillLayout(SWT.VERTICAL));
		
		composite_5 = new Composite(composite_3, SWT.NONE);
		composite_5.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		composite_6 = new Composite(composite_5, SWT.NONE);
		composite_6.setLayout(new BorderLayout(0, 0));
		
		composite_4 = new Composite(composite_6, SWT.NONE);
		composite_4.setLayoutData(BorderLayout.EAST);
		RowLayout rl_composite_4 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_4.marginRight = 30;
		rl_composite_4.marginTop = 20;
		composite_4.setLayout(rl_composite_4);
		

		okButton = new Button(composite_4, SWT.CENTER);
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				pressCancelButton();
			}
		});
		okButton.setText(OK);
		
				cancelarButton = new Button(composite_4, SWT.NONE);
				cancelarButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(final SelectionEvent arg0) {
						pressOkButton();
					}
				});
				cancelarButton.setText(CANCELAR);
		
		rebuildCompositePanel();
		
		initComponents();
	}
	
	protected void pressOkButton() {
		
		try {
			getAction().pressOkButton();
		} catch (Exception e) {
			logger.debug(e);
		}		
	}

	protected void pressCancelButton() {
		try {
			getAction().pressCancelButton();
		} catch (Exception e) {
			logger.debug(e);
		}		
	}

	public Composite rebuildCompositePanel(){
		configurationPanelComposite = new Composite(getShellDialog(), SWT.NONE);
		configurationPanelComposite.setLayout(new BorderLayout(0, 0));
		configurationPanelComposite.setLayoutData(BorderLayout.CENTER);

		final Composite configCompositeHeader = new Composite(configurationPanelComposite, SWT.BORDER);
		configCompositeHeader.setLayoutData(BorderLayout.NORTH);
		configCompositeHeader.setLayout(new BorderLayout(0, 0));

		header = new Label(configCompositeHeader, SWT.NONE);
		header.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.BOLD));
		header.setLayoutData(BorderLayout.NORTH);
		header.setText(title);
		
		setCompositeBody(new Composite(configurationPanelComposite, SWT.NONE));
		getCompositeBody().setLayout(new BorderLayout(0, 0));
		getCompositeBody().setLayoutData(BorderLayout.CENTER);
		
		return getCompositeBody();
	}
	



	private void initComponents() {
		//action = new ConfigurationAction(this);
	}
	public Button getOkButton() {
		return okButton;
	}
	public Button getCancelarButton() {
		return cancelarButton;
	}
	public Label getHeader() {
		return header;
	}

	public void setShellDialog(Shell shellDialog) {
		this.shellDialog = shellDialog;
	}

	public Shell getShellDialog() {
		return shellDialog;
	}

	public void setMainView(Composite mainView) {
		this.mainView = mainView;
	}

	public Composite getMainView() {
		return mainView;
	}

	public void setCompositeBody(Composite compositeBody) {
		this.compositeBody = compositeBody;
	}

	public Composite getCompositeBody() {
		return compositeBody;
	}



	public void setAction(UIDialogAction action) {
		this.action = action;
	}



	public UIDialogAction getAction() throws Exception {
		
		if(action==null){
			throw new Exception("É necessário associar uma ACTION para o dialog");
		}
		
		return action;
	}
}
