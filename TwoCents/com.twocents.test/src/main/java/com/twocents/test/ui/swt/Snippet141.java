package com.twocents.test.ui.swt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.twocents.ui.resources.UIImages;

public class Snippet141 {
	Display display;
	Shell shell;
	GC shellGC;
	//Canvas shellGC;
	Color shellBackground;
	ImageLoader loader;
	ImageData[] imageDataArray;
	Thread animateThread;
	Image image;
	final boolean useGIFBackground = false;

	public static void main(String[] args) {
		new Snippet141();
	}
	public Snippet141(){
		
		display = new Display();
		shell = new Shell(display,SWT.NO_TRIM | SWT.ON_TOP);
		shell.setSize(110, 110);
		shell.setLocation(700,300);
		shell.open();
		
		/*final Image ideaImage = SWTResourceManager.getImage(ProgressBarDialog.class, UIImageUtil.PROGRESSBAR_FOR_PROCESS02);
	    Label label = new Label(shell,SWT.NONE);
	    label.setImage(ideaImage);
	    Canvas canvas = new Canvas(shell,SWT.NO_REDRAW_RESIZE);
	    canvas.addPaintListener(new PaintListener() {
	        public void paintControl(PaintEvent e) {
	         e.gc.drawImage(ideaImage,0,0);
	        }
	    });*/
		
		shellGC = new GC(shell);
		//shellGC = new Canvas(shell,SWT.NONE);
		
		shellBackground = shell.getBackground();
		
		String fileName = UIImages.PROGRESSBAR_FOR_PROCESS02;
    	URL url = getClass().getResource(fileName);

		if (fileName != null) {
			loader = new ImageLoader();
			try {
				
				 File aFile = new File(fileName);
				    FileInputStream inputFile = null;
				    try {
				      inputFile = new FileInputStream(url.getFile());
				    } catch (FileNotFoundException e) {
				      e.printStackTrace(System.err);
				      System.exit(1);
				    }
				
				imageDataArray = loader.load(inputFile);
				if (imageDataArray.length > 1) {
					animateThread = new Thread("Animation") {
						public void run() {
							
						
							 
							Image offScreenImage = new Image(display,
									loader.logicalScreenWidth,
									loader.logicalScreenHeight);
							GC offScreenImageGC = new GC(offScreenImage);
							offScreenImageGC.setBackground(shellBackground);
							offScreenImageGC.fillRectangle(0, 0,
									loader.logicalScreenWidth,
									loader.logicalScreenHeight);

							try {
								
								
								 
								int imageDataIndex = 0;
								ImageData imageData = imageDataArray[imageDataIndex];
								if (image != null && !image.isDisposed())
									image.dispose();
								image = new Image(display, imageData);
								offScreenImageGC.drawImage(image, 0, 0,
										imageData.width, imageData.height,
										imageData.x, imageData.y,
										imageData.width, imageData.height);

								
							
								 
								int repeatCount = loader.repeatCount;
								while (loader.repeatCount == 0
										|| repeatCount > 0) {
									switch (imageData.disposalMethod) {
									case SWT.DM_FILL_BACKGROUND:
										
										
										 
										Color bgColor = null;
										if (useGIFBackground
												&& loader.backgroundPixel != -1) {
											bgColor = new Color(
													display,
													imageData.palette
															.getRGB(loader.backgroundPixel));
										}
										offScreenImageGC
												.setBackground(bgColor != null ? bgColor
														: shellBackground);
										offScreenImageGC.fillRectangle(
												imageData.x, imageData.y,
												imageData.width,
												imageData.height);
										if (bgColor != null)
											bgColor.dispose();
										break;
									case SWT.DM_FILL_PREVIOUS:
										
									
										 
										offScreenImageGC.drawImage(image, 0, 0,
												imageData.width,
												imageData.height, imageData.x,
												imageData.y, imageData.width,
												imageData.height);
										break;
									}

									imageDataIndex = (imageDataIndex + 1)
											% imageDataArray.length;
									imageData = imageDataArray[imageDataIndex];
									image.dispose();
									image = new Image(display, imageData);
									offScreenImageGC.drawImage(image, 0, 0,
											imageData.width, imageData.height,
											imageData.x, imageData.y,
											imageData.width, imageData.height);

								
									shellGC.drawImage(offScreenImage, 0, 0);

								
									 
									try {
										int ms = imageData.delayTime * 10;
										if (ms < 20)
											ms += 30;
										if (ms < 30)
											ms += 10;
										Thread.sleep(ms);
									} catch (InterruptedException e) {
									}

								
									 
									if (imageDataIndex == imageDataArray.length - 1)
										repeatCount--;
								}
							} catch (SWTException ex) {
								System.out
										.println("There was an error animating the GIF");
							} finally {
								if (offScreenImage != null
										&& !offScreenImage.isDisposed())
									offScreenImage.dispose();
								if (offScreenImageGC != null
										&& !offScreenImageGC.isDisposed())
									offScreenImageGC.dispose();
								if (image != null && !image.isDisposed())
									image.dispose();
							}
						}
					};
					animateThread.setDaemon(true);
					animateThread.start();
				}
			} catch (SWTException ex) {
				System.out.println("There was an error loading the GIF");
			}
		}
		
		
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public void image() {
		Thread animateThread;
		final boolean useGIFBackground = false;

		final String fileName = UIImages.PROGRESSBAR_FOR_PROCESS02;

		loader = new ImageLoader();
		try {

			final ImageLoader loader = new ImageLoader();
			
			final ImageData[] imageDataArray = loader.load(fileName);
			if (imageDataArray.length > 1) {
				animateThread = new Thread("Animation") {
					public void run() {
						/*
						 * Create an off-screen image to draw on, and fill it
						 * with the shell background.
						 */
						Image offScreenImage = new Image(display,
								loader.logicalScreenWidth,
								loader.logicalScreenHeight);
						GC offScreenImageGC = new GC(offScreenImage);
						offScreenImageGC.setBackground(shellBackground);
						offScreenImageGC.fillRectangle(0, 0,
								loader.logicalScreenWidth,
								loader.logicalScreenHeight);

						try {
							/*
							 * Create the first image and draw it on the
							 * off-screen image.
							 */
							int imageDataIndex = 0;
							ImageData imageData = imageDataArray[imageDataIndex];
							Image image=null;

							if (image != null && !image.isDisposed())
								image.dispose();
							image = new Image(display, imageData);
							offScreenImageGC.drawImage(image, 0, 0,
									imageData.width, imageData.height,
									imageData.x, imageData.y, imageData.width,
									imageData.height);

							/*
							 * Now loop through the images, creating and drawing
							 * each one on the off-screen image before drawing
							 * it on the shell.
							 */
							int repeatCount = loader.repeatCount;
							while (loader.repeatCount == 0 || repeatCount > 0) {
								switch (imageData.disposalMethod) {
								case SWT.DM_FILL_BACKGROUND:
									/*
									 * Fill with the background color before
									 * drawing.
									 */
									Color bgColor = null;
									if (useGIFBackground
											&& loader.backgroundPixel != -1) {
										bgColor = new Color(
												display,
												imageData.palette
														.getRGB(loader.backgroundPixel));
									}
									offScreenImageGC
											.setBackground(bgColor != null ? bgColor
													: shellBackground);
									offScreenImageGC.fillRectangle(imageData.x,
											imageData.y, imageData.width,
											imageData.height);
									if (bgColor != null)
										bgColor.dispose();
									break;
								case SWT.DM_FILL_PREVIOUS:
									/*
									 * Restore the previous image before
									 * drawing.
									 */
									offScreenImageGC.drawImage(image, 0, 0,
											imageData.width, imageData.height,
											imageData.x, imageData.y,
											imageData.width, imageData.height);
									break;
								}

								imageDataIndex = (imageDataIndex + 1)
										% imageDataArray.length;
								imageData = imageDataArray[imageDataIndex];
								image.dispose();
								image = new Image(display, imageData);
								offScreenImageGC.drawImage(image, 0, 0,
										imageData.width, imageData.height,
										imageData.x, imageData.y,
										imageData.width, imageData.height);

								/* Draw the off-screen image to the shell. */
								shellGC.drawImage(offScreenImage, 0, 0);

								/*
								 * Sleep for the specified delay time (adding
								 * commonly-used slow-down fudge factors).
								 */
								try {
									int ms = imageData.delayTime * 10;
									if (ms < 20)
										ms += 30;
									if (ms < 30)
										ms += 10;
									Thread.sleep(ms);
								} catch (InterruptedException e) {
								}

								/*
								 * If we have just drawn the last image,
								 * decrement the repeat count and start again.
								 */
								if (imageDataIndex == imageDataArray.length - 1)
									repeatCount--;
							}
						} catch (SWTException ex) {
							System.out
									.println("There was an error animating the GIF");
						} finally {
							if (offScreenImage != null
									&& !offScreenImage.isDisposed())
								offScreenImage.dispose();
							if (offScreenImageGC != null
									&& !offScreenImageGC.isDisposed())
								offScreenImageGC.dispose();
							if (image != null && !image.isDisposed())
								image.dispose();
						}
					}
				};
				animateThread.setDaemon(true);
				animateThread.start();
			}
		} catch (SWTException ex) {
			System.out.println("There was an error loading the GIF");
		}
	}
}