package com.twocents.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("Sell")
public class Sell extends StockOperation {

	private static final long serialVersionUID = -4441212857418361807L;
	private Double profit = 0.0;

	
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}

	@Transient
	public Double getAvgPrice() {
		return (getPrice());
	}

}
