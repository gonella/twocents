package com.twocents.ui.client.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class RoundedBox {

	/** Left top edge */
	public static final int EDGE_LT = 1 << 1;
	/** Right top edge */
	public static final int EDGE_RT = 1 << 2;
	/** Left bottom edge */
	public static final int EDGE_LB = 1 << 3;
	/** Right bottom edge */
	public static final int EDGE_RB = 1 << 4;
	/** Left side - left top and bottom edges */
	public static final int EDGES_LEFT = EDGE_LT | EDGE_LB;
	/** Right side - right top and bottom edges */
	public static final int EDGES_RIGHT = EDGE_RT | EDGE_RB;
	/** Top side - top left and right edges */
	public static final int EDGES_TOP = EDGE_LT | EDGE_RT;
	/** Bottom side - bottom left and right edges */
	public static final int EDGES_BOTTOM = EDGE_LB | EDGE_RB;
	/** All edges */
	public static final int EDGES_ALL = EDGES_LEFT | EDGES_RIGHT;

	/**
	 * Returns a rounded rectangle region * @param bounds The rounded box size:
	 * x, y: offsets, width, height: size
	 * 
	 * @param radius
	 *            : edge radius
	 * @param edges
	 *            : which edges are rounded. Combination of EDGE_* constants
	 *            (using "|")
	 * @return Region to be set using Control.setRegion()
	 */
	public static Region regionRoundedBox(Rectangle bounds, int radius,
			int edges) {
		Region res = new Region();
		
		res.add(bounds);
		Rectangle rect = new Rectangle(0, 0, 1, 0);
		
		for (int i = 0; i < radius; i++) {
			int y = radius
					- (int) Math.round(Math.sqrt(radius * radius - (radius - i)
							* (radius - i)));

			// top left
			rect.x = bounds.x + i;
			rect.y = bounds.y;
			rect.height = y + 1;
			if ((edges & EDGE_LT) != 0)
				res.subtract(rect);

			// top right
			rect.x = bounds.x + bounds.width - i - 1;
			if ((edges & EDGE_RT) != 0)
				res.subtract(rect);

			// bottom right
			rect.y = bounds.y + bounds.height - y - 1;
			if ((edges & EDGE_RB) != 0)
				res.subtract(rect);

			// bottom left
			rect.x = bounds.x + i;
			if ((edges & EDGE_LB) != 0)
				res.subtract(rect);
		}

		return res;
	}

	public static void addRoundedBoxOnResize(final Control c, final int radius,
			final int edges) {
		// doplnÃme listener
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				Rectangle r = c.getBounds();
				r.x = 0;
				r.y = 0;
				
				c.setRegion(regionRoundedBox(r, radius, edges));
			}
		};
		c.addListener(SWT.Resize, listener);
		
		

		// a hneÄ ho aj vyvolÃme (ak by nedoÅlo k resize...)
		listener.handleEvent(null);
	}
}
