package com.twocents.report.tax;

import java.io.FileOutputStream;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import com.twocents.model.Darf;


public class BuildReport{

	Logger logger = Logger.getLogger(BuildReport.class);
	
	public static void main(String[] args) {
		
		Darf darf=new Darf();
		
		darf.setNome("ADRIANO GONELLA");
		darf.setTelefone("51-81335747");
		darf.setCodigoDaReceita("6015");
		darf.setPeriodoDeApuracao("03/03/2008");
		darf.setCpfOuCpnj("55555555555");
		darf.setNumeroDeReferencia("");
		darf.setDataDeVencimento("30/04/2008");
		darf.setValorDoPrincipal("50000");
		darf.setValorDaMulta("100");
		darf.setJuros("100");
		darf.setValorTotal("100000");		
		
		
		new BuildReport().buildDARF("build/table_1.pdf",darf);
	}

	public void buildDARF(String fileName, Darf darf){
 
		logger.info("Starting the DARF creation");
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName)); document.open();
          
            document.add(new BuildDarf().buildingADarf(darf));
                    
        }
        catch(Exception ex){
        	logger.error(ex);
        }
        document.close();
        
        logger.info("The DARF was created successful");
    }
	
	public void buildReport(String fileName){
		 
	    logger.info("Creating a pdf file with table");
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName)); document.open();
          
            document.add(new BuildDarf().buildingADarf());
                    
        }
        catch(Exception ex){
        	logger.error(ex);
        }
        document.close();
    }
	
}
