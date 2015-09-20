package com.twocents.ui.client.components.google;

import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.nebula.widgets.pshelf.RedmondShelfRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.nebula.widgets.cdatetime.CButton;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.ui.client.common.action.PanelCompositeAction;

public class GoogleComposite extends PanelCompositeAction {
	public static final String CONECTAR = "Conectar";
	public static final String DESCONECTAR = "Desconectar";

	private Text username;
	
	private Text password;
	private GoogleCompositeAction action;
	private Button saveData;
	private Button recoverData;
	private Button connect;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public GoogleComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		//setSize(150, 200);
		setBounds(0,0,150,200);
		
		PShelf shelf = new PShelf(this, SWT.NONE);
		shelf.setRenderer(new RedmondShelfRenderer());	

		PShelfItem shelfItem = new PShelfItem(shelf, SWT.NONE);
		shelfItem.setText("Conta Google - Sincronizar dados");
		shelfItem.getBody().setLayout(new BorderLayout(0, 0));
		
		Composite composite_1 = new Composite(shelfItem.getBody(), SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		composite_1.setLayout(new BorderLayout(0, 0));
		
		Composite compositeHeader = new Composite(composite_1, SWT.NONE);
		compositeHeader.setLayoutData(BorderLayout.NORTH);
		compositeHeader.setLayout(new RowLayout(SWT.VERTICAL));
		
		Composite composite = new Composite(composite_1, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);
		composite.setLayout(new BorderLayout(0, 0));
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayoutData(BorderLayout.WEST);
		composite_2.setLayout(new BorderLayout(0, 0));
		
		Composite composite_4 = new Composite(composite_2, SWT.NONE);
		composite_4.setLayoutData(BorderLayout.NORTH);
		FillLayout fl_composite_4 = new FillLayout(SWT.VERTICAL);
		fl_composite_4.marginHeight = 25;
		composite_4.setLayout(fl_composite_4);
		
		Label label = new Label(composite_4, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(GoogleComposite.class, "/com/twocents/ui/resources/images/TwoCents-GmailRepository_Icon_48X48.png"));
		
		Composite composite_3 = new Composite(composite, SWT.NONE);
		composite_3.setLayoutData(BorderLayout.CENTER);
		composite_3.setLayout(new BorderLayout(0, 0));
		
		Composite composite_5 = new Composite(composite_3, SWT.NONE);
		composite_5.setLayoutData(BorderLayout.NORTH);
		FillLayout fl_composite_5 = new FillLayout(SWT.VERTICAL);
		fl_composite_5.marginWidth = 5;
		composite_5.setLayout(fl_composite_5);
		
		Label lblUsurio = new Label(composite_5, SWT.NONE);
		lblUsurio.setText("Usu\u00E1rio :");
		
		username = new Text(composite_5, SWT.BORDER);
		
		Label lblSenha = new Label(composite_5, SWT.NONE);
		lblSenha.setText("Senha :");
		
		password = new Text(composite_5, SWT.BORDER | SWT.PASSWORD);
		
		
		//UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_BOTAO_CONECTAR01_PNG
		
		setConnect(new Button(composite_5, SWT.NONE));
		getConnect().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				getAction().connect(username.getText(),password.getText());
				
			}
		});
		getConnect().setText(CONECTAR);
		
		Composite composite_7 = new Composite(composite_3, SWT.NONE);
		composite_7.setLayoutData(BorderLayout.SOUTH);
		composite_7.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Composite composite_8 = new Composite(composite_3, SWT.NONE);
		composite_8.setLayoutData(BorderLayout.CENTER);
		FillLayout fl_composite_8 = new FillLayout(SWT.VERTICAL);
		fl_composite_8.marginWidth = 5;
		composite_8.setLayout(fl_composite_8);
		
		Composite composite_6 = new Composite(composite_8, SWT.NONE);
		
		setSaveData(new Button(composite_8, SWT.NONE));
		getSaveData().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				getAction().saveData();
				
			}
		});
		getSaveData().setText("Salvar dados");
		
		getSaveData().setVisible(false);
		
		setRecoverData(new Button(composite_8, SWT.NONE));
		getRecoverData().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getAction().recoverData();
			}
		});
		getRecoverData().setText("Recuperar dados");
		getRecoverData().setVisible(false);
		Composite composite_9 = new Composite(composite_8, SWT.NONE);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public GoogleCompositeAction getAction() {

		if(action==null){
			action = new GoogleCompositeAction(this);
		}
		
		return action;
	}

	public void setSaveData(Button saveData) {
		this.saveData = saveData;
	}

	public Button getSaveData() {
		return saveData;
	}

	public void setRecoverData(Button recoverData) {
		this.recoverData = recoverData;
	}

	public Button getRecoverData() {
		return recoverData;
	}

	public void setConnect(Button connect) {
		this.connect = connect;
	}

	public Button getConnect() {
		return connect;
	}
}
