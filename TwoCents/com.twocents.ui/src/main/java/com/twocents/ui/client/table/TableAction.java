package com.twocents.ui.client.table;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public interface TableAction<T> {

	void populateTable(Table sourceTable);

	T deleteItemFromTable(int index, TableItem item, int col);

	T editTableItem(int index, TableItem item, int col);

}
