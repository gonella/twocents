package com.twocents.model;

import java.util.Date;

public class OperationView {

	private Long id;
	private String description;
	private Date operationDate;
	private String operationType;
	private Double price;
	private Integer amount;
	private String code;

	public OperationView() {

	}

	public OperationView(Long id, String description, Date operationDate,
			String operationType, Double price, Integer amount, String code) {
		super();
		this.id = id;
		this.description = description;
		this.operationDate = operationDate;
		this.operationType = operationType;
		this.price = price;
		this.amount = amount;
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getStockCode() {
		return code;
	}

	public void setStockCode(String stockCode) {
		this.code = stockCode;
	}

}
