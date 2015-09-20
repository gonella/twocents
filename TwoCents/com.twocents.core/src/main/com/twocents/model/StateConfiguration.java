package com.twocents.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;

@Entity
@DiscriminatorValue("StateConfiguration")
public class StateConfiguration extends Configuration {
	
	private static final long serialVersionUID = 3614966190483107691L;

	private TwUser userSelected;
	private Account accountSelected;
	private Broker brokerSelected;
	private OperationType operationTypeSelected;
		
	
	public StateConfiguration(){
		super();
	}
	
	public TwUser getUserSelected() {
		return userSelected;
	}

	public void setUserSelected(TwUser userSelected) {
		this.userSelected = userSelected;
	}

	public Account getAccountSelected() {
		return accountSelected;
	}

	public void setAccountSelected(Account accountSelected) {
		this.accountSelected = accountSelected;
	}

	public Broker getBrokerSelected() {
		return brokerSelected;
	}

	public void setBrokerSelected(Broker brokerSelected) {
		this.brokerSelected = brokerSelected;
	}

	public OperationType getOperationTypeSelected() {
		return operationTypeSelected;
	}

	public void setOperationTypeSelected(OperationType operationTypeSelected) {
		this.operationTypeSelected = operationTypeSelected;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.TW_DEFAULT);
	}
	
}
