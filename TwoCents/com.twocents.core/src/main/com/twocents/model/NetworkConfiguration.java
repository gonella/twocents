package com.twocents.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("NetworkConfiguration")
public class NetworkConfiguration extends Configuration {
	
	private static final long serialVersionUID = 7143626235756174870L;
	
	private boolean manualProxyConfiguration;
	
	private String httpProxy;
	private String httpProxyPort;
	
	private boolean proxyAuthentication;
	private String username;
	private String password;
	
	public boolean isManualProxyConfiguration() {
		return manualProxyConfiguration;
	}

	public void setManualProxyConfiguration(boolean manualProxyConfiguration) {
		this.manualProxyConfiguration = manualProxyConfiguration;
	}

	public String getHttpProxy() {
		return httpProxy;
	}

	public void setHttpProxy(String httpProxy) {
		this.httpProxy = httpProxy;
	}

	public String getHttpProxyPort() {
		return httpProxyPort;
	}

	public void setHttpProxyPort(String httpProxyPort) {
		this.httpProxyPort = httpProxyPort;
	}

	public boolean isProxyAuthentication() {
		return proxyAuthentication;
	}

	public void setProxyAuthentication(boolean proxyAuthentication) {
		this.proxyAuthentication = proxyAuthentication;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public NetworkConfiguration(){
		super();
	}
	
	
}
