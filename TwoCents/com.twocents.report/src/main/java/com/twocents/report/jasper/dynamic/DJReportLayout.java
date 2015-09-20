package com.twocents.report.jasper.dynamic;

import java.util.Date;

import org.apache.log4j.Logger;

import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

import com.twocents.context.ContextHolder;
import com.twocents.core.util.FormatUtil;
import com.twocents.environment.Environment;
import com.twocents.report.resources.ReportImages;

public class DJReportLayout extends FastReportBuilder{


	private final Logger logger = Logger.getLogger(DJReportLayout.class);

	
	private final ReportStyle style=new ReportStyle();

	private String title;
	private String subTitle;
	private String accountName=null;
	
	public DJReportLayout(String title,String subTitle,String accountName,Page page){
		this.title = title;
		this.subTitle = subTitle;
		this.accountName = accountName;
		setPageSizeAndOrientation(page);
		config();
	}
	public DJReportLayout(String title,String subTitle,String accountName){
		this.title = title;
		this.subTitle = subTitle;
		this.accountName = accountName;
		setPageSizeAndOrientation(Page.Page_A4_Landscape());
		config();
	}
	
	public DJReportLayout(String title,String subTitle){
		this.title = title;
		this.subTitle = subTitle;
		setPageSizeAndOrientation(Page.Page_A4_Landscape());
		config();
	}

	private void config(){
		setReportLocale(FormatUtil.BRAZIL);
		setOddRowBackgroundStyle(getStyle().getOddRowStyle());
		
		addFirstPageImageBanner(getClass().getResource(ReportImages.COM_TWOCENTS_RESOURCES_IMAGES_TWO_CENTS_LOGO_PNG).toString(),new Integer(197), new Integer(60),ImageBanner.ALIGN_LEFT);
		//Colocar o logo da companhia que comprou
		//addFirstPageImageBanner(System.getProperty("user.dir")+ EnvironmentCore.COMPANY_LOGO,new Integer(197), new Integer(60),ImageBanner.ALIGN_RIGHT);

		if(accountName!=null){
			addAutoText("Conta : "+accountName, AutoText.POSITION_HEADER, AutoText.ALIGMENT_RIGHT,200);
		}
		addAutoText("_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________", AutoText.POSITION_HEADER, AutoText.ALIGMENT_CENTER,850);
		addAutoText("", AutoText.POSITION_HEADER, AutoText.ALIGMENT_LEFT,200);
		
		
		
		setTitleStyle(getStyle().getTitleStyle());
		setTitle(getTitle());
		setSubtitle(getSubTitle());

		
		Integer margin = new Integer(20);
		Integer detailHeight=new Integer(15);
		
		setDetailHeight(detailHeight);
		setLeftMargin(margin);
		setRightMargin(margin);
		setTopMargin(margin);
		setBottomMargin(margin);
		setPrintBackgroundOnOddRows(true);
		setUseFullPageWidth(true);
		
		addAutoText(AutoText.AUTOTEXT_PAGE_X_SLASH_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_RIGHT,30,30);
		//addAutoText(AutoText.AUTOTEXT_CREATED_ON, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT,AutoText.PATTERN_DATE_DATE_TIME);
		//addAutoText(FormatUtil.formatDateAndTime(new Date()), AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT,30,30);		
		addAutoText("[ "+FormatUtil.formatDateAndTime(new Date())+" ]", AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT,200);

		addAutoText(Environment.TW_PRODUCT+" "+ContextHolder.getTwRunningVersion(), AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER,200);
		
		
	}
	
	public AbstractColumn defineColumn(String propery,String title,int witdh,String type,Style headerStyle,Style style) throws ColumnBuilderException{
		AbstractColumn column = ColumnBuilder.getInstance().setColumnProperty(propery, type)
		.setTitle(title).setWidth(new Integer(witdh))
		.setStyle(style).setHeaderStyle(headerStyle).build();
		
		
		return column;
	}
	
	public AbstractColumn createColumn(String propery,String title,int witdh,String type,Style headerStyle,Style style){
		AbstractColumn defineColumn=null;
		try {
			defineColumn = defineColumn(propery, title, witdh, type, headerStyle, style);
			//addColumn(defineColumn);		
		} catch (ColumnBuilderException e) {
			logger.error("Não foi possível adicionar coluna no relatorio",e);
		}
		return defineColumn;
	}

	public ReportStyle getStyle() {
		return style;
	}

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}
	
	
}
