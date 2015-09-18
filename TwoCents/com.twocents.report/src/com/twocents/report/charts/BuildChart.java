package com.twocents.report.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.ChartType;
import com.twocents.resources.CoreMessages;

public class BuildChart {

	private static final Logger logger = Logger.getLogger(BuildChart.class);

	public static BufferedImage generateChart(Integer sizeX, Integer sizeY,
			String titleChart, Map<String, Number> map, ChartType type)
			throws CoreException {

		if ((map == null && map.size() <= 0) || type == null) {
			return createFailedImage(sizeX, sizeY);
		}

		BufferedImage bImage = null;
		JFreeChart chart=null;
		logger.debug("Gerando imagem para o relatório");
		try {

			if (ChartType.PIE.equals(type)) {

				DefaultPieDataset dataSet = new DefaultPieDataset();
				Set<String> keySet = map.keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					dataSet.setValue(key, map.get(key));
				}

				chart = createChartPie(titleChart, dataSet);
				
			} else if (ChartType.BAR.equals(type)) {

				DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
				Set<String> keySet = map.keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					dataSet.addValue(map.get(key), key, "CARTEIRA");
				}

				chart = createChartBar(titleChart, dataSet);
			}

		} catch (Exception e) {
			throw new CoreException("Falhou em construir o grafico", e);
		}
		
		bImage = chart.createBufferedImage(sizeX, sizeY);
		
		//bImage = toBufferedImage(chart, sizeX, sizeY);
		
		return bImage;
	}

	public static BufferedImage generateChartTimeSeries(Integer sizeX,
			Integer sizeY, String titleChart, String titleVertical,
			String titleHorizontal, TimeSeriesCollection dataset)
			throws CoreException {
		BufferedImage bImage = null;
		logger.debug("Gerando imagem para o relatório de grafico - "
				+ titleChart);

		try {
			bImage = toBufferedImage(
					createChartTimeSeries(sizeX, sizeY, titleChart,
							titleVertical, titleHorizontal, dataset), sizeX,
					sizeY);

		} catch (Exception e) {
			throw new CoreException("Failed in Generated a Chart", e);
		}
		return bImage;
	}

	public static JFreeChart createChartPie(String titleChart, DefaultPieDataset dataSet) throws CoreException {

		JFreeChart chart = ChartFactory.createPieChart(titleChart, dataSet,
				true, true, false);

		// chart.setBorderVisible(false);
		chart.setBorderPaint(Color.WHITE);
		chart.setAntiAlias(true);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setCircular(true);

		
		plot.setBackgroundPaint(Color.white);
		//plot.setSectionPaint(3, Color.white);		
		plot.setOutlineVisible(false);

		StandardPieItemLabelGenerator standardPieItemLabelGenerator = new StandardPieItemLabelGenerator(
				"{0} {2} ({1})", FormatUtil.DINHEIRO_REAL,
				NumberFormat.getPercentInstance());
		plot.setLabelGenerator(standardPieItemLabelGenerator);

		plot.setToolTipGenerator(new StandardPieItemLabelGenerator());

		plot.setLabelFont(new Font("Verdana", Font.BOLD, 14));
		
		return chart;
	}

	public static JFreeChart createChartBar(String titleChart, DefaultCategoryDataset dataSet)
			throws CoreException {

		JFreeChart chart = ChartFactory.createBarChart(titleChart, "Ativos",
				"Volume", dataSet, PlotOrientation.VERTICAL, true, true, false);
		
		chart.setAntiAlias(true);

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setWeight(500);

		plot.setOutlineVisible(false);
		//plot.setsetLabelFont(new Font("Verdana", Font.BOLD, 14));

		// disable bar outlines...
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		

		return chart;
	}

	public static BufferedImage toBufferedImage(JFreeChart chart, int width,
			int height) {
		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = img.createGraphics();
		chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));
		g2.dispose();
		return img;
	}

	private static JFreeChart createChartTimeSeries(int sizeX, int sizeY,
			String titleChart, String titleVertical, String titleHorizontal,
			XYDataset dataset) throws CoreException {

		JFreeChart chart = ChartFactory.createTimeSeriesChart(titleChart, // title
				titleHorizontal, // x-axis label
				titleVertical, // y-axis label
				dataset, // data
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

		return chart;
	}

	private static BufferedImage createFailedImage(int width, int height)
			throws CoreException {

		logger.info("###Criando uma imagem de falha para mostrar");
		BufferedImage buffer = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = buffer.createGraphics();

		Font font = new Font("Serif", Font.PLAIN, 18);
		g.setFont(font);
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString(CoreMessages.getMessage("IMAGEM_FALHA"), 10, 10);

		return buffer;
	}

}
