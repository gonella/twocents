package com.twocents.ui.client.components.export;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import swing2swt.layout.BorderLayout;

import com.twocents.ui.client.components.FileDialogSave;
import com.twocents.ui.client.components.export.action.ExportCompositeAction;
import com.swtdesigner.SWTResourceManager;

public class ExportComposite extends Composite {
	private Text path;

	private final ExportCompositeAction action=new ExportCompositeAction(this);

	private Button btnXls;

	private Button btnCsv;

	private Label accountSelected;
	
	public ExportComposite(Composite parent) {
		super(parent, SWT.NONE);
		setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(BorderLayout.NORTH);
		composite.setLayout(new GridLayout(2, false));
		
		Label lblContaSelecionada = new Label(composite, SWT.NONE);
		lblContaSelecionada.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblContaSelecionada.setText("Conta Selecionada :");
		
		setAccountSelected(new Label(composite, SWT.NONE));
		getAccountSelected().setText("ACCOUNT_SELECTED");
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.SOUTH);
		
		Composite composite_2 = new Composite(this, SWT.NONE);
		composite_2.setLayoutData(BorderLayout.CENTER);
		composite_2.setLayout(new GridLayout(4, false));
		
		Label lblExportarParaO = new Label(composite_2, SWT.NONE);
		lblExportarParaO.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblExportarParaO.setText("Exportar para o arquivo :");
		
		setPath(new Text(composite_2, SWT.BORDER));
		getPath().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button button = new Button(composite_2, SWT.NONE);
		button.setText("...");
		
		button.addSelectionListener(new FileDialogSave(this.getShell(),"Digite o nome do arquivo"){

			@Override
			public void process(String fileName) {
				if(getPath()!=null){
					getPath().setText(fileName);
				}
				
			}
		});
		
		Button btnExportar = new Button(composite_2, SWT.NONE);
		btnExportar.setText("Exportar");
		
		btnExportar.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		    	  getAction().exportFile(getPath().getText());
		      }
			}
		);
		
		new Label(composite_2, SWT.NONE);
		
		setBtnXls(new Button(composite_2, SWT.RADIO));
		getBtnXls().setText("XLS");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		getBtnXls().setSelection(true);
		
		/*setBtnCsv(new Button(composite_2, SWT.RADIO));
		getBtnCsv().setText("CSV");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);*/
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public ExportCompositeAction getAction() {
		return action;
	}

	public void setPath(Text path) {
		this.path = path;
	}

	public Text getPath() {
		return path;
	}

	public void setBtnXls(Button btnXls) {
		this.btnXls = btnXls;
	}

	public Button getBtnXls() {
		return btnXls;
	}

	public void setBtnCsv(Button btnCsv) {
		this.btnCsv = btnCsv;
	}

	public Button getBtnCsv() {
		return btnCsv;
	}

	public void setAccountSelected(Label accountSelected) {
		this.accountSelected = accountSelected;
		accountSelected.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
	}

	public Label getAccountSelected() {
		return accountSelected;
	}
}
