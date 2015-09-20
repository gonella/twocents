package com.twocents.ui.client.components.export;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.ui.client.common.windows.PanelCompositeBase;
import com.twocents.ui.client.components.FileDialogOpen;
import com.twocents.ui.client.components.export.action.ImportCompositeAction;

public class ImportComposite extends PanelCompositeBase {
	private Table table;
	private Text path;

	private final ImportCompositeAction action=new ImportCompositeAction(this);
	private Label accountSelected;
	
	public ImportComposite(Composite parent) {
		super(parent);
		setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(BorderLayout.NORTH);
		composite.setLayout(new GridLayout(1, false));
		
		Composite composite_3 = new Composite(composite, SWT.NONE);
		composite_3.setLayout(new GridLayout(2, false));
		
		Label lblContaSelecionada = new Label(composite_3, SWT.NONE);
		lblContaSelecionada.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblContaSelecionada.setText("Conta Selecionada :");
		
		setAccountSelected(new Label(composite_3, SWT.NONE));
		getAccountSelected().setText("ACCOUNT_SELECTED");
		
		Label lblOsArquivosXls = new Label(composite, SWT.NONE);
		lblOsArquivosXls.setText("OBS : O arquivo XLS(At\u00E9 Excel 2007) deve ter o formato abaixo:");
		
		Label lblData = new Label(composite, SWT.NONE);
		lblData.setText("                            DATA | ATIVO | TIPO DE OPERA\u00C7\u00C3O | QUANTIDADE | PRE\u00C7O | CODIGO BOVESPA | CORRETORA");
		
		Label lblExemplos = new Label(composite, SWT.NONE);
		lblExemplos.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblExemplos.setText("Exemplos:");
		
		Label lblData_1 = new Label(composite, SWT.NONE);
		lblData_1.setText("DATA - 20/12/2010 ou 20/12/2010 14:00:00");
		
		Label lblTipoDeOperao = new Label(composite, SWT.NONE);
		lblTipoDeOperao.setText("TIPO DE OPERA\u00C7\u00C3O - COMPRA ou VENDA");
		
		Label lblPreo = new Label(composite, SWT.NONE);
		lblPreo.setText("PRE\u00C7O - 58,5");
		
		Label lblCorretoraNome = new Label(composite, SWT.NONE);
		lblCorretoraNome.setText("CORRETORA - Nome da corretora cadastrada no sistema, ver em configura\u00E7\u00F5es.");
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		composite_1.setLayout(new BorderLayout(0, 0));
		
		Composite composite_2 = new Composite(composite_1, SWT.BORDER);
		composite_2.setLayoutData(BorderLayout.NORTH);
		composite_2.setLayout(new GridLayout(4, false));
		
		Label lblArquivoXls = new Label(composite_2, SWT.NONE);
		lblArquivoXls.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblArquivoXls.setText("Arquivo XLS ou CSV");
		
		setPath(new Text(composite_2, SWT.BORDER));
		getPath().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnProcurar = new Button(composite_2, SWT.NONE);
		btnProcurar.setText("...");
		
		btnProcurar.addSelectionListener(new FileDialogOpen(this.getShell(),"Abrir"){


			@Override
			public void process(String fileName) {
				getPath().setText(fileName);
				
			}
		});
		//saveItem.addSelectionListener(new Save());

		
		Button btnImportar = new Button(composite_2, SWT.NONE);
		btnImportar.setText("Importar");
		
		btnImportar.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		    	  getAction().importFile(getPath().getText());
		      }
			}
		);
		
		setTable(new Table(composite_1, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL));
		getTable().setLayoutData(BorderLayout.CENTER);
		getTable().setHeaderVisible(true);
		getTable().setLinesVisible(true);
		
		TableColumn tblclmnId = new TableColumn(getTable(), SWT.CENTER);
		tblclmnId.setWidth(100);
		tblclmnId.setText("Id Operação");
		
		TableColumn tblclmnStatus = new TableColumn(getTable(), SWT.CENTER);
		tblclmnStatus.setWidth(100);
		tblclmnStatus.setText("Status");
		
		TableColumn tblclmnDetalhe = new TableColumn(getTable(), SWT.LEFT);
		int width = this.getClientArea().width - tblclmnId.getWidth() - tblclmnStatus.getWidth();
	    
		tblclmnDetalhe.setWidth(480);
		tblclmnDetalhe.setText("Detalhes");
		
		
		getAction().init();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void createComponents(Composite parent) {
		
	}

	@Override
	public void initAction() {
		
	}

	

	public ImportCompositeAction getAction() {
		return action;
	}


	public void setPath(Text path) {
		this.path = path;
	}

	public Text getPath() {
		return path;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Table getTable() {
		return table;
	}

	public void setAccountSelected(Label accountSelected) {
		this.accountSelected = accountSelected;
		accountSelected.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
	}

	public Label getAccountSelected() {
		return accountSelected;
	}
}
