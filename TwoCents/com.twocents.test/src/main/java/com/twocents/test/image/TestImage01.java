package com.twocents.test.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

public class TestImage01 {

	private static Logger logger = Logger.getLogger(TestImage01.class);

	public static void main(String arg[]) throws IOException, DecoderException{
		
		

		File input = new File("test/Image_256x256.png");
		System.out.println(input.isFile());
		BufferedImage buffImg = ImageIO.read(input);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(buffImg, "png", outputStream);
		byte[] data = outputStream.toByteArray();

		logger.info("BYTE :"+data.length);
		
		char[] encodeHex = Hex.encodeHex(data);
		logger.info(encodeHex.length);
		
		logger.info(new String(encodeHex));
		
		byte[] decodeHex = Hex.decodeHex(encodeHex);
		
		logger.info(decodeHex.length);

		
		/*String encodeHexString = Hex.encodeHexString(data);

		logger.info(encodeHexString);
*/
/*		String encodeBase64String = Base64.encodeBase64String(data);
				
		logger.info(encodeBase64String);
*/	}
}
