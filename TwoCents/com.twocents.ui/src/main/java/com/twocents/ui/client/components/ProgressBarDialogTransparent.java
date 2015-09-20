package com.twocents.ui.client.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.ui.handler.progress.threadprogress.ThreadProgress;
import com.twocents.ui.resources.UIImages;

public class ProgressBarDialogTransparent extends Dialog {

	protected Object result;
	private Shell shell;
	private final ThreadProgress threadProgress;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param threadProgress 
	 * @param style
	 */
	public ProgressBarDialogTransparent(Shell parent, ThreadProgress threadProgress) {
		super(parent, SWT.APPLICATION_MODAL | SWT.NO_TRIM | SWT.ON_TOP);
		this.threadProgress = threadProgress;
		setText("");
		
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		getShell().open();
		
		getShell().layout();
		Display display = getParent().getDisplay();
		
		boolean isFinished=getThreadProgress()==null?false:getThreadProgress().isFinished();

		while (!getShell().isDisposed() && 	!isFinished) {
			
			isFinished=getThreadProgress()==null?false:getThreadProgress().isFinished();
			
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
		//getShell().setSize(450, 300);
		getShell().setText(getText());
		
		final Image image = SWTResourceManager.getImage(ProgressBarDialogTransparent.class, UIImages.PROGRESSBAR_FOR_PROCESS01);
		int windowWidth=image.getBounds().width;
		int windowHeight=image.getBounds().height;
		
		//http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/Createanonrectangularshelltosimulatetransparency.htm
		
		shell.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_RED));
	    // define a region
	    Region region = new Region();
	    Rectangle pixel = new Rectangle(0, 0, 1, 1);
	    for (int y = 0; y < windowHeight; y += 2) {
	      for (int x = 0; x < windowWidth; x += 2) {
	        pixel.x = x;
	        pixel.y = y;
	        region.add(pixel);
	      }
	    }
	    // define the shape of the shell using setRegion
	    shell.setRegion(region);
	    Rectangle size = region.getBounds();
	    shell.setSize(size.width, size.height);
	    shell.addPaintListener(new PaintListener() {
	      public void paintControl(PaintEvent e) {
	        Rectangle bounds = image.getBounds();
	        Point size = shell.getSize();
	        e.gc.drawImage(image, 0, 0, bounds.width, bounds.height, 10,
	            10, size.x - 20, size.y - 20);
	      }
	    });
	    
		
		getShell().setSize(windowWidth,windowHeight);

		int widthTmp=(getParent().getBounds().width-windowWidth)/2;
		int heightTmp=(getParent().getBounds().height - windowHeight)/2;
		
		int positionX=getParent().getBounds().x+(widthTmp);
		int positionY=getParent().getBounds().y+(heightTmp);
		getShell().setBounds(positionX,positionY,windowWidth,windowHeight);
		shell.setLayout(new BorderLayout(0, 0));
		
		
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
