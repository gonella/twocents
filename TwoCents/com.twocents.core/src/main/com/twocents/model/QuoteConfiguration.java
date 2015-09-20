package com.twocents.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("QuoteConfiguration")
public class QuoteConfiguration extends Configuration{

	private static final long serialVersionUID = 4219237354055978173L;

	private String url;
	private String adapterBeanName;
	
	private boolean activateUpdateQuote;
	private Integer updateInterval;
	
	public boolean isActivateUpdateQuote() {
		return activateUpdateQuote;
	}
	public Integer getUpdateInterval() {
		return updateInterval;
	}
	public void setActivateUpdateQuote(boolean activateUpdateQuote) {
		this.activateUpdateQuote = activateUpdateQuote;
	}
	public void setUpdateInterval(Integer updateInterval) {
		this.updateInterval = updateInterval;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String link) {
		this.url = link;
	}
	public String getAdapterBeanName() {
		return adapterBeanName;
	}
	public void setAdapterBeanName(String reflectionClass) {
		this.adapterBeanName = reflectionClass;
	}
	public QuoteConfiguration(){
		super();
	}
	
}
