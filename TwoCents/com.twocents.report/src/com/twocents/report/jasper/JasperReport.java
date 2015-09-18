package com.twocents.report.jasper;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;

import com.twocents.core.util.ResourceUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.OutputType;
import com.twocents.report.constant.ReportDefault;

public class JasperReport {
	public static final Logger logger = Logger.getLogger(JasperReport.class);
	
	public static JasperPrint generateReportError(String message) throws CoreException {	
		try {
			logger.info("###Relatorio de erro - Motivo: "+message);
			HashMap<String,Object> param = new HashMap<String,Object>();
			
			ArrayList<HashMap> array=new ArrayList<HashMap>();
			HashMap<String,String> map=new HashMap<String,String>();
			map.put("date", new Date().toString());
			map.put("message", message);
			array.add(map);
			
			JRDataSource jrDataSourse = new JRBeanArrayDataSource(array.toArray());
			JasperPrint jasperPrint = JasperFillManager.fillReport(ResourceUtil.getResourceAsStream(ReportDefault.DEFAULT_ERROR_REPORT),param, jrDataSourse);
			
			return jasperPrint;
		} catch (Exception e) {
			throw new CoreException("Falhou em construir relatorio de erro",e);
		}
	}	
	
	public static JasperPrint generateReportBlank(String message) throws CoreException {	
		try {
			logger.info("###Gerando relatório sem informações");
			HashMap<String,Object> param = new HashMap<String,Object>();
			
			ArrayList<HashMap> array=new ArrayList<HashMap>();
			HashMap<String,String> map=new HashMap<String,String>();
			map.put("date", new Date().toString());
			map.put("message", message);
			array.add(map);
			
			JRDataSource jrDataSourse = new JRBeanArrayDataSource(array.toArray());
			JasperPrint jasperPrint = JasperFillManager.fillReport(ResourceUtil.getResourceAsStream(ReportDefault.DEFAULT_ERROR_REPORT),param, jrDataSourse);
			
			//doOutputWithJasperPrint(type,jasperPrint,outputName);
			return jasperPrint;
			
		} catch (Exception e) {
			return generateReportError(e);
			//throw new CoreException("Failed: Error Report Generation: ",e);
		}
	}
	
	public static void generateJasperReport(OutputType type, ArrayList array,String outputName,String reportName,String reportOut) throws CoreException {	
		
		if(CollectionUtils.isEmpty(array)){
			generateReportBlankResult();
			return;
		}
		
		try {
			logger.info("Generating the JASPER Report for "+reportName);
			HashMap<String,Object> param = new HashMap<String,Object>();
			JRDataSource jrDataSourse = new JRBeanArrayDataSource(array.toArray());
			JasperPrint jasperPrint = JasperFillManager.fillReport(ResourceUtil.getResourceAsStream(reportOut),param, jrDataSourse);
			
			doOutputWithJasperPrint(type,jasperPrint,outputName);
			
		} catch (Exception e) {
			generateReportError(e);
		}
	}
	
	private static void doOutputWithJasperPrint(OutputType type, JasperPrint jasperPrint,
			String outputName) throws CoreException {
		
		if(OutputType.HTML.equals(type)){
			toHtml(jasperPrint, outputName);
		}
		else if(OutputType.PDF.equals(type)){
			toPdf(jasperPrint, outputName);
		}
	}


	static private void toPdf(JasperPrint jasperPrint,String fileName) throws CoreException{
		
		try {
			logger.info("Creating pdf file: "+fileName);
			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			FileOutputStream out = new FileOutputStream(fileName);
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
			logger.info("** Report as a pdf file created");

		} catch (Exception e) {
			throw new CoreException("Failed during file creation", e);
		}
	
		
	}
	static private void toHtml(JasperPrint jasperPrint,String url) throws CoreException{
		
		try{
			logger.info("Creating html file :"+url);
			JasperExportManager.exportReportToHtmlFile(jasperPrint, url);
			logger.info("** Report as html created");
		}catch(Exception e){
			throw new CoreException("Failed during file creation",e);
		}

	}
	
	public static JasperPrint generateDynamicReport(DynamicReport dr,Collection<HashMap> array,String reportName) throws CoreException {	
		
		if(CollectionUtils.isEmpty(array)){
			return generateReportBlankResult();
		}
		
		try {
			logger.debug("Gerando Dynamic Jasper para o relatório : "+reportName);
			
			JRDataSource ds = new JRBeanCollectionDataSource(array);   
			JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
			
			return jp;
		} catch (Exception e) {
			return generateReportError(e);
		}
	}
	
	public static JasperPrint generateReportBlankResult() throws CoreException{
		//logger.info("Sem informações para gerar relatório");
		return generateReportBlank("Sem informações para gerar relatorio");
	}
	public static JasperPrint generateReportError(Exception e) throws CoreException{
		return generateReportError(e.getMessage());
	}
}
