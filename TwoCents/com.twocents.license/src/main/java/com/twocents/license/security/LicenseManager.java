package com.twocents.license.security;

import java.security.Key;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;

import com.twocents.core.util.ResourceUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.LicenseKey;
import com.twocents.security.license.LicenseFormat;
import com.twocents.security.license.PublicLicenseManager;
import com.twocents.security.util.TypeEncode;

public class LicenseManager {

	private static final Logger logger = Logger.getLogger(LicenseManager.class);
	
	private Cipher cipher;
	private Key pubKey;
	private Key privKey;
	
	public Cipher getCipher() {
		return cipher;
	}

	private void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}

	private void setPubKey(Key pubKey) {
		this.pubKey = pubKey;
	}

	private void setPrivKey(Key privKey) {
		this.privKey = privKey;
	}

	public Key getPubKey() {
		return pubKey;
	}

	public Key getPrivKey() {
		return privKey;
	}

	public LicenseManager() throws CoreException{
		try {
			setCipher(Cipher.getInstance("RSA"));
			setPubKey(CertificationManager.getPublicKey());
			setPrivKey(CertificationManager.getPrivateKey());
	        
	      }catch(Exception e){
	    	  throw new CoreException("License Manager failed during loading",e);
	      }
	}
	
	public String encrypt(String informationToEncrypt) throws CoreException{
		logger.debug("Encoding the information :"+informationToEncrypt);
		String keyEncoded=null;
		
		try {
			if(informationToEncrypt==null){
				throw new CoreException(5004);
			}
			
			getCipher().init(Cipher.ENCRYPT_MODE, getPrivKey());
			// encrypt
			// byte[] digest = hash.digest();
			byte[] digest = informationToEncrypt.getBytes();
			byte[] cipherText = getCipher().doFinal(digest);
			
			keyEncoded = TypeEncode.hexEncode(cipherText);
			
			logger.debug("Key Genenated :" + keyEncoded);
        
		} catch(Exception e){
			throw new CoreException("Encoding key failed",e);
		}
		 
		return keyEncoded;
	}
	
	private String decrypt(String key) throws CoreException{
		return new PublicLicenseManager().decrypt(key);
	}
	
	public LicenseKey getLicense(String key) throws CoreException{
		return new PublicLicenseManager().getLicense(key);
	}
	
	public boolean createLicense(String pathFileName,LicenseKey keyToGenetate) throws CoreException{
		
		try{
			logger.info("Creating license in path :"+pathFileName);
			String keyGen = encrypt(LicenseFormat.encodeFormat(keyToGenetate));
			return ResourceUtil.saveContentsToFile(pathFileName,keyGen);
			
		}catch(Exception e){
			throw new CoreException(e.getMessage());
		}
		
	}
}
