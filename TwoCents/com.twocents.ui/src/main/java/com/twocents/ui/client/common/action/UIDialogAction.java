package com.twocents.ui.client.common.action;

import org.apache.log4j.Logger;

public abstract class UIDialogAction extends UIAction {

	public Logger logger = Logger.getLogger(this.getClass());

	
	@Override
	public void refresh() {

	}
	
	public abstract void pressOkButton();
	
	public abstract void pressCancelButton();
	

}
