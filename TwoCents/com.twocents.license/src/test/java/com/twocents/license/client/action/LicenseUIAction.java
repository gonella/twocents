package com.twocents.license.client.action;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.twocents.license.client.LicenseUI;
import com.twocents.license.security.LicenseManager;
import com.twocents.model.LicenseKey;
import com.twocents.model.ReportParam;
import com.twocents.ui.client.common.action.UIAction;

public class LicenseUIAction extends UIAction {

	private Logger logger = Logger.getLogger(LicenseUIAction.class);
	
	private final LicenseUI UI;

	public LicenseUI getUI() {
		return UI;
	}

	public LicenseUIAction(LicenseUI UI) {
		super();
		this.UI = UI;

		init();
	}

	public void init() {
		logger.debug("Loading License configuration");

	}

	public void refresh() {

	}

	
	public void createLicense(){
	
		logger.debug("Creating a new license");
		
		String name = getUI().getNameText().getText();
		String email = getUI().getEmailText().getText();
		
		String dateBegin = getUI().getDateBeginText().getText();
		String dateEnd = getUI().getDateEndText().getText();
		
		try {
			
			LicenseManager manager=new LicenseManager();
			LicenseKey keyFilled=new LicenseKey(name,dateBegin,dateEnd,null);
			
			manager.createLicense("TestActive.key", keyFilled);
			
			
		} catch (Exception e) {
			logger.error(e);
		}
		
		
	}


	


}
