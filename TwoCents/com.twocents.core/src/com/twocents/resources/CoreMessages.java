package com.twocents.resources;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import com.twocents.spring.ServiceLocator;

public class CoreMessages {

	
	public static String getMessage(Integer errorCode){
		return getMessage(errorCode.toString());
	}
	
	public static String getMessage(String key){
		return getMessage(key, new Object[]{});
	}
	
	public static String getMessage(String key, Object ... args){
		try {
			return ServiceLocator.getContext().getMessage(key, args, LocaleContextHolder.getLocale());
		} catch (NoSuchMessageException e) {
			return key;
		}
	}
	
}
