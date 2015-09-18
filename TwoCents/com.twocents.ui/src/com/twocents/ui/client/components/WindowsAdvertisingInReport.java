package com.twocents.ui.client.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BorderLayout;

import com.twocents.ui.resources.UIImages;

public class WindowsAdvertisingInReport extends Composite {


	public WindowsAdvertisingInReport(Composite arg0) {
		super(arg0, SWT.NONE);
		
		final FillLayout fillLayout = new FillLayout();
		fillLayout.marginWidth = 3;
		//fillLayout.marginHeight = 10;
		setLayout(fillLayout);
		setBackground(SWTResourceManager.getColor(155, 205, 255)); //AZUL CLARO
		//setBackground(SWTResourceManager.getColor(3, 16, 48)); //AZUL ESCURO
		setLayoutData(BorderLayout.EAST);
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(255,255,255));		
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(WindowsAdvertisingInReport.class, UIImages.COM_TWOCENTS_UI_RESOURCES_IMAGES_ADVERTISING_REPORT_PNG));
		//composite.setSize(200, 500);
		
		//setBounds(0, 0, 200, 500);
		setSize(150, 500);
		
	}
}
