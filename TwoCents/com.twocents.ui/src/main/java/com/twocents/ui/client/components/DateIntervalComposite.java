package com.twocents.ui.client.components;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.nebula.widgets.datechooser.DateChooserCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import com.swtdesigner.SWTResourceManager;
import com.twocents.core.util.FormatUtil;

public class DateIntervalComposite extends Composite {
	
	private Label labelStartDate;
	private DateChooserCombo startDate;
	private Label labelEndDate;
	private DateChooserCombo endDate;
	private Button actionButton;

	public DateIntervalComposite(Composite composite, int style) {
		super(composite, style);
		final GridLayout gridLayoutInterval = new GridLayout();
		gridLayoutInterval.numColumns = 5;
		setLayout(gridLayoutInterval);
		setBackground(SWTResourceManager.getColor(255, 255, 255));
		
		labelStartDate = new Label(this, SWT.NONE);
		labelStartDate.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		labelStartDate.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NONE));
		labelStartDate.setText("Data Início: ");
		
			
		startDate = new DateChooserCombo(this, SWT.BORDER);
		startDate.setFooterVisible(true);
		startDate.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {
			}

			public void widgetSelected(SelectionEvent event) {
				Date newDate = (Date)event.data;
				validateStartDate(newDate);
			}
		});
		
		startDate.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent event) {
			}

			public void focusLost(FocusEvent event) {
				Date newDate = ((DateChooserCombo)event.getSource()).getValue();
				validateStartDate(newDate);
			}

		});
	
	    
	    GridData data12 = new GridData();
		data12.widthHint = 80;
		startDate.setLayoutData(data12);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.DAY_OF_MONTH, calendar1.getActualMinimum(Calendar.DAY_OF_MONTH));
		startDate.setValue(calendar1.getTime());
		
		labelEndDate = new Label(this, SWT.NONE);
		labelEndDate.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		labelEndDate.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NONE));
		labelEndDate.setText("Data Fim: ");
		
		endDate = new DateChooserCombo(this, SWT.BORDER);
		endDate.setFooterVisible(true);
		endDate.setLayoutData(data12);
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(Calendar.DAY_OF_MONTH,calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		endDate.setValue(calendar2.getTime());

		//endDate.setValue(new Date());
		endDate.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {
			}

			public void widgetSelected(SelectionEvent event) {
				Date newDate = (Date)event.data;
				validateFinalDate(newDate);
			}
		});
		
		endDate.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent event) {
			}

			public void focusLost(FocusEvent event) {
				Date newDate = ((DateChooserCombo)event.getSource()).getValue();
				validateFinalDate(newDate);
			}

		});
		
		actionButton = new Button(this, SWT.NONE);
		actionButton.setText("Buscar");
	}

	private void validateStartDate(Date newDate) {
		if(newDate != null) { 
			if(FormatUtil.getMinTimestamp(newDate).after(getEndDate())){
				endDate.setValue(newDate);
			}
		} else {
			startDate.setValue(endDate.getValue());
		}
	}


	private void validateFinalDate(Date newDate) {
		if(newDate != null) {
			if(FormatUtil.getMaxTimestamp(newDate).before(getStartDate())){
				startDate.setValue(newDate);
			}
		} else {
			endDate.setValue(startDate.getValue());
		}
	}

	
	public Timestamp getStartDate() {
		return FormatUtil.getMinTimestamp(startDate.getValue());
	}
	
	public Timestamp getEndDate() {
		return FormatUtil.getMaxTimestamp(endDate.getValue());
	}
	
	
	public void addActionButtonListener(Listener listener) {
		actionButton.addListener(SWT.Selection, listener);
	}

}
