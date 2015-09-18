package com.twocents.ui.client.report;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.springframework.util.CollectionUtils;

import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJGroupLabel;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.LabelPosition;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import com.twocents.bovespa.TradeProcess;
import com.twocents.core.common.CommonAction;
import com.twocents.core.util.DateUtil;
import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.BrokerageNote;
import com.twocents.model.Income;
import com.twocents.model.ReportGroupType;
import com.twocents.model.ReportParam;
import com.twocents.model.ReportParamInterval;
import com.twocents.model.ReportPropertyType;
import com.twocents.model.StockType;
import com.twocents.report.jasper.JasperReport;
import com.twocents.report.jasper.dynamic.DJReportLayout;
import com.twocents.report.jasper.dynamic.ReportStyle;
import com.twocents.report.resources.ReportMessages;
import com.twocents.ui.client.report.header.HeaderReportFilterByDatesComposite;

@SuppressWarnings("unchecked")
public class ReportUITradeResult extends Report {

	private final Logger logger = Logger.getLogger(ReportUITradeResult.class);

	public ReportUITradeResult(Composite parent) {
		super(parent,ReportMessages.getMessage("REPORT_TRADE_RESULT"),
				ReportGroupType.OPERATION, ReportMessages
						.getMessage("REPORT_TRADE_RESULT_DESCRIPTION"));

	}

	public JasperPrint generateData(Account account, HashMap mapParam)
			throws CoreException {

		ReportParamInterval param = null;
		Timestamp startDate;			
		Timestamp endDate;

		try {
			param = (ReportParamInterval) mapParam.get(REPORT_PARAM_KEY);
			
			startDate = param.getDateStart();
			endDate = param.getDateEnd();
			
			if(startDate==null || endDate==null){
				throw new Exception("As datas informadas são inválidas");
			}
			
		} catch (Exception e) {
			throw new CoreException(7001,e);
		}

		ArrayList<HashMap> array = new ArrayList<HashMap>();
		HashMap map = new HashMap();

		logger.info("Consultando notas de corretagem no periodo ["+ (new Date(startDate.getTime())) + ","+ (new Date(endDate.getTime())) + "]");

		List<BrokerageNote> listAllBrokerageNote = getBrokerageNoteService().findBrokerageNoteByDateWithDateOrderedAsc(account,startDate, endDate);

		if (CollectionUtils.isEmpty(listAllBrokerageNote)) {
			throw new CoreException(7005); 
		}

		logger.info("Notas de corretagens encontradas ["+ listAllBrokerageNote.size() + "] no intervalo["+ param.getDateStart() + " - " + param.getDateEnd() + "]");

		if (CollectionUtils.isEmpty(listAllBrokerageNote)) {
			throw new CoreException(7003);
		}

		Map<String, Income> mapMonthIncome = new TradeProcess().findMonthIncome(listAllBrokerageNote);
		
		Set<String> keySet = mapMonthIncome.keySet();
		if (CollectionUtils.isEmpty(keySet)) {
			throw new CoreException(7004);
		}
		logger.info("Numero de Meses Criados [" + mapMonthIncome.size() + "]");

		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Income income = mapMonthIncome.get(key);
			
			//income.calculateTax();
			
			map = new HashMap();

			Calendar calendar=Calendar.getInstance();
			calendar.setTime(income.getDate());
			String month = DateUtil.getMonthName(new Integer(calendar.get(calendar.MONTH))).toString();
			String year = new Integer(calendar.get(calendar.YEAR)).toString();

			//String mesAno = key;
			String mesAno = month+"/"+year;
			
			String tipoAtivo = StockType.SHARE.toString();
			String tipoDeTrade = "NORMAL";

			Double lucro = FormatUtil.formatDouble(income.getLucro());
			Double prejuizo = FormatUtil.formatDouble(income.getPrejuizo());

			Double corretagem=FormatUtil.formatDouble(income.getCorretagem());
			Double taxaRetida=FormatUtil.formatDouble(income.getTaxasRetidas());
			Double impostoASerPago=FormatUtil.formatDouble(income.getImpostoAPagar());
			
			map.put(ReportPropertyType.MES_E_ANO.toString(), mesAno);
			map.put(ReportPropertyType.TIPO_ATIVO.toString(), tipoAtivo);
			map.put(ReportPropertyType.TIPO_DE_TRADE.toString(), tipoDeTrade);

			map.put(ReportPropertyType.LUCRO.toString(), lucro);
			map.put(ReportPropertyType.PREJUIZO.toString(), prejuizo);

			map.put(ReportPropertyType.CORRETAGEM.toString(), corretagem);
			map.put(ReportPropertyType.IMPOSTO_A_SER_PAGO.toString(),impostoASerPago);
			map.put(ReportPropertyType.TAXAS_RETIDAS.toString(), taxaRetida);

			array.add(map);

			map = new HashMap();

			tipoDeTrade = "DAYTRADE";

			Double lucroDaytrade = FormatUtil
					.formatDouble(income.getLucroDayTrade());
			Double prejuizoDaytrade = FormatUtil
					.formatDouble(income.getPrejuizoDayTrade());

			Double corretagemDaytrade=FormatUtil.formatDouble(income.getCorretagemDayTrade());
			Double taxaRetidaDaytrade=FormatUtil.formatDouble(income.getTaxasRetidasDayTrade());
			Double impostoASerPagoDayTrade=FormatUtil.formatDouble(income.getImpostoAPagarDayTrade());
			
			map.put(ReportPropertyType.MES_E_ANO.toString(), mesAno);
			map.put(ReportPropertyType.TIPO_ATIVO.toString(), tipoAtivo);
			map.put(ReportPropertyType.TIPO_DE_TRADE.toString(), tipoDeTrade);

			map.put(ReportPropertyType.LUCRO.toString(), lucroDaytrade);
			map.put(ReportPropertyType.PREJUIZO.toString(), prejuizoDaytrade);

			map.put(ReportPropertyType.CORRETAGEM.toString(), corretagemDaytrade);
			map.put(ReportPropertyType.IMPOSTO_A_SER_PAGO.toString(), impostoASerPagoDayTrade);
			map.put(ReportPropertyType.TAXAS_RETIDAS.toString(), taxaRetidaDaytrade);

			array.add(map);
		}

		JasperPrint jasperPrint = JasperReport.generateDynamicReport(
				getReportForm(), array, getName());

		return jasperPrint;
	}

	public DynamicReport getReportForm() throws CoreException {

		/*
		 * Mes/Ano A vista Normal LUCRO, Prejuizos,IR(ser pago), IR
		 * retido,Corretagem Day trade LUCRO, Prejuizos,Impostos(A ser pago),
		 * Imposto retido,Corretagem Opções
		 */

		DJReportLayout reportLayout = new DJReportLayout(getName(),
				getDescription(),getAccountSelected().getUser().getName());
		
		ReportStyle style = reportLayout.getStyle();
		Style detailStyle = style.getDetailStyle();
		Style headerStyle = style.getHeaderStyle();
		Style groupOne = style.getGroupOne();
		Style groupTwo = style.getGroupTwo();
		Style headerSum = style.getHeaderSum();
		Style importeStyle = style.getImporteStyle();

		AbstractColumn columnAno = reportLayout.createColumn(
				ReportPropertyType.MES_E_ANO.toString(), "Mês e Ano", 40,
				String.class.getName(), detailStyle, groupOne);
		AbstractColumn columnTipoAtivo = reportLayout.createColumn(
				ReportPropertyType.TIPO_ATIVO.toString(), "Mercado", 40,
				String.class.getName(), detailStyle, groupTwo);
		AbstractColumn columnDaytrade = reportLayout.createColumn(
				ReportPropertyType.TIPO_DE_TRADE.toString(), "Tipo", 50,
				String.class.getName(), headerStyle, detailStyle);

		AbstractColumn columnLucro = reportLayout.createColumn(
				ReportPropertyType.LUCRO.toString(), "Lucro", 50, Double.class
						.getName(), headerStyle, importeStyle);
		columnLucro.setPattern("R$ #,##0.00");

		AbstractColumn columnPrejuizo = reportLayout.createColumn(
				ReportPropertyType.PREJUIZO.toString(), "Prejuizo", 50,
				Double.class.getName(), headerStyle, importeStyle);
		columnPrejuizo.setPattern("R$ #,##0.00");

		AbstractColumn columnCorretagem = reportLayout.createColumn(
				ReportPropertyType.CORRETAGEM.toString(), "Corretagem", 50,
				Double.class.getName(), headerStyle, importeStyle);
		AbstractColumn columnIRPago = reportLayout.createColumn(
				ReportPropertyType.IMPOSTO_A_SER_PAGO.toString(), "Imposto a Ser Pago",
				50, Double.class.getName(), headerStyle, importeStyle);
		AbstractColumn columnTaxasRetidas = reportLayout.createColumn(
				ReportPropertyType.TAXAS_RETIDAS.toString(), "Taxas Retidas", 50,
				Double.class.getName(), headerStyle, importeStyle);

		GroupBuilder gb1 = new GroupBuilder();

		Style glabelStyle = new StyleBuilder(false).setFont(Font.ARIAL_SMALL)
				.setHorizontalAlign(HorizontalAlign.RIGHT).setBorderTop(
						Border.THIN).setStretchWithOverflow(false).build();
		DJGroupLabel glabel1 = new DJGroupLabel("Total", glabelStyle,
				LabelPosition.TOP);
		DJGroupLabel glabel2 = new DJGroupLabel("Total", glabelStyle,
				LabelPosition.TOP);

		// define the criteria column to group by (columnState)
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnAno)
				.addFooterVariable(columnLucro, DJCalculation.SUM, headerSum,
						null, glabel1) // tell the group place a variable footer
										// of the column "columnAmount" with the
										// SUM of allvalues of the columnAmount
										// in this group.
				.addFooterVariable(columnPrejuizo, DJCalculation.SUM,
						headerSum, null, glabel2) // tell the group place a
													// variable footer of the
													// column "columnAmount"
													// with the SUM of allvalues
													// of the columnAmount in
													// this group.

				.setGroupLayout(GroupLayout.VALUE_IN_HEADER) // tells the group
																// how to be
																// shown, there
																// are
																// manyposibilities,
																// see the
																// GroupLayout
																// for more.
				.build();

		GroupBuilder gb2 = new GroupBuilder(); // Create another group (using
												// another column as criteria)
		DJGroup g2 = gb2.setCriteriaColumn((PropertyColumn) columnTipoAtivo) // and
																				// we
																				// add
																				// the
																				// same
																				// operations
																				// for
																				// the
																				// columnAmount
																				// and
				.addFooterVariable(columnLucro, DJCalculation.SUM, headerSum,
						null, glabel1) // tell the group place a variable footer
										// of the column "columnAmount" with the
										// SUM of allvalues of the columnAmount
										// in this group.
				.addFooterVariable(columnPrejuizo, DJCalculation.SUM,
						headerSum, null, glabel2) // tell the group place a
													// variable footer of the
													// column "columnAmount"
													// with the SUM of allvalues
													// of the columnAmount in
													// this group.

				.build();

		reportLayout.addColumn(columnAno);
		reportLayout.addColumn(columnTipoAtivo);
		reportLayout.addColumn(columnDaytrade);
		reportLayout.addColumn(columnLucro);
		reportLayout.addColumn(columnPrejuizo);
		reportLayout.addColumn(columnCorretagem);
		reportLayout.addColumn(columnTaxasRetidas);
		reportLayout.addColumn(columnIRPago);


		reportLayout.addGroup(g1); // add group g1
		reportLayout.addGroup(g2); // add group g2

		return reportLayout.build();
	}

	@Override
	public void buildHeader(Composite showReportFields) {
		setHeader(new HeaderReportFilterByDatesComposite(getHeaderComposite()));
		getHeader().setReport(this);
	}

	/*public HashMap buildParameters() {
		ReportParamInterval param = getReportParam().get(REPORT_PARAM_KEY) != null ? (ReportParamInterval) getReportParam()
				.get(REPORT_PARAM_KEY)
				: null;

		if (param == null) {
			// Setting the DEFAULT VALUE
			param = new ReportParamInterval(getName(), getGroupType());
			param.setDateStart(FormatUtil.getMinTimestamp(DateUtil
					.buildDate(Calendar.getInstance().get(Calendar.YEAR),
							Calendar.JANUARY)));
			param.setDateEnd(FormatUtil.getMaxTimestamp(new Date()));

			HashMap map = new HashMap<String, ReportParam>();
			map.put(REPORT_PARAM_KEY, param);
			setReportParam(map);
		}

		return getReportParam();
	}*/

}
