package com.twocents.test.ui.chart;

/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ------------------
 * PieChartDemo7.java
 * ------------------
 * (C) Copyright 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: PieChartDemo7.java,v 1.1 2004/05/26 13:02:03 mungady Exp $
 *
 * Changes
 * -------
 * 25-May-2004 : Version 1 (DG);
 *
 */

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.twocents.exceptions.CoreException;
import com.twocents.report.charts.BuildChart;

/**
 * A demo showing four pie charts.
 */
public class ChartMain extends ApplicationFrame {

	/**
	 * Creates a new demo instance.
	 * 
	 * @param title
	 *            the frame title.
	 * @throws CoreException 
	 */
	public ChartMain(String title) throws CoreException {

		super(title);
		JPanel panel = new JPanel(new GridLayout(1, 1));
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Section 1", 23.3);
		dataset.setValue("Section 2", 56.5);
		dataset.setValue("Section 3", 43.3);
		dataset.setValue("Section 4", 11.1);

		
		int sizeX = 600;
		int sizeY = 600;
		String titleChart = "Test";
		JFreeChart chart=BuildChart.createChartPie(titleChart, dataset);
		
		panel.add(new ChartPanel(chart));
		
		panel.setPreferredSize(new Dimension(sizeX, sizeY));
		setContentPane(panel);
	}

	/**
	 * The starting point for the demo.
	 * 
	 * @param args
	 *            ignored.
	 */
	public static void main(String[] args) {
		ChartMain demo;
		try {
			demo = new ChartMain("Chart");
			demo.pack();
			RefineryUtilities.centerFrameOnScreen(demo);
			demo.setVisible(true);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}

}