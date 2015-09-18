package com.twocents.ui.client.components.configuration.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;

import com.twocents.context.ContextHolder;
import com.twocents.core.adapter.QuoteAdapter;
import com.twocents.spring.ServiceLocator;
import com.twocents.ui.client.components.configuration.action.QuoteConfigurationAction;

public class QuoteConfigurationUI extends Composite {

	// private Logger logger = Logger.getLogger(QuoteConfigurationUI.class);

	private QuoteAdapter selected;
	private QuoteConfigurationAction action;

	private void updateQuoteAdapterSelected(QuoteAdapter selected) {
		this.selected = selected;
		ContextHolder.setCurrentQuoteAdapter(selected);
	}

	public QuoteConfigurationUI(Composite parent, int style,
			QuoteConfigurationAction action) {
		super(parent, style);
		this.action = action;
	}

	public void createComponents() {
		List<QuoteAdapter> adapters = ServiceLocator.getQuoteAdapters();
		// selected = action.getCurrentQuoteAdapter();
		selected = ContextHolder.getCurrentQuoteAdapter();

		int groupWeight = adapters.size() * 60;

		Group grpFonteDeDados = new Group(this, SWT.NONE);
		grpFonteDeDados.setText("Fonte de Dados");
		grpFonteDeDados.setBounds(10, 10, 430, groupWeight == 0 ? 80
				: groupWeight);

		int xPos = 24;
		int yPos = 28;

		Button btnAplicar = new Button(this, SWT.NONE);
		btnAplicar.setEnabled(false);
		btnAplicar.setText("Aplicar");

		final List<Button> buttons = new ArrayList<Button>();
		buttons.add(btnAplicar);
		Button button;

		for (final QuoteAdapter quoteAdapter : adapters) {
			button = new Button(grpFonteDeDados, SWT.RADIO);

			buttons.add(button);

			button.setText(quoteAdapter.getAdapterName());
			button.setBounds(xPos, yPos, 220, 16);

			button.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					updateQuoteAdapterSelected(quoteAdapter);
					enableButtons(buttons);
				}
			});

			/*
			 * if (selected != null && selected.getAdapterBeanName().equals(
			 * quoteAdapter.getAdapterBeanName())) { logger.info("Test");
			 * button.setSelection(true); //button.setEnabled(false); }
			 */
			disableButtons(buttons);
			updateQuoteAdapterSelected(quoteAdapter);
			button.setSelection(true);

			yPos += 30;

		}

		btnAplicar.setBounds(315, yPos + 30, 125, 25);
		btnAplicar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				action.applyQuoteConfiguration(selected);
				// disableButtons(buttons);
			}
		});
	}

	public void setAction(QuoteConfigurationAction action) {
		this.action = action;
	}

	private void enableButtons(List<Button> buttons) {

		for (Button button : buttons) {
			if (selected != null
					&& !selected.getAdapterName().equals(button.getText())) {
				button.setEnabled(true);
			}
		}
	}

	private void disableButtons(List<Button> buttons) {

		for (Button button : buttons) {
			if ((selected != null
					&& selected.getAdapterName().equals(button.getText()) || button
					.getText().equals("Aplicar"))) {
				// button.setEnabled(false);
				button.setSelection(false);
			}
		}
	}

	public static void main(String[] args) {
		// Display display = Display.getDefault();
		// Shell shell = new Shell(display);
		//
		// new QuoteConfigurationUI(shell, SWT.NONE);
		//
		// shell.open();
		// while (!shell.isDisposed()) {
		// if (!display.readAndDispatch())
		// display.sleep();
		// }
		// display.dispose();

	}

}
