package com.twocents.bovespa.stock;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.ZipInputStream;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.IOUtils;

/**
 * Esta classe exporta todos os ativos negociados na Bovespa em um arquivo xls.
 * Ela serve como um utilitátrio para que os ativos que são automaticamente
 * mostrados no painel de operações estejam sempre atualizados com os que estão
 * em negociação na Bovespa.
 * 
 * @author Alexandre
 * 
 */
public class AtivosBovespaParser {

	public static void main(String[] args) throws MalformedURLException,
			IOException {
		AtivosBovespaParser ativosBovespaParser = new AtivosBovespaParser();
		ativosBovespaParser.exportToExcel();
	}

	private String unzip(String url) throws MalformedURLException, IOException {
		ZipInputStream zis = new ZipInputStream(new URL(url).openStream());

		StringWriter writer = new StringWriter();

		if (zis.getNextEntry() != null) {
			IOUtils.copy(zis, writer);
		}

		return writer.toString();
	}

	public void exportToExcel() throws IOException, MalformedURLException {

		String allStocks = unzip("http://www.bmfbovespa.com.br/suplemento/ExecutaAcaoDownload.asp?arquivo=Titulos_Negociaveis.zip");
		StringTokenizer tokenizer = new StringTokenizer(allStocks, "\n");

		Map<EmpresaKey, List<Ativo>> map = new HashMap<EmpresaKey, List<Ativo>>();

		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken();
			if (line.startsWith("01")) {
				List<Ativo> ativos = new ArrayList<Ativo>();
				String baseSymbol = line.substring(2, 6);
				String nomeEmpresa = line.substring(6, line.indexOf("  "));

				EmpresaKey key = new EmpresaKey();
				key.baseSymbol = baseSymbol;
				key.nomeEmpresa = nomeEmpresa;

				map.put(key, ativos);

			} else if (line.startsWith("02") && !line.contains("0000000DRN")
					&& !line.contains("0000000ITE")
					&& line.contains("LOTE PADRAO")) {
				String baseSymbol = line.substring(2, 6);
				EmpresaKey key = new EmpresaKey();
				key.baseSymbol = baseSymbol;

				List<Ativo> ativos = map.get(key);
				String symbol = line.substring(2, 8).trim();
				String tipo = line.substring(line.indexOf("0000000") + 7, line
						.indexOf("0000000") + 10);
				String governanca = line.substring(
						line.indexOf("9999-12-31") - 2,
						line.indexOf("9999-12-31")).trim();

				Ativo ativo = new Ativo();
				ativo.governanca = governanca.trim();
				ativo.symbol = symbol;
				ativo.tipo = tipo.trim();

				ativos.add(ativo);

			}
		}

		int coluna = 0;
		int linha = 0;

		URI uri = new File("src/com/twocents/resources/ListaAtivosDaBovespa.xls").toURI();

		WritableWorkbook workbook = Workbook.createWorkbook(new File(uri));
		WritableSheet sheet = workbook.createSheet("Sheet 1", 0);

		try {
			// StringBuffer buffer = new StringBuffer();

			for (EmpresaKey key : map.keySet()) {
				List<Ativo> ativos = map.get(key);

				coluna = 0;

				if (!ativos.isEmpty()) {
					// buffer.append(key);

					Label label = new Label(coluna, linha, key.nomeEmpresa);
					int keyIndex = 0;

					for (Ativo ativo : ativos) {

						if (!ativo.governanca.equals("MB")
								&& (ativo.tipo.equals("ON")
										|| ativo.tipo.equals("UNT") || ativo.tipo
										.contains("PN"))) {

							// buffer.append(ativo).append("\n");

							if (keyIndex == 0) {
								sheet.addCell(label);
								keyIndex++;
							}

							label = new Label(++coluna, linha, ativo.tipo
									+ (!ativo.governanca.isEmpty() ? " "
											+ ativo.governanca : ""));
							sheet.addCell(label);

							label = new Label(++coluna, linha, ativo.symbol);
							sheet.addCell(label);

							linha++;
							coluna = 0;

						}
					}

				}
			}

			workbook.write();
			workbook.close();

			// System.out.println(buffer.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class Ativo {

	public Ativo() {
	}

	String tipo;
	String governanca;
	String symbol;

	@Override
	public String toString() {
		String text = (governanca != null && !governanca.isEmpty()) ? " "
				+ governanca : ("");
		return "," + tipo.trim() + text + ",\"" + symbol + "\"";
	}
}

class EmpresaKey {

	String nomeEmpresa;
	String baseSymbol;

	@Override
	public String toString() {
		return "\"" + nomeEmpresa + "\"";
	}

	@Override
	public boolean equals(Object obj) {
		EmpresaKey empr = (EmpresaKey) obj;
		return baseSymbol.equals(empr.baseSymbol);
	}

	@Override
	public int hashCode() {
		return baseSymbol.hashCode();
	}
}
