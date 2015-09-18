package com.twocents.core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.twocents.exceptions.CoreException;

public class DateUtil {

	public enum MONTH{
		JAN("Janeiro")
		,FEV("Fevereiro")
		,MAR("Março")
		,ABR("Abril")
		,MAI("Maio")
		,JUN("Junho")
		,JUL("Julho")
		,AGO("Agosto")
		,SET("Setembro")
		,OUT("Outubro")
		,NOV("Novembro")
		,DEZ("Dezembro")
		;
		private final String value;

		private MONTH(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
		public String toString(){
			return getValue();
		}
	}
	
	private static final String MONTH_ARRAY[]={MONTH.JAN.toString()
			,MONTH.FEV.toString()
			,MONTH.MAR.toString()
			,MONTH.ABR.toString()
			,MONTH.MAI.toString()
			,MONTH.JUN.toString()
			,MONTH.JUL.toString()
			,MONTH.AGO.toString()
			,MONTH.SET.toString()
			,MONTH.OUT.toString()
			,MONTH.NOV.toString()
			,MONTH.DEZ.toString()
			};
		
	private static Logger logger = Logger.getLogger(DateUtil.class);

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public static Date convert(String date) throws CoreException{
		try{
			java.util.Date result = dateFormat.parse(date);
			return result;
		}catch(ParseException e){
			//logger.error(e);
			throw new CoreException(6004,e);
		}
	}

	public static int calculateDifference(Date a, Date b)
	{
	    int tempDifference = 0;
	    int difference = 0;
	    Calendar earlier = Calendar.getInstance();
	    Calendar later = Calendar.getInstance();
	 
	    if (a.compareTo(b) < 0)
	    {
	        earlier.setTime(a);
	        later.setTime(b);
	    }
	    else
	    {
	        earlier.setTime(b);
	        later.setTime(a);
	    }
	 
	    while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR))
	    {
	        tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
	        difference += tempDifference;
	 
	        earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
	    }
	 
	    if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR))
	    {
	        tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
	        difference += tempDifference;
	 
	        earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
	    }
	 
	    return difference;
	}
	
	public static String getMonthName(Integer month){
		return MONTH_ARRAY[month];
	}
	
	public static String getKeyMonth(String dateWithYearAndMonth){
		String key=null;		
		key=FormatUtil.formatDateByYearAndMonth(FormatUtil.parseDateByYearAndMonth(dateWithYearAndMonth));		
		return key;
	}
	
	public static Integer getMonth(String dateWithYearAndMonth ){
		Integer month=new Integer(FormatUtil.formatDateByMonth(FormatUtil.parseDateByMonth(dateWithYearAndMonth)));
		return month;		
	}
	
	public static Date getLastDayOfCurrentMonth(){
		
		Calendar instance = Calendar.getInstance();		
		instance.set(Calendar.DAY_OF_MONTH, instance.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return instance.getTime();
	}
	
	public static Date getFirstDayOfCurrentMonth(){
		
		Calendar instance = Calendar.getInstance();		
		instance.set(Calendar.DAY_OF_MONTH, instance.getActualMinimum(Calendar.DAY_OF_MONTH));
		
		return instance.getTime();
	}
	
	public static int getYear(Date date){
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		return instance.get(Calendar.YEAR);		
	}
	
	public static int getMonth(Date date){
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		return instance.get(Calendar.MONTH);		
	}
	
	public static Date buildDate(Integer year,Integer month){
		
		Calendar calendar=Calendar.getInstance();
		
		calendar.set ( Calendar.DAY_OF_MONTH, 1 ) ;   
		
		calendar.set ( Calendar.MONTH, month!=null?month:0 ) ;   
		
		calendar.set ( Calendar.YEAR, year ) ; 
	
		return calendar.getTime();
	}
	
	public static Date buildDate(Integer year,Integer month, Integer day){
		
		Calendar calendar=Calendar.getInstance();
		
		calendar.set ( Calendar.DAY_OF_MONTH, day ) ;   
		
		calendar.set ( Calendar.MONTH, month!=null?month:0 ) ;   
		
		calendar.set ( Calendar.YEAR, year ) ; 
	
		return calendar.getTime();
	}
	public static Date buildLastDayInMonth(Integer year, Integer month){
		Date date = buildDate(year,month);
		
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		
		int actualMaximum = c.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		
		c.set ( Calendar.DAY_OF_MONTH, actualMaximum ) ;   
		
		return c.getTime();
	}
	
	public static Date buildFirstDayInMonth(Integer year, Integer month){
		Date date = buildDate(year,month);
		
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		
		
		c.set ( Calendar.DAY_OF_MONTH, 1 ) ;   
		
		return c.getTime();
	}
	
	
	public static Date getLastDayInYear(Calendar current){
		
		current.set(Calendar.DAY_OF_YEAR, current.getActualMaximum(Calendar.DAY_OF_YEAR));
		
		return current.getTime();
	}
	
	public static Date getFirstDayInYear(Calendar current){
		
		current.set(Calendar.DAY_OF_YEAR, current.getActualMinimum(Calendar.DAY_OF_YEAR));

		return current.getTime();
	}
	
	public static Timestamp getTimestamp(String date) throws Exception{

		Date converted = FormatUtil.parseDateAndTime(date);
		
		return new Timestamp(converted.getTime());
	}
}
