package com.twocents.model;

import java.awt.image.BufferedImage;


public class LicenseKey {

	
	public String ownerName;
	public String dateEnd;
	public String dateBegin;
	public BufferedImage image;
	
	public String getOwnerName() {
		return ownerName;
	}
	
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getDateBegin() {
		return dateBegin;
	}
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public LicenseKey(String ownerName,String dateBegin,String dateEnd,BufferedImage bImage) {
		this.ownerName = ownerName;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.image = bImage;
	}
	
	
}
