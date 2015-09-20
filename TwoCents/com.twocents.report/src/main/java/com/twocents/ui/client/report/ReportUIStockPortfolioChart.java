package com.twocents.ui.client.report;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;
import org.springframework.util.CollectionUtils;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Stretching;

import com.twocents.core.util.ImageUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.ChartType;
import com.twocents.model.Custody;
import com.twocents.model.ReportGroupType;
import com.twocents.model.ReportParamChart;
import com.twocents.model.ReportPropertyType;
import com.twocents.report.charts.BuildChart;
import com.twocents.report.constant.ReportDefault;
import com.twocents.report.jasper.JasperReport;
import com.twocents.report.jasper.dynamic.DJReportLayout;
import com.twocents.report.jasper.dynamic.ReportStyle;
import com.twocents.report.resources.ReportMessages;
import com.twocents.ui.client.report.header.HeaderReportFilterByChartTypeComposite;

public class ReportUIStockPortfolioChart extends Report {

	private final Logger logger = Logger.getLogger(ReportUIStockPortfolioChart.class);

	public ReportUIStockPortfolioChart(Composite parent) {
		super(parent,ReportMessages.getMessage("REPORT_FOR_STOCK_PORTFOLIO_CHART"),ReportGroupType.ACCOUNT
				,ReportMessages.getMessage("REPORT_FOR_STOCK_PORTFOLIO_CHART_DESCRIPTION")				
		);
	}

	
	public JasperPrint generateData(Account account, HashMap mapParam) throws CoreException {
		
		ReportParamChart param=null;
		ChartType chartType=null;
		try{
			param=(ReportParamChart)mapParam.get(REPORT_PARAM_KEY);
			
			chartType=param.getChartType();
			
		}catch(Exception e){
			throw new CoreException(7001,e);
		}		
		
		List<Custody> custodyList = getCustodyService().findCustodyByAccount(account);
		
		ArrayList<HashMap> array=new ArrayList<HashMap>();

		if(!CollectionUtils.isEmpty(custodyList)){
		
			Map<String,Number> data=new HashMap<String,Number>();
			for (Iterator iterator = custodyList.iterator(); iterator.hasNext();) {
				Custody custody = (Custody) iterator.next();
				data.put(custody.getStock().getCode(), (custody.getAmount()*custody.getAveragePrice()));	
			}
			
			HashMap map=new HashMap();
			
			InputStream is=null;
			try {
				BufferedImage bImage = BuildChart.generateChart(
						ReportDefault.DEFAULT_WITDH_FOR_CHART_X, 
						ReportDefault.DEFAULT_WITDH_FOR_CHART_Y,
						null,//UIMessages.getMessage("CARTEIRA"), 
						data,
						chartType);
				//ImageUtil.saveBufferedImage(bImage,ReportDefault.DEFAULT_FILENAME_CHART,ReportDefault.DEFAULT_IMAGE_TYPE);
		
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(bImage, "png", os);
				is = new ByteArrayInputStream(os.toByteArray());
			} catch (IOException e) {
				logger.error("Problema em gerar imagem para o relatório",e);
			}
			
			//map.put(ReportPropertyType.IMAGE.toString(),ResourceUtil.getInputStream(ReportDefault.DEFAULT_FILENAME_CHART));
			map.put(ReportPropertyType.IMAGE.toString(),is);
			
			array.add(map);
		}
		return JasperReport.generateDynamicReport(getReportForm(),array,getName());
	}
	
	public DynamicReport getReportForm() throws CoreException{

		//FastReportBuilder reportLayout = new FastReportBuilder();
		DJReportLayout reportLayout = new DJReportLayout(getName(),getDescription(),getAccountSelected().getUser().getName(),Page.Page_A4_Portrait());
		//DJReportLayout reportLayout = new DJReportLayout(getName(),getDescription(),Page.Page_A4_Landscape());
		
		ReportStyle reportStyle = reportLayout.getStyle();

		Style style = reportStyle.getHeaderStyleForChart();
		//Style style = new StyleBuilder(false).setHorizontalAlign(HorizontalAlign.CENTER).setStretching(Stretching.RELATIVE_TO_TALLEST_OBJECT).setBorderColor(Color.BLACK).setBorder(Border.THIN).build();
		
		
		try{
			reportLayout.addImageColumn(getName()
					,ReportPropertyType.IMAGE.toString()
					,ReportDefault.DEFAULT_WITDH_FOR_CHART_X					
					,true
					,ImageScaleMode.FILL 
					,style)
			.setPrintColumnNames(false)
			//.setUseFullPageWidth(false)
			
			.setDetailHeight(ReportDefault.DEFAULT_WITDH_FOR_CHART_Y)
			
			;
			
		
		} catch (ColumnBuilderException e1) {
			throw new CoreException(e1.getMessage(),e1);
		} catch (ClassNotFoundException e1) {
			throw new CoreException(e1.getMessage(),e1);		
		}
		
		return reportLayout.build();
	}

	@Override
	public void buildHeader(Composite showReportFields) {
		setHeader(new HeaderReportFilterByChartTypeComposite(getHeaderComposite()));
		getHeader().setReport(this);
	}
	
}
