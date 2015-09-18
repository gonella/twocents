package com.twocents.model;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;

@Entity
public class VariableTax extends Tax {

	private static final long serialVersionUID = -6867466381915736239L;

	private float minor1;
	private float max1;
	private float fixed1;
	private float percent1;

	private float minor2;
	private float max2;
	private float fixed2;
	private float percent2;

	private float minor3;
	private float max3;
	private float fixed3;
	private float percent3;

	private float minor4;
	private float max4;
	private float fixed4;
	private float percent4;

	private float from5;
	private float fixed5;
	private float percent5;

	public VariableTax() {
		super();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.TW_DEFAULT);
	}

	public float getMinor1() {
		return minor1;
	}

	public void setMinor1(float minor1) {
		this.minor1 = minor1;
	}

	public float getMax1() {
		return max1;
	}

	public void setMax1(float max1) {
		this.max1 = max1;
	}

	public float getFixed1() {
		return fixed1;
	}

	public void setFixed1(float fixed1) {
		this.fixed1 = fixed1;
	}

	public float getPercent1() {
		return percent1;
	}

	public void setPercent1(float percent1) {
		this.percent1 = percent1;
	}

	public float getMinor2() {
		return minor2;
	}

	public void setMinor2(float minor2) {
		this.minor2 = minor2;
	}

	public float getMax2() {
		return max2;
	}

	public void setMax2(float max2) {
		this.max2 = max2;
	}

	public float getFixed2() {
		return fixed2;
	}

	public void setFixed2(float fixed2) {
		this.fixed2 = fixed2;
	}

	public float getPercent2() {
		return percent2;
	}

	public void setPercent2(float percent2) {
		this.percent2 = percent2;
	}

	public float getMinor3() {
		return minor3;
	}

	public void setMinor3(float minor3) {
		this.minor3 = minor3;
	}

	public float getMax3() {
		return max3;
	}

	public void setMax3(float max3) {
		this.max3 = max3;
	}

	public float getFixed3() {
		return fixed3;
	}

	public void setFixed3(float fixed3) {
		this.fixed3 = fixed3;
	}

	public float getPercent3() {
		return percent3;
	}

	public void setPercent3(float percent3) {
		this.percent3 = percent3;
	}

	public float getMinor4() {
		return minor4;
	}

	public void setMinor4(float minor4) {
		this.minor4 = minor4;
	}

	public float getMax4() {
		return max4;
	}

	public void setMax4(float max4) {
		this.max4 = max4;
	}

	public float getFixed4() {
		return fixed4;
	}

	public void setFixed4(float fixed4) {
		this.fixed4 = fixed4;
	}

	public float getPercent4() {
		return percent4;
	}

	public void setPercent4(float percent4) {
		this.percent4 = percent4;
	}

	public float getFrom5() {
		return from5;
	}

	public void setFrom5(float from5) {
		this.from5 = from5;
	}

	public float getFixed5() {
		return fixed5;
	}

	public void setFixed5(float fixed5) {
		this.fixed5 = fixed5;
	}

	public float getPercent5() {
		return percent5;
	}

	public void setPercent5(float percent5) {
		this.percent5 = percent5;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public double getCorretagem(double volumeFinance) {
		double corretagem = 0;

		if (volumeFinance > minor1 && volumeFinance < max1) {
			corretagem = fixed1 + (volumeFinance * percent1);
		} else if (volumeFinance > minor2 && volumeFinance < max2) {
			corretagem = fixed2 + (volumeFinance * percent2);
		} else if (volumeFinance > minor3 && volumeFinance < max3) {
			corretagem = fixed3 + (volumeFinance * percent3);
		} else if (volumeFinance > minor4 && volumeFinance < max4) {
			corretagem = fixed4 + (volumeFinance * percent4);
		} else {
			corretagem = fixed5 + (volumeFinance * percent5);
		}

		return corretagem;
	}

}
