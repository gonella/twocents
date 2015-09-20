package com.twocents.report.handler.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Images {
	
	  private static final Map<String, Image> IMAGE_MAP = new HashMap<String, Image>();

	
/*	  public static final String IMG_Loading01_1 = "Loading01_1.png";
	  public static final String IMG_Loading01_2 = "Loading01_2.png";
	  public static final String IMG_Loading01_3 = "Loading01_3.png";
	  public static final String IMG_Loading01_4 = "Loading01_4.png";
	  public static final String IMG_Loading01_5 = "Loading01_5.png";
	  public static final String IMG_Loading01_6 = "Loading01_6.png";
	  public static final String IMG_Loading01_7 = "Loading01_7.png";
	  public static final String IMG_Loading01_8 = "Loading01_8.png";
	  public static final String IMG_Loading01_9 = "Loading01_9.png";
	  public static final String IMG_Loading01_10 = "Loading01_10.png";
	  public static final String IMG_Loading01_11 = "Loading01_11.png";
*/
	  public static final String IMG_Loading02_1 = "Loading02_1.png";
	  public static final String IMG_Loading02_2 = "Loading02_2.png";
	  public static final String IMG_Loading02_3 = "Loading02_3.png";
	  public static final String IMG_Loading02_4 = "Loading02_4.png";
	  public static final String IMG_Loading02_5 = "Loading02_5.png";
	  public static final String IMG_Loading02_6 = "Loading02_6.png";
	  public static final String IMG_Loading02_7 = "Loading02_7.png";
	  public static final String IMG_Loading02_8 = "Loading02_8.png";
	  public static final String IMG_Loading02_9 = "Loading02_9.png";
	  public static final String IMG_Loading02_10 = "Loading02_10.png";
	  public static final String IMG_Loading02_11 = "Loading02_11.png";
	  public static final String IMG_Loading02_12 = "Loading02_12.png";
	  public static final String IMG_Loading02_13 = "Loading02_13.png";
	  public static final String IMG_Loading02_14 = "Loading02_14.png";
	  public static final String IMG_Loading02_15 = "Loading02_15.png";
	  public static final String IMG_Loading02_16 = "Loading02_16.png";
	  public static final String IMG_Loading02_17 = "Loading02_17.png";
	  public static final String IMG_Loading02_18 = "Loading02_18.png";
	  public static final String IMG_Loading02_19 = "Loading02_19.png";
	  public static final String IMG_Loading02_20 = "Loading02_20.png";
	  

	  static {

		    /*IMAGE_MAP.put(IMG_Loading01_1, loadImage(IMG_Loading01_1));
		    IMAGE_MAP.put(IMG_Loading01_2, loadImage(IMG_Loading01_2));
		    IMAGE_MAP.put(IMG_Loading01_3, loadImage(IMG_Loading01_3));
		    IMAGE_MAP.put(IMG_Loading01_4, loadImage(IMG_Loading01_4));
		    IMAGE_MAP.put(IMG_Loading01_5, loadImage(IMG_Loading01_5));
		    IMAGE_MAP.put(IMG_Loading01_6, loadImage(IMG_Loading01_6));
		    IMAGE_MAP.put(IMG_Loading01_7, loadImage(IMG_Loading01_7));
		    IMAGE_MAP.put(IMG_Loading01_8, loadImage(IMG_Loading01_8));
		    IMAGE_MAP.put(IMG_Loading01_9, loadImage(IMG_Loading01_9));
		    IMAGE_MAP.put(IMG_Loading01_10, loadImage(IMG_Loading01_10));
		    IMAGE_MAP.put(IMG_Loading01_11, loadImage(IMG_Loading01_11));
*/
		  	IMAGE_MAP.put(IMG_Loading02_1, loadImage(IMG_Loading02_1));
		    IMAGE_MAP.put(IMG_Loading02_2, loadImage(IMG_Loading02_2));
		    IMAGE_MAP.put(IMG_Loading02_3, loadImage(IMG_Loading02_3));
		    IMAGE_MAP.put(IMG_Loading02_4, loadImage(IMG_Loading02_4));
		    IMAGE_MAP.put(IMG_Loading02_5, loadImage(IMG_Loading02_5));
		    IMAGE_MAP.put(IMG_Loading02_6, loadImage(IMG_Loading02_6));
		    IMAGE_MAP.put(IMG_Loading02_7, loadImage(IMG_Loading02_7));
		    IMAGE_MAP.put(IMG_Loading02_8, loadImage(IMG_Loading02_8));
		    IMAGE_MAP.put(IMG_Loading02_9, loadImage(IMG_Loading02_9));
		    IMAGE_MAP.put(IMG_Loading02_10, loadImage(IMG_Loading02_10));
		    IMAGE_MAP.put(IMG_Loading02_11, loadImage(IMG_Loading02_11));
		    IMAGE_MAP.put(IMG_Loading02_12, loadImage(IMG_Loading02_12));
		    IMAGE_MAP.put(IMG_Loading02_13, loadImage(IMG_Loading02_13));
		    IMAGE_MAP.put(IMG_Loading02_14, loadImage(IMG_Loading02_14));
		    IMAGE_MAP.put(IMG_Loading02_15, loadImage(IMG_Loading02_15));
		    IMAGE_MAP.put(IMG_Loading02_16, loadImage(IMG_Loading02_16));
		    IMAGE_MAP.put(IMG_Loading02_17, loadImage(IMG_Loading02_17));
		    IMAGE_MAP.put(IMG_Loading02_18, loadImage(IMG_Loading02_18));
		    IMAGE_MAP.put(IMG_Loading02_19, loadImage(IMG_Loading02_19));
		    IMAGE_MAP.put(IMG_Loading02_20, loadImage(IMG_Loading02_20));
		    

	}
	  
	  
	  public static Image getImage(String key){
		    return IMAGE_MAP.get(key);
		  }

		 

		  public static Image loadImage (String imageFilename) {
		    InputStream stream = Images.class.getResourceAsStream (imageFilename);
		    if (stream == null){
		      //logger.log(Level.WARNING, "The image "+imageFilename+"no longer exists");
		      return null;
		    }
		    Image image = null;
		    try {
		      image = new Image (Display.getDefault(), stream);
		    } catch (SWTException ex) {
		      //logger.log(Level.SEVERE, "SWTException while trying to load image {0}", imageFilename);
		    } finally {
		      try {
		        stream.close ();
		      } catch (IOException ex) {}
		    }
		    return image;
		  }
	  
}
