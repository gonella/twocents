package com.twocents.report.tax;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.twocents.model.Darf;
import com.twocents.resources.CoreMessages;


/**
 * For more information about IText, it takes a look in http://itextdocs.lowagie.com/tutorial/ 
 * @author Adriano Gonella
 *
 */
public class BuildDarf{

	private Logger logger = Logger.getLogger(BuildDarf.class);
	private String BRAZILLOGO_FILENAME = "core/src/com/twocents/resources/images/brasil.gif";
	
	private Darf darf;
	
	public Darf getDarf() {
		return darf;
	}

	public void setDarf(Darf darf) {
		this.darf = darf;
	}

	/**
	 * it will build an empty darf
	 * 
	 * @return Darf Table
	 * @throws BadElementException
	 */
	public PdfPTable buildingADarf() throws BadElementException{
		
		PdfPTable table = new PdfPTable(2);
		table.addCell(getLeftContent());        
		table.addCell(getRightContent());		
        return table;
		
	}
	
	/**
	 * It will build an darf with full information
	 * 
	 * @param darf
	 * @return  Darf Table
	 * @throws BadElementException
	 */
	public PdfPTable buildingADarf(Darf darf) throws BadElementException{
		
		setDarf(darf);
		
		PdfPTable table = new PdfPTable(2);
		table.addCell(getLeftContent());        
		table.addCell(getRightContent());		
        return table;
		
	}
	
	
	private PdfPTable getLeftContent() throws BadElementException{		
		PdfPTable table = new PdfPTable(1);		
		table.addCell(getLogo());
		
		PdfPTable userDetailTable = new PdfPTable(1);
		Phrase p2_0=getPhrase("DESCRIPTION_RIGHT_02",fontForHeading_4());
				
		Phrase p2_1=new Phrase((getDarf()!=null && getDarf().getNome()!=null)?getDarf().getNome():"",fontForHeading_4_BOLD());
		Phrase p2_2=new Phrase((getDarf()!=null && getDarf().getTelefone()!=null)?getDarf().getTelefone():"", fontForHeading_4_BOLD());
						
		userDetailTable.addCell(p2_0);
		userDetailTable.addCell(p2_1);
		userDetailTable.addCell(p2_2);
		
		table.addCell(userDetailTable);
		
		
		Phrase p3=getPhrase("DESCRIPTION_RIGHT_03",fontForHeading_4());
		table.addCell(p3);
		
		Phrase p4=getPhrase("DESCRIPTION_RIGHT_04",fontForHeading_5());
				
		
		table.addCell(p4);		
		
		return table;
		
	}
	
	private PdfPTable getRightContent() throws BadElementException{		
		PdfPTable table = new PdfPTable(2);		
		
		String array[][]=populateTheDarfField();
		
		for(int i=0;i<array.length;i++){
			Phrase p=new Phrase(array[i][0],fontForHeading_4());			
			table.addCell(p);
			
			Phrase p1_1=new Phrase(array[i][1],fontForHeading_4_BOLD());		
			table.addCell(p1_1);			
		}
		
		return table;		
	}	
	
	
	private PdfPTable getLogo() throws BadElementException{
		
		PdfPTable table = new PdfPTable(2);
		Image brazilLogo = getBrazilLogo();
		
		table.addCell(brazilLogo);		
		
		PdfPTable tableRight = new PdfPTable(1);		
		
		Phrase p1=getPhrase("GOVERN_TITLE_1",fontForHeading_2());
		tableRight.addCell(p1);
		
		Phrase p2=getPhrase("GOVERN_TITLE_2",fontForHeading_2());		
		tableRight.addCell(p2);
		
		Phrase p3=getPhrase("GOVERN_TITLE_3",fontForHeading_5());
		tableRight.addCell(p3);
		
		Phrase p4=getPhrase("GOVERN_TITLE_4",fontForHeading_1());
		tableRight.addCell(p4);
		
		
		table.addCell(tableRight);
		
		
        return table;		
	}
	
	private Image getBrazilLogo(){
		
		Image image=null;
		try {	
			
			image = Image.getInstance(BRAZILLOGO_FILENAME);
			
		} catch (BadElementException e) {
			logger.error(e);
		} catch (MalformedURLException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		
		return image;
		
	}
	
	private Phrase getPhrase(String key){		
		return new Phrase(CoreMessages.getMessage(key));
	}
	
	private Phrase getPhrase(String key, Font font){		
		return new Phrase(CoreMessages.getMessage(key),font);
	}
	
	private String[][] populateTheDarfField(){
		
		String[][] rightTable=new String[10][2];
		rightTable[0][0] = CoreMessages.getMessage("DESCRIPTION_LEFT_01");
		rightTable[1][0] = CoreMessages.getMessage("DESCRIPTION_LEFT_02");
		rightTable[2][0] = CoreMessages.getMessage("DESCRIPTION_LEFT_03");
		rightTable[3][0] = CoreMessages.getMessage("DESCRIPTION_LEFT_04");
		rightTable[4][0] = CoreMessages.getMessage("DESCRIPTION_LEFT_05");
		rightTable[5][0] = CoreMessages.getMessage("DESCRIPTION_LEFT_06");
		rightTable[6][0] = CoreMessages.getMessage("DESCRIPTION_LEFT_07");
		rightTable[7][0] = CoreMessages.getMessage("DESCRIPTION_LEFT_08");
		rightTable[8][0] = CoreMessages.getMessage("DESCRIPTION_LEFT_09");
		rightTable[9][0] = CoreMessages.getMessage("DESCRIPTION_LEFT_10");
		
		rightTable[0][1] = (getDarf()!=null && getDarf().getPeriodoDeApuracao()!=null)?getDarf().getPeriodoDeApuracao():"";
		rightTable[1][1] = (getDarf()!=null && getDarf().getCpfOuCpnj()!=null)?getDarf().getCpfOuCpnj():"";
		rightTable[2][1] = (getDarf()!=null && getDarf().getCodigoDaReceita()!=null)?getDarf().getCodigoDaReceita():"";
		rightTable[3][1] = (getDarf()!=null && getDarf().getNumeroDeReferencia()!=null)?getDarf().getNumeroDeReferencia():"";
		rightTable[4][1] = (getDarf()!=null && getDarf().getDataDeVencimento()!=null)?getDarf().getDataDeVencimento():"";
		rightTable[5][1] = (getDarf()!=null && getDarf().getValorDoPrincipal()!=null)?getDarf().getValorDoPrincipal():"";
		rightTable[6][1] = (getDarf()!=null && getDarf().getValorDaMulta()!=null)?getDarf().getValorDaMulta():"";
		rightTable[7][1] = (getDarf()!=null && getDarf().getJuros()!=null)?getDarf().getJuros():"";
		rightTable[8][1] = (getDarf()!=null && getDarf().getValorTotal()!=null)?getDarf().getValorTotal():"";
		rightTable[9][1] = "";
		
		
		return rightTable;
	}
	
	
	private Font fontForHeading_1(){		
		return new Font(Font.TIMES_ROMAN, 12, Font.BOLD);		
	}
	
	private Font fontForHeading_2(){		
		return new Font(Font.TIMES_ROMAN, 10, Font.BOLD);		
	}
	private Font fontForHeading_3(){		
		return new Font(Font.TIMES_ROMAN, 10, Font.BOLD);		
	}
	
	
	private Font fontForHeading_4(){		
		return new Font(Font.TIMES_ROMAN, 8, Font.NORMAL);		
	}
	
	private Font fontForHeading_4_BOLD(){		
		return new Font(Font.TIMES_ROMAN, 8, Font.BOLD);		
	}	
	
	private Font fontForHeading_5(){		
		return new Font(Font.TIMES_ROMAN, 7, Font.NORMAL);		
	}
	
	private Font fontForHeading_5_BOLD(){		
		return new Font(Font.TIMES_ROMAN, 7, Font.BOLD);		
	}
	
	
	
}
