package com.twocents.ui.client.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.ui.handler.progress.threadprogress.ThreadProgress;
import com.twocents.ui.resources.UIImages;

public class ProgressBarDialogNew extends Dialog {

	protected Object result;
	private Shell shell;
	private final ThreadProgress threadProgress;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param threadProgress 
	 * @param style
	 */
	public ProgressBarDialogNew(ThreadProgress threadProgress) {
		super(threadProgress.getShell(), SWT.APPLICATION_MODAL);
		this.threadProgress = threadProgress;
		setText("");
		
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		Display display = getParent().getDisplay();

		createContents();
		getShell().open();
		
		getShell().pack();
		
		while (!getShell().isDisposed() && 	!getThreadProgress().isFinished()) {

			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		//getShell().close();
		getShell().dispose();
		
		return result;
	}
	
	public void close(){
		getShell().close();
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		setShell(new Shell(getParent(), getStyle()));
		getShell().setSize(450, 300);
		getShell().setText(getText());
		getShell().setLayout(new BorderLayout(0, 0));
		
		Composite compositeImage = new Composite(getShell(), SWT.NONE);
		compositeImage.setLayoutData(BorderLayout.CENTER);
		compositeImage.setLayout(new FillLayout(SWT.HORIZONTAL));

		/*Composite composite = new Composite(getShell(), SWT.NONE);
		composite.setLayoutData(BorderLayout.SOUTH);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		ProgressBar compositeProgressBar = new ProgressBar(composite,  SWT.INDETERMINATE);
		*/
		final Image image = SWTResourceManager.getImage(ProgressBarDialogNew.class, UIImages.PROGRESSBAR_FOR_PROCESS05);
		Label labelImage = new Label(compositeImage, SWT.NONE);
		labelImage.setImage(image);
		
		Rectangle bounds = image.getBounds();
		int windowWidth=bounds.width;
		int windowHeight=bounds.height;
		getShell().setSize(windowWidth,windowHeight);

		Shell shellToCenter = getThreadProgress().getShellToCenter();
		
		int widthTmp=(shellToCenter.getBounds().width-windowWidth)/2;
		int heightTmp=(shellToCenter.getBounds().height - windowHeight)/2;
		
		int positionX=shellToCenter.getBounds().x+(widthTmp);
		int positionY=shellToCenter.getBounds().y+(heightTmp);
		getShell().setBounds(positionX,positionY,windowWidth,windowHeight);
		
		
		getShell().addListener(SWT.Traverse, new Listener() {
			//Desabilitando o ESC durante a progress bar
	          public void handleEvent(Event e) {
	            if (e.detail == SWT.TRAVERSE_ESCAPE) {
	              e.doit = false;
	              
	            }
	          }
	     });
		
		
		getShell().layout();
		
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public Shell getShell() {
		return shell;
	}

	public ThreadProgress getThreadProgress() {
		return threadProgress;
	}
}
