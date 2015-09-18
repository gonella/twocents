package com.twocents.ui.client.common.action;

import org.eclipse.swt.widgets.Composite;

public abstract class PanelCompositeAction extends Composite{

	
	public PanelCompositeAction(Composite arg0, int arg1) {
		super(arg0, arg1);
	}

	/**
	 * Fazer singleton da action respostavel pela UI. 
	*/
	public abstract UIAction getAction();
	
	
	
	
}
