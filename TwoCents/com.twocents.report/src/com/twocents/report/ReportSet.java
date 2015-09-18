package com.twocents.report;

import com.twocents.ui.client.report.ReportUIBrokerageNote;
import com.twocents.ui.client.report.ReportUIStockPortfolioChart;
import com.twocents.ui.client.report.ReportUITradeResult;

public class ReportSet {

	public static Class[] classReport=new Class[]{								
			ReportUIStockPortfolioChart.class,
			
			ReportUIBrokerageNote.class,
			ReportUITradeResult.class,
	};
	
	
	public static void main(String[] args){

	    /*ClassLoader classLoader = ReportSet.class.getClassLoader();

	    try {
	    	for (int i = 0; i < classReport.length; i++) {
				Class string = classReport[i];
				
		        System.out.println("aClass.getName() = " + string.getName());
		        
		        //MyObject object = (MyObject)myClassReloadingFactory.newInstance("com.jenkov.MyObject");
		        //http://tutorials.jenkov.com/java-reflection/dynamic-class-loading-reloading.html
			}
	        
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }*/

	}
}
