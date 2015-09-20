/***************************************************************************
 * Copyright 2001-2007 The VietSpider         All rights reserved.  		 *
 **************************************************************************/
package com.twocents.ui.handler.progress.worker;

import org.eclipse.swt.widgets.Control;

import com.twocents.report.handler.progress.Worker;

/** 
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@yahoo.com
 * Dec 10, 2007  
 */
public class ThreadExecutor extends Thread {
  
  private Control control;
  private Worker excutor;
  
  public ThreadExecutor(Worker excutor, Control control) {
    this.control = control;
    this.excutor = excutor;
  }
  
  public void run () {
    if(control.isDisposed()) return;
    control.getDisplay().syncExec(new Runnable () {
      public void run () {
        excutor.executeBefore();
        
        new Thread(new Runnable() {
          public void run () {
            excutor.executeTask();
          }
        }).start();
        
        while(excutor.isRunning()) {
          if(control.isDisposed()) return;
          if(!control.getDisplay().readAndDispatch()) control.getDisplay().sleep();
        }
        
        try {
          excutor.after();
        } catch (Exception e) {
          //ClientLog.getInstance().setException(null, e);
        }
      }
    });
  }

}
