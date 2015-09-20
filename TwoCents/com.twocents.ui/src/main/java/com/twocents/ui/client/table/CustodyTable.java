package com.twocents.ui.client.table;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class CustodyTable {

	private Table custodyTable;

	public CustodyTable(Composite group, int layout) {
		custodyTable = new Table(group, layout);
		buildTable();
	}

	public Table getCustodyTable() {
		return custodyTable;
	}
		
	private void buildTable() {

		custodyTable.setLinesVisible(true);
		custodyTable.setHeaderVisible(true);

		final TableColumn arrowIcon = new TableColumn(
				custodyTable, SWT.NONE);

		arrowIcon.setWidth(20);
		arrowIcon.setText("");

		final TableColumn stockCol = new TableColumn(
				custodyTable, SWT.NONE);
		stockCol.setWidth(55);
		stockCol.setText("Ativo");

		final TableColumn quantCol = new TableColumn(
				custodyTable, SWT.NONE);
		quantCol.setWidth(50);
		quantCol.setText("Qtde");

		final TableColumn blockedCol = new TableColumn(
				custodyTable, SWT.NONE);
		blockedCol.setWidth(50);
		blockedCol.setText("Bloq.");

		final TableColumn mediumPriceCol = new TableColumn(
				custodyTable, SWT.NONE);
		mediumPriceCol.setWidth(60);
		mediumPriceCol.setText("Preço Médio");

		final TableColumn newColumnTableColumn_10 = new TableColumn(
				custodyTable, SWT.NONE);
		newColumnTableColumn_10.setWidth(100);
		newColumnTableColumn_10.setText("Total");

		final TableColumn currentPriceCol = new TableColumn(
				custodyTable, SWT.NONE);
		currentPriceCol.setWidth(70);
		currentPriceCol.setText("Cotação");

		final TableColumn variationCol = new TableColumn(
				custodyTable, SWT.NONE);
		variationCol.setWidth(60);
		variationCol.setText("Variação");
		
	}
	
}
