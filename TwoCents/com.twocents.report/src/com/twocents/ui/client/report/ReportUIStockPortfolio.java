package com.twocents.ui.client.report;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.builders.ChartBuilderException;
import ar.com.fdvs.dj.domain.chart.DJChart;
import ar.com.fdvs.dj.domain.chart.DJChartOptions;
import ar.com.fdvs.dj.domain.chart.builder.DJPieChartBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.CustodyView;
import com.twocents.model.ReportGroupType;
import com.twocents.report.jasper.JasperReport;
import com.twocents.report.jasper.dynamic.DJReportLayout;
import com.twocents.report.resources.ReportMessages;

@SuppressWarnings("unchecked")
public class ReportUIStockPortfolio extends Report {

	private final Logger logger = Logger
			.getLogger(ReportUIStockPortfolio.class);
	private CommonUIStockPortfolio commonReport;

	public ReportUIStockPortfolio(Composite parent) {
		super(
				parent,
				ReportMessages
						.getMessage("REPORT_FOR_STOCK_PORTFOLIO_COMPLETE"),
				ReportGroupType.ACCOUNT,
				ReportMessages
						.getMessage("REPORT_FOR_STOCK_PORTFOLIO_COMPLETE_DESCRIPTION"));
	}

	public JasperPrint generateData(Account account, HashMap mapParam)
			throws CoreException {

		Account ac = getAccountSelected();
		logger
				.info("Construindo o report de custodias e rentabilidade da carteira do cliente: "
						+ ac.getName());

		List<CustodyView> list = getCustodyService().findCustodyViewByAccount(
				account);

		commonReport = new CommonUIStockPortfolio(getCustodyService(),
				ReportMessages.getMessage("REPORT_FOR_STOCK_PORTFOLIO_COMPLETE"),
				ReportMessages
						.getMessage("REPORT_FOR_STOCK_PORTFOLIO_COMPLETE_DESCRIPTION"),
				ac);

		ArrayList<HashMap> array = commonReport.mountReportContent(list);

		return JasperReport.generateDynamicReport(getReportForm(), array,
				getName());
	}

	@SuppressWarnings("serial")
	public DynamicReport getReportForm() throws CoreException {

		DJReportLayout layout = commonReport.getReportForm();

		DJChart djChart;
		try {
			djChart = new DJPieChartBuilder()
					// chart
					// .setX(20)
					.setY(10).setWidth(500).setHeight(250).setCentered(true)

					.setBackColor(Color.LIGHT_GRAY).setShowLegend(true)
					.setPosition(DJChartOptions.POSITION_FOOTER).setTitle(
							new StringExpression() {
								public Object evaluate(Map fields,
										Map variables, Map parameters) {
									return variables.get("group_state_name");
								}
							}).setTitleColor(Color.DARK_GRAY).setTitleFont(
							Font.ARIAL_BIG_BOLD).setSubtitle(
							"Composição da Carteira").setSubtitleColor(
							Color.DARK_GRAY).setSubtitleFont(
							Font.COURIER_NEW_BIG_BOLD).setLegendColor(
							Color.DARK_GRAY).setLegendFont(
							Font.COURIER_NEW_MEDIUM_BOLD)
					.setLegendBackgroundColor(Color.WHITE).setLegendPosition(
							DJChartOptions.EDGE_BOTTOM).setTitlePosition(
							DJChartOptions.EDGE_TOP).setLineStyle(
							DJChartOptions.LINE_STYLE_DOTTED).setLineWidth(1)
					.setLineColor(Color.DARK_GRAY)
					// .setPadding(5)
					// dataset
					.setKey((PropertyColumn) layout.getColumn(1)).addSerie(
							layout.getColumn(6)).setLabelFormat("{0} - R$ {1}")
					// plot
					.setCircular(true)

					.build();

			layout.addChart(djChart);

		} catch (ChartBuilderException e) {
			throw new CoreException(
					"Não foi possível gerar o gráfico para a carteira", e);
		}

		return layout.build();
	}

	@Override
	public void buildHeader(Composite showReportFields) {
		/*
		 * setHeader(new HeaderReportCompositeEmpty(showReportFields,
		 * SWT.NONE)); getHeader().setReport(this);
		 */
		showReportFields.dispose();
		setHeader(null);

	}

}
