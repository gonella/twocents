package com.twocents.adapter;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.MasonTagTypes;
import net.htmlparser.jericho.MicrosoftTagTypes;
import net.htmlparser.jericho.PHPTagTypes;
import net.htmlparser.jericho.Source;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.twocents.core.adapter.QuoteAdapter;
import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Quote;
import com.twocents.model.Stock;
import com.twocents.service.CustodyService;
import com.twocents.spring.ServiceLocator;

public class BovespaQuoteAdapter implements QuoteAdapter {

	public static String BEAN_NAME = "bovespaQuoteAdapter";

	private CustodyService custodyService = (CustodyService) ServiceLocator
			.getBean(CustodyService.BEAN_NAME);

	private static final Logger logger = Logger
			.getLogger(BovespaQuoteAdapter.class);
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");

	private String link = "http://www.bmfbovespa.com.br/Cotacao-Rapida/ExecutaAcaoCotRapXSL.asp?gstrCA=&intIdiomaXsl=0&txtCodigo=";

	public BovespaQuoteAdapter() {
		MasonTagTypes.register();
		MicrosoftTagTypes.register();
		PHPTagTypes.register();
	}

	public Quote findQuote(Stock stock) throws CoreException {

		logger.info("Baixando cotações do link: " + link);

		Quote quotation = stock.getLastQuote();

		if (quotation == null) {
			quotation = new Quote();
		}

		logger.info("Atualizando ativo: " + stock.getCode());

		String proxyHost = System.getProperty("http.proxyHost");
		String proxyPort = System.getProperty("http.proxyPort");

		try {

			URL url = new URL(link + stock.getCode());
			Source source = null;

			if (StringUtils.isNotBlank(proxyPort)) {
				SocketAddress address = new InetSocketAddress(proxyHost,
						Integer.valueOf(proxyPort));
				Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
				source = new Source(url.openConnection(proxy));

			} else {
				source = new Source(url);
			}

			if (source.getElementById("msgErro") == null) {
				Element table = source.getElementById("tbCotacoesInfo");
				List<Element> tdElements = table
						.getAllElementsByClass("tdValor");

				for (Element td : tdElements) {

					String content = td.getContent().toString().trim();

					try {
						logger.debug(td);
						double price = FormatUtil.toDouble(content.substring(2).trim());
						quotation.setPrice(price);
						break;
					} catch (Exception e) {
						logger.debug("Elemento atual: " + td);
					}

				}

				List<Element> spanElements = source
						.getAllElements(HTMLElementName.SPAN);
				StringBuffer timestampBuffer = new StringBuffer();

				for (Element element : spanElements) {
					logger.debug(element);

					if (element.getStartTag().toString().contains(
							"divDataMovimentacaoAtivo")) {
						timestampBuffer.append(element.getContent().toString()
								.trim());
						timestampBuffer.append(" ");

					}

					if (element.getStartTag().toString().contains(
							"divHorarioMovimentacaoAtivo")) {
						timestampBuffer.append(element.getContent().toString()
								.trim());

					}

				}

				if (timestampBuffer.length() < 19) {
					timestampBuffer.append("00:00:00");
				}

				Timestamp quoteDate = new Timestamp(dateFormatter.parse(
						timestampBuffer.toString()).getTime());
				quotation.setQuoteDate(quoteDate);
				quotation.setStock(stock);

				custodyService.updateCustodyLastPrice(stock, quotation
						.getPrice());
			}

		} catch (HttpException e) {
			logger.error(e);
			throw new CoreException("Fatal protocol violation: ",e);
		} catch (Exception e) {
			logger.error(e);
			throw new CoreException("Fatal transport error: ",e);
		}

		logger.info("Cotacao atualizada: " + quotation);
		return quotation;
	}

	public String getAdapterName() {
		return "Bovespa";
	}

	public String getAdapterBeanName() {
		return BEAN_NAME;
	}

	public void setUrl(String url) {
		this.link = url;
	}

	@Override
	public List<Quote> findQuote(List<Stock> stocks) throws CoreException {

		if (stocks == null || stocks.isEmpty()) {
			return new ArrayList<Quote>();
		}

		List<Quote> quotes = new ArrayList<Quote>();
		for (Stock st : stocks) {
			quotes.add(this.findQuote(st));
		}

		return quotes;
	}

	public static void main(String[] args) {
		BovespaQuoteAdapter adapter = new BovespaQuoteAdapter();
		Stock st = new Stock();
		st.setCode("ETER3");
		try {
			adapter.findQuote(st);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
