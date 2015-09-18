package com.twocents.report.handler.progress;

import static com.twocents.report.handler.resources.Images.getImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.twocents.report.handler.resources.Images;

public class WaitLoading {

  private Shell shell;

  private Worker excutor;

  private Control control;
  
  public WaitLoading(Control control, Worker excutor_) {
    this(control, excutor_, SWT.APPLICATION_MODAL);
  }

  public WaitLoading(Control control, Worker excutor_, int style) {
    this.excutor = excutor_;
    this.control = control;
    shell = new Shell(control.getShell(), style);
    Shell parent  = control.getShell();
//    System.out.println(parent.getLocation().x + " , "+parent.getLocation().y);
    int x = parent.getLocation().x + parent.getSize().x ;
    int y = parent.getLocation().y + parent.getSize().y;
    shell.setLocation(x/2, y/2);
    
    //shell.setLocation(parent.getSize().x/2, parent.getSize().y/2);
    
    shell.setLayout(new GridLayout(2, true));

    shell.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_INFO_BACKGROUND));

    ImageSequencer seqB = new ImageSequencer(shell, SWT.NONE,
        new Image[]{ 
        /*getImage(Images.IMG_INDICATOR_B_1),
        getImage(Images.IMG_INDICATOR_B_2),
        getImage(Images.IMG_INDICATOR_B_3),
        getImage(Images.IMG_INDICATOR_B_4),
        getImage(Images.IMG_INDICATOR_B_5),
        getImage(Images.IMG_INDICATOR_B_6),
        getImage(Images.IMG_INDICATOR_B_7),
        getImage(Images.IMG_INDICATOR_B_8),
        */
        /*getImage(Images.IMG_Loading01_1),
        getImage(Images.IMG_Loading01_2),
        getImage(Images.IMG_Loading01_3),
        getImage(Images.IMG_Loading01_4),
        getImage(Images.IMG_Loading01_5),
        getImage(Images.IMG_Loading01_6),
        getImage(Images.IMG_Loading01_7),
        getImage(Images.IMG_Loading01_8),
        getImage(Images.IMG_Loading01_9),
        getImage(Images.IMG_Loading01_10),
        getImage(Images.IMG_Loading01_11),*/
   		
    	getImage(Images.IMG_Loading02_1),
        getImage(Images.IMG_Loading02_2),
        getImage(Images.IMG_Loading02_3),
        getImage(Images.IMG_Loading02_4),
        getImage(Images.IMG_Loading02_5),
        getImage(Images.IMG_Loading02_6),
        getImage(Images.IMG_Loading02_7),
        getImage(Images.IMG_Loading02_8),
        getImage(Images.IMG_Loading02_9),
        getImage(Images.IMG_Loading02_10),
        getImage(Images.IMG_Loading02_11),
        getImage(Images.IMG_Loading02_12),
        getImage(Images.IMG_Loading02_13),
        getImage(Images.IMG_Loading02_14),
        getImage(Images.IMG_Loading02_15),
        getImage(Images.IMG_Loading02_16),
        getImage(Images.IMG_Loading02_17),
        getImage(Images.IMG_Loading02_18),
        getImage(Images.IMG_Loading02_19),
        getImage(Images.IMG_Loading02_20),
        
        
    },
    250,true);

    seqB.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING,false,false));
    seqB.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

    Button button = new Button(shell, SWT.PUSH);
    button.setText("Abort");
    button.addSelectionListener(new SelectionAdapter(){
      @SuppressWarnings("unused")
      public void widgetSelected(SelectionEvent e) {
        excutor.abortTask();
        shell.dispose();
      }
    });
    shell.pack();
  }

  public void open() {
    
    shell.open();
    Display display = shell.getDisplay();
    
    Thread thread = new Thread () {
      public void run () {
        shell.getDisplay().syncExec(new Runnable () {
          public void run () {
            excutor.executeBefore();
            new Thread(new Runnable() {
              public void run () {
                excutor.executeTask();
              }
            }).start();
            
            while(excutor.isRunning()) {
              if(control.isDisposed() || shell.isDisposed()) return;
              if (!shell.getDisplay().readAndDispatch()) shell.getDisplay().sleep();
            }
            
            excutor.after();
            shell.dispose();
          }
        });
      }
    };
    thread.start ();
    
    
    while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) {
			display.sleep();
		}
	}
  }

  public Shell getShell() { return shell; }
  

}