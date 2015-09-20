package com.twocents.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;

@Entity
@NamedQueries( {
    @NamedQuery(name = "broker.findBrokerByName", query = "select b from Broker b where b.name = :name and b.stockBroker.id = :stockBrokerId"),
    @NamedQuery(name = "broker.findBrokerByStockBroker", query = "select b from Broker b where b.stockBroker.id = :stockBrokerId") })
public class Broker extends Persistent implements Comparable<Broker> {

    private static final long serialVersionUID = -7950400393649883023L;
    private List<Account> accounts;
    private Tax tax;
    private String name;
    private StockBroker stockBroker;

    public static final String FIND_BROKER_BY_NAME = "broker.findBrokerByName";
    public static final String FIND_BROKER_BY_STOCK_BROKER = "broker.findBrokerByStockBroker";

    public Broker() {
        super();
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "broker", cascade = { CascadeType.MERGE,
            CascadeType.REMOVE }, fetch = FetchType.EAGER)
    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.TW_DEFAULT);
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Tax getTax() {
        return tax;
    }

    @ManyToOne
    public StockBroker getStockBroker() {
        return stockBroker;
    }

    public void setStockBroker(StockBroker stockBroker) {
        this.stockBroker = stockBroker;
    }

    public int compareTo(Broker brk) {
        return name.compareTo(brk.getName());
    }

}
