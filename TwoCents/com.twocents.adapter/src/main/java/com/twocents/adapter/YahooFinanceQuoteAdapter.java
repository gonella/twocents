package com.twocents.adapter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.twocents.core.adapter.QuoteAdapter;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Quote;
import com.twocents.model.Stock;
import com.twocents.service.CustodyService;
import com.twocents.service.QuoteService;
import com.twocents.spring.ServiceLocator;

public class YahooFinanceQuoteAdapter implements QuoteAdapter {

	private QuoteService quoteService = (QuoteService) ServiceLocator
			.getBean(QuoteService.BEAN_NAME);
	private CustodyService custodyService = (CustodyService) ServiceLocator
			.getBean(CustodyService.BEAN_NAME);

	public static String BEAN_NAME = "yahooQuoteAdapter";

	private static final Logger logger = Logger
			.getLogger(BovespaQuoteAdapter.class);
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"dd/MM/yyyy h:mma", Locale.getDefault());

	private String url = "http://finance.yahoo.com/d/quotes.csv?f=d1l1t1&s=";
	private HttpClient client = new HttpClient();

	public List<Quote> findQuote(List<Stock> stocks) throws CoreException {
		
		if (stocks == null || stocks.isEmpty()) {
			return new ArrayList<Quote>();
		}

		
		
		
		StringBuilder stockCode = new StringBuilder();
		//logger.info("Ativos a serem atualizadadas :");
		for (Stock stock : stocks) {
			stockCode.append(stock.getCode());
			stockCode.append(".SA");
			stockCode.append("+");
			
			logger.info("Atualizando Ativo :"+stock.getCode());
			
		}

		stockCode.deleteCharAt(stockCode.length() - 1);

		logger.info("Baixando cotações do link: " + url + stockCode);
		
		HttpMethod method = new GetMethod(url + stockCode);

		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		String proxyHost = System.getProperty("http.proxyHost");
		String proxyPort = System.getProperty("http.proxyPort");
		if (StringUtils.isNotBlank(proxyPort)) {
			client.getHostConfiguration().setProxy(proxyHost,
					Integer.valueOf(proxyPort));
		}

		List<Quote> quotations = new ArrayList<Quote>();

		try {

			// Determine the conection timeout
			client.getParams().setParameter("http.connection.timeout",
					45 * 1000);

			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				throw new CoreException("Method failed: "+ method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary
			// data
			String message = new String(responseBody);
			logger.debug(message);

			StringTokenizer strTke = new StringTokenizer(message, ",\n");

			Timestamp quoteDate = null;
			Double price = null;
			int index = 0;

			do {
				String lastTradeDate = strTke.nextToken().trim().replace('\"',
						' ');

				if (lastTradeDate.trim().equalsIgnoreCase("N/A")) {
					for (int i = 0; i < 2; i++) {
						strTke.nextToken();
					}
					continue;
				}

				String lastTradeValue = strTke.nextToken().trim().replace('\"',
						' ');
				String lastTradeTime = strTke.nextToken().trim().replace('\"',
						' ');
				String finalDate = lastTradeDate + " " + lastTradeTime;
				quoteDate = new Timestamp(dateFormatter.parse(finalDate)
						.getTime());
				price = Double.valueOf(lastTradeValue);

				Stock currentStock = stocks.get(index++);
				Quote quotation = quoteService.getCurrentQuote(currentStock
						.getId());

				if (quotation == null) {
					quotation = new Quote();
				}

				quotation.setQuoteDate(quoteDate);
				quotation.setPrice(price);
				quotation.setStock(currentStock);

				custodyService.updateCustodyLastPrice(currentStock, price);

				quotations.add(quotation);

			} while (strTke.hasMoreTokens());

		} catch (HttpException e) {
			logger.error(e);
			throw new CoreException("Fatal protocol violation: "
					+ e.getMessage());
		} catch (Exception e) {
			logger.error(e);
			throw new CoreException("Fatal transport error: " + e.getMessage());
		} finally {
			// Release the connection.
			method.releaseConnection();
		}

		return quotations;
	}

	public String getAdapterName() {
		return "Yahoo Finance";
	}

	public String getAdapterBeanName() {
		return BEAN_NAME;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Quote findQuote(Stock stock) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}
}
