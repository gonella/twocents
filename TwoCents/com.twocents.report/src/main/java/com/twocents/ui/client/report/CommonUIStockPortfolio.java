package com.twocents.ui.client.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJGroupLabel;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.LabelPosition;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJGroupVariable;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.CustodyView;
import com.twocents.model.ReportPropertyType;
import com.twocents.report.jasper.dynamic.DJReportLayout;
import com.twocents.report.jasper.dynamic.ReportStyle;
import com.twocents.service.CustodyService;

@SuppressWarnings("all")
class CommonUIStockPortfolio {

	private Double valorInvestido = 0.0;
	private Double valorCorrente = 0.0;
	private CustodyService custodyService;
	private String reportName;
	private String reportDescription;
	private Account selectedAccount;

	public CommonUIStockPortfolio(CustodyService custodyService,
			String reportName, String reportDescription, Account selectedAccount) {
		this.custodyService = custodyService;
		this.reportName = reportName;
		this.reportDescription = reportDescription;
		this.selectedAccount = selectedAccount;
	}

	public ArrayList<HashMap> generateData(Account account, HashMap mapParam)
			throws CoreException {

		List<CustodyView> list = custodyService
				.findCustodyViewByAccount(account);

		return mountReportContent(list);

	}

	public ArrayList<HashMap> mountReportContent(List<CustodyView> list) {
		ArrayList<HashMap> array = new ArrayList<HashMap>();
		HashMap map = new HashMap();

		// map=defaultValueForReport();
		// array.add(map);

		int id = 0;

		valorInvestido = 0.0;
		valorCorrente = 0.0;

		for (CustodyView custody : list) {

			map = new HashMap();

			String ativo = custody.getStock();
			Integer quantidade = custody.getAmount();
			Double precoMedioDouble = custody.getAveragePrice();
			Double precoMedio = precoMedioDouble;
			String tipo = "";

			Double cotacaoDouble = custody.getCurrentPrice() != null ? custody
					.getCurrentPrice() : new Double(0);

			Double rentabilidadeDouble = cotacaoDouble > 0 ? (cotacaoDouble - precoMedioDouble)
					/ precoMedioDouble
					: 0.0;

			Double total = quantidade * precoMedioDouble;

			Double valorPosicao = quantidade * cotacaoDouble;

			map.put(ReportPropertyType.TIPO_ATIVO.toString(), tipo);
			map.put(ReportPropertyType.ATIVO.toString(), ativo);
			map.put(ReportPropertyType.QUANTIDADE.toString(), quantidade
					.toString());
			map.put(ReportPropertyType.PRECO.toString(), precoMedio);

			map.put(ReportPropertyType.VALOR_TOTAL.toString(), total);
			map.put(ReportPropertyType.COTACAO.toString(), cotacaoDouble);
			map.put(ReportPropertyType.VALOR_POSICAO.toString(), valorPosicao);
			map.put(ReportPropertyType.RENTABILIDADE.toString(),
					rentabilidadeDouble);

			array.add(map);

			id++;

			valorInvestido += total;
			valorCorrente += valorPosicao;
		}

		return array;
	}

	public DJReportLayout getReportForm() throws CoreException {

		DJReportLayout reportLayout = new DJReportLayout(reportName,
				reportDescription, selectedAccount.getUser().getName());
		ReportStyle style = reportLayout.getStyle();
		Style detailStyle = style.getDetailStyle();
		Style headerStyle = style.getHeaderStyle();
		Style textColumnContentAtivo = style.getTextColumnContentAtivo();

		Style groupOne = style.getGroupOne();
		groupOne.setHorizontalAlign(HorizontalAlign.RIGHT);
		// groupOne.setVerticalAlign(VerticalAlign.MIDDLE);

		Style headerSum = style.getHeaderSum();

		AbstractColumn columnTipoAtivo = reportLayout.createColumn(
				ReportPropertyType.TIPO_ATIVO.toString(), "Tipo", 40,
				String.class.getName(), detailStyle, groupOne);

		AbstractColumn columnAtivo = reportLayout.createColumn(
				ReportPropertyType.ATIVO.toString(), "Ativo", 50, String.class
						.getName(), headerStyle, textColumnContentAtivo);

		AbstractColumn columnQuant = reportLayout.createColumn(
				ReportPropertyType.QUANTIDADE.toString(), "Quantidade", 50,
				String.class.getName(), headerStyle, detailStyle);

		AbstractColumn columnPreco = reportLayout.createColumn(
				ReportPropertyType.PRECO.toString(), "Preço", 50, Double.class
						.getName(), headerStyle, detailStyle);
		columnPreco.setPattern("R$ #,##0.00");

		AbstractColumn columnVTotal = reportLayout.createColumn(
				ReportPropertyType.VALOR_TOTAL.toString(), "Valor Investido",
				50, Double.class.getName(), headerStyle, detailStyle);
		columnVTotal.setPattern("R$ #,##0.00");

		AbstractColumn columnCotacao = reportLayout.createColumn(
				ReportPropertyType.COTACAO.toString(), "Cotação", 50,
				Double.class.getName(), headerStyle, detailStyle);
		columnCotacao.setPattern("R$ #,##0.00");

		AbstractColumn columnPosicao = reportLayout.createColumn(
				ReportPropertyType.VALOR_POSICAO.toString(),
				"Valor da Posição", 50, Double.class.getName(), headerStyle,
				detailStyle);
		columnPosicao.setPattern("R$ #,##0.00");

		AbstractColumn columnRent = reportLayout.createColumn(
				ReportPropertyType.RENTABILIDADE.toString(), "Rent(%)", 50,
				Double.class.getName(), headerStyle, detailStyle);
		columnRent.setPattern("#,##0.00 %");

		GroupBuilder gb1 = new GroupBuilder();

		Style glabelStyle = new StyleBuilder(false).setFont(Font.ARIAL_SMALL)
				.setHorizontalAlign(HorizontalAlign.RIGHT).setBorderTop(
						Border.THIN).setStretchWithOverflow(false).build();
		DJGroupLabel glabel1 = new DJGroupLabel("Total", glabelStyle,
				LabelPosition.TOP);
		DJGroupLabel glabel2 = new DJGroupLabel("Total", glabelStyle,
				LabelPosition.TOP);

		// Definicao da expression para calculo da rentbilidade total da
		// carteira
		RentabilidadeExpression valueExpression = new RentabilidadeExpression(
				(valorCorrente - valorInvestido) / valorInvestido);

		DJGroupLabel glabel3 = new DJGroupLabel("Total", glabelStyle,
				LabelPosition.TOP);
		DJGroupVariable djg = new DJGroupVariable(columnRent, valueExpression,
				headerSum);
		djg.setLabel(glabel3);

		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnTipoAtivo)
				.addFooterVariable(columnVTotal, DJCalculation.SUM, headerSum,
						null, glabel1) // tell the group place a variable footer
				// of the column "columnAmount" with the
				// SUM of allvalues of the columnAmount
				// in this group.
				.addFooterVariable(columnPosicao, DJCalculation.SUM, headerSum,
						null, glabel2).addFooterVariable(djg).setGroupLayout(
						GroupLayout.VALUE_IN_HEADER) // tells the group
				// how to be shown, there are manyposibilities, see the
				// GroupLayout for more.
				.build();

		reportLayout.addColumn(columnTipoAtivo);
		reportLayout.addColumn(columnAtivo);
		reportLayout.addColumn(columnQuant);
		reportLayout.addColumn(columnPreco);
		reportLayout.addColumn(columnVTotal);
		reportLayout.addColumn(columnCotacao);
		reportLayout.addColumn(columnPosicao);
		reportLayout.addColumn(columnRent);

		reportLayout.addGroup(g1); // add group g1

		return reportLayout;
	}

	private DynamicReport createLevel2Subreport() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb.addColumn(
				ReportPropertyType.TIPO_ATIVO.toString(),
				ReportPropertyType.TIPO_ATIVO.toString(), Date.class.getName(),
				100).addColumn("Average", "average", Float.class.getName(), 50)
				.addColumn("%", "percentage", Float.class.getName(), 50)
				.addColumn("Amount", "amount", Float.class.getName(), 50)
				.addGroups(1).addField("dummy3", Collection.class.getName())
				.setMargins(5, 5, 20, 20).setUseFullPageWidth(true).setTitle(
						"Level 2 Subreport").build();
		return dr;
	}

	@SuppressWarnings("all")
	private class RentabilidadeExpression implements CustomExpression {

		private Double averageValue;

		public RentabilidadeExpression(Double averageValue) {
			this.averageValue = averageValue;
		}

		@Override
		public Object evaluate(Map fields, Map variables, Map parameters) {
			return averageValue;
		}

		@Override
		public String getClassName() {
			return Double.class.getName();
		}

	}

}
