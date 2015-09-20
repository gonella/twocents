package com.twocents.core.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.twocents.resources.CoreMessages;

public class CoreUtil {
	
	private static Map<String, Integer> months = new HashMap<String, Integer>();
	
	static {
		months.put("A", Calendar.JANUARY);
		months.put("B", Calendar.FEBRUARY);
		months.put("C", Calendar.MARCH);
		months.put("D", Calendar.APRIL);
		months.put("E", Calendar.MAY);
		months.put("F", Calendar.JUNE);
		months.put("G", Calendar.JULY);
		months.put("H", Calendar.AUGUST);
		months.put("I", Calendar.SEPTEMBER);
		months.put("J", Calendar.OCTOBER);
		months.put("K", Calendar.NOVEMBER);
		months.put("L", Calendar.DECEMBER);
	}
	
	public static int getOptionExpirationMonth(String letter) {
		Integer month = months.get(letter.toUpperCase());
		if(month != null) {
			return month;
		}
		return Calendar.JANUARY;
	}
	
	/**
	 * It does not check the holidays.
	 * @param letter
	 * @return
	 */
	public static Date getOptionExpirationDate(String letter) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, getOptionExpirationMonth(letter));
		cal.set(Calendar.WEEK_OF_MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			cal.set(Calendar.WEEK_OF_MONTH, 3);
		} else {
			cal.set(Calendar.WEEK_OF_MONTH, 4);
		}	
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}
	
	public static void main(String[] args) {
		System.out.println(getOptionExpirationDate("A"));
		System.out.println(getOptionExpirationDate("B"));
		System.out.println(getOptionExpirationDate("C"));
		System.out.println(getOptionExpirationDate("D"));
		System.out.println(getOptionExpirationDate("E"));
		System.out.println(getOptionExpirationDate("F"));
		System.out.println(getOptionExpirationDate("G"));
		System.out.println(getOptionExpirationDate("H"));
		System.out.println(getOptionExpirationDate("I"));
		System.out.println(getOptionExpirationDate("J"));
		System.out.println(getOptionExpirationDate("K"));
		System.out.println(getOptionExpirationDate("L"));
		System.out.println(getOptionExpirationDate("M"));
	}

	
	public static String getOperationType(String type) {
		return CoreMessages.getMessage(type);
	}
	
	public static Timestamp getMaxTimestamp(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}
	
	public static Timestamp getMinTimestamp(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}
	
	

	

}
