package com.twocents.ui.client.common.windows;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.twocents.model.ConfigurationGroupType;

public abstract class PanelConfigurationComposite extends PanelCompositeBase{

	private final ConfigurationGroupType type;
	
	public ConfigurationGroupType getType() {
		return type;
	}

	public PanelConfigurationComposite(Composite composite,ConfigurationGroupType type,String title) {
		super(composite,SWT.NONE,title);
		this.type = type;
	}
	
	public abstract PanelConfigurationComposite rebuild(Composite composite);
}
