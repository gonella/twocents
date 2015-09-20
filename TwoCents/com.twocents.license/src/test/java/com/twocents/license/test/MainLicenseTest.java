package com.twocents.license.test;

import org.apache.log4j.Logger;

import com.twocents.security.license.LicenseValidate;


public class MainLicenseTest {

	private static final Logger logger = Logger.getLogger(MainLicenseTest.class);
	
	public static void main(String[] args){
		
		for(int i=0;i<100;i++){
			logger.info("Testing "+i);
			LicenseValidate.validateLicense();
		}
		
	}
	
}
