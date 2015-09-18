package com.twocents.test.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.twocents.ui.client.components.ProgressBarDialogTransparent;

public class TestWindows {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TestWindows window = new TestWindows();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	private ProgressBarDialogTransparent progressBarDialogTransparent;
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.NORTH);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button btnOpen = new Button(composite, SWT.NONE);
		btnOpen.addSelectionListener(new SelectionAdapter() {
			

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getProgressBarDialogTransparent().open();
			}
		});
		btnOpen.setText("Open()");
		
		Button btnClose = new Button(composite, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getProgressBarDialogTransparent().close();
			}
		});
		btnClose.setText("Close");
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		
		Button button = new Button(composite_1, SWT.RADIO);
		button.setBounds(27, 76, 90, 16);
		button.setText("Radio Button");
		
		Button button_1 = new Button(composite_1, SWT.RADIO);
		button_1.setBounds(27, 98, 90, 16);
		button_1.setText("Radio Button");

	}

	public void setProgressBarDialogTransparent(
			ProgressBarDialogTransparent progressBarDialogTransparent) {
		this.progressBarDialogTransparent = progressBarDialogTransparent;
	}

	public ProgressBarDialogTransparent getProgressBarDialogTransparent() {
		
		if(progressBarDialogTransparent==null){
			System.out.println("INIT");
			progressBarDialogTransparent=new ProgressBarDialogTransparent(shell, null);
		}
		
		return progressBarDialogTransparent;
	}
}
