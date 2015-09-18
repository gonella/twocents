package com.twocents.ui.client.components.manage;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import com.swtdesigner.SWTResourceManager;
import com.twocents.core.populate.PopulateData;
import com.twocents.core.util.StringCheck;
import com.twocents.exceptions.CoreException;
import com.twocents.model.StockBroker;
import com.twocents.ui.client.common.action.UIAction;
import com.twocents.ui.exceptions.UIException;
import com.twocents.ui.resources.UIImages;

public class ManageStockBrokerAction extends UIAction{

	private Logger logger = Logger.getLogger(ManageStockBrokerAction.class);
	private final ManageStockBrokerDialog manageStockBrokerDialog;

	public ManageStockBrokerAction(ManageStockBrokerDialog manageStockBrokerDialog){
		this.manageStockBrokerDialog = manageStockBrokerDialog;
		
		init();
	}
	
	@Override
	public void init() {
		
		try {
			popuplateTable(getList());
		} catch (UIException e) {
			logger.error("Não foi possível popular clientes na tabela",e);
		}
			
		
	}

	public ManageStockBrokerDialog getUI(){
		return manageStockBrokerDialog;
	}
	
	public void popuplateTable(List<StockBroker> list){
		
		
		getUI().getTableClient().removeAll();
		 
		TableItem item;
		for (StockBroker stockBroker : list) {
			item = new TableItem(getUI().getTableClient(), SWT.NONE);
			populateRowString(stockBroker, item);
		}
		
	}


	@Override
	public void refresh() {
		
		try {
			popuplateTable(getList());
		} catch (UIException e) {
			logger.error("Não foi possível popular clientes na tabela",e);
		}
		
	}
	
	public List<StockBroker> getList() throws UIException{
		
		List<StockBroker> listAllStockBroker = getEnvironmentFacade().listAllStockBroker();
		
		return listAllStockBroker;
	}
	
	public void populateRowString(StockBroker stockBroker, TableItem item){
		
		//X|NOME|EMAIL|TEL|USERNAME
		item.setText(new String[] {"", stockBroker.getName(),stockBroker.getEmail(),stockBroker.getTelefone(),stockBroker.getUsername()});
		
		item.setImage(SWTResourceManager.getImage(this.getClass(), UIImages.DELETE_ICON_16));
		
	}
	public void deleteItemSelectedOnTable(int index, TableItem item, int col)  {

		try {
			if(col!=0) return;
			String idStr = item.getText(1);
			logger.info("Operation deleted id: " + idStr);
			if(StringCheck.isNumeric(idStr)) {
				//Operation operation = new Operation();
				//operation.setId(new Long(idStr));
				//operationFacade.removeOperation(operation);
				removeStockBroker();
				
				refresh();
				
			} else {
				logger.info("Id is not a number.");
			}
			
		}catch(Exception e){
			logger.error(e);
		}
	}
	
	public void removeStockBroker(){
		
		logger.info("Removendo corretor");
		/*try {
			
			
			
			refresh();
			
		} catch (UIException e1) {
			e1.printStackTrace();
		}*/
	}
	
	public boolean addStockBroker(String name,String email,String tel,String username,String password) {
		logger.info("Criando Corretor: "+name);
		try{
			
			StockBroker stockBroker=new StockBroker();
			
			stockBroker.setName(name);
			stockBroker.setEmail(email);
			stockBroker.setTelefone(tel);
			stockBroker.setUsername(username);
			stockBroker.setPassword(password);
			//stockBroker.setBroker(broker);
			
			new PopulateData().createStockBroker(stockBroker, null);
			
			refresh();
			
			return true;
		}catch(CoreException e){
			logger.error(e);
		}
		return false;
	}

	
}
