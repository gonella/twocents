package com.twocents.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("Buy")
public class Buy extends StockOperation {

	private static final long serialVersionUID = -3038178133509044901L;

	@Transient
	public Double getAvgPrice() {
		return (getPrice());
	}

}
