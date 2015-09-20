package com.twocents.report.jasper.dynamic;

import java.awt.Color;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

public class ReportStyle {

	private final Color HEADER1_BLUE_AWT=new Color(100,149,237);
	private final Color HEADER2_BLUE_AWT=new Color(135,206,235);
	
	private final Color ODDROW1_BLUE_AWT=new Color(156,208,221);
	private final Color ODDROW2_BLUE_AWT=new Color(214,247,255);
	
	private final Color TITLE_BLUE_AWT=new Color(30,144,255);
	
	private Style detailStyle;
	private Style headerStyle;
	private Style titleStyle;
	private Style importeStyle;
	private Style oddRowStyle;
	private Style headerStyleForChart;
	private Style groupOne;
	private Style groupTwo;
	private Style defaultContent;
	private Style headerSum;
	private Style textColumnContentAtivo;

	public ReportStyle(){
		
		groupOne = new Style();
		groupOne.setFont(new Font(14, Font._FONT_VERDANA, true,false,true));
		groupOne.setBorderBottom(Border.PEN_2_POINT);
		groupOne.setBorderRight(Border.PEN_2_POINT);
		groupOne.setBorderColor(Color.WHITE);
		groupOne.setHorizontalAlign(HorizontalAlign.CENTER);
		groupOne.setVerticalAlign(VerticalAlign.MIDDLE);
		groupOne.setBackgroundColor(HEADER1_BLUE_AWT);
		groupOne.setTextColor(Color.WHITE);
		groupOne.setTransparency(Transparency.OPAQUE);
	
		groupTwo = new Style();
		groupTwo.setFont(new Font(12, Font._FONT_VERDANA, true,false,false));
	
		defaultContent=new Style();
		defaultContent.setHorizontalAlign(HorizontalAlign.RIGHT);

		headerSum = new Style();
		headerSum.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerSum.setHorizontalAlign(HorizontalAlign.RIGHT);
		headerSum.setVerticalAlign(VerticalAlign.TOP);
		
		detailStyle = new Style();
		detailStyle.setHorizontalAlign(HorizontalAlign.RIGHT);

		setTextColumnContentAtivo(new Style());
		getTextColumnContentAtivo().setHorizontalAlign(HorizontalAlign.CENTER);

		
		//detailStyle.setBorderBottom(Border.PEN_2_POINT);
		
		headerStyle = new Style();
		headerStyle.setFont(Font.VERDANA_MEDIUM_BOLD);
		headerStyle.setBorderBottom(Border.PEN_2_POINT);
		headerStyle.setBorderRight(Border.PEN_2_POINT);
		headerStyle.setBorderColor(Color.WHITE);
		
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		
		
		headerStyle.setBackgroundColor(HEADER1_BLUE_AWT);
		headerStyle.setTextColor(Color.WHITE);
		headerStyle.setTransparency(Transparency.OPAQUE);

		titleStyle = new Style();
		titleStyle.setFont(new Font(18, Font._FONT_VERDANA, true,false,false));
		titleStyle.setTextColor(HEADER1_BLUE_AWT);
		//titleStyle.setBorderBottom(Border.PEN_2_POINT);

		importeStyle = new Style();
		importeStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		//importeStyle.setBorderBottom(Border.PEN_2_POINT);
		
		oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER);
		oddRowStyle.setBackgroundColor(ODDROW2_BLUE_AWT);
		oddRowStyle.setTransparency(Transparency.OPAQUE);
		//oddRowStyle.setBorderBottom(Border.PEN_2_POINT);
		
		
		setHeaderStyleForChart(new Style());
		getHeaderStyleForChart().setFont(Font.VERDANA_MEDIUM_BOLD);
		//getHeaderStyleForChart().setBorderBottom(Border.PEN_2_POINT);
		//getHeaderStyleForChart().setBorderRight(Border.PEN_2_POINT);
		getHeaderStyleForChart().setBorderColor(Color.WHITE);
		
		getHeaderStyleForChart().setHorizontalAlign(HorizontalAlign.CENTER);
		getHeaderStyleForChart().setVerticalAlign(VerticalAlign.MIDDLE);
		//getHeaderStyleForChart().setBackgroundColor(Color.DARK_GRAY);
		getHeaderStyleForChart().setTextColor(Color.BLACK);
		//getHeaderStyleForChart().setTransparency(Transparency.OPAQUE);
	}

	public Style getDetailStyle() {
		return detailStyle;
	}

	public void setDetailStyle(Style detailStyle) {
		this.detailStyle = detailStyle;
	}

	public Style getHeaderStyle() {
		return headerStyle;
	}

	public void setHeaderStyle(Style headerStyle) {
		this.headerStyle = headerStyle;
	}

	public Style getTitleStyle() {
		return titleStyle;
	}

	public void setTitleStyle(Style titleStyle) {
		this.titleStyle = titleStyle;
	}

	public Style getImporteStyle() {
		return importeStyle;
	}

	public void setImporteStyle(Style importeStyle) {
		this.importeStyle = importeStyle;
	}

	public Style getOddRowStyle() {
		return oddRowStyle;
	}

	public void setOddRowStyle(Style oddRowStyle) {
		this.oddRowStyle = oddRowStyle;
	}

	public void setHeaderStyleForChart(Style headerStyleForChart) {
		this.headerStyleForChart = headerStyleForChart;
	}

	public Style getHeaderStyleForChart() {
		return headerStyleForChart;
	}

	public Style getGroupOne() {
		return groupOne;
	}

	public void setGroupOne(Style groupOne) {
		this.groupOne = groupOne;
	}

	public Style getGroupTwo() {
		return groupTwo;
	}

	public void setGroupTwo(Style groupTwo) {
		this.groupTwo = groupTwo;
	}

	public void setDefaultContent(Style defaultContent) {
		this.defaultContent = defaultContent;
	}

	public Style getDefaultContent() {
		return defaultContent;
	}

	public Style getHeaderSum() {
		return headerSum;
	}

	public void setHeaderSum(Style headerSum) {
		this.headerSum = headerSum;
	}

	public void setTextColumnContentAtivo(Style textColumnContentAtivo) {
		this.textColumnContentAtivo = textColumnContentAtivo;
	}

	public Style getTextColumnContentAtivo() {
		return textColumnContentAtivo;
	}

}
