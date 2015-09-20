package com.twocents.ui.client.listener;

import java.text.DecimalFormatSymbols;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

public class CurrencyValidationListener implements VerifyListener{

	//private Logger logger = Logger.getLogger(CurrencyValidationListener.class);

	private static DecimalFormatSymbols symbols = new DecimalFormatSymbols();

	public void verifyText(VerifyEvent event) {

		// Assume we don't allow it
		event.doit = false;

		Text text = (Text)event.widget;

		String currentValue = text.getText();

		//logger.info(currentValue);

		// Get the character typed
		char myChar = event.character;

		// Allow 0-9
		if (Character.isDigit(myChar))  event.doit = true;

		if(myChar == symbols.getDecimalSeparator()) event.doit = true;

		// Allow backspace
		if (myChar == '\b') {
			event.doit = true;

		}

		try {
			Double.parseDouble((currentValue + event.text)
					.replaceAll(",", "\\."));
		} catch (Exception e) {
			event.doit = false;
		}

	}

}
