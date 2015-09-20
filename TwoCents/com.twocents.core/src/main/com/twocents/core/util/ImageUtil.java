package com.twocents.core.util;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

public class ImageUtil {

	private static Logger logger = Logger.getLogger(ImageUtil.class);

	
	public static BufferedImage toBufferedImage(java.awt.Image image) {
	    if (image instanceof BufferedImage)
	        return (BufferedImage) image;
	    if (image instanceof VolatileImage) 
	        return ((VolatileImage)image).getSnapshot();
	    
	    //too lazy to use MediaTracker directly:
	    ImageIcon imageIcon = new ImageIcon(image);
	    
	    if (imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE)
	        throw new IllegalArgumentException("Can't load image");
	    
	    image = imageIcon.getImage();
	    int w = image.getWidth(null);
	    int h = image.getHeight(null);
	 
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    GraphicsConfiguration gc = gd.getDefaultConfiguration();
	    BufferedImage output = gc.createCompatibleImage(w,h);
	    //what about transparency? This code assumes it's OPAQUE,
	    //but you can interrogate the image's source for its ColorModel
	    Graphics2D g = output.createGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();
	    return output;
	}
	
	/*public static byte[] bufferedImageToByteArray(BufferedImage img) throws ImageFormatException, IOException{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
		encoder.encode(img);
		return os.toByteArray();	
	}*/
	
	public static void saveBufferedImage(BufferedImage bImage,String fileName,String type){
		try {
			logger.debug("Salvando Objecto Buffered Image To "+fileName);
			ImageIO.write(bImage, type, new File(fileName));
		} catch (IOException e) {
			logger.error("Não foi possível salvar Object BufferedImage: "+fileName);
		}
	}
	
}
