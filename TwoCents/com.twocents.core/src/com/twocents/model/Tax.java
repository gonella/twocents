package com.twocents.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;

@Entity
public abstract class Tax extends Persistent {

	private static final long serialVersionUID = -6867466381915736239L;

	private boolean fixed;

	public Tax() {
		super();
	}

	@Column(nullable = false)
	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.TW_DEFAULT);
	}
	
	public abstract double getCorretagem(double volumeFinance);

}
