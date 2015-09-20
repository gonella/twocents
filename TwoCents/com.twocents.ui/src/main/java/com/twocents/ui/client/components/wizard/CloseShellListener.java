package com.twocents.ui.client.components.wizard;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.twocents.ui.client.action.TwoCentsUIAction;
import com.twocents.ui.util.DialogUtil;

public class CloseShellListener implements Listener {

    private Shell dialogShell;
    public CloseShellListener(Shell dialogShell){
        this.dialogShell = dialogShell;

    }
    public void handleEvent(Event event) {

        if (DialogUtil
                .showDialogQuestion(
                        dialogShell,
                        "Sair?",
                        "Deseja sair do TwoCents? \n\n(Os dados serão perdidos e devem ser re-inseridos na próxima execução do TwoCents.)")) {
            TwoCentsUIAction.exit();
        } else {
            Display display = dialogShell.getDisplay();
            while (!dialogShell.isDisposed()) {
                if (!display.readAndDispatch())
                    display.sleep();
            }

        }

    }
}
