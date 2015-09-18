package com.twocents.security.license;

import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.twocents.exceptions.CoreException;
import com.twocents.model.LicenseKey;
import com.twocents.security.util.TypeEncode;

public class LicenseFormat {

	private static final Logger logger = Logger.getLogger(LicenseFormat.class);
	
	private static final int ARGS_IN_FORMAT=3; //Number of field inside the format.
	
	public static String encodeFormat(LicenseKey key) throws CoreException{
		String encoded=null;
		logger.debug("Encoding in license format");
		try{
			
			StringBuffer sBuffer=new StringBuffer();
			
			if(key.getOwnerName()==null){
				throw new CoreException(5000);
			}
			sBuffer.append(TypeEncode.hexEncode(key.getOwnerName())+",");
			
			if(key.getDateBegin()==null){
				throw new CoreException(5000);
			}
			sBuffer.append(TypeEncode.hexEncode(key.getDateBegin())+",");
			
			if(key.getDateEnd()==null){
				throw new CoreException(5000);
			}
			sBuffer.append(TypeEncode.hexEncode(key.getDateEnd()));
			
			/*
			if(key.getImage()==null){
				throw new CoreException(5000);
			}
			sBuffer.append(ImageHandler.toHex(key.getImage()));
			*/
			
			encoded=sBuffer.toString();
			
		}catch(Exception e){
			throw new CoreException("Failed during ENCODING FORMAT for license",e);
		}
		
		return encoded;
	}

	
	public static LicenseKey decodeFormat(String informationFormat) throws CoreException{
		LicenseKey decoded=null;
		
		if(informationFormat==null){
			throw new CoreException(5000);
		}
		
		logger.debug("Decoding in license format");
		try{
			
			StringTokenizer token=new StringTokenizer(informationFormat,",");
			
			if(token!=null && token.hasMoreElements() && token.countTokens()==ARGS_IN_FORMAT){
	
				String ownerName_encoded=(String)token.nextElement();
				String dateBegin_encoded=(String)token.nextElement();
				String dateEnd_encoded=(String)token.nextElement();
				//String image_encoded=(String)token.nextElement();
				
				String ownerName=new String(TypeEncode.byteEncode(ownerName_encoded));
				String dateBegin=new String(TypeEncode.byteEncode(dateBegin_encoded));
				String dateEnd=new String(TypeEncode.byteEncode(dateEnd_encoded));
				
				//BufferedImage bImage=ImageHandler.toBufferedImage(TypeEncode.byteEncode(image_encoded));
				
				decoded=new LicenseKey(ownerName,dateBegin,dateEnd,null);
			}
		}catch(Exception e){			
			throw new CoreException("Failed during DECODING FORMAT for license",e);
		}
		
		return decoded;
	}
}
