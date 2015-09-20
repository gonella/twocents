package com.twocents.data.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;

import com.twocents.core.populate.PopulateOperationData;
import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Broker;
import com.twocents.model.OperationType;
import com.twocents.model.StockBroker;
import com.twocents.service.AccountService;
import com.twocents.service.BrokerService;
import com.twocents.spring.ServiceLocator;

public class ImportProcess {

	public static final String OK = "OK";
	public static final String ERROR = "ERROR";
	
	public final String formatExpected="DATA | TIPO DE OPERAÇÃO | ATIVO | QUANTIDADE | PREÇO | NUMERO BOVESPA | CORRETORA";
	private final AccountService accountService = (AccountService) ServiceLocator.getBean(AccountService.BEAN_NAME);
	private final BrokerService brokerService = (BrokerService)ServiceLocator.getBean(BrokerService.BEAN_NAME);
	private final PopulateOperationData populateOperationData = new PopulateOperationData();

	private static Logger logger = Logger.getLogger(ImportProcess.class);

	public final Integer COLUMNS=7;
	private Workbook workbook;
	private final String fileName;
	
	public ImportProcess(String fileName) throws CoreException{
		
		logger.info("[Importação] do arquivo :"+getFileName());

		this.fileName = fileName;
		try {
			
			workbook = Workbook.getWorkbook(new File(fileName));
		} catch (Exception e) {
			//logger.error(e);
			throw new CoreException("Não foi possível abrir o arquivo",e);
		}		
	}
	
	public List<String[]> read(){
		
		if(workbook==null){
			return null;
		}
		logger.info("Iniciando leitura do arquivo para importação :"+getFileName());
		
		List<String[]> list=new ArrayList();
		
		String row[]=new String[COLUMNS];
		
		Sheet sheet = workbook.getSheet(0);

		int rows = sheet.getRows();
		logger.debug("Importing - Row:"+rows+" Col:"+COLUMNS);
		for (int i = 0; i < rows; i++) {
			
			row=new String[COLUMNS];
			
			for (int j = 0; j < COLUMNS; j++) {
				Cell cell = sheet.getCell(j,i);
				row[j] = cell.getContents();
			}
			
			list.add(row);
		}
		
		return list;
	}
	
	/**
	 * Processa os items e insere, retorna a lista dos items de sucesso e não sucesso.
	 * @param list
	 */
	public List<String> process(List<String[]> list, StockBroker stockBroker){
		List<String> listResult=new ArrayList<String>();

		String ativo;
		Date date;
		OperationType operationType;
		Integer quantidade;
		Double preco;
		String accountNumber;
		String brokerName;					
		Account account;
		Broker broker;


		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			String[] str = (String[]) iterator.next();
			
			try{
				date = FormatUtil.parseDate(str[0]);
				if(date==null){
					throw new CoreException("Formato da data inválida [Ex: 20/12/2010 ou 20/12/2010 14:00:00]");	
				}
				
				try{
					//TODO no futuro validar se o ativo é ação ou algo do tipo
					ativo = str[1];
				}catch(Exception e){
					throw new CoreException("Formato do Ativo não valido");
				}
				
				operationType = OperationType.convert(str[2]);
				if(operationType==null){
					throw new CoreException("Tipo da Operação não valida");
				}
				try{
					quantidade = Integer.valueOf(str[3]);
				}catch(Exception e){
					throw new CoreException("Quantidade informada não valida, é necessário ser do tipo inteiro");
				}
				
				try{
				preco = Double.valueOf(FormatUtil.toDouble(str[4]));
				}catch(Exception e){
					throw new CoreException("Preço informado não valido, é necessário ser do tipo real, Ex: 45 ou 45,0 ou 45,5");
				}
				
				accountNumber = str[5];
				try{
					account = getAccountService().getByAccountNumber(accountNumber);
					if(account==null){
						throw new CoreException("Não foi possível encontrar a conta através do Numero Bovespa informado");
					}
				}catch(Exception e){
					throw new CoreException("Não foi possível encontrar a conta através do Numero Bovespa informado");
				}
				
				brokerName = str[6];
				
				try{
					broker = getBrokerService().findBrokerByName(brokerName, stockBroker);
					if(broker==null){
						throw new CoreException("Não foi possível encontrar a corretora através do nome informado");
					}
				}catch(Exception e){
					throw new CoreException("Não foi possível encontrar a corretora através do nome informado");
				}
				
				logger.info("Inserindo operação importada na base de dados");
				
				getPopulateOperationData().registerOperation(operationType, date, ativo, quantidade, preco, account, broker);
				
				listResult.add(OK);
			}
			catch(Exception e){
				logger.debug(e);
				listResult.add(e.getMessage());
			}
			
			
		}
		return listResult;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public BrokerService getBrokerService() {
		return brokerService;
	}

	public PopulateOperationData getPopulateOperationData() {
		return populateOperationData;
	}

	public String getFileName() {
		return fileName;
	}
	
}
