package com.twocents.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GeneralConfiguration")
public class GeneralConfiguration extends Configuration{

	private static final long serialVersionUID = -1239966468521040157L;

	boolean activeInternetConnectivity;

	public boolean isActiveInternetConnectivity() {
		return activeInternetConnectivity;
	}

	public void setActiveInternetConnectivity(boolean activeInternetConnectivity) {
		this.activeInternetConnectivity = activeInternetConnectivity;
	}
	
	public GeneralConfiguration(){
		super();
	}
	
}
