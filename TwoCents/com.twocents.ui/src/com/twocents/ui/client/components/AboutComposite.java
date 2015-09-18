package com.twocents.ui.client.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

import swing2swt.layout.BorderLayout;

import com.twocents.ui.client.components.action.AboutCompositeAction;

public class AboutComposite extends Composite {

	private AboutCompositeAction action=null;
	private StyledText styledText;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AboutComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(BorderLayout.NORTH);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		setStyledText(new StyledText(composite_1, SWT.BORDER));
		
		getAction().init();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public AboutCompositeAction getAction() {
		
		if(action==null){
			action=new AboutCompositeAction(this);
		}
		
		return action;
	}

	public void setStyledText(StyledText styledText) {
		this.styledText = styledText;
	}

	public StyledText getStyledText() {
		return styledText;
	}

}
