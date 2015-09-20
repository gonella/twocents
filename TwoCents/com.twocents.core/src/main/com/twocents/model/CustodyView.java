package com.twocents.model;

public class CustodyView {

	private Long id;
	private String stock;
	private Integer amount;
	private Integer amountBlocked = 0;
	private Double averagePrice;
	private Double currentPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getAmountBlocked() {
		return amountBlocked;
	}

	public void setAmountBlocked(Integer amountBlocked) {
		this.amountBlocked = amountBlocked;
	}

	public Double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(Double averagePrice) {
		this.averagePrice = averagePrice;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public CustodyView(Long id, String stock, Integer amount,
			Integer amountBlocked, Double averagePrice, Double currentPrice) {
		super();
		this.id = id;
		this.stock = stock;
		this.amount = amount;
		this.amountBlocked = amountBlocked;
		this.averagePrice = averagePrice;
		this.currentPrice = currentPrice;
	}

}
