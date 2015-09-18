package com.twocents.security.util;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import com.twocents.exceptions.CoreException;

public class TypeEncode {

	private static final Logger logger = Logger.getLogger(TypeEncode.class);
	
	
	public static String hexEncode(byte[] aInput) throws CoreException{
		
		try{
			return new BigInteger(aInput).toString(16);
		}catch(Exception e){
			logger.error(e);
			throw new CoreException(6003);
		}		
	}
	
	public static String hexEncode(String str) throws CoreException{
		return hexEncode(str.getBytes());		
	}
	
	public static byte[] byteEncode(String hex) throws CoreException{
		try{
			return new BigInteger(hex,16).toByteArray();
		}catch(Exception e){
			logger.error(e);
			throw new CoreException(6002);
		}
		
		
	}
	
}
