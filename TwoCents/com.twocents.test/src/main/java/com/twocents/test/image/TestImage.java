package com.twocents.test.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class TestImage {

	private static Logger logger = Logger.getLogger(TestImage.class);

	public static void main(String[] args) throws NoSuchAlgorithmException,
			Exception {

		String string = "test/Image_256x256.png";
		File input = new File(string);
		System.out.println(input.isFile());
		BufferedImage buffImg = ImageIO.read(input);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(buffImg, "png", outputStream);
		byte[] data = outputStream.toByteArray();

		logger.info(data.length);
		
		System.out.println("Start MD5 Digest");
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(data);
		byte[] hash = md.digest();
		System.out.println(returnHex(hash));
	} // Belongs to main class

	// Below method of converting Byte Array to hex
	// Can be found at: http://www.rgagnon.com/javadetails/java-0596.html
	static String returnHex(byte[] inBytes) throws Exception {
		String hexString = null;
		for (int i = 0; i < inBytes.length; i++) { // for loop ID:1
			hexString += Integer.toString((inBytes[i] & 0xff) + 0x100, 16)
					.substring(1);
		} // Belongs to for loop ID:1
		return hexString;
	} // Belongs to returnHex class

}
