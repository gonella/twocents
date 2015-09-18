package com.twocents.ui.client.components;

import org.eclipse.nebula.widgets.gallery.DefaultGalleryGroupRenderer;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryItemRenderer;
import org.eclipse.nebula.widgets.gallery.Gallery;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;

import com.twocents.ui.client.action.TwReportUIAction;
import com.twocents.ui.client.report.Report;
import com.twocents.ui.resources.UIImages;

public class ReportGallery extends Composite {

	private final TwReportUIAction action;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @param reportTabAction 
	 */
	public ReportGallery(Composite parent, int style, TwReportUIAction action) {
		super(parent, style);
		this.action = action;
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Gallery gallery = new Gallery(this, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		/*gallery.setVertical(true);
		gallery.setGroupRenderer(new DefaultGalleryGroupRenderer());
		gallery.setItemRenderer(new DefaultGalleryItemRenderer());
		*/

		// Renderers
		DefaultGalleryGroupRenderer gr = new DefaultGalleryGroupRenderer();
		gr.setMinMargin(2);
		gr.setItemHeight(56);
		gr.setItemWidth(72);
		gr.setAutoMargin(true);
		gallery.setGroupRenderer(gr);

		DefaultGalleryItemRenderer ir = new DefaultGalleryItemRenderer();
		gallery.setItemRenderer(ir);
		
		GalleryItem galleryItem = new GalleryItem(gallery, SWT.NONE);
		galleryItem.setText("Relatorios");
		galleryItem.setExpanded(true);
		
		Image itemImage = new Image(getDisplay(), Program.findProgram("jpg").getImageData()); //$NON-NLS-1$
		Report[] reports = getAction().getReports();

		GalleryItem itemTemp = new GalleryItem(galleryItem, SWT.NONE);
		itemTemp.setImage(com.swtdesigner.SWTResourceManager.getImage(this.getClass(), UIImages.BULLET_REPORT1_ICON_24));
		itemTemp.setText("TESTE");
			
		for (int i = 0; i < reports.length; i++) {
			GalleryItem item = new GalleryItem(galleryItem, SWT.NONE);
			//if (itemImage != null) 
			{
				
				item.setImage(com.swtdesigner.SWTResourceManager.getImage(this.getClass(), UIImages.BULLET_REPORT1_ICON_24));
				item.setText(reports[i].getName());
				

				//item.setImage(itemImage);
			}
			//item.setText("Item " + i); //$NON-NLS-1$
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public TwReportUIAction getAction() {
		return action;
	}
}
