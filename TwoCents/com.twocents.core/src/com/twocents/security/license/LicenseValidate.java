package com.twocents.security.license;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.twocents.core.util.DateUtil;
import com.twocents.core.util.ResourceUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.LicenseKey;
import com.twocents.resources.CoreMessages;

public final class LicenseValidate {
	
	private static Logger logger = Logger.getLogger(LicenseValidate.class);
	private static final String ACTIVATION_FILENAME="ActivationKey.key";	

	private static LicenseKey keyForUser;
	
	private static LicenseKey getUserLicense() throws CoreException{
		
		if(keyForUser==null){
			logger.debug("Getting License for Current User");	
			
			File fActivation=null;
			try {
				fActivation = ResourceUtil.getFile(ACTIVATION_FILENAME);		
				
			} catch (Exception e) {
				logger.error(e);
				throw new CoreException(5002);
			}
			
			if(fActivation==null || (fActivation!=null && !fActivation.exists())){
				throw new CoreException(5002);
			}
			
			String activationKey=ResourceUtil.getContents(fActivation);
			logger.debug("Key for Activation :"+activationKey);
			
			PublicLicenseManager pManager=new PublicLicenseManager();		
			keyForUser=pManager.getLicense(activationKey);
		}
		
		return keyForUser;
		
		
		
	}
	
	public static void validateLicense(){
		synchronized (ACTIVATION_FILENAME) {
			/*
			try {
					
				LicenseKey license = getUserLicense();
							
				logger.info("Checking license information");
				isLicenseDateValid(license.getDateBegin(), license.getDateEnd());
				
				
				
				logger.info("** License OWNER :"+license.getOwnerName());
				logger.info("** TwoCents is Licensed");
			} catch (Exception e) {			
				//logger.error(e);
				logger.error(CoreMessages.getMessage(5003),e);
				System.exit(0);			
			}
			*/
		
		}
	}
	
	private static void isLicenseDateValid(String dateBegin,String dateEnd) throws Exception{
		
		logger.debug("Verifying if license date is expired");		
		
		Date currentDate = Calendar.getInstance().getTime();
		Date beginDate=DateUtil.convert(dateBegin);
		Date endDate = DateUtil.convert(dateEnd);
		
		Long current=currentDate.getTime();
		Long begin=beginDate.getTime();
		Long end=endDate.getTime();
		
		logger.debug("Checking if the CURRENT DATE is in license interval");
		if( ! (current >begin && current < end) ){
			throw new CoreException(5005);
		}
		
	}
	

	
}
