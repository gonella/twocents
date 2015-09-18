package com.twocents.model;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.log4j.Logger;

import com.twocents.core.util.FormatUtil;

@Entity
@NamedQueries( { @NamedQuery(name = "brokerageNote.removeByAccount", query = "delete from BrokerageNote bn where bn.account.id = :accountId") })
public class BrokerageNote extends Brokerage implements
		Comparable<BrokerageNote> {

	private static final Logger logger = Logger.getLogger(BrokerageNote.class);

	private static final long serialVersionUID = 8064780370356478749L;

	public static final String REMOVE_BY_ACCOUNT = "brokerageNote.removeByAccount";

	// Day
	@Column(unique = true)
	private Date date = null;

	private Set<BrokerageNoteItem> brokerageItemNoteList = new TreeSet<BrokerageNoteItem>();

	private Set<Operation> operations = new TreeSet<Operation>();

	private Double totalCBLC = 0.0;

	private Double totalBovespaESoma = 0.0;

	private Double totalCorretagemEDespesas = 0.0;

	private Double liquido = 0.0;

	private Account account;

	@ManyToOne(cascade = CascadeType.MERGE)
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BrokerageNote() {
		super();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@OneToMany(mappedBy = "brokerageNote", cascade = { CascadeType.REMOVE,
			CascadeType.MERGE }, fetch = FetchType.EAGER)
	public Set<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	@OneToMany(mappedBy = "brokerageNote", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	//@OrderBy("operation.operationDate")
	@OrderBy("operation")
	public Set<BrokerageNoteItem> getBrokerageItemNoteList() {
		return brokerageItemNoteList;
	}

	public void setBrokerageItemNoteList(
			Set<BrokerageNoteItem> brokerageItemNoteList) {
		this.brokerageItemNoteList = brokerageItemNoteList;
	}

	@Override
	public int compareTo(BrokerageNote arg0) {
		int results = this.getDate().compareTo(arg0.getDate());

		return results;

	}

	public void setTotalCBLC(Double totalCBLC) {
		this.totalCBLC = totalCBLC;
	}

	public Double getTotalCBLC() {
		return totalCBLC;
	}

	public void setTotalBovespaESoma(Double totalBovespaESoma) {
		this.totalBovespaESoma = totalBovespaESoma;
	}

	public Double getTotalBovespaESoma() {
		return totalBovespaESoma;
	}

	public void setTotalCorretagemEDespesas(Double totalCorretagemEDespesas) {
		this.totalCorretagemEDespesas = totalCorretagemEDespesas;
	}

	public Double getTotalCorretagemEDespesas() {
		return totalCorretagemEDespesas;
	}

	public void setLiquido(Double liquido) {
		this.liquido = liquido;
	}

	public Double getLiquido() {
		return liquido;
	}

	public void addBrokerageItemNote(BrokerageNoteItem item) {
		// processItem(item);
		getBrokerageItemNoteList().add(item);
	}

	public void processAllItems() {

		// Zerando todas as var envolvidas, esse metod serve para recalcular
		// tudo novamente.
		setCorretagem(0.0);
		setEmolumentos(0.0);
		setISS(0.0);
		setTaxaDeLiquidacao(0.0);
		setValorDasOperacoes(0.0);
		setValorLiquidoDasOperacoes(0.0);

		Set<BrokerageNoteItem> list = getBrokerageItemNoteList();

		if (list != null) {
			for (BrokerageNoteItem brokerageNoteItem : list) {
				processItem(brokerageNoteItem);
			}
		}

	}

	public void processItem(BrokerageNoteItem item) {

		logger.debug("Processando items da Nota");

		// Acumular
		setCorretagem(getCorretagem() + item.getCorretagem());
		setEmolumentos(getEmolumentos() + item.getEmolumentos());
		setISS(getISS() + item.getISS());
		setTaxaDeLiquidacao(getTaxaDeLiquidacao() + item.getTaxaDeLiquidacao());
		setValorDasOperacoes(getValorDasOperacoes()	+ item.getValorDasOperacoes());
		setValorLiquidoDasOperacoes(Math.abs(getValorLiquidoDasOperacoes()+ item.getValorLiquidoDasOperacoes()));
		setIRRF(getIRRF()+ item.getIRRF());
		
		//TODO VERIFICAR o calculo da CBLC, reparei que em algumas notas é feito a soma e outra a substração.
		// Resumo do Quadro geral
		setTotalCBLC(getValorLiquidoDasOperacoes() - getTaxaDeLiquidacao());
		setTotalBovespaESoma(getEmolumentos());
		setTotalCorretagemEDespesas(getCorretagem() + getIRRF());

		//Não vi o ISS sendo cobrando nas notas originais por isso estou tirando
		//Vai ficar liquido para o cliente em 3 dias uteis
		setLiquido(getTotalCBLC() - getTotalBovespaESoma() - getTotalCorretagemEDespesas());
		//setLiquido(getTotalCBLC() - getTotalBovespaESoma() - getTotalCorretagemEDespesas() - getISS());

	}

	public String toString() {
		return "Nota - Items(" + getBrokerageItemNoteList().size() + ") "
				+ /* FormatUtil.formatDate( */getDate() + " [ TotalCBLC :"
				+ FormatUtil.toReal(getTotalCBLC())
				+ ", TotalBovespaESoma :"
				+ FormatUtil.toReal(getTotalBovespaESoma())
				+ ", TotalCorretagemEDespesas :"
				+ FormatUtil.toReal(getTotalCorretagemEDespesas())
				+ ", Liquido :" + FormatUtil.toReal(getLiquido()) + "]";
	}

}
