package com.twocents.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.twocents.core.adapter.QuoteAdapter;

public class ServiceLocator {
	
	private static 	ApplicationContext context =  new ClassPathXmlApplicationContext(new String[]{"spring-config.xml", "dataSource-config.xml"}, ServiceLocator.class);
	
	private static 	ApplicationContext contextAdapters = 
		new ClassPathXmlApplicationContext(new String[]{"quote-config.xml"}, ServiceLocator.class);
	
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
	
	public static ApplicationContext getContext() {
		return context;
	}

	@SuppressWarnings("unchecked")
	public static List<QuoteAdapter> getQuoteAdapters() {
		List<QuoteAdapter> adapters = new ArrayList<QuoteAdapter>();
		Map beansOfType = contextAdapters.getBeansOfType(QuoteAdapter.class);
		for (Object quoteAdapter : beansOfType.values()) {
			adapters.add((QuoteAdapter)quoteAdapter);
		}
		return adapters;
	}
	
	public static QuoteAdapter getQuoteAdapter(String adapterBeanName) {
		return (QuoteAdapter)contextAdapters.getBean(adapterBeanName);
	}
	
	
}
