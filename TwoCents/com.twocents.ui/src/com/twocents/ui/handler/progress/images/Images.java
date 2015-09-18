package com.twocents.ui.handler.progress.images;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Images {
	
	  private static final Map<String, Image> IMAGE_MAP = new HashMap<String, Image>();

	
	  public static final String IMG_INDICATOR_B_1 = "indicator_b_1.png";
	  public static final String IMG_INDICATOR_B_2 = "indicator_b_2.png";
	  public static final String IMG_INDICATOR_B_3 = "indicator_b_3.png";
	  public static final String IMG_INDICATOR_B_4 = "indicator_b_4.png";
	  public static final String IMG_INDICATOR_B_5 = "indicator_b_5.png";
	  public static final String IMG_INDICATOR_B_6 = "indicator_b_6.png";
	  public static final String IMG_INDICATOR_B_7 = "indicator_b_7.png";
	  public static final String IMG_INDICATOR_B_8 = "indicator_b_8.png";
	  static {

		    IMAGE_MAP.put(IMG_INDICATOR_B_1, loadImage(IMG_INDICATOR_B_1));
		    IMAGE_MAP.put(IMG_INDICATOR_B_2, loadImage(IMG_INDICATOR_B_2));
		    IMAGE_MAP.put(IMG_INDICATOR_B_3, loadImage(IMG_INDICATOR_B_3));
		    IMAGE_MAP.put(IMG_INDICATOR_B_4, loadImage(IMG_INDICATOR_B_4));
		    IMAGE_MAP.put(IMG_INDICATOR_B_5, loadImage(IMG_INDICATOR_B_5));
		    IMAGE_MAP.put(IMG_INDICATOR_B_6, loadImage(IMG_INDICATOR_B_6));
		    IMAGE_MAP.put(IMG_INDICATOR_B_7, loadImage(IMG_INDICATOR_B_7));
		    IMAGE_MAP.put(IMG_INDICATOR_B_8, loadImage(IMG_INDICATOR_B_8));
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
