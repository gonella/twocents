package com.twocents.ui.client.components;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.twocents.core.util.StringCheck;

public abstract class FileDialogSave implements SelectionListener {
	
	public Logger logger = Logger.getLogger(this.getClass());

	private final String title;
	private final String[] extension;

	private final Shell shell;

	public FileDialogSave(Shell shell,String title,String extension[]){
		this.shell = shell;
		this.title = title;
		this.extension = extension;
		
	}
	public FileDialogSave(Shell shell,String title){
		this.shell = shell;
		this.title = title;
		this.extension = new String[]{ "*.xls", "*.csv", "*.*" };
	}
	
      public void widgetSelected(SelectionEvent event) {
        FileDialog fd = new FileDialog(shell, SWT.SAVE);
        fd.setText(title);
        //fd.setFilterPath("C:/");
        //String[] filterExt = { "*.txt", "*.doc", ".rtf", "*.*" };
        fd.setFilterExtensions(extension);
        String selected = fd.open();
        
        if(!StringCheck.isEmpty(selected)){
	        logger.debug("Arquivo selecionado para salvar : "+selected);
	        process(selected);
        }
      }

      public void widgetDefaultSelected(SelectionEvent event) {
      }
      
      public abstract void process(String fileName);

}