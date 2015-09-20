package com.twocents.ui.exceptions;

import com.twocents.exceptions.CoreException;
import com.twocents.ui.resources.UIMessages;


public class UIException extends CoreException {
	
	
	public UIException(int errorCode) {
		super(UIMessages.getMessage(errorCode));		
	}
	public UIException(int errorCode, Throwable cause) {
		super(UIMessages.getMessage(errorCode));		
	}
	
	public UIException(String message, Throwable cause) {
		super(message, cause);		
	}

	
	
	public UIException(Throwable cause) {
		super((cause.getCause() != null)? cause.getCause().getMessage():cause.getMessage());
	}
	
	
	
}
