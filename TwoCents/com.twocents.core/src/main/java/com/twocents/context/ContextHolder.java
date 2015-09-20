package com.twocents.context;

import com.twocents.core.adapter.QuoteAdapter;
import com.twocents.environment.Environment;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.StockBroker;
import com.twocents.model.TwUser;

public class ContextHolder {
	
	private static String TW_RUNNING_VERSION=Environment.DEV_MODE;
	
	private static ThreadLocal<QuoteAdapter> currentQuoteAdpater = new InheritableThreadLocal<QuoteAdapter>();
	private static ThreadLocal<TwUser> currentUser = new ThreadLocal<TwUser>();
	private static ThreadLocal<StockBroker> currentStockBroker = new ThreadLocal<StockBroker>();
	private static ThreadLocal<Account> currentAccount = new ThreadLocal<Account>();
	
	public static QuoteAdapter getCurrentQuoteAdapter() {
		return currentQuoteAdpater.get();
	}
		
	public static void setCurrentQuoteAdapter (QuoteAdapter quoteAdapter) {
		currentQuoteAdpater.set(quoteAdapter);
	}
	

	public static TwUser getUserLogged() throws CoreException {

		TwUser user = currentUser.get();
		if (user == null) {
			throw new CoreException(1006);
		}
		return user;
	}
	public static Account getAccountSelected() throws CoreException{
		
		Account account = currentAccount.get();
		if (account == null) {
			throw new CoreException(1005);
		}
		return account;
	}
	
	public static StockBroker getStockBrokerLogged() throws CoreException{
		
		StockBroker stockBroker= currentStockBroker.get();
		if (stockBroker == null) {
			throw new CoreException(1007);
		}
		return stockBroker;
	}
	public static void setAccountSelected(Account account){
		currentAccount.set(account);
	}
	
	public static void setStockBrokerSelected(StockBroker stockBroker){
		currentStockBroker.set(stockBroker);
	}

	public static String getTwRunningVersion() {
		return TW_RUNNING_VERSION;
	}
	public static void setTwRunningVersion(String TW_RUNNING_VERSION) {
		ContextHolder.TW_RUNNING_VERSION=TW_RUNNING_VERSION;
	}

}
