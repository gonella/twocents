package com.twocents.report.jasper.dynamic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.log4j.Logger;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;

import com.twocents.core.util.ResourceUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.ChartType;
import com.twocents.model.ReportPropertyType;
import com.twocents.report.charts.BuildChart;

public class ReportDynamicChart extends DynamicJasperReportLayout {

	private final Logger logger = Logger.getLogger(ReportDynamicChart.class);

	private final String title;
	private final String subTitle;

	private final Integer witdh;

	private final Integer height;

	private final String titleColumn;
	
	public ReportDynamicChart(String title,String subTitle,String titleColumn,Integer witdh,Integer height){
		this.title = title;
		this.subTitle = subTitle;
		this.titleColumn = titleColumn;
		this.witdh = witdh;
		this.height = height;
	}
	
	public DynamicReport buildReport() throws CoreException {
		
		FastReportBuilder drb = createDefaultHeader(getTitle(),getSubTitle());

		try {
			
			//drb
			//.setTitleStyle(getStyle().getHeaderStyleForChart())
			//;
			drb
			.addImageColumn(getTitleColumn(), 
					ReportPropertyType.IMAGE.toString(), 
					getWitdh(), false,
					ImageScaleMode.FILL_PROPORTIONALLY ,
					getStyle().getHeaderStyleForChart())
			.setDetailHeight(getWitdh())
			.setUseFullPageWidth(true)

			;			
		} catch (ColumnBuilderException e1) {
			throw new CoreException(e1.getMessage());
		} catch (ClassNotFoundException e1) {
			throw new CoreException(e1.getMessage());		
		}
		
		DynamicReport dr = drb.build();
		
		return dr;
	}
	
	public static void main(String[] args) throws Exception {
		
		String title="November 2006 sales report";
		String subTitle="The items in this report correspond to the main products: DVDs, Books, Foods and Magazines";
		
		ReportDynamicChart test = new ReportDynamicChart(
				title,subTitle,
				null,
				null,//EnvironmentUI.DEFAULT_WITDH_FOR_CHART_X,
				null//EnvironmentUI.DEFAULT_WITDH_FOR_CHART_Y
				);
		
		DynamicReport buildReport = test.buildReport();
		
		
		try {
			Map<String,Number> data=new HashMap<String,Number>();
			data.put("ATIVO1", 1000);
			data.put("ATIVO2", 2000);
			data.put("ATIVO3", 3000);
			
			BufferedImage bImage = BuildChart.generateChart(
					null,//EnvironmentUI.DEFAULT_WITDH_FOR_CHART_X, 
					null,//EnvironmentUI.DEFAULT_WITDH_FOR_CHART_Y,
					"Gráfico", 
					data,
					ChartType.PIE);
			
			/*ImageIO.write(bImage, 
					EnvironmentUI.DEFAULT_IMAGE_TYPE, new File(
							EnvironmentUI.DEFAULT_FILENAME_CHART
							));
			*/
			
		} catch (Exception e) {
			throw new CoreException("Failed in Generated a Chart",e);
		}
		
		//FILENAME_IMAGE
		List<Map> list=new ArrayList<Map>();

		Map map=new HashMap();
		map.put(ReportPropertyType.IMAGE.toString(),ResourceUtil.getInputStream(
				null//EnvironmentUI.DEFAULT_FILENAME_CHART
				)
				);
		
		list.add(map);
		
		JRDataSource ds = new JRBeanCollectionDataSource(list);   
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(buildReport, new ClassicLayoutManager(), ds);
		JasperViewer.viewReport(jp);    //finally display the report report
		
	}

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public Integer getWitdh() {
		return witdh;
	}

	public String getTitleColumn() {
		return titleColumn;
	}
	
}
