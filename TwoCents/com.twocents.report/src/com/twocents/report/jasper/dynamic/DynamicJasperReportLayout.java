package com.twocents.report.jasper.dynamic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ar.com.fdvs.dj.core.CoreException;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

import com.twocents.model.ReportPropertyType;
import com.twocents.report.resources.ReportImages;

public abstract class DynamicJasperReportLayout {
	

	private final Logger logger = Logger.getLogger(DynamicJasperReportLayout.class);
	
	public static final String COLUMN_GROUP = "COLUMN_GROUP";
	public static final String COLUMN_GROUP_LAYOUT = "COLUMN_GROUP_LAYOUT";
	public static final String COLUMN_PROPERTY_TO_SUM = "COLUMN_PROPERTY_TO_SUM";

	
	public static final String COLUMN_PROPERTY="COLUMN_PROPERTY";
	public static final String COLUMN_TITLE="COLUMN_TITLE";
	public static final String COLUMN_WITDH="COLUMN_WITDH";
	public static final String COLUMN_TYPE="COLUMN_TYPE";
	public static final String COLUMN_STYLE="COLUMN_STYLE";

	private static ReportStyle style=new ReportStyle();
	
	private AbstractColumn columnVectors[];

	private List<Map<String, Object>> columnData=null;
	
	public DynamicJasperReportLayout(){
	}
	
	public DynamicJasperReportLayout(List<Map<String,Object>> columnData){
		this.columnData = columnData;		
		
		this.columnVectors=columnStructure(columnData,style.getHeaderStyle());
	}
	
	public abstract DynamicReport buildReport() throws Exception;
	//public abstract List<Map<String,Object>> dataStructure();
	
	public DynamicReportBuilder createHeader(String title,String subTitle){
		DynamicReportBuilder drb = createDefaultHeader(title, subTitle);
		
		drb.setOddRowBackgroundStyle(getStyle().getOddRowStyle());
		
		return drb;
	}
	
	public FastReportBuilder createDefaultHeader(String title,String subTitle){
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setPageSizeAndOrientation(Page.Page_A4_Landscape());

		//drb.addAutoText("Observações - Observações - Observações - Observações", AutoText.POSITION_HEADER, AutoText.ALIGMENT_RIGHT,200);
		
		
		drb.addAutoText("_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________", AutoText.POSITION_HEADER, AutoText.ALIGMENT_CENTER,800);
		
		//drb.addAutoText("More Information", AutoText.POSITION_HEADER, AutoText.ALIGMENT_CENTER,200);
		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_SLASH_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_RIGHT,30,30);
		drb.addAutoText(AutoText.AUTOTEXT_CREATED_ON, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT,AutoText.PATTERN_DATE_DATE_TIME);
		
		drb
			.setTitleStyle(getStyle().getTitleStyle())
			.setTitle(title)
			.setSubtitle(subTitle)
				
			.addFirstPageImageBanner(
					getClass().getResource(ReportImages.COM_TWOCENTS_RESOURCES_IMAGES_TWO_CENTS_LOGO_PNG).toString(),
					//System.getProperty("user.dir")+ EnvironmentCore.TWOCENTS_LOGO,
					new Integer(197), new Integer(60),
					ImageBanner.ALIGN_LEFT)

			//Colocar o logo da companhia que comprou
			//.addFirstPageImageBanner(System.getProperty("user.dir")+ EnvironmentCore.COMPANY_LOGO,new Integer(197), new Integer(60),ImageBanner.ALIGN_RIGHT)
			
					
		;
		
		Integer margin = new Integer(20);
		Integer detailHeight=new Integer(15);
	
		
		drb
		.setDetailHeight(detailHeight)
		.setLeftMargin(margin)
		.setRightMargin(margin)
		.setTopMargin(margin)
		.setBottomMargin(margin)
		.setPrintBackgroundOnOddRows(true)
		;
				
		return drb;
	}
	
	public static Map<String,Object> addRegistry(String property,String title,Integer witdh,String type,Style style){
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put(COLUMN_PROPERTY, property);
		map.put(COLUMN_TITLE, title);
		map.put(COLUMN_WITDH,witdh);
		map.put(COLUMN_TYPE, type);
		map.put(COLUMN_STYLE,style);
		//map.put(COLUMN_GROUP,new Boolean(false));
		
		return map;
	}
	
	public static Map<String,Object> addRegistry(String property,String title,Integer witdh,String type,Style style,Boolean isGroupFilter,GroupLayout layout){
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put(COLUMN_PROPERTY, property);
		map.put(COLUMN_TITLE, title);
		map.put(COLUMN_WITDH,witdh);
		map.put(COLUMN_TYPE, type);
		map.put(COLUMN_STYLE,style);
		map.put(COLUMN_GROUP,isGroupFilter);
		map.put(COLUMN_GROUP_LAYOUT,layout);

		return map;
	}
	public static Map<String,Object> addRegistry(String property,String title,Integer witdh,String type,Style style,Boolean isGroupFilter,GroupLayout layout,ReportPropertyType propertyToSum){
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put(COLUMN_PROPERTY, property);
		map.put(COLUMN_TITLE, title);
		map.put(COLUMN_WITDH,witdh);
		map.put(COLUMN_TYPE, type);
		map.put(COLUMN_STYLE,style);
		map.put(COLUMN_GROUP,isGroupFilter);
		map.put(COLUMN_GROUP_LAYOUT,layout);
		map.put(COLUMN_PROPERTY_TO_SUM,propertyToSum);

		return map;
	}
	
	public static AbstractColumn defineColumn(String propery,String title,int witdh,String type,Style style,Style headerStyle) throws ColumnBuilderException{
		AbstractColumn column = ColumnBuilder.getInstance().setColumnProperty(propery, type)
		.setTitle(title).setWidth(new Integer(witdh))
		.setStyle(style).setHeaderStyle(headerStyle).build();
		return column;
	}
		
	public AbstractColumn[] columnStructure(List<Map<String, Object>> listDataStructure,Style headerStyle) throws CoreException{
		columnVectors = null;
		try{
			
			if(listDataStructure!=null && listDataStructure.size()>0){
				
				columnVectors=new AbstractColumn[listDataStructure.size()];
				int i=0;
				for (Iterator iterator = listDataStructure.iterator(); iterator.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) iterator.next();
					
					String property=(String)map.get(COLUMN_PROPERTY);
					String strtitle=(String)map.get(COLUMN_TITLE);
					Style style=(Style)map.get(COLUMN_STYLE);
					Integer witdh=(Integer)map.get(COLUMN_WITDH);
					String type=(String)map.get(COLUMN_TYPE);
	
					columnVectors[i] = defineColumn(property,strtitle,witdh,type,style,headerStyle);
					
					i++;
				}
			}
			
		}catch(ColumnBuilderException e){
			throw new CoreException(e);
		}
		
		return columnVectors;
	}
	public static ReportStyle getStyle() {
		return style;
	}

	public AbstractColumn[] getColumnVectors() {
		return columnVectors;
	}

	public List<Map<String, Object>> getColumnData() {
		return columnData;
	}
	
}
