package com.twocents.ui.client.listener;

import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.Text;

public class NumberValidationListener implements VerifyListener{
	
	public void verifyText(VerifyEvent event) {
		 
	    // Assume we don't allow it
		event.doit = false;
	 
	    // Get the character typed
	    char myChar = event.character;
	    String text = ((Text) event.widget).getText();
	 
	    // Allow 0-9
	   if (Character.isDigit(myChar)) event.doit = true;
	 
	   // Allow backspace
	   if (myChar == '\b') {
		   event.doit = true;
	  	
	   }
	   
	}
	
}
