package com.twocents.report.util;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class DialogUtil {

	private static final String AVISO = "Aviso";
	private Logger logger = Logger.getLogger(DialogUtil.class);

	public static void errorMessage(Composite composite, String title,
			String message) {

		MessageBox messagebox = new MessageBox(composite.getShell(), SWT.OK
				| SWT.ICON_ERROR);
		messagebox.setText(title);
		messagebox.setMessage(message);
		messagebox.open();
	}
	
	public static void errorMessage(Composite composite,String message) {

		MessageBox messagebox = new MessageBox(composite.getShell(), SWT.OK
				| SWT.ICON_ERROR);
		messagebox.setText(AVISO);
		messagebox.setMessage(message);
		messagebox.open();

	}

	public static boolean showDialogQuestion(Shell shell, String titleMessage,
			String contentTitle) {

		boolean result = MessageDialog.openQuestion(shell, titleMessage,
				contentTitle);
		return result;
	}

	
	public static boolean showRemoveConfirmation(String title, String content,
			Shell shell) {
		return showDialogQuestion(shell, title, content);
	}

	
}
