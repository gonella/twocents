package com.twocents.bovespa.stock;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.twocents.core.util.StringCheck;

/**
 * Ler uma planilha de tres colunas - NOME EMPRESA | ON ou PN | CODIGO
 * 
 * Retirado do link:
 * http://www.bmfbovespa.com.br/cias-listadas/titulos-negociaveis
 * /BuscaTitulosNegociaveis.aspx?Idioma=pt-br
 * 
 * @author Adriano Gonella
 * 
 */
public class ListStockInBovespa {

	private static Logger logger = Logger.getLogger(ListStockInBovespa.class);
	private final String fileName = "/com/twocents/resources/ListaAtivosDaBovespa.xls";

	public final Integer COLUMNS = 3;
	private Workbook workbook;
	private Map<String, StockSuggestion> listStockAvailableInBovespaWebSite;

	public static void main(String arg[]) {

		ListStockInBovespa listStockInBovespa = new ListStockInBovespa();
		Map<String, StockSuggestion> map = listStockInBovespa.getList();

		if (CollectionUtils.isEmpty(map)) {
			return;
		}
		Set<String> keySet = map.keySet();
		for (String keyCode : keySet) {
			StockSuggestion stockSuggestion = map.get(keyCode);
			logger.info(stockSuggestion);
		}

	}

	public ListStockInBovespa()// throws CoreException
	{
		try {

			InputStream inputFileName = getClass()
					.getResourceAsStream(fileName);

			// URL url = LoadResource.getResource(fileName, this.getClass());

			logger.info("Importando ativos da bovespa :" + fileName);

			/*
			 * if(!file.exists()){ throw new
			 * CoreException("Não foi encontrado o arquivo :"+fileNamePath); }
			 */

			// InputStream inputFileName =
			// LoadResource.getResourceAsStream(fileName, this.getClass());
			workbook = Workbook.getWorkbook(inputFileName);

			setListStockAvailableInBovespaWebSite(read());

		} catch (Exception e) {
			logger.error(this.getClass().toString(), e);
		}
	}

	private Map<String, StockSuggestion> read() {

		if (workbook == null) {
			return null;
		}
		Map<String, StockSuggestion> map = new HashMap<String, StockSuggestion>();

		String row[] = new String[COLUMNS];

		Sheet sheet = workbook.getSheet(0);

		int rows = sheet.getRows();
		logger.debug("Arquivo - Row:" + rows + " Col:" + COLUMNS);
		String companyName = null;
		for (int i = 0; i < rows; i++) {

			row = new String[COLUMNS];

			for (int j = 0; j < COLUMNS; j++) {
				Cell cell = sheet.getCell(j, i);
				row[j] = cell.getContents();
			}

			if (StringCheck.isEmpty(row[0])) {

				if (StringCheck.isEmpty(companyName)) {
					continue;
				} else {
					map.put(row[2], new StockSuggestion(companyName, row[2],
							row[1]));
				}
			} else {
				map.put(row[2], new StockSuggestion(row[0], row[2], row[1]));
			}
			companyName = row[0];

		}
		if (!CollectionUtils.isEmpty(map.keySet())) {
			logger.info("Ativos carregados [" + map.keySet().size() + "]");
		}

		return map;
	}

	public void setListStockAvailableInBovespaWebSite(
			Map<String, StockSuggestion> listStockAvailableInBovespaWebSite) {
		this.listStockAvailableInBovespaWebSite = listStockAvailableInBovespaWebSite;
	}

	public Map<String, StockSuggestion> getList() {
		return listStockAvailableInBovespaWebSite;
	}
}
