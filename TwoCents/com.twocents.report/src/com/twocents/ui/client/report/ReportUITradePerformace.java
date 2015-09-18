package com.twocents.ui.client.report;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.util.CollectionUtils;

import ar.com.fdvs.dj.domain.DynamicReport;

import com.twocents.core.util.DateUtil;
import com.twocents.core.util.FormatUtil;
import com.twocents.core.util.ImageUtil;
import com.twocents.core.util.ResourceUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.ReportGroupType;
import com.twocents.model.ReportParam;
import com.twocents.model.ReportParamInterval;
import com.twocents.model.ReportPropertyType;
import com.twocents.report.charts.BuildChart;
import com.twocents.report.constant.ReportDefault;
import com.twocents.report.jasper.JasperReport;
import com.twocents.report.jasper.dynamic.ReportDynamicChart;
import com.twocents.report.resources.ReportMessages;
import com.twocents.ui.client.report.header.HeaderReportCompositeEmpty;

public class ReportUITradePerformace extends Report {

	private final Logger logger = Logger.getLogger(ReportUITradePerformace.class);

	
	public ReportUITradePerformace(Composite parent) {
		super(parent,ReportMessages.getMessage("REPORT_FOR_TRADE_PERFORMACE_CHART"),ReportGroupType.OPERATION
				,ReportMessages.getMessage("REPORT_FOR_TRADE_PERFORMACE_CHART_DESCRIPTION")
		);
	}
	
	public JasperPrint generateData(Account account, HashMap mapParam) throws CoreException {
		
		ReportParamInterval param=null;
		try{
			param=(ReportParamInterval)buildParameters().get(REPORT_PARAM_KEY);
		}catch(Exception e){
			throw new CoreException(7001);
		}		
		ArrayList<HashMap> array=new ArrayList<HashMap>();

		List<Map<String, Object>> list = getOperationService()
		.findStockOperationByAccountAndInterval(account,
				param.getDateStart(), param.getDateEnd());
		/*
		if(!CollectionUtils.isEmpty(list)){
			
			Map<String,List> data=new HashMap<String,List>();
			
			TimeSeries s1 = new TimeSeries("PETR4");
			Integer str2008=2008;
			Integer str2009=2009;
			
			s1.add(new Month(2, str2008), 181.8);
			s1.add(new Month(3, str2008), 167.3);
			s1.add(new Month(4, str2008), 153.8);
			s1.add(new Month(5, str2008), 167.6);
			s1.add(new Month(6, str2008), 158.8);
			s1.add(new Month(7, str2008), 148.3);
			s1.add(new Month(8, str2008), 153.9);
			s1.add(new Month(9, str2008), 142.7);
			s1.add(new Month(10, str2008), 123.2);
			s1.add(new Month(11, str2008), 131.8);
			s1.add(new Month(12, str2008), 139.6);
			s1.add(new Month(1, str2009), 142.9);
			s1.add(new Month(2, str2009), 138.7);
			s1.add(new Month(3, str2009), 137.3);
			s1.add(new Month(4, str2009), 143.9);
			s1.add(new Month(5, str2009), 139.8);
			s1.add(new Month(6, str2009), 137.0);
			s1.add(new Month(7, str2009), 132.8);
	
			TimeSeries s2 = new TimeSeries("VALE5");
			s2.add(new Month(2, str2008), 129.6);
			s2.add(new Month(3, str2008), 123.2);
			s2.add(new Month(4, str2008), 117.2);
			s2.add(new Month(5, str2008), 124.1);
			s2.add(new Month(6, str2008), 122.6);
			s2.add(new Month(7, str2008), 119.2);
			s2.add(new Month(8, str2008), 116.5);
			s2.add(new Month(9, str2008), 112.7);
			s2.add(new Month(10, str2008), 101.5);
			s2.add(new Month(11, str2008), 106.1);
			s2.add(new Month(12, str2008), 110.3);
			s2.add(new Month(1, str2009), 111.7);
			s2.add(new Month(2, str2009), 111.0);
			s2.add(new Month(3, str2009), 109.6);
			s2.add(new Month(4, str2009), 113.2);
			s2.add(new Month(5, str2009), 111.6);
			s2.add(new Month(6, str2009), 108.8);
			s2.add(new Month(7, str2009), 101.6);
	
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			dataset.addSeries(s1);
			dataset.addSeries(s2);
	
			BufferedImage bImage = BuildChart.generateChartTimeSeries(
					ReportDefault.DEFAULT_SIZE_CHART_TIME_SERIES_X, 
					ReportDefault.DEFAULT_SIZE_CHART_TIME_SERIES_Y,
					null,
					"Rentabilidade",
					"Data",
					dataset);
			
			ImageUtil.saveBufferedImage(bImage, 
					ReportDefault.DEFAULT_FILENAME_CHART, 
					ReportDefault.DEFAULT_IMAGE_TYPE);
			
			HashMap map=new HashMap();
			map.put(ReportPropertyType.IMAGE.toString(),
					ResourceUtil.getInputStream(ReportDefault.DEFAULT_FILENAME_CHART));
			array.add(map);
		}
		*/	
		
		return JasperReport.generateDynamicReport(getReportForm(),array, getName());
	}
	
	public DynamicReport getReportForm() throws CoreException{
		
		ReportDynamicChart reportForm = 
			new ReportDynamicChart(
					getName(),
					getDescription(),
					null,
					ReportDefault.DEFAULT_WITDH_FOR_CHART_X,ReportDefault.DEFAULT_WITDH_FOR_CHART_Y);	
		
		return reportForm.buildReport();
	}

	@Override
	public void buildHeader(Composite showReportFields) {
		showReportFields.dispose();
		setHeader(null);
	}
	
	public HashMap buildParameters() {
		ReportParamInterval param = getReportParam().get(REPORT_PARAM_KEY)!=null?(ReportParamInterval)getReportParam().get(REPORT_PARAM_KEY):null;
		
		if(param==null){
			//Setting the DEFAULT VALUE
			param=new ReportParamInterval(getName(),getGroupType());
			param.setDateStart(FormatUtil.getMinTimestamp(DateUtil.buildDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.JANUARY)));
			param.setDateEnd(FormatUtil.getMaxTimestamp(new Date()));
			
			HashMap map=new HashMap<String,ReportParam>();
			map.put(REPORT_PARAM_KEY, param);
			setReportParam(map);
		}
		
		return getReportParam();
	}
}
