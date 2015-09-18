package com.twocents.ui.util;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.twocents.ui.constant.UIDefault;
import com.twocents.ui.resources.UIMessages;

public class DialogUtil {

	private Logger logger = Logger.getLogger(DialogUtil.class);

	
	public static void errorMessage(Composite composite, String title,
			String message) {

		MessageBox messagebox = new MessageBox(composite.getShell(), SWT.OK
				| SWT.ICON_ERROR);
		messagebox.setText(title);
		messagebox.setMessage(message);
		messagebox.open();

	}

	public static boolean showDialogQuestion(Shell shell, String titleMessage,
			String contentTitle) {

		boolean result = MessageDialog.openQuestion(shell, titleMessage,
				contentTitle);
		return result;
	}

	public static boolean showRemoveConfirmation(Shell shell) {

		String titleMessage = UIMessages.getMessage("DIALOG_CONFIRMATION");
		String content = UIMessages.getMessage("DIALOG_CONFIRMATION_REMOVE");
		return showRemoveConfirmation(titleMessage, content, shell);
	}

	public static boolean showRemoveConfirmation(String title, String content,
			Shell shell) {
		return showDialogQuestion(shell, title, content);
	}

	private String openDialogToSaveTheFile(Shell shell){
		
		FileDialog dialog = new FileDialog (shell, SWT.SAVE);
		//dialog.setFilterNames (new String [] {"Imagens", "Documentos(Doc)"});
		dialog.setFilterExtensions (new String [] {"*.pdf", "*.xls"}); //Windows wild cards
		dialog.setFilterPath (UIDefault.DEFAULT_PATH_ATTACH_FILE); //Windows path
		
		String fileSelected=dialog.open ();
		if(fileSelected!=null){
			logger.info("Filename Informed to save :" + fileSelected);
			UIDefault.DEFAULT_PATH_ATTACH_FILE=fileSelected;			
		}
		
		return fileSelected;
	}
}
