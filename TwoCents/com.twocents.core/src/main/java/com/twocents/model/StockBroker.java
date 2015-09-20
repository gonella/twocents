package com.twocents.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries( {
    @NamedQuery(name = "twUser.findStockBrokerByName", query = "select t from StockBroker t where t.name = :name"),
    @NamedQuery(name = "twUser.findStockBrokerByUsernameAndPassword", query = "select t from StockBroker t where t.username = :username and t.password = :password"),
    @NamedQuery(name = "twUser.findStockBrokerByUsername", query = "select t from StockBroker t where t.username = :username")

}

        )
public class StockBroker extends Persistent implements Comparable<StockBroker> {

    private static final long serialVersionUID = -7860626366099187946L;

    public static final String FIND_BY_NAME = "twUser.findStockBrokerByName";;
    public static final String FIND_BY_USERNAME_AND_PASSWORD = "twUser.findStockBrokerByUsernameAndPassword";

    public static final String FIND_BY_USERNAME = "twUser.findStockBrokerByUsername";;

    private String name;

    private String username;
    private String password;

    private String email;
    private String telefone;

    private List<Account> accountList;

    private Set<Broker> brokers;

    public StockBroker() {
        super();
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @OneToMany(mappedBy = "stockBroker", fetch = FetchType.EAGER)
    @Column(nullable = true)
    public List<Account> getAccountList() {
        return accountList;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @OneToMany(fetch = FetchType.EAGER)
    public Set<Broker> getBrokers() {
        return brokers;
    }

    public void setBrokers(Set<Broker> brokers) {
        this.brokers = brokers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object obj) {
        StockBroker stb = (StockBroker) obj;
        return stb.getId() != null && stb.getId().equals(this.getId());

    }

    public int compareTo(StockBroker stockBroker) {

        if (stockBroker.getId() == this.getId()) {
            return 0;
        }
        return -1;
    }

}
