package com.twocents.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;

@Entity
@NamedQueries( {
		@NamedQuery(name = "twUser.findUserByName", query = "select t from TwUser t where t.name = :name"),
		@NamedQuery(name = "twUser.removeByAccountNumber", query = "delete from TwUser t where t.accountNumber = :accountNumber") })
public class TwUser extends Persistent {

	private static final long serialVersionUID = -6419665667131701560L;

	public static final String FIND_BY_NAME = "twUser.findUserByName";
	public static final String REMOVE_BY_ACCOUNT_NUMBER = "twUser.removeByAccountNumber";

	private String name;
	private String email;
	private String accountNumber;
	private String telephone;

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public TwUser() {
		super();
	}

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.TW_DEFAULT);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
