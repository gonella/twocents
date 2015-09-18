package com.twocents.license.client;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.twocents.license.client.action.LicenseUIAction;

public class LicenseUI {

	private Text dateEndText;
	private Text emailText;
	private Text dateBeginText;
	private Text nameText;
	protected Shell shell;

	private LicenseUIAction action;
	public LicenseUIAction getAction() {
		return action;
	}

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LicenseUI window = new LicenseUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window
	 */
	public void open() {
		final Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setLayout(new FillLayout());
		shell.setSize(598, 257);
		shell.setText("License Manager");

		final Composite composite = new Composite(shell, SWT.NONE);

		final Group group = new Group(composite, SWT.NONE);
		group.setText("Criar Licença");
		group.setBounds(10, 10, 570, 204);

		final Button createButton = new Button(group, SWT.NONE);
		createButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				createLicense();
			}
		});
		createButton.setText("Criar");
		createButton.setBounds(402, 157, 158, 34);

		nameText = new Text(group, SWT.BORDER);
		nameText.setBounds(10, 41, 288, 25);

		final Label nomeLabel = new Label(group, SWT.NONE);
		nomeLabel.setText("Nome");
		nomeLabel.setBounds(10, 22, 35, 13);

		final Label dataInicioDaLabel = new Label(group, SWT.NONE);
		dataInicioDaLabel.setText("Data inicio da licença");
		dataInicioDaLabel.setBounds(10, 146, 110, 13);

		dateBeginText = new Text(group, SWT.BORDER);
		dateBeginText.setBounds(10, 165, 130, 25);

		emailText = new Text(group, SWT.BORDER);
		emailText.setBounds(10, 102, 288, 25);

		final Label emailLabel = new Label(group, SWT.NONE);
		emailLabel.setBounds(10, 84, 35, 13);
		emailLabel.setText("Email");

		final Label dataInicioDaLabel_1 = new Label(group, SWT.NONE);
		dataInicioDaLabel_1.setBounds(168, 146, 110, 13);
		dataInicioDaLabel_1.setText("Data final da licença");

		dateEndText = new Text(group, SWT.BORDER);
		dateEndText.setBounds(168, 165, 130, 25);
		
		initAction();
		//
	}

	public void initAction(){
		action=new LicenseUIAction(this);
	}
	
	protected void createLicense() {
		getAction().createLicense();
		
	}
	public Text getNameText() {
		return nameText;
	}
	public Text getEmailText() {
		return emailText;
	}
	public Text getDateEndText() {
		return dateEndText;
	}
	public Text getDateBeginText() {
		return dateBeginText;
	}

}
