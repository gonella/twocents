/***************************************************************************
 * Copyright 2001-2008 The VietSpider         All rights reserved.  		 *
 **************************************************************************/
package com.twocents.report.handler.progress;


/** 
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@yahoo.com
 * Mar 3, 2008  
 */
public abstract class Worker {

  private int isRunning = -1;

  public void executeBefore() {
    isRunning = 0;
    before();
  }

  public void executeTask() {
    execute();
    isRunning = 1;
  }

  public void abortTask() {
    abort();
    isRunning = -2;
  }

  public boolean isRunning() { return isRunning == 0; }

  public boolean isComplete() {return isRunning == 1; }

  public abstract void before() ;

  public abstract void execute();

  public abstract void abort();

  public abstract void after();

}
