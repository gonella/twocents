package com.twocents.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;

@Entity
@NamedQueries( {
		@NamedQuery(name = "account.findAccountByUserId", query = "select o from Account o where o.user.id = :userId"),
		@NamedQuery(name = "account.findAccountByAccountNumber", query = "select o from Account o where o.accountNumber = :accountNumber"),
		@NamedQuery(name = "account.removeByAccountId", query = "delete from Account o where o.id = :id"),
		@NamedQuery(name = "account.findAccountByStockBroker", query = "select o from Account o where o.stockBroker.id = :stockBrokerId")

})
public class Account extends Persistent {

	public static final String FIND_ACCOUNT_BY_USER_ID = "account.findAccountByUserId";
	public static final String FIND_ACCOUNT_BY_STOCKBROKER_ID = "account.findAccountByStockBroker";
	public static final String FIND_ACCOUNT_BY_ACCOUNT_NUMBER = "account.findAccountByAccountNumber";
	public static final String REMOVE_BY_ACCOUNT_ID = "account.removeByAccountId";

	private static final long serialVersionUID = -7860626326099187946L;
	private String name;
	private String number;
	private Double balance;

	private AccountType accountType;
	private Broker broker;
	private TwUser user;
	private StockBroker stockBroker;
	private AccountFollowUp accountFollowUp;
	private String accountNumber;

	private List<Operation> operations;
	
	private Set<BrokerageNoteItem> brokerageNoteItems;
		
	@OneToMany(mappedBy="account", cascade=CascadeType.MERGE)
	public Set<BrokerageNoteItem> getBrokerageNoteItems() {
		return brokerageNoteItems;
	}

	public void setBrokerageNoteItems(Set<BrokerageNoteItem> brokerageNoteItems) {
		this.brokerageNoteItems = brokerageNoteItems;
	}

	@OneToMany(mappedBy="account", cascade=CascadeType.ALL)
	public Set<BrokerageNote> getBrokerageNotes() {
		return brokerageNotes;
	}

	public void setBrokerageNotes(Set<BrokerageNote> brokerageNotes) {
		this.brokerageNotes = brokerageNotes;
	}

	private List<Custody> custodies;
	
	private Set<BrokerageNote> brokerageNotes;


	public Account() {
		super();
	}

	@ManyToOne
	public Broker getBroker() {
		return broker;
	}

	@ManyToOne
	public StockBroker getStockBroker() {
		return stockBroker;
	}

	@ManyToOne(cascade = CascadeType.REMOVE)
	public TwUser getUser() {
		return user;
	}

	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public List<Operation> getOperations() {
		return operations;
	}

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	@Column(nullable = false)
	@Enumerated
	public AccountType getAccountType() {
		return accountType;
	}

	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public List<Custody> getCustodies() {
		return custodies;
	}

	@Column(nullable = true)
	public AccountFollowUp getAccountFollowUp() {
		return accountFollowUp;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setUser(TwUser user) {
		this.user = user;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public void setBroker(Broker broker) {
		this.broker = broker;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public void setCustodies(List<Custody> custodies) {
		this.custodies = custodies;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.TW_DEFAULT);
	}

	public void setStockBroker(StockBroker stockBroker) {
		this.stockBroker = stockBroker;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAccountFollowUp(AccountFollowUp accountFollowUp) {
		this.accountFollowUp = accountFollowUp;
	}


}
