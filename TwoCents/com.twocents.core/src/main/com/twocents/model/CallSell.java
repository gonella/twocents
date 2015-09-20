package com.twocents.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("CallSell")
public class CallSell extends StockOperation {

	private static final long serialVersionUID = -8822168108027634458L;

	@Transient
	public Double getAvgPrice() {
		return (getPrice());
	}

	
	
}
