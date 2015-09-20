package com.twocents.security.license;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import org.apache.log4j.Logger;

import com.twocents.core.util.ResourceUtil;

public class PublicCertificationManager {

	private static final Logger logger = Logger.getLogger(PublicCertificationManager.class);
	
	private static final String PUBLIC_CERTIFICATION_FILENAME="com/twocents/security/certification/resource/twocents_cert_public.x509";
	

	private static java.security.cert.Certificate importPublicCertificate(
			File file) throws FileNotFoundException, CertificateException {
		java.security.cert.Certificate cert = null;

		FileInputStream is = new FileInputStream(file);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		cert = cf.generateCertificate(is);

		return cert;
	}

	private static PublicKey importPublicKeyFromFile(File file) throws Exception {

		PublicKey publicKey = null;
		publicKey = importPublicCertificate(file).getPublicKey();
		return publicKey;
	}

	public static PublicKey getPublicKey() throws Exception{
		
		return null;//importPublicKeyFromFile(ResourceUtil.getFileByURL(PUBLIC_CERTIFICATION_FILENAME));
		
	}
	
}
