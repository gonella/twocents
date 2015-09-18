package com.twocents.ui.client.common.windows;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.twocents.ui.client.common.action.UIAction;

public abstract class PanelCompositeBase extends Composite{

	private String title=null;
	private Integer config=null;
		
	public int getConfig() {
		return config;
	}

	public String getTitle() {
		return title;
	}

	public PanelCompositeBase(Composite composite, int config,String title) {
		super(composite,config);
		this.config = config;
		this.title = title;
		
		createComponents(composite);
		
		initAction();
	}
	
	public PanelCompositeBase(Composite composite) {
		super(composite,SWT.NONE);
		
		//initAction();
	}
	
	public abstract void createComponents(Composite parent);
	public abstract void initAction();

	

	
}
