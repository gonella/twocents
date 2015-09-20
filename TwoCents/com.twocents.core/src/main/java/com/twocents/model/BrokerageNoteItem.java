package com.twocents.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import org.apache.log4j.Logger;

import com.twocents.bovespa.Bovespa;
import com.twocents.core.util.FormatUtil;

@Entity
@NamedQueries( {
    @NamedQuery(name = "brokerageNoteItem.removeByAccount", query = "delete from BrokerageNoteItem bni where bni.account.id = :accountId"),
    @NamedQuery(name = "brokerageNoteItem.removeByOperation", query = "delete from BrokerageNoteItem bni where bni.operation.id = :operationId"),
    @NamedQuery(name = "brokerageNoteItem.findByOperation", query = "select bni from BrokerageNoteItem bni where bni.operation.id = :operationId") })
public class BrokerageNoteItem extends Brokerage implements
Comparable<BrokerageNoteItem> {

    public static final String REMOVE_BY_ACCOUNT = "brokerageNoteItem.removeByAccount";
    public static final String FIND_BY_OPERATION = "brokerageNoteItem.findByOperation";
    public static final String REMOVE_BY_OPERATION = "brokerageNoteItem.removeByOperation";

    private static final long serialVersionUID = -1528020040967461915L;

    private static final Logger logger = Logger
            .getLogger(BrokerageNoteItem.class);

    private Operation operation;
    private String stockCode;
    private StockType stockType;
    private OperationType operationType;
    private Integer amount;
    private Double price;
    private boolean dayTrade;
    private BrokerType brokerType;

    private BrokerageNote brokerageNote;

    private Account account;

    public BrokerageNoteItem() {
        super();
    }

    public void process(boolean isLiquidated) {

        Double volumeFinance = amount * price;

        setValorDasOperacoes(volumeFinance);

        double corretagem = getAccount().getBroker().getTax().getCorretagem(volumeFinance);

        if (OperationType.BUY.toString().equals(getOperationType().toString())) {
            setValorLiquidoDasOperacoes(volumeFinance);
        } else if (OperationType.SELL.toString().equals(
                getOperationType().toString())) {
            setValorLiquidoDasOperacoes(-volumeFinance);
        }

        setCorretagem(corretagem);

        setEmolumentos(Bovespa.getEmolumentos(dayTrade, stockType,volumeFinance));

        // Usado quando uma operação liquidar a outra.
        if (isLiquidated) {
            setIRRF(Bovespa.getImpostoRetidoNaFonte(dayTrade,volumeFinance));
        }

        //TODO - Verificar se esse atributos são realmente necessários
        setTaxaDeLiquidacao(Bovespa.getTaxaLiquidacao(dayTrade, stockType,volumeFinance));
        setISS(Bovespa.getISS(volumeFinance));


        logger.debug(this);
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public StockType getStockType() {
        return stockType;
    }

    public void setStockType(StockType stockType) {
        this.stockType = stockType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public boolean isDayTrade() {
        return dayTrade;
    }

    public void setDayTrade(boolean dayTrade) {
        this.dayTrade = dayTrade;
    }

    @OneToOne(cascade = CascadeType.REMOVE)
    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public BrokerType getBrokerType() {
        return brokerType;
    }

    public void setBrokerType(BrokerType brokerType) {
        this.brokerType = brokerType;
    }

    public void setBrokerageNote(BrokerageNote brokerageNote) {
        this.brokerageNote = brokerageNote;
    }

    @ManyToOne
    public BrokerageNote getBrokerageNote() {
        return brokerageNote;
    }

    @Override
    public String toString() {
        return "Item da Nota: [" + getOperationType() + ", " + getStockCode()
        + ((isDayTrade()) ? ",Daytrade" : "") + ", Qtd :" + getAmount()
        + ", Preço :" + FormatUtil.toReal(getPrice())
        + ", Corretagem :" + FormatUtil.toReal(getCorretagem())
        + ", Emolumentos :"
        + FormatUtil.toReal(getEmolumentos()) + ", Date: "
        + operation.getOperationDate() + "]";
    }


    public int compareTo(BrokerageNoteItem arg0) {
        int results = this.getOperation().getOperationDate().compareTo(
                arg0.getOperation().getOperationDate());

        return results;

    }
}
