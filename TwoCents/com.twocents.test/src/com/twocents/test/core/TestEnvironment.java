package com.twocents.test.core;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.twocents.core.populate.PopulateOperationData;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.BrokerType;
import com.twocents.model.BrokerageNote;
import com.twocents.model.OperationKey;
import com.twocents.model.OperationType;
import com.twocents.model.StockBroker;
import com.twocents.ui.client.facade.EnvironmentFacade;
import com.twocents.ui.client.facade.OperationFacade;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.mockdata.MockData;

public abstract class TestEnvironment {

	public static final Logger logger = Logger.getLogger(TestEnvironment.class);

	private static final PopulateOperationData populateOperationData=new PopulateOperationData();
	private static final MockData defaultData=new MockData();
	
	public static final OperationFacade operationFacade=new OperationFacade();
	public static final EnvironmentFacade environmentFacade=new EnvironmentFacade();	
	
	public static EnvironmentFacade getEnvironmentFacade() {
		return environmentFacade;
	}
	
	public static OperationFacade getOperationFacade() {
		return operationFacade;
	}

	public static PopulateOperationData getPopulateOperationdata() {
		return populateOperationData;
	}

	public static MockData getDefaultdata() {
		return defaultData;
	}

	public TestEnvironment(){
		List<BrokerageNote> allStockBrokerAvailable = getOperationFacade().getBrokerageNoteService().findAll();
		
		if(allStockBrokerAvailable==null || (allStockBrokerAvailable!=null && allStockBrokerAvailable.size()<=0)){
			initData();
		}
	}
	
	public void initData(){
		
		//cria os usuários
		createStockBrokerMockData();
		
		//seta as operaçoes para cada usuário criado
		try {
			populateDataForAllStockBrokers(mockOperationsData());
		} catch (CoreException e) {
			logger.error(e);
		}
	}

	/**
	 * Metodo responsavel por cadastrar os stockbroker
	 */
	public abstract void createStockBrokerMockData();

	/**
	 * Metodo responsavel gerar todas as operações mock para test
	 */
	public abstract List<Map<OperationKey, Object>> mockOperationsData();
	
	private void populateDataForAllStockBrokers(List<Map<OperationKey, Object>> list) throws CoreException{
		
		if(CollectionUtils.isEmpty(list) || (list!=null && list.size()<=0)){
			throw new CoreException("Não existe operação para ser registrada");
		}
		
		PopulateOperationData populateOperation = new PopulateOperationData();
		
		List<StockBroker> listAllStockBroker = getEnvironmentFacade().listAllStockBroker();
		
		for (Iterator iteratorsb = listAllStockBroker.iterator(); iteratorsb
				.hasNext();) {
			StockBroker stockBroker = (StockBroker) iteratorsb.next();
			
			logger.info("TestEnvironment : Getting information about StockBroker :"+stockBroker.getName());
			
			List<Account> accountList = stockBroker.getAccountList();
			
			for (Iterator iterator = accountList.iterator(); iterator.hasNext();) {
				Account account = (Account) iterator.next();
				logger.info("Mocking Account: "+account.getUser().getName());
				populateOperation.registerOperations(account,list);
				break;
			}
			break;
		}
		
	}
	

}
