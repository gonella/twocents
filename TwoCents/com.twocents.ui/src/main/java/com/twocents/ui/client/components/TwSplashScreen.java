package com.twocents.ui.client.components;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import com.swtdesigner.SWTResourceManager;
import com.twocents.ui.client.action.TwoCentsUIAction;
import com.twocents.ui.client.common.windows.FormBase;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.resources.UIImages;

public class TwSplashScreen {

    private Logger logger = Logger.getLogger(TwSplashScreen.class);
    private Display display;
    private ProgressBar bar;
    private Shell shell;
    private Label label1;
    TwoCentsUIAction environmentAction;

    public TwSplashScreen() {

        display = new Display();
        final int[] count = new int[] { 4 };
        final Image image = new Image(display, 300, 300);
        GC gc = new GC(image);
        gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
        gc.fillRectangle(image.getBounds());
        gc.drawText("Loading..", 10, 10);

        gc.dispose();
        shell = new Shell(SWT.ON_TOP);
        {
            /*label1 = new Label(shell, SWT.NONE);
			FormData label1LData = new FormData(100, 20);
			label1LData.left =  new FormAttachment(0, 1000, 6);
			label1LData.top =  new FormAttachment(0, 1000, 220);
			label1LData.width = 100;
			label1LData.height = 20;
			label1.setLayoutData(label1LData);

			label1.setText(CoreUtil.getTwoCentsVersion());
			label1.setSize(100, 20);*/
        }
        bar = new ProgressBar(shell, SWT.INDETERMINATE);
        Label label = new Label(shell, SWT.NONE);
        label.setImage(SWTResourceManager.getImage(TwSplashScreen.class, UIImages.COM_TWOCENTS_UI_RESOURCES_SPLASH_SCREEN));
        FormLayout layout = new FormLayout();
        shell.setLayout(layout);
        FormData labelData = new FormData();
        labelData.right = new FormAttachment(100, 0);
        labelData.bottom = new FormAttachment(100, 0);
        label.setLayoutData(labelData);
        FormData progressData = new FormData();
        progressData.left = new FormAttachment(0, 5);
        progressData.right = new FormAttachment(100, -5);
        progressData.bottom = new FormAttachment(100, -5);
        bar.setLayoutData(progressData);
        shell.pack();
        Rectangle splashRect = shell.getBounds();
        Rectangle displayRect = display.getBounds();
        int x = (displayRect.width - splashRect.width) / 2;
        int y = (displayRect.height - splashRect.height) / 2;
        shell.setLocation(x, y);
        shell.open();
    }

    public void start(FormBase twoCentsWindow, int nStep){

        new Thread(){
            @Override
            public void run() {
                environmentAction = new TwoCentsUIAction();
            }
        }.start();


        // Set up the event loop.
        while (environmentAction ==null || ! environmentAction.isInitialized()) {
            if (!display.readAndDispatch()) {
                // If no more entries in event queue
                display.sleep();
            }
        }
        display.dispose();
        ContextHolderUI.setEnvironmentAction(environmentAction);
        twoCentsWindow.setEnvironmentAction(environmentAction);
        twoCentsWindow.open();


    }

}
