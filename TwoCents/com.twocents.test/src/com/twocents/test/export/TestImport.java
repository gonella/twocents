package com.twocents.test.export;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.twocents.data.excel.ImportProcess;
import com.twocents.exceptions.CoreException;
import com.twocents.model.BrokerageNote;
import com.twocents.model.BrokerageNoteItem;
import com.twocents.model.Operation;
import com.twocents.model.OperationKey;
import com.twocents.model.StockBroker;
import com.twocents.test.core.TestEnvironment;

public class TestImport extends TestEnvironment {

	private static Logger logger = Logger.getLogger(TestImport.class);

	public TestImport() {

		String fileName = "test/ImportTest.xls";
		ImportProcess importProcess = null;
		try {
			importProcess = new ImportProcess(fileName);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String[]> read = importProcess.read();

		String str = "";
		for (String[] strings : read) {
			str = "";
			for (String string : strings) {
				str += string + ",";
			}
			logger.info(str);
		}

		StockBroker stockBroker = getPopulateOperationdata()
				.getStockBrokerService().findAll().get(0);
		List<String> process = importProcess.process(read, stockBroker);
		
		for (String string : process) {
			logger.info(string);
		}

	}

	public static void main(String arg[]) {
		new TestImport();

		List<Operation> findAllOperation = getOperationFacade()
				.getOperationService().findAll();
		for (Iterator iterator = findAllOperation.iterator(); iterator
				.hasNext();) {
			Operation operation = (Operation) iterator.next();
			logger.info(operation);
		}

		List<BrokerageNote> findAll = getOperationFacade()
				.getBrokerageNoteService().findAll();

		for (Iterator iterator = findAll.iterator(); iterator.hasNext();) {
			BrokerageNote brokerageNote = (BrokerageNote) iterator.next();
			Date date = brokerageNote.getDate();

			Set<BrokerageNoteItem> brokerageItemNoteList = brokerageNote
					.getBrokerageItemNoteList();
			logger.info(brokerageNote.toString());

			/*
			 * for (Iterator iterator2 = brokerageItemNoteList.iterator();
			 * iterator2 .hasNext();) { BrokerageNoteItem brokerageNoteItem =
			 * (BrokerageNoteItem) iterator2 .next();
			 * 
			 * logger.info(brokerageNoteItem.toString()); }
			 */

		}

	}

	@Override
	public void createStockBrokerMockData() {
		getDefaultdata().createStockBrokerMockData();

	}

	@Override
	public List<Map<OperationKey, Object>> mockOperationsData() {
		return null;
	}
}
