package com.twocents.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.twocents.bovespa.Bovespa;
import com.twocents.bovespa.TradeProcess;
import com.twocents.core.util.FormatUtil;
import com.twocents.dao.BrokerageNoteKey;

/**
 * 
 * @author Adriano Gonella
 * 
 * Rendimentos no mês, classe que cuida do lucro, prejuizo e taxas ocorridas no mês das operações. Usada para calcular o imposto a ser pago.
 *
 */
public class Income {

	public static Logger logger = Logger.getLogger(Income.class);

	private final Date date;

	public Income(Date date) {
		super();
		this.date = date;
		setVolume(0.0);
		setLucro(0.0);
		setPrejuizo(0.0);
		setLucroDayTrade(0.0);
		setPrejuizoDayTrade(0.0);
		
		setCorretagem(0.0);
		setTaxasRetidas(0.0);
		
		setCorretagemDayTrade(0.0);
		setTaxasRetidasDayTrade(0.0);
		
		setImpostoAPagar(0.0);
		setImpostoAPagarDayTrade(0.0);
	}
	private Double volume;
	
	private Double lucro;
	private Double prejuizo;
	
	private Double lucroDayTrade;
	private Double prejuizoDayTrade;
	
	private Double corretagem; 
	private Double taxasRetidas;
	
	private Double corretagemDayTrade; 
	private Double taxasRetidasDayTrade;
	
	private Double impostoAPagar;
	private Double impostoAPagarDayTrade;
	
	public Double getLucro() {
		return lucro;
	}
	public void setLucro(Double lucro) {
		this.lucro = lucro;
	}
	public Double getPrejuizo() {
		return prejuizo;
	}
	public void setPrejuizo(Double prejuizo) {
		this.prejuizo = prejuizo;
	}
	public Double getLucroDayTrade() {
		return lucroDayTrade;
	}
	public void setLucroDayTrade(Double lucroDayTrade) {
		this.lucroDayTrade = lucroDayTrade;
	}
	public Double getPrejuizoDayTrade() {
		return prejuizoDayTrade;
	}
	public void setPrejuizoDayTrade(Double prejuizoDayTrade) {
		this.prejuizoDayTrade = prejuizoDayTrade;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Double getVolume() {
		return volume;
	}
	public Date getDate() {
		return date;
	}

	public String toString(){
		
		String result="Rendimento ["
					+FormatUtil.formatDateByYearAndMonth(getDate())
					+"] - Lucro:"+FormatUtil.toReal(getLucro())
					+", Lucro DayTrade:"+FormatUtil.toReal(getLucroDayTrade())
					+", Prejuizo:"+FormatUtil.toReal(getPrejuizo())
					+", Prejuizo DayTrade:"+FormatUtil.toReal(getPrejuizoDayTrade())
					+", Corretagem:"+FormatUtil.toReal(getCorretagem())
					+", Corretagem DayTrade:"+FormatUtil.toReal(getCorretagemDayTrade())
					+", Taxas Retidas:"+FormatUtil.toReal(getTaxasRetidas())
					+", Taxas Retidas DayTrade:"+FormatUtil.toReal(getTaxasRetidasDayTrade())
		;
		
		return result;
	}

	/**
	 * Procura a taxa para a respectiva data e seta no income.
	 * @param income
	 * @param taxasTotaisAcumuladasPorMes
	 */
	/*public void populateWithTax(List<Map<String, Object>> taxasTotaisAcumuladasPorMes) {

		String dateMonthAndYear = FormatUtil.formatDateByYearAndMonth(getDate()); 

		String month=null;
		String year=null;
		String dateMonthAndYearTmp=null;
		
		//logger.info("populateIncomeWithTax : "+date);
		for (Map<String, Object> map : taxasTotaisAcumuladasPorMes) {
			
			month = ((Integer)map.get(BrokerageNoteKey.MONTH.toString())).toString();
			year = ((Integer)map.get(BrokerageNoteKey.YEAR.toString())).toString();
			
			dateMonthAndYearTmp=FormatUtil.formatDateByYearAndMonth(FormatUtil.parseDateByYearAndMonth(month+"/"+year));
			
			//logger.info("populateIncomeWithTax(dateTmp) : "+dateMonthAndYearTmp);
			if(dateMonthAndYear.equals(dateMonthAndYearTmp)){
				
				setCorretagem((Double)map.get(BrokerageNoteKey.CORRETAGEM.toString()));
				
				Double ISS=((Double)map.get(BrokerageNoteKey.ISS.toString()));
				Double EMOLUMENTOS=((Double)map.get(BrokerageNoteKey.EMOLUMENTOS.toString()));
				Double TAXA_DE_LIQUIDACAO=((Double)map.get(BrokerageNoteKey.TAXADELIQUIDACAO.toString()));
				
				setTaxasRetidas(ISS+EMOLUMENTOS+TAXA_DE_LIQUIDACAO);
				
				
			}
			
		}
		
	}*/
	
	public void setCorretagem(Double corretagem) {
		this.corretagem = corretagem;
	}
	public Double getCorretagem() {
		return corretagem;
	}
	public void setTaxasRetidas(Double taxasRetidas) {
		this.taxasRetidas = taxasRetidas;
	}
	public Double getTaxasRetidas() {
		return taxasRetidas;
	}
	public void populateWithTax(BrokerageNoteItem item) {

		logger.debug("Populando Income com Taxas do Item da Nota");
		
		if(item.isDayTrade()){
			setCorretagemDayTrade(getCorretagemDayTrade()+item.getCorretagem());
			setTaxasRetidasDayTrade(
					getTaxasRetidasDayTrade() 
					+ (item.getISS()+item.getEmolumentos()+item.getTaxaDeLiquidacao())
					);
			
		}
		else{
			setCorretagem(getCorretagem()+item.getCorretagem());
			setTaxasRetidas(
					getTaxasRetidas() 
					+ 
					(item.getISS()+item.getEmolumentos()+item.getTaxaDeLiquidacao())
					);
		}
		
	}
	public void setCorretagemDayTrade(Double corretagemDayTrade) {
		this.corretagemDayTrade = corretagemDayTrade;
	}
	public Double getCorretagemDayTrade() {
		return corretagemDayTrade;
	}
	public void setTaxasRetidasDayTrade(Double taxasRetidasDayTrade) {
		this.taxasRetidasDayTrade = taxasRetidasDayTrade;
	}
	public Double getTaxasRetidasDayTrade() {
		return taxasRetidasDayTrade;
	}
	public void setImpostoAPagar(Double impostoAPagar) {
		this.impostoAPagar = impostoAPagar;
	}
	public Double getImpostoAPagar() {
		return impostoAPagar;
	}
	public void setImpostoAPagarDayTrade(Double impostoAPagarDayTrade) {
		this.impostoAPagarDayTrade = impostoAPagarDayTrade;
	}
	public Double getImpostoAPagarDayTrade() {
		return impostoAPagarDayTrade;
	}
	
	/**
	 * Depois do Income ter processado todas os Items no mês, o metodo abaixo ira calcular o imposto a ser pago.
	 */
	private void calculateTaxToBePaid(){
		
		setImpostoAPagar(Bovespa.impostoASerPago(false, getLucro(), getPrejuizo(), getTaxasRetidas()));
		setImpostoAPagarDayTrade(Bovespa.impostoASerPago(true, getLucroDayTrade(), getPrejuizoDayTrade(), getTaxasRetidasDayTrade()));
		
	}
	
	/**
	 * Calcula a rentabilidade gerada, lucro ou prejuizo. E ja coloca o imposto gerado.
	 * 
	 * @param isDaytrade
	 * @param amount
	 * @param priceAcquired
	 * @param priceExecuted
	 */
	public void doIncome(boolean isDaytrade, Integer amount,Double priceAcquired,Double priceExecuted){
		
		Double incomeReal= amount * (priceExecuted-priceAcquired);
		
		logger.info("[Income] isDaytrade:"+isDaytrade+" Income: "+FormatUtil.toReal(incomeReal)+" priceAcquired : "+priceAcquired+" priceExecuted :"+priceExecuted+" Quantidade :"+amount);
		
		
		double volumeTmp = amount * priceAcquired;
		
		setVolume(getVolume() + volumeTmp);
		
		if(isDaytrade){
			
			//Ou seja, deu lucro
			if(incomeReal > 0){
				setLucroDayTrade(getLucroDayTrade() + incomeReal);
			}
			//Deu prejuizo
			else if(incomeReal < 0){
				setPrejuizoDayTrade(getPrejuizoDayTrade() + incomeReal);
			}
			else{
				setLucroDayTrade(getLucroDayTrade() + incomeReal);
				setPrejuizoDayTrade(getPrejuizoDayTrade() + incomeReal);
			}
			
		}else{
			
			//Ou seja, deu lucro
			if(incomeReal > 0){
				setLucro(getLucro() + incomeReal);
			}
			//Deu prejuizo
			else if(incomeReal < 0){
				setPrejuizo(getPrejuizo() + incomeReal);
			}
			else{
				setLucro(getLucro() + incomeReal);
				setPrejuizo(getPrejuizo() + incomeReal);
			}
		}
		
		//Com os novos valores de lucro e prejuizo atualizados calcula os impostos a serem pagos
		calculateTaxToBePaid();
		
	}
	
}
