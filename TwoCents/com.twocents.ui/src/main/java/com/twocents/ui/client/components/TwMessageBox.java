package com.twocents.ui.client.components;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.twocents.data.excel.ExportProcess;
import com.twocents.exceptions.CoreException;

public class TwMessageBox{

	private static Logger logger = Logger.getLogger(TwMessageBox.class);

	private static final String AVISO = "Aviso";
	

	public static void messageWarning(Shell shell,CoreException e){
		
		logger.error("MessageBox",e);
		MessageBox twMessageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		
		twMessageBox.setMessage(e.getMessage());
		twMessageBox.setText(AVISO);
		int response = twMessageBox.open();
		/*if (response == SWT.OK)
		 System.exit(0);
		*/
	}
public static void messageWarning(Shell shell,String str){
		
		logger.error("MessageWarning - "+str);
		
		MessageBox twMessageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		
		twMessageBox.setMessage(str);
		twMessageBox.setText(AVISO);
		int response = twMessageBox.open();
		/*if (response == SWT.OK)
		 System.exit(0);
		*/
	}
	
	public static void messageInformation(Shell shell,String msg){
		
		logger.info(msg);
		MessageBox twMessageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		
		twMessageBox.setMessage(msg);
		twMessageBox.setText(AVISO);
		int response = twMessageBox.open();
		/*if (response == SWT.OK)
		 System.exit(0);
		*/
	}
}
