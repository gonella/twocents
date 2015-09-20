package com.twocents.report.handler.progress;

import org.eclipse.swt.widgets.Shell;

public abstract class ReportThreadProgress extends Thread {

	private boolean isFinished = false;

	private static ReportProgressBar progressBar=null;

	private final Shell shell;

	private final Shell shellToCenter;

	/**
	 * 
	 * @param shellToModal - O container que vai ser MODAL
	 * @param shellToCenter - A progress bar vai ser centralizada baseada nesse container
	 */
	public ReportThreadProgress(final Shell shellToModal,final Shell shellToCenter) {
		this.shell = shellToModal;
		this.shellToCenter = shellToCenter;
		start();
		
		//if(progressBar==null)
		{
			progressBar = new ReportProgressBar(this);
		}
		progressBar.open();
		
	}

	public abstract void before();

	public abstract void after();

	public abstract void abort();

	public abstract void execute();

	public void run() {
		
		before();

		//FIXME Chamada dentro de um Display.synch(ex: refresh de tela), não podem demorar muito, vão segurar a animação da progressbar.
		
		getShell().getDisplay().syncExec(new Runnable() {
			
			@Override
			public void run() {

				execute();
			}
		});
		
		

		after();

		setFinished(true);
		
	}

	public ReportProgressBar getProgressBar() {
		return progressBar;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public Shell getShell() {
		return shell;
	}

	public Shell getShellToCenter() {
		return shellToCenter;
	}

}
