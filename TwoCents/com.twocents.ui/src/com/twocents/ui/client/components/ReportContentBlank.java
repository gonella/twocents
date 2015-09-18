package com.twocents.ui.client.components;

import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

public class ReportContentBlank extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ReportContentBlank(Composite parent) {
		super(parent, SWT.NONE);
		setLayout(new BorderLayout(0, 0));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setImage(SWTResourceManager.getImage(ReportContentBlank.class, "/com/twocents/ui/resources/images/TwoCents_Logo.PNG"));
		lblNewLabel.setLayoutData(BorderLayout.CENTER);
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(BorderLayout.NORTH);
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.SOUTH);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
