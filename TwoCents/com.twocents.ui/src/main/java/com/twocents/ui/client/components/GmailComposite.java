package com.twocents.ui.client.components;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Label;
import com.swtdesigner.SWTResourceManager;

public class GmailComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public GmailComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new BorderLayout(0, 0));
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.WEST);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label label = new Label(composite_1, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(GmailComposite.class, "/com/twocents/ui/resources/images/TwoCents-GmailRepository_Icon_48X48.png"));
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayoutData(BorderLayout.CENTER);

		setBounds(0, 0, 500, 100);
		
		//pack();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
