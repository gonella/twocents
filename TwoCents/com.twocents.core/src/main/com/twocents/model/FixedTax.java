package com.twocents.model;

import javax.persistence.Entity;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.twocents.core.util.ToStringStyle;

@Entity
public class FixedTax extends Tax {

	private static final long serialVersionUID = -6867466381915736239L;

	private Double value;

	public FixedTax() {
		super();
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.TW_DEFAULT);
	}

	@Override
	public double getCorretagem(double volumeFinance) {
		return this.value;
	}

}
