package com.twocents.security.license;

import java.security.Key;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;

import com.twocents.exceptions.CoreException;
import com.twocents.model.LicenseKey;
import com.twocents.security.util.TypeEncode;

public class PublicLicenseManager {

	private static Logger logger = Logger.getLogger(LicenseValidate.class);
	
	private Cipher cipher=null;
	private Key pubKey=null;
	
	
	private Cipher getCipher() {
		return cipher;
	}

	private void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}

	private void setPubKey(Key pubKey) {
		this.pubKey = pubKey;
	}

	private Key getPubKey() {
		return pubKey;
	}

	public PublicLicenseManager() throws CoreException{
		try {
			setCipher(Cipher.getInstance("RSA"));
			setPubKey(PublicCertificationManager.getPublicKey());
		     
	      }catch(Exception e){
	    	  //logger.error("License Manager failed during loading",e);
	    	  throw new CoreException("Public License Manager failed during loading",e);
	      }
	}
	
	public String decrypt(String key) throws CoreException{

		logger.debug("Decoding the key :"+key);
		String keyDecoded=null;
		
		try {
			if(key==null){
				throw new CoreException(5003);
			}
			
			// now decrypt
			getCipher().init(Cipher.DECRYPT_MODE, getPubKey());
			byte[] byteEncode = TypeEncode.byteEncode(key);
			byte[] plainText = getCipher().doFinal(byteEncode);

			keyDecoded = new String(plainText);
			//logger.info("Plain decrypt :" +keyDecoded);
		
		} catch(Exception e){			
			throw new CoreException("Decoding key failed",e);
		}
		
		
		return keyDecoded;
	}
	
	public LicenseKey getLicense(String key) throws CoreException{
		String informationAbout=decrypt(key);
		return LicenseFormat.decodeFormat(informationAbout);
	}
}
