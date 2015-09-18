package com.twocents.ui.client.components;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.ui.handler.progress.threadprogress.ThreadProgress;
import com.twocents.ui.resources.UIImages;

public class ProgressBarDialog extends Dialog {

	private static final Image DEFAULT_IMAGE_PROGRESS_BAR = SWTResourceManager.getImage(ProgressBarDialog.class,UIImages.PROGRESSBAR_FOR_PROCESS01);
	protected Object result;
	private Shell shell;
	private Logger logger = Logger.getLogger(ProgressBarDialog.class);

	private int windowHeight = 125;
	private int windowWidth = 550;
	private final ThreadProgress threadProgress;
	private Image imageProgressBar;
	private Label labelImageProgressBar;

	public ProgressBarDialog(Shell shell, ThreadProgress threadProgress,Image imageProgressBar) {
		this(shell,threadProgress);
		this.setImageProgressBar(imageProgressBar);
		//getLabelImageProgressBar().setBackgroundImage(getImageProgressBar());

	}
	/**
	 * Create the dialog.
	 * 
	 * @param shell
	 * @param threadProgress
	 * @param style
	 * @wbp.parser.constructor
	 */
	public ProgressBarDialog(Shell shell, ThreadProgress threadProgress) {
		super(shell, 
				SWT.ON_TOP 
				| SWT.SHELL_TRIM 
				| SWT.CLOSE 
				| SWT.TITLE 
				//| SWT.APPLICATION_MODAL
				| SWT.PRIMARY_MODAL
				//SWT.SYSTEM_MODAL
		);
		this.threadProgress = threadProgress;
		this.setImageProgressBar(DEFAULT_IMAGE_PROGRESS_BAR);
		setText("Processando");

		createContents();

		final int[] count = new int[] { 4 };
		final Image image = new Image(shell.getDisplay(), 300, 300);
		GC gc = new GC(image);
		gc.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		gc.fillRectangle(image.getBounds());
		gc.drawText("Processando..", 10, 10);
		gc.dispose();
		getShell().pack();

		getShell().setSize(windowWidth, windowHeight);
		getShell().setLayout(new BorderLayout(0, 0));

		ProgressBar progressBar = new ProgressBar(getShell(), SWT.INDETERMINATE);
		progressBar.setLayoutData(BorderLayout.SOUTH);

		setLabelImageProgressBar(new Label(getShell(), SWT.NONE));
		getLabelImageProgressBar().setLayoutData(BorderLayout.CENTER);

		getLabelImageProgressBar().setBackgroundImage(DEFAULT_IMAGE_PROGRESS_BAR);

		Rectangle shellBounds = shell.getBounds();
		Point dialogSize = getShell().getSize();

		int x = shellBounds.x + (shellBounds.width - dialogSize.x) / 2;
		int y = shellBounds.y + (shellBounds.height - dialogSize.y) / 2;
		getShell().setLocation(x + 25, y + 50);

		getShell().addListener(SWT.Traverse, new Listener() {
			//Desabilitando o ESC durante a progress bar
	          public void handleEvent(Event e) {
	            if (e.detail == SWT.TRAVERSE_ESCAPE) {
	              e.doit = false;
	              logger.debug("Clicando ESC.");
	            }
	          }
	        });
		

	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		getShell().open();
		getShell().layout();
		Display display = getParent().getDisplay();

		while (!getShell().isDisposed() && 	!getThreadProgress().isFinished()) {

			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		getShell().close();

		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		setShell(new Shell(getParent(), SWT.NO_TRIM | SWT.ON_TOP));
		getShell().setSize(windowWidth, windowHeight);
		getShell().setText(getText());

	}

	public ThreadProgress getThreadProgress() {
		return threadProgress;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public Shell getShell() {
		return shell;
	}
	public void setImageProgressBar(Image imageProgressBar) {
		this.imageProgressBar = imageProgressBar;
	}
	public Image getImageProgressBar() {
		return imageProgressBar;
	}
	public void setLabelImageProgressBar(Label labelImageProgressBar) {
		this.labelImageProgressBar = labelImageProgressBar;
	}
	public Label getLabelImageProgressBar() {
		return labelImageProgressBar;
	}

}
