package com.twocents.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)

@DiscriminatorColumn(
    name="stocktype",
    discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue("Stock")
@NamedQueries({
	@NamedQuery(name="stock.findByCode", 
			query="select s from Stock s where lower(s.code) = lower(:code)"),
	@NamedQuery(name="stock.findByCodePrefix", 
			query="select s from Stock s where lower(s.code) like lower(:codePrefix) and s.class != Option " +
					" order by s.code desc")
			}
)

public class Stock extends Persistent {

	private static final long serialVersionUID = 2457934317404687056L;

	public static final String FIND_BY_CODE = "stock.findByCode";
	public static final String FIND_BY_CODEPREFIX = "stock.findByCodePrefix";
	
	private String code;
	private String description;
	private List<Quote> quotes = new ArrayList<Quote>();
		
	private StockType type=StockType.SHARE;
	
	public Stock() {
		super();
	}

	@Column(nullable=false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(mappedBy="stock",  cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	@OrderBy("quoteDate desc")
	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quote> quotations) {
		this.quotes = quotations;
	}
	
	@Transient
	public Quote getLastQuote() {
		Quote quotation = null;
		if(!quotes.isEmpty()) {
			quotation = quotes.get(0);
		}
		return quotation;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.TW_DEFAULT);
	}
	
	@Column(nullable=false)
	@Enumerated
	public StockType getType() {
		return type;
	}

	public void setType(StockType type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		Stock stock = (Stock) obj;
		return stock.getId().equals(this.getId());
	}
}
