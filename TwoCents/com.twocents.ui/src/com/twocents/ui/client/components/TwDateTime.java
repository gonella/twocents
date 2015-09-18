package com.twocents.ui.client.components;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.twocents.core.util.FormatUtil;

public class TwDateTime {

	private static final Logger logger = Logger.getLogger(TwDateTime.class);
	private DateTime calendar;
	private Shell shell;

	private Date date;
	private final Label labelDate;

	
	public TwDateTime(Shell parent, int x, int y, Label labelDate) {

		this.labelDate = labelDate;
		shell = new Shell(parent, SWT.NO_TRIM);
		shell.setLayout(new FillLayout());
		shell.setLocation(x, y);

		setCalendar(new DateTime(shell, SWT.CALENDAR));
		getCalendar().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {

				if (arg0.character == SWT.ESC) {
					logger.info("Closing Calendar");
					shell.dispose();
				}
			}
		});
		
		getCalendar().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				logger.debug("Calendar date selected (DD/MM/YYYY) = "
						+  getCalendar().getDay()
						+ "/" + (getCalendar().getMonth() )
						+ "/" + getCalendar().getYear());
				
				Calendar instance = Calendar.getInstance();
				instance.set(Calendar.YEAR, getCalendar().getYear ());
				instance.set(Calendar.MONTH, getCalendar().getMonth () );
				instance.set(Calendar.DAY_OF_MONTH, getCalendar().getDay ());
				
				setDate(instance.getTime());
				changeDate(getDate());
				shell.dispose();

			}
		});

		shell.pack();
		shell.open();

		/*while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();*/
	}
	
	protected void changeDate(Date date) {
		if(date!=null){
			logger.info("Changing date to :"+FormatUtil.formatDate(date));
			getLabelDate().setText(FormatUtil.formatDate(date));
		}
	}


	public void setCalendar(DateTime calendar) {
		this.calendar = calendar;
		calendar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				close();
			}
		});
	}

	public DateTime getCalendar() {
		return calendar;
	}
	
	public void close(){
		shell.close();
	}
	
	public boolean isDisposed(){
		return shell.isDisposed();
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public Label getLabelDate() {
		return labelDate;
	}
	
}