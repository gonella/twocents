package com.twocents.ui.client.common.windows;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.ui.TwoCentsUI;
import com.twocents.ui.client.components.configuration.action.ConfigurationAction;

public class ConfigurationUIDialog extends Dialog {

	private Label header;
	private Button cancelarButton;
	private Button okButton;
	private Tree configurationTree;
	protected Object result;
	protected Shell shell;
	private final Shell parent;
	private ConfigurationAction action;
	private Composite configurationPanelComposite;
	private Composite configCompositeBody;
	private final TwoCentsUI twoCentsUI;



	public Composite getConfigCompositeBody() {
		return configCompositeBody;
	}

	public Shell getShell() {
		return shell;
	}

	public Tree getConfigurationTree() {
		return configurationTree;
	}

	public Composite getConfigurationPanelComposite() {
		return configurationPanelComposite;
	}

	public ConfigurationAction getAction() {
		return action;
	}

	/**
	 * Create the dialog
	 * @param twoCentsUI
	 * @param style
	 */
	public ConfigurationUIDialog(TwoCentsUI twoCentsUI, int style) {
		super(twoCentsUI.getShell(), style);
		this.twoCentsUI = twoCentsUI;
		this.parent = twoCentsUI.getShell();
	}

	/**
	 * Create the dialog
	 * @param twoCentsUI
	 */
	public ConfigurationUIDialog(TwoCentsUI twoCentsUI) {
		this(twoCentsUI, SWT.NONE);
	}

	/**
	 * Open the dialog
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setLayout(new BorderLayout(0, 0));
		//shell.setSize(676, 500);
		
		int positionX=parent.getBounds().x+(parent.getBounds().width /4);
		
		shell.setBounds(positionX,parent.getBounds().y+100,700,550);
		shell.setText("Configurações");
		
		configurationTree = new Tree(shell, SWT.BORDER);
		configurationTree.setLayoutData(BorderLayout.WEST);
		

		configurationTree.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {				
				displayConfigurationSelected((TreeItem)(e.item));				
			}
		});

		final Composite composite = new Composite(shell, SWT.BORDER);
		composite.setLayoutData(BorderLayout.SOUTH);

		/*okButton = new Button(composite, SWT.NONE);
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				dispatchOkForConfiguration();
			}
		});
		okButton.setText("Ok");
	*/
		cancelarButton = new Button(composite, SWT.NONE);
		cancelarButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				dispatchCancelForConfiguration();
			}
		});
		cancelarButton.setText("Fechar");
		final GroupLayout groupLayout = new GroupLayout(composite);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(GroupLayout.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(499, Short.MAX_VALUE)
					//.add(okButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.RELATED)
					.add(cancelarButton, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
					.addContainerGap()
					.add(groupLayout.createParallelGroup(GroupLayout.BASELINE)
						.add(cancelarButton)
						//.add(okButton)
						)
					.addContainerGap(30, Short.MAX_VALUE))
		);
		composite.setLayout(groupLayout);
		
		rebuildCompositePanel();
		
		initComponents();
				
		//
	}
	
	protected void dispatchCancelForConfiguration() {
		getAction().dispatchCancelForConfiguration();
	}

	protected void dispatchOkForConfiguration() {
		getAction().dispatchOkForConfiguration();		
	}

	public Composite rebuildCompositePanel(){
		configurationPanelComposite = new Composite(shell, SWT.NONE);
		configurationPanelComposite.setLayout(new BorderLayout(0, 0));
		configurationPanelComposite.setLayoutData(BorderLayout.CENTER);

		final Composite configCompositeHeader = new Composite(configurationPanelComposite, SWT.BORDER);
		configCompositeHeader.setLayoutData(BorderLayout.NORTH);
		configCompositeHeader.setLayout(new BorderLayout(0, 0));

		header = new Label(configCompositeHeader, SWT.NONE);
		header.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.BOLD));
		header.setLayoutData(BorderLayout.NORTH);
		header.setText("TwoCents Configurações");
		
		configCompositeBody = new Composite(configurationPanelComposite, SWT.NONE);
		configCompositeBody.setLayout(new BorderLayout(0, 0));
		configCompositeBody.setLayoutData(BorderLayout.CENTER);
		
		return configCompositeBody;
	}
	





	protected void displayConfigurationSelected(TreeItem treeItem) {
		getAction().displaySelected(treeItem);
		
	}

	private void initComponents() {
		action = new ConfigurationAction(this);
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

	public TwoCentsUI getTwoCentsUI() {
		return twoCentsUI;
	}

}
