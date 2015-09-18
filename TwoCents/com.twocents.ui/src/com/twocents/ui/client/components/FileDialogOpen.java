package com.twocents.ui.client.components;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.twocents.core.util.StringCheck;

public abstract class FileDialogOpen implements SelectionListener {
	
	public Logger logger = Logger.getLogger(this.getClass());

	private final String title;
	private final String[] extension;

	private final Shell shell;

	public FileDialogOpen(Shell shell,String title,String extension[]){
		this.shell = shell;
		this.title = title;
		this.extension = extension;
		
	}
	public FileDialogOpen(Shell shell,String title){
		this.shell = shell;
		this.title = title;
		this.extension = new String[]{ "*.xls", "*.csv", "*.*" };
	}
	
    public void widgetSelected(SelectionEvent event) {
    	FileDialog fd = new FileDialog(shell, SWT.OPEN);
        fd.setText(title);
        //fd.setFilterPath("C:/");
        //String[] filterExt = { "*.xls", "*.csv", "*.*" };
        fd.setFilterExtensions(extension);
        String selected = fd.open();
        
        if(!StringCheck.isEmpty(selected)){
	        logger.debug("Arquivo selecionado para abrir :"+selected);
	        process(selected);
        }
    }

    public void widgetDefaultSelected(SelectionEvent event) {
    }
    
    public abstract void process(String fileName);
      
}