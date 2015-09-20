package com.twocents.license.test;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStoreException;

import org.apache.log4j.Logger;

import com.twocents.core.util.ImageUtil;
import com.twocents.core.util.ResourceUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.license.security.LicenseManager;
import com.twocents.model.LicenseKey;
import com.twocents.security.license.LicenseFormat;


public class MainLicenseTest2 {

	private static final Logger logger = Logger.getLogger(MainLicenseTest2.class);
	
	public static void main(String[] args) throws CoreException, IOException, KeyStoreException {
		
		URL defaultLogo = ResourceUtil.getURL("com/twocents/resources/images/DefaultLogoForReportOperation.gif");
		Image image = Toolkit.getDefaultToolkit().getImage(defaultLogo);
		BufferedImage bImage=ImageUtil.toBufferedImage(image);
		
		LicenseManager manager=new LicenseManager();
		LicenseKey keyFilled=new LicenseKey("TWOCENTS","01/01/2009","01/10/2009",bImage);
		
		try {
			//Serial
			String keyGen = manager.encrypt(LicenseFormat.encodeFormat(keyFilled));
			
			System.out.println("KeyGen :"+keyGen);
			
			manager.createLicense("ActivationKey.key", keyFilled);
			
			LicenseKey licenseInformation = manager.getLicense(keyGen);
			
			System.out.println("License Information : Owner:"+licenseInformation.getOwnerName());
			
			
		} catch (CoreException e) {
			logger.error("Test License Falhou",e);
		}
	
		
	}
	
}
