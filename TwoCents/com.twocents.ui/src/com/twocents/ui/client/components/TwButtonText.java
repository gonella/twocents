package com.twocents.ui.client.components;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.nebula.widgets.cdatetime.CButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import com.swtdesigner.SWTResourceManager;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class TwButtonText extends Composite {
	private CButton button = new CButton(this, SWT.NONE);
	private Label label = new Label(this, SWT.NONE);
	public TwButtonText(Composite parent, int style) {
		super(parent, style);
		setLayout(new BorderLayout(0, 0));
		{
			
			label.setAlignment(SWT.CENTER);
			label.setLayoutData(BorderLayout.SOUTH);
			label.setText("New Label");
		}
		{
			button.addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent arg0) {
					buttonClick();
				}
			});
			button.setText("");
			
			button.setLayoutData(BorderLayout.CENTER);
		}

	}
	
	public void setButtonImage(Image image){
	    button.setImage(image);	
	}
	
	public void setButtonText(String text){
		this.label.setText(text);
	}
	
	public void setButtonTextColor(Color color){
		this.label.setForeground(color);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void buttonClick(){
		
	}
}
