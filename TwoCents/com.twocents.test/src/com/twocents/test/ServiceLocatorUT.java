package com.twocents.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceLocatorUT {
	
	private static 	ApplicationContext context =  new ClassPathXmlApplicationContext(new String[]{"spring-tests.xml"}, ServiceLocatorUT.class);
	
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
	
	public static ApplicationContext getContext() {
		return context;
	}

}
