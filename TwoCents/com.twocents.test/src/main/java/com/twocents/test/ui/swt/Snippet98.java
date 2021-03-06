/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.twocents.test.ui.swt;
 
/*
 * Composite example snippet: create and dispose children of a composite
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.twocents.ui.handler.progress.threadprogress.ThreadProgress;

public class Snippet98 {

static int pageNum = 0;
static Composite pageComposite;

public static void main(String args[]) {
	Display display = new Display();
	final Shell shell = new Shell(display);
	shell.setLayout(new GridLayout());
	Button button = new Button(shell, SWT.PUSH);
	button.setText("Push");
	pageComposite = new Composite(shell, SWT.NONE);
	pageComposite.setLayout(new GridLayout());
	pageComposite.setLayoutData(new GridData());

	button.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event event) {
			
			ThreadProgress progress=new ThreadProgress(pageComposite.getShell(),pageComposite.getShell()){

				@Override
				public void before() {
				}

				@Override
				public void after() {
				}

				@Override
				public void abort() {
				}

				@Override
				public void execute() {
					int i=0;
					while(i<15){
						try {
							System.out.println("i:"+i);
							Thread.sleep(1000);
							i++;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
			};
			
			/*if ((pageComposite != null) && (!pageComposite.isDisposed())) {
				pageComposite.dispose();
			}
			pageComposite = new Composite(shell, SWT.NONE);
			pageComposite.setLayout(new GridLayout());
			pageComposite.setLayoutData(new GridData());
			if (pageNum++ % 2 == 0) {
				Table table = new Table(pageComposite, SWT.BORDER);
				table.setLayoutData(new GridData());
				for (int i = 0; i < 5; i++) {
					new TableItem(table, SWT.NONE).setText("table item " + i);
				}
			} else {
				new Button(pageComposite, SWT.RADIO).setText("radio");
			}
			shell.layout(true);*/
		}
	});

	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}