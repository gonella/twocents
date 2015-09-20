package com.twocents.core.common;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.twocents.model.ReportParam;

public abstract class CoreAction {
	
	private Logger loggerCommon = Logger.getLogger(CoreAction.class);
	
	public abstract void init();

}
