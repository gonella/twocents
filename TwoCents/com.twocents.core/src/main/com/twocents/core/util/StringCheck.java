package com.twocents.core.util;

import org.apache.commons.lang.StringUtils;


public class StringCheck {

	
	public static boolean isEmpty(String str){
		return StringUtils.isEmpty(str);
	}
	
	public static boolean isNotBlank(String str){
		return StringUtils.isNotBlank(str);
	}
	
	public static boolean isNumeric(String str){
		return StringUtils.isNumeric(str);
	} 
}
