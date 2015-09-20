package com.twocents.ui.client.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BorderLayout;

import com.twocents.ui.resources.TwColors;
import com.twocents.ui.resources.UIImages;

public class WindowsAdvertisingInOperation extends Composite {


	public WindowsAdvertisingInOperation(Composite arg0) {
		super(arg0, SWT.NONE);
		
		final FillLayout fillLayout = new FillLayout();
		fillLayout.marginWidth = 3;
		fillLayout.marginHeight = 3;
		setLayout(fillLayout);
		setBackground(TwColors.BLUE_LIGHT); //AZUL CLARO
		//setBackground(Colors.BLUE_DARK);
		setLayoutData(BorderLayout.EAST);
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(255,255,255));		
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(WindowsAdvertisingInOperation.class, UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_ADVERTISING_OPERATION_PNG));
		//composite.setSize(200, 500);
		
		//setBounds(0, 0, 200, 500);
		setSize(270, 270);
		
	}
}
