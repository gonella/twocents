package com.twocents.model;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;

@Entity
public class Feeder extends Persistent {
	
	private static final long serialVersionUID = -694805708140370503L;
	
	private String login;
	private String password;
	private String urlLogin;
	private String urlQuotation;
	private String name;
	private boolean defaultFeeder;

	public Feeder(){
		super();
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public String getUrlLogin() {
		return urlLogin;
	}



	public void setUrlLogin(String urlLogin) {
		this.urlLogin = urlLogin;
	}



	public String getUrlQuotation() {
		return urlQuotation;
	}



	public void setUrlQuotation(String urlQuotation) {
		this.urlQuotation = urlQuotation;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}


	public boolean isDefaultFeeder() {
		return defaultFeeder;
	}

	public void setDefaultFeeder(boolean defaultFeeder) {
		this.defaultFeeder = defaultFeeder;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.TW_DEFAULT);
	}
	
}
