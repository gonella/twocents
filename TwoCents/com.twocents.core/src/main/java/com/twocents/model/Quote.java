package com.twocents.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;

@Entity
@NamedQueries( {
		@NamedQuery(name = "quote.findLastQuoteMapByStockId", query = "select new map(q.stock.id as stockId, q.price as quotePrice) from Quote q "
				+ "where q.stock.id = :stockId and q.quoteDate = (select max(q1.quoteDate) from Quote q1 where q1.stock.id = :stockId)"),
		@NamedQuery(name = "quote.findQuoteByStockId", query = "select q from Quote q where q.stock.id = :stockId") })


public class Quote extends Persistent {

	private static final long serialVersionUID = -4847205378030118857L;

	public static final String FIND_LAST_QUOTE_BY_STOCK = "quote.findLastQuoteMapByStockId";
	public static final String FIND_QUOTE_BY_STOCK = "quote.findQuoteByStockId";

	private Double price;
	private Timestamp quoteDate;
	private Stock stock;

	public Quote() {
		super();
	}

	@Column(nullable = false)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(nullable = false)
	public Timestamp getQuoteDate() {
		return quoteDate;
	}

	public void setQuoteDate(Timestamp date) {
		this.quoteDate = date;
	}

	@ManyToOne
	@JoinColumn(nullable = false)
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.TW_DEFAULT);
	}

}
