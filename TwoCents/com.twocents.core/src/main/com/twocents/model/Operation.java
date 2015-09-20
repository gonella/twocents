package com.twocents.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "operationtype", discriminatorType = DiscriminatorType.STRING)
@NamedQueries( {
		@NamedQuery(name = "operation.findOperationByAccount", query = "select o from Operation o where o.account.id = :accountId order by o.operationDate desc"),
		@NamedQuery(name = "operation.findOperationMapByAccount", query = "select new map(o.id as id, o.description as description, "
				+ "o.operationDate as operationDate, o.class as type, o.price as price, o.amount as amount, "
				+ "s.code as stock) from Operation o left join o.stock as s where o.account.id = :accountId "
				+ "and o.operationDate between :startDate and :endDate order by o.operationDate desc"),
		@NamedQuery(name = "operation.removeByAccount", query = "delete from Operation o where o.account.id = :accountId"),
		@NamedQuery(name = "operation.findOperationViewByAccount", query = "select new com.twocents.model.OperationView(o.id, o.description, o.operationDate, o.class, o.price, o.amount, s.code) from Operation o, Stock s where o.account.id = :accountId and o.stock.id = s.id "
				+ "and o.operationDate between :startDate and :endDate order by o.operationDate desc") })
public class Operation extends Persistent {

	private static final long serialVersionUID = 2051689520866102208L;

	public static final String FIND_OPERATION_BY_ACCOUNT = "operation.findOperationByAccount";

	public static final String FIND_OPERATION_MAP_BY_ACCOUNT = "operation.findOperationMapByAccount";

	public static final String FIND_OPERATION_VIEW_BY_ACCOUNT = "operation.findOperationViewByAccount";
	
	public static final String REMOVE_BY_ACCOUNT = "operation.removeByAccount";

	private String description;
	private Account account;
	private Timestamp operationDate;
	private Broker broker;

	private BrokerageNote brokerageNote;

	private List<Document> documents = new ArrayList<Document>();

	public Operation() {
		super();
	}

	@ManyToOne(cascade = CascadeType.MERGE)
	public BrokerageNote getBrokerageNote() {
		return brokerageNote;
	}

	public void setBrokerageNote(BrokerageNote brokerageNote) {
		this.brokerageNote = brokerageNote;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Column(nullable = false)
	public Timestamp getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Timestamp date) {
		this.operationDate = date;
	}

	@OneToMany(mappedBy = "operation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.TW_DEFAULT);
	}

	@ManyToOne
	public Broker getBroker() {
		return broker;
	}

	public void setBroker(Broker broker) {
		this.broker = broker;
	}

	@Override
	public boolean equals(Object obj) {
		Operation oper = (Operation) obj;
		return oper.getId().equals(super.getId());
	}

}
