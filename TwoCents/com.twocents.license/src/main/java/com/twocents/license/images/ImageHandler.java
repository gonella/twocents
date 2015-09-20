package com.twocents.license.images;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.twocents.exceptions.CoreException;
import com.twocents.security.util.TypeEncode;

public class ImageHandler {

	private static final Logger logger = Logger.getLogger(ImageHandler.class);
	
	public static byte[] toByteArray(BufferedImage bImage) throws CoreException{
		
		try{
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		    ImageIO.write(bImage, "jpeg", bOut);
		    
		    return bOut.toByteArray();
		    
		}catch(Exception e){
			logger.error(e);
			throw new CoreException(e.getMessage());
		}
	}
	
	public static String toHex(BufferedImage bImage) throws CoreException{
		try {
			return TypeEncode.hexEncode(toByteArray(bImage));
		} catch (Exception e) {
			logger.error(e);
			throw new CoreException(e.getMessage());
		}
	}
	
	
	
}
