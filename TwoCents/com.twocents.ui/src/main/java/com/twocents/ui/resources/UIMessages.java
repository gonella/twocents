package com.twocents.ui.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class UIMessages {

	private static Logger logger = Logger.getLogger(UIMessages.class);
	
	private static String UI_MESSAGES="com/twocents/ui/resources/TwoCentsUI";
	
	private static String UNKNOWN = "UNKNOWN";
	
	private static ResourceBundle resource = null;
		
	/**
	 * @return the props
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static ResourceBundle getBundle(){
		
		if(resource==null){
			resource = ResourceBundle.getBundle(UI_MESSAGES); 
		}		
		return resource;
	}
		
	public static String getMessage(Integer errorCode){
		return getMessage(errorCode.toString());
	}
	
	public static String getMessage(String key){		
		
		try {			
			return getBundle().getString(key);
			
		} catch (NullPointerException e) {			
			logger.error(e);
		}
		
		return UNKNOWN;
	}
	
	
}
