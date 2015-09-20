package com.twocents.ui.handler.progress.worker;

import static com.twocents.ui.handler.progress.images.Images.getImage;

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

import com.twocents.report.handler.progress.ImageSequencer;
import com.twocents.report.handler.progress.Worker;
import com.twocents.ui.handler.progress.images.Images;

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
    shell.setLayout(new GridLayout(2, true));

    shell.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_INFO_BACKGROUND));

    ImageSequencer seqB = new ImageSequencer(shell, SWT.NONE,
        new Image[]{ 
        getImage(Images.IMG_INDICATOR_B_1),
        getImage(Images.IMG_INDICATOR_B_2),
        getImage(Images.IMG_INDICATOR_B_3),
        getImage(Images.IMG_INDICATOR_B_4),
        getImage(Images.IMG_INDICATOR_B_5),
        getImage(Images.IMG_INDICATOR_B_6),
        getImage(Images.IMG_INDICATOR_B_7),
        getImage(Images.IMG_INDICATOR_B_8),				
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
    //XPWidgetTheme.setWin32Theme(shell);
    shell.open();
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
  }

  public Shell getShell() { return shell; }
  
  /*public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Progress indicators");
		shell.setLayout(new GridLayout(1,false));	
		shell.setBounds(10, 50, 250, 200);		

		new WaitLoading(shell, );

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()){
				display.sleep();
			}
		}

		display.dispose();
	}*/
}