package com.twocents.test.export;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.twocents.core.populate.PopulateOperationData;
import com.twocents.core.util.FormatUtil;
import com.twocents.data.excel.ExportProcess;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.OperationKey;
import com.twocents.model.OperationType;
import com.twocents.test.core.TestEnvironment;

public class TestExport extends TestEnvironment {

	private static Logger logger = Logger.getLogger(TestExport.class);

	public TestExport() {
		super();
		try {

			Account account = new Account();
			account.setId(new Long(1));
			account.setAccountNumber("17171717");

			ExportProcess process = new ExportProcess(true, "test/export.xls");

			process.write(account);

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String arg[]) {
		new TestExport();

	}

	@Override
	public void createStockBrokerMockData() {
		getDefaultdata().createStockBrokerMockData();

	}

	@Override
	public List<Map<OperationKey, Object>> mockOperationsData() {
		List<Map<OperationKey, Object>> list = new ArrayList<Map<OperationKey, Object>>();

		try {
			list.add(PopulateOperationData.createOperationMap("CYRE3",
					OperationType.BUY, 1200, 22.46, FormatUtil
							.parseDateAndTime("04/11/2010 12:00:10"), "-"));
			list.add(PopulateOperationData.createOperationMap("CYRE3",
					OperationType.SELL, 1200, 22.19, FormatUtil
							.parseDateAndTime("04/11/2010 12:00:90"), "-"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
}
