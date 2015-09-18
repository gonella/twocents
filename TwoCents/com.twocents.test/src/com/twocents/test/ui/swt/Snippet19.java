package com.twocents.test.ui.swt;

/*
 * Text example snippet: verify input (only allow digits)
 *
 * For a list of all SWT example snippets see
 * http://dev.eclipse.org/viewcvs/index.cgi/%7Echeckout%7E/platform-swt-home/dev.html#snippets
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Snippet19 {

  public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    Text text = new Text(shell, SWT.BORDER | SWT.V_SCROLL);
    text.setBounds(10, 10, 200, 200);
    text.addListener(SWT.Verify, new Listener() {
      public void handleEvent(Event e) {
        String string = e.text;
        char[] chars = new char[string.length()];
        string.getChars(0, chars.length, chars, 0);
        for (int i = 0; i < chars.length; i++) {
          if (!('0' <= chars[i] && chars[i] <= '9')) {
            e.doit = false;
            return;
          }
        }
      }
    });
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }
}
