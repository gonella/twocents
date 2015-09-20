package com.twocents.license.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.log4j.Logger;

import com.twocents.security.license.PublicCertificationManager;

public class CertificationManager {

	private static final Logger logger = Logger.getLogger(CertificationManager.class);
	private static final String KEYSTORE_FILENAME="com/twocents/license/security/key/twocents_keystore.jks";
	private static final String KEYSTORE_ALIAS="twocents";
	private static final String FILENAME_PASSWORD="tchpcomplab";

	private static PrivateKey importPrivateKeyFromKeyStore(File cert, String alias,
			String password) throws Exception {
		
		KeyStore ks = KeyStore.getInstance("JKS");
		char[] pwd = password.toCharArray();
		InputStream is = new FileInputStream(cert);
		ks.load(is, pwd);
		is.close();
		Key key = ks.getKey(alias, pwd);
		if (key instanceof PrivateKey) {
			return (PrivateKey) key;
		}
		return null;
	}
	
	public static PrivateKey getPrivateKey() throws Exception{
		return null;//importPrivateKeyFromKeyStore(ResourceUtil.getFileByURL(KEYSTORE_FILENAME), KEYSTORE_ALIAS, FILENAME_PASSWORD);
	}
	
	public static PublicKey getPublicKey() throws Exception {
		return PublicCertificationManager.getPublicKey();
	}
	
	
}
