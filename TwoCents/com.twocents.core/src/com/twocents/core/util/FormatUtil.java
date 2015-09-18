package com.twocents.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.twocents.resources.CoreMessages;

public class FormatUtil {

	private static final Logger logger = Logger.getLogger(FormatUtil.class);

	public static final String EDIT_MASK = "###0.##";
	public static final String DISPLAY_MASK = "R$ ###0.##";

	public final static Locale	 BRAZIL = new Locale("pt","BR");  
	public final static DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRAZIL);  
	public final static DecimalFormat DINHEIRO_REAL = new DecimalFormat("¤ ###,###,##0.00",REAL);  
	private final static int DECIMAL_PLACES = 2;

	private final static DateFormat dateFormatScreen = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy");
	
	/*
	 * 
	 * private final static NumberFormat realFormatterFULL = NumberFormat.getCurrencyInstance(getLocaleBR());
	private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	private static NumberFormat percentFormatter = NumberFormat.getPercentInstance();
		private static DecimalFormat realFormatter = null;
	 */
	
	private static DateFormat formatDateAndTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static DateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
	private static DateFormat formatDayAndMonthAndYear = new SimpleDateFormat("dd/MM/yyyy");
	private static DateFormat formatYearAndMonth = new SimpleDateFormat("MM/yyyy");
	public static DateFormat year = new SimpleDateFormat("yyyy");
	public static DateFormat month = new SimpleDateFormat("MM");

	

	/*static {
		percentFormatter.setMinimumFractionDigits(2);
		percentFormatter.setMaximumFractionDigits(2);

		getRealformatter().setMinimumFractionDigits(2);
		getRealformatter().setMaximumFractionDigits(2);

		DecimalFormatSymbols dfs = getRealformatter().getDecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		getRealformatter().setDecimalFormatSymbols(dfs);

		currencyFormatter.setMinimumFractionDigits(2);
		currencyFormatter.setMaximumFractionDigits(2);
	}
*/

	public static String formatDateAndTime(Object value) {
		return formatDateAndTime.format(value);
	}

	public static String formatDateByYearAndMonth(Date value) {
		return formatYearAndMonth.format(value);
	}

	public static String parseAndFormatDate(String value) {
		return formatDayAndMonthAndYear.format(parseDate(value));
	}

	public static String formatDate(Object value) {
		return formatDayAndMonthAndYear.format(value);
	}

	public static String formatTime(Object value) {
		return formatTime.format(value);
	}

	public static Date parseDate(String value) {
		Date str = null;
		try {
			str = formatDayAndMonthAndYear.parse(value);
		} catch (ParseException e) {
			logger.debug(e.getMessage());
		}
		return str;
	}

	public static Date parseTime(String value) {
		Date str = null;
		try {
			str = formatTime.parse(value);
		} catch (ParseException e) {
			logger.debug(e.getMessage());
		}
		return str;
	}

	public static Date parseDateAndTime(String value) throws ParseException {
		Date str = formatDateAndTime.parse(value);
		
		return str;
	}

	public static Date parseDateByYearAndMonth(String value) {
		Date str = null;
		try {
			str = formatYearAndMonth.parse(value);
		} catch (ParseException e) {
			logger.debug(e.getMessage());
		}
		return str;
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

	public static String formatBoolean(boolean value) {

		if (value) {
			return CoreMessages.getMessage("LABEL_YES");
		} else {
			return CoreMessages.getMessage("LABEL_NO");
		}
	}

	public static String formatDayTrade(boolean isDaytrade) {

		if (isDaytrade) {
			return CoreMessages.getMessage("DAYTRADE");
		} else {
			return "";
		}
	}

	public static String formatDateByMonth(Date value) {
		return month.format(value);
	}

	public static Date parseDateByMonth(String value) {
		Date str = null;
		try {
			str = month.parse(value);
		} catch (ParseException e) {
			logger.info(e.getMessage());
		}
		return str;
	}

	/*
	 * public static String percentage(Double oldPrice,Double newPrice){
	 * 
	 * Double variation = (oldPrice/newPrice * 100) - 100;
	 * 
	 * Double value=oldPrice < newPrice ? variation*(-1): variation;
	 * 
	 * return FormatUtil.formatDecimal(value).toString();
	 * 
	 * } public static String formatPercentage(Double d1,Double d2){ return
	 * percentage(d1,d2)+"%"; }
	 */
	public static Double percentage(Double oldPrice, Double newPrice) {

		Double value = (newPrice - oldPrice) / newPrice;

		return value;

	}

	public static Double calculateRevenue(Double oldPrice, Double newPrice) {
		Double variation = (oldPrice / newPrice * 100) - 100;

		Double value = oldPrice < newPrice ? variation * (-1) : variation;

		return value;
	}

	public static String getDateDefault() {
		Date hoje = Calendar.getInstance(Locale.getDefault()).getTime();
		return dateFormatScreen.format(hoje);
	}

	public static String getDateBR() {
		Date hoje = Calendar.getInstance(BRAZIL).getTime();
		return dateFormatScreen.format(hoje);
	}

	/*public static DecimalFormat getRealformatter() {

		realFormatter = new DecimalFormat("#,##");
		realFormatter.setDecimalFormatSymbols(new DecimalFormatSymbols(getLocaleBR()));
		realFormatter.setRoundingMode(RoundingMode.HALF_UP);

		return realFormatter;
	}

	public static String toReal(Double value) {
		return getRealformatter().format(value);
	}

	public static String toDouble(String value) throws ParseException {
		return getRealformatter().parse(value).toString();
	}
*/
	/*public static NumberFormat getRealformatterfull() {
		return realFormatterFULL;
	}
	
	public static String formatPercent(Object value) {
		return percentFormatter.format(value);
	}

	public static String formatCurrency(Object value) {
		return currencyFormatter.format(value);
	}
	public static String toPercentage(Double value) {
		return percentFormatter.format(value);
	}
	public static String toRealFull(Double value) {
		return getRealformatterfull().format(value);
	}
	
	*/
	public static Double toDouble(String value) throws ParseException{
		 Number parse = DINHEIRO_REAL.parse(value);
		 
		 return parse.doubleValue();
	}
	public static Double formatDouble(Double value){
		
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP); // setScale is immutable
		value = bd.doubleValue();
		
		
		return value;
	}
	
	public static String toReal(Double value){
		
		value = formatDouble(value);
		String format = DINHEIRO_REAL.format(value);
		
		return format;
	}
	public static Double fromReal(String value) throws ParseException{
		
		DecimalFormat realFormatter = new DecimalFormat("#,##");
		realFormatter.setDecimalFormatSymbols(new DecimalFormatSymbols(BRAZIL));
		Number parse = realFormatter.parse(value);

		return parse.doubleValue();
	}
	
	public static String toPercentage(Double value){
		Double formatDouble = formatDouble(value);
		String result=formatDouble+"%";
		return result;
	}
	
	/*public static Number parseDecimal(String value) {
		try {
			return getRealformatter().parse(value);
		} catch (ParseException e) {
			return null;
		}
	}
	public static String formatDecimal(Object value) {
		return getRealformatter().format(value);
	}
*/

}
