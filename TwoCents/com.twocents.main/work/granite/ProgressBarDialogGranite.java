package com.twocents.ui.client.components;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.pushingpixels.granite.content.Stage1LoadingProgress;

public abstract class ProgressBarDialogGranite extends Dialog {

	private static final int _HEIGH = 40;
	private static final int _WIDTH = 300;
	protected Object result;
	private Shell shell;
	private Stage1LoadingProgress progressBar;
	private Logger logger = Logger.getLogger(ProgressBarDialogGranite.class);

	private boolean finishedProcess = false;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param threadProgress
	 * @param style
	 */
	public ProgressBarDialogGranite(Shell parent) {
		super(parent, SWT.TOP | SWT.NO_TRIM | SWT.APPLICATION_MODAL);
		shell = parent;
		// setText("SWT Dialog");
		open();
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {

		createContents();
		getShell().open();
		getShell().layout();
		
		
		/*
		 * getShell().getDisplay().asyncExec(new Runnable() {
		 * 
		 * @Override public void run() {
		 */try {
			
				
				getProgressBar().setLoading(true);

			Thread thread = new Thread() {
				@Override
				public void run() {
					/*Job job = new Job("Fetch Amazon") {
						@Override
						protected org.eclipse.core.runtime.IStatus run(
								org.eclipse.core.runtime.IProgressMonitor monitor) {
					*/
					try {
						logger.info("ProgressBar Executando..");
						
						execute();
						getShell().getDisplay().asyncExec(new Runnable() {
							@Override
							public void run() {
								
								
								
								getShell().close();
							}
						});
						
						//getProgressBar().setLoading(false);

					} catch (Throwable exc) {
						logger.error("Falhou em apresentar", exc);
						getProgressBar().setLoading(false);
						
					}
			}

			};

			thread.start();
			
			/*return Status.OK_STATUS;
			}
		};
		job.schedule();	
			*/
			
		} catch (Exception e) {
			logger.error("Falhou em sincronizar apresentação", e);
		}

		Display display = getShell().getDisplay();
		while (!getShell().isDisposed() && !isFinishedProcess()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	public abstract void execute();

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		setShell(new Shell(getParent(), getStyle()));
		getShell().setSize(450, 300);
		getShell().setText(getText());

		getShell().setBounds(500, 300, _WIDTH, _HEIGH);
		getShell().setLayout(new FillLayout());

		int widthTmp = (getParent().getBounds().width - _WIDTH) / 2;
		int heightTmp = (getParent().getBounds().height - _HEIGH) / 2;

		int positionX = getParent().getBounds().x + (widthTmp);
		int positionY = getParent().getBounds().y + (heightTmp);
		getShell().setBounds(positionX, positionY, _WIDTH, _HEIGH);

		setProgressBar(new Stage1LoadingProgress(getShell()));

		RoundedBox.addRoundedBoxOnResize(getShell(), 10, RoundedBox.EDGES_ALL);

		// RoundedBox.addRoundedBoxOnResize(getShell(), 10,
		// RoundedBox.EDGES_ALL);

	}

	private void setProgressBar(Stage1LoadingProgress progressBar) {
		this.progressBar = progressBar;
	}

	private Stage1LoadingProgress getProgressBar() {
		return progressBar;
	}

	private void setShell(Shell shell) {
		this.shell = shell;
	}

	private Shell getShell() {
		return shell;
	}

	public void setFinishedProcess(boolean finishedProcess) {
		this.finishedProcess = finishedProcess;
	}

	public boolean isFinishedProcess() {
		return finishedProcess;
	}

}
