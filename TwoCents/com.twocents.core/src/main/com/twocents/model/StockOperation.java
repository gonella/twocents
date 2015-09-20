package com.twocents.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class StockOperation extends Operation {

	private Stock stock;
	
	private Double price = 0.0;
	private Integer amount = 0;
	private boolean dayTrade;
	private boolean optionExercise;

	
	@Column(nullable=false)
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Column(nullable=false)
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(nullable=false)
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
	
	public boolean isDayTrade() {
		return dayTrade;
	}
	public void setDayTrade(boolean dayTrade) {
		this.dayTrade = dayTrade;
	}
	
	public boolean isOptionExercise() {
		return optionExercise;
	}
	public void setOptionExercise(boolean optionExercise) {
		this.optionExercise = optionExercise;
	}
	
	
	@Transient
	public abstract Double getAvgPrice();
	
	@Transient
	public Double getTotal() {
		return amount * price;
	}
	
}
