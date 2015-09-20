package com.twocents.ui.client.report.header;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import swing2swt.layout.BorderLayout;

public abstract class HeaderReportCompositeBody extends HeaderReportComposite {

	
	private Composite body;

	private Button btnConsultar;

	
	public HeaderReportCompositeBody(Composite reportComposite) {
		super(reportComposite);
		
		setBody(new Composite(this, SWT.NONE));
		getBody().setLayoutData(BorderLayout.CENTER);
		
		
		getBody().setLayout(new RowLayout(SWT.HORIZONTAL));
		{
			
			/*setBtnConsultar(new Button(this, SWT.CENTER));
			
			getBtnConsultar().setText("     Filtrar     ");
			getBtnConsultar().addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent arg0) {

					
				}
			});
*/		}

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void setBody(Composite body) {
		this.body = body;
	}
	public Composite getBody() {
		return body;
	}
	public void setBtnConsultar(Button btnConsultar) {
		this.btnConsultar = btnConsultar;
		btnConsultar.setLayoutData(BorderLayout.SOUTH);
	}
	public Button getBtnConsultar() {
		return btnConsultar;
	}

	public abstract void init();

	public void setBodyInside(Composite bodyInside) {
	}

	
	
}
