package com.twocents.model;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;

@Entity
@DiscriminatorValue("Option")
public class Option extends Stock {

	private static final long serialVersionUID = 2457934317404687056L;

	private Double strikePrice;
	private Date expirationDate;
	private Stock stock;
	
	private OptionType optionType = OptionType.CALL;
	
	public Option() {
		super();
				
	}

	public Double getStrikePrice() {
		return strikePrice;
	}

	public void setStrikePrice(Double strikePrice) {
		this.strikePrice = strikePrice;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	@Enumerated
	public OptionType getOptionType() {
		return optionType;
	}

	public void setOptionType(OptionType type) {
		this.optionType = type;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.TW_DEFAULT);
	}

}
