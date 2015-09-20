package com.twocents.ui.client.table;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.twocents.ui.resources.UIMessages;
import com.twocents.ui.util.DialogUtil;

public class DeleteTableItemListener implements Listener {

    private Table sourceTable;
    private TableAction<Object> action;
    private String contentMessage;

    public DeleteTableItemListener(Table sourceTable, String contentMessage,
            TableAction<Object> action) {
        this.sourceTable = sourceTable;
        this.contentMessage = contentMessage;
        this.action = action;
    }


    public void handleEvent(Event event) {
        Point pt = new Point(event.x, event.y);
        TableItem item = sourceTable.getItem(pt);

        if (item == null)
            return;
        for (int col = 0; col < sourceTable.getColumnCount(); col++) {

            if (col == 0) {
                Rectangle rect = item.getBounds(col);
                if (rect.contains(pt)) {
                    int index = sourceTable.indexOf(item);

                    deleteItemSelectedOnTable(index, item, col);

                    break;
                }
            }
        }

    }

    private void deleteItemSelectedOnTable(int index, TableItem item, int col) {

        boolean showDialogConfirmation = DialogUtil.showRemoveConfirmation(
                UIMessages.getMessage("DIALOG_CONFIRMATION_REMOVE"),
                contentMessage, sourceTable.getShell());

        if (showDialogConfirmation) {
            action.deleteItemFromTable(index, item, col);
        }

    }

}
