package com.twocents.ui.handler.progress.threadprogress;

import org.eclipse.swt.widgets.Shell;

import com.twocents.ui.client.components.ProgressBarDialogNew;

public abstract class ThreadProgress extends Thread {

	private boolean isFinished = false;

	private static ProgressBarDialogNew progressBar=null;

	private final Shell shell;

	private final Shell shellToCenter;

	/**
	 * 
	 * @param shellToModal - O container que vai ser MODAL
	 * @param shellToCenter - A progress bar vai ser centralizada baseada nesse container
	 */
	public ThreadProgress(final Shell shellToModal,final Shell shellToCenter) {
		this.shell = shellToModal;
		this.shellToCenter = shellToCenter;
		start();
		
		//if(progressBar==null)
		{
			progressBar = new ProgressBarDialogNew(this);
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

	public ProgressBarDialogNew getProgressBar() {
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
