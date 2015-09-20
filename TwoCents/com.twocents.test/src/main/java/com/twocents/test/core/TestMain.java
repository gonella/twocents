package com.twocents.test.core;

import java.text.DecimalFormat;
import java.text.ParseException;

import com.twocents.core.util.FormatUtil;

public class TestMain {

	public static void main(String arg[]) throws ParseException {

		Double value=115.7455201487011418;
		
		value = FormatUtil.formatDouble(value);
		System.out.println(value);
	    
		String real = FormatUtil.toReal(value);
		System.out.println(real);
		
		DecimalFormat percent = new DecimalFormat("0,00%",FormatUtil.REAL );	

		String st = percent.format(value);
		
		System.out.println(st);

	}
}
