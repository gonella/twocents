package com.twocents.data.excel;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Broker;
import com.twocents.model.OperationKey;
import com.twocents.model.OperationType;
import com.twocents.service.OperationService;
import com.twocents.spring.ServiceLocator;

public class ExportProcess {

	private final OperationService operationService = (OperationService) ServiceLocator.getBean(OperationService.BEAN_NAME);
	private static Logger logger = Logger.getLogger(ExportProcess.class);

	private static final String OPERACOES_EXPORTADAS = "Operações Exportadas";
	private final String fileName;
	private final boolean isXls;
	private WritableWorkbook workbook;

	public ExportProcess(boolean isXls,String fileName) throws CoreException{
		this.isXls = isXls;
		this.fileName = fileName;

		fileName=validateName(isXls,fileName);
		
		logger.info("[Exportação] para o arquivo :"+getFileName());

		
		try {
			workbook = Workbook.createWorkbook(new File(fileName));

		} catch (Exception e) {
			throw new CoreException(e.getMessage(),e);
		}			
	}
	private String validateName(boolean isXlsTmp, String fileNameTmp) {
		
		String extensionXls=".xls";
		
		if(isXlsTmp){
			int indexOf = fileNameTmp.indexOf(extensionXls);
			if(indexOf<0){
				fileNameTmp = fileNameTmp+extensionXls;
			}
		}
		
		return fileNameTmp;
	}
	public void write(Account account) throws CoreException{

		try {
			//List<StockOperation> allOperationFromAccount = getOperationService().findStockOperationByAccount(account);
			List<Map<String, Object>> allOperationFromAccount = getOperationService().findOperationByAccountWithDateOrderedAsc(account);
		
			if(CollectionUtils.isEmpty(allOperationFromAccount)){
				throw new CoreException("Não existe operações para serem exportadas");
			}
			
			logger.info("Exportando ["+allOperationFromAccount.size()+"] operações");
			if(isXls){
				logger.debug("Exportando XLS");
				WritableSheet sheet = workbook.createSheet(OPERACOES_EXPORTADAS, 0);
		
			
				int row=0;
				
				for (Map<String, Object> map : allOperationFromAccount) {
								
					String date= FormatUtil.formatDateAndTime(new Date( ((Timestamp)map.get(OperationKey.OPERATION_DATE.toString())).getTime() ));
					String ativo=(String)map.get(OperationKey.STOCK.toString());
					String tipo = OperationType.convert( OperationType.toType( (String)map.get(OperationKey.OPERATION_TYPE.toString()) ) );
					
					String quantidade = ((Integer)map.get(OperationKey.AMOUNT.toString())).toString();
					String preco = FormatUtil.toReal(((Double)map.get(OperationKey.PRICE.toString())));
					String codigoBovespa=account.getAccountNumber();
					String corretora=account.getBroker().getName();
					
					sheet.addCell(new Label(0,row, date)); 
					sheet.addCell(new Label(1,row, ativo));
					sheet.addCell(new Label(2,row, tipo));
					sheet.addCell(new Label(3,row, quantidade));
					sheet.addCell(new Label(4,row, preco));
					sheet.addCell(new Label(5,row, codigoBovespa));
					sheet.addCell(new Label(6,row, corretora));
					
					row++;
				}
				
				workbook.write(); 
				workbook.close();
				logger.debug("Export com sucesso. "+getFileName());
			}
			else{
				throw new CoreException("Operação não implementada");
			}
		
		} catch (Exception e) {
			throw new CoreException("Problema durante o Export Process",e);
		} 
		
	}

	public String getFileName() {
		return fileName;
	}

	public boolean isXls() {
		return isXls;
	}
	public OperationService getOperationService() {
		return operationService;
	}
}
