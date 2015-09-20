package com.twocents.ui.client.report;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;
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

import com.twocents.core.util.DateUtil;
import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.BrokerageByMonth;
import com.twocents.model.BrokerageNote;
import com.twocents.model.BrokerageNoteItem;
import com.twocents.model.OperationType;
import com.twocents.model.ReportGroupType;
import com.twocents.model.ReportParamInterval;
import com.twocents.model.ReportPropertyType;
import com.twocents.report.jasper.JasperReport;
import com.twocents.report.jasper.dynamic.DJReportLayout;
import com.twocents.report.jasper.dynamic.ReportStyle;
import com.twocents.report.resources.ReportMessages;
import com.twocents.ui.client.report.header.HeaderReportFilterByDatesComposite;

public class ReportUIBrokerageNote extends Report {

	private final Logger logger = Logger.getLogger(ReportUIBrokerageNote.class);

	public ReportUIBrokerageNote(Composite parent) {
		super(parent,ReportMessages.getMessage("REPORT_BROKERAGE_NOTE"),ReportGroupType.TAX, ReportMessages.getMessage("REPORT_BROKERAGE_NOTE_DESCRIPTION"));
	}

	/**
	 * A lista de entrada precisa estar ordenada na ASC
	 * 
	 * @param listBrokerageNote
	 * @return
	 */
	private List<BrokerageByMonth> groupByMonth(
			List<BrokerageNote> listBrokerageNote) {

		List<BrokerageByMonth> listBrokerageByMonth = new ArrayList<BrokerageByMonth>();

		String dateMonthAndYear = null;
		String dateMonthAndYearOld = null;

		BrokerageByMonth brokerageByMonthTmp = null;
		for (int i = 0; i < listBrokerageNote.size(); i++) {
			BrokerageNote brokerageNote = listBrokerageNote.get(i);

			dateMonthAndYear = FormatUtil
					.formatDateByYearAndMonth(brokerageNote.getDate());

			if (!dateMonthAndYear.equals(dateMonthAndYearOld)) {

				if (brokerageByMonthTmp != null) {
					listBrokerageByMonth.add(brokerageByMonthTmp);
				}

				brokerageByMonthTmp = new BrokerageByMonth(FormatUtil
						.parseDateByYearAndMonth(dateMonthAndYear));
			}

			if (brokerageByMonthTmp != null) {
				brokerageByMonthTmp.addBrokerageNote(brokerageNote);

			}

			dateMonthAndYearOld = dateMonthAndYear;

			if (i == listBrokerageNote.size() - 1) {
				listBrokerageByMonth.add(brokerageByMonthTmp);
			}

		}
		for (Iterator iterator = listBrokerageByMonth.iterator(); iterator
				.hasNext();) {
			BrokerageByMonth brokerageByMonth = (BrokerageByMonth) iterator.next();
			logger.info(brokerageByMonth);
		}

		return listBrokerageByMonth;
	}

	public JasperPrint generateData(Account account, HashMap mapParam)	throws CoreException {
		
		Timestamp startDate;
		Timestamp endDate;
		try {
			ReportParamInterval param = (ReportParamInterval) mapParam.get(REPORT_PARAM_KEY);
			
			startDate = param.getDateStart();
			endDate = param.getDateEnd();
			
		} catch (Exception e) {			
			throw new CoreException(7001);
		}
		
		String month = null;
		String year = null;

		Collection<HashMap> array = new ArrayList<HashMap>();
		
		HashMap map = new HashMap();
		
		logger.info("Consultando notas de corretagem no periodo ["+ (new Date(startDate.getTime())) + ","+ (new Date(endDate.getTime())) + "]");

		List<BrokerageNote> listAllBrokerageNote = getBrokerageNoteService().findBrokerageNoteByDateWithDateOrderedAsc(account, startDate,endDate);
		
		if (!CollectionUtils.isEmpty(listAllBrokerageNote)) {
			logger.info("Listando notas de corretagem[Size: "+ listAllBrokerageNote.size() + "] no periodo ["
					+ (new Date(startDate.getTime())) + ","
					+ (new Date(endDate.getTime())) + "]");
			List<BrokerageByMonth> groupByMonth = groupByMonth(listAllBrokerageNote);

			// Invertendo a ordem da lista, as corretagens mais novas devem
			// aparecer
			// List<BrokerageByMonth> listBrokerageByMonth=new
			// ArrayList<BrokerageByMonth>(collectionBrokerageByMonth);
			//Collections.reverse(groupByMonth);

			for (Iterator iterator = groupByMonth.iterator(); iterator
					.hasNext();) {
				BrokerageByMonth brokerageByMonth = (BrokerageByMonth) iterator
						.next();

				String key = FormatUtil
						.formatDateByYearAndMonth(brokerageByMonth
								.getDateYearAndMonth());

				Date date = FormatUtil.parseDateByYearAndMonth(key);
				Calendar instance = Calendar.getInstance();
				instance.setTime(date);

				month = DateUtil.getMonthName(
						new Integer(instance.get(instance.MONTH))).toString();
				year = new Integer(instance.get(instance.YEAR)).toString();

				// Invertendo a ordem da lista, as corretagens mais novas devem aparecer
				List<BrokerageNote> listBrokerageNote = brokerageByMonth.getListNote();
				//Collections.reverse(listBrokerageNote);

				if (!CollectionUtils.isEmpty(listBrokerageNote)) {

					for (Iterator iterator2 = listBrokerageNote.iterator(); iterator2
							.hasNext();) {

						BrokerageNote brokerageNote = (BrokerageNote) iterator2
								.next();

						logger.info(brokerageNote);

						String data = FormatUtil.formatDate(brokerageNote
								.getDate());

						Set<BrokerageNoteItem> brokerageItemNoteList = brokerageNote.getBrokerageItemNoteList();

						for (Iterator iterator3 = brokerageItemNoteList
								.iterator(); iterator3.hasNext();) {
							BrokerageNoteItem brokerageItemNote = (BrokerageNoteItem) iterator3
									.next();
							logger.info("--"+brokerageItemNote);

							map = new HashMap();

							String hora = FormatUtil
									.formatTime(brokerageItemNote
											.getOperation().getOperationDate());
							String ativo = brokerageItemNote.getStockCode();
							OperationType operationType = brokerageItemNote
									.getOperationType();

							Integer quantidade = brokerageItemNote.getAmount();
							String preco = FormatUtil
									.toReal(brokerageItemNote
											.getPrice());

							String daytrade = FormatUtil
									.formatDayTrade(brokerageItemNote
											.isDayTrade());

							Double corretagem = new Double(FormatUtil
									.formatDouble(brokerageItemNote
											.getCorretagem()));
							Double emolumento = new Double(FormatUtil
									.formatDouble(brokerageItemNote
											.getEmolumentos()));
							Double taxaDeLiquidacao = new Double(FormatUtil
									.formatDouble(brokerageItemNote
											.getTaxaDeLiquidacao()));
							Double iss = new Double(FormatUtil
									.formatDouble(brokerageItemNote.getISS()));

							map.put(ReportPropertyType.ANO.toString(), month+ "/" +year );
							map.put(ReportPropertyType.MES.toString(), month);
							map.put(ReportPropertyType.DATA.toString(), data);

							map.put(ReportPropertyType.HORA.toString(), hora);

							map.put(ReportPropertyType.ATIVO.toString(), ativo);
							map
									.put(ReportPropertyType.TIPO_OPERACAO
											.toString(), operationType
											.toString());

							map.put(ReportPropertyType.QUANTIDADE.toString(),
									quantidade);
							map.put(ReportPropertyType.PRECO_MEDIO.toString(),
									preco);
							map.put(ReportPropertyType.DAYTRADE.toString(),
									daytrade);

							map.put(ReportPropertyType.CORRETAGEM.toString(),
									corretagem);
							map.put(ReportPropertyType.EMOLUMENTOS.toString(),
									emolumento);
							map.put(ReportPropertyType.TAXA_DE_LIQUIDACAO
									.toString(), taxaDeLiquidacao);
							map.put(ReportPropertyType.ISS.toString(), iss);

							array.add(map);
						}

					}
				}

			}
		} else {
			logger.info("Notas ["+(listAllBrokerageNote!=null?listAllBrokerageNote.size():0)+"]");
			//throw new CoreException(7003);
		}

		return JasperReport.generateDynamicReport(getReportForm(), array,
				getName());

	}

	public DynamicReport getReportForm() throws CoreException {

		DJReportLayout reportLayout = new DJReportLayout(getName(),
				getDescription(),getAccountSelected().getUser().getName());
		ReportStyle style = reportLayout.getStyle();
		Style detailStyle = style.getDetailStyle();
		Style headerStyle = style.getHeaderStyle();
		Style groupOne = style.getGroupOne();
		Style groupTwo = style.getGroupTwo();
		Style importeStyle = style.getImporteStyle();
		Style headerSum = style.getHeaderSum();

		AbstractColumn columnAno = reportLayout.createColumn(
				ReportPropertyType.ANO.toString(), "Ano", 40, String.class
						.getName(), detailStyle, groupOne);
		// reportLayout.addColumn(ReportPropertyType.MES.toString(),"Mês",80,String.class.getName(),titleStyle,true,GroupLayout.VALUE_IN_HEADER));
		AbstractColumn columnData = reportLayout.createColumn(
				ReportPropertyType.DATA.toString(), "Data", 65, String.class
						.getName(), headerStyle, groupTwo);

		AbstractColumn columnHora = reportLayout.createColumn(
				ReportPropertyType.HORA.toString(), "Hora", 50, String.class
						.getName(), headerStyle, detailStyle);

		AbstractColumn columnAtivo = reportLayout.createColumn(
				ReportPropertyType.ATIVO.toString(), "Ativo", 50, String.class
						.getName(), headerStyle, detailStyle);
		AbstractColumn columnTipoOperacao = reportLayout.createColumn(
				ReportPropertyType.TIPO_OPERACAO.toString(), "Tipo", 50,
				String.class.getName(), headerStyle, detailStyle);

		AbstractColumn columnQuant = reportLayout.createColumn(
				ReportPropertyType.QUANTIDADE.toString(), "Quant", 50,
				Integer.class.getName(), headerStyle, detailStyle);
		AbstractColumn createColumn3 = reportLayout.createColumn(
				ReportPropertyType.PRECO_MEDIO.toString(), "Preço Médio", 40,
				String.class.getName(), headerStyle, detailStyle);
		// AbstractColumn createColumn4 =
		// reportLayout.createColumn(ReportPropertyType.PRECO_VENDA.toString(),"Preço Venda",40,String.class.getName(),headerStyle,importeStyle);
		AbstractColumn createColumn5 = reportLayout.createColumn(
				ReportPropertyType.DAYTRADE.toString(), "DayTrade", 50,
				String.class.getName(), headerStyle, importeStyle);
		
		AbstractColumn columnCorretagem = reportLayout.createColumn(
				ReportPropertyType.CORRETAGEM.toString(), "Corretagem", 50,
				Double.class.getName(), headerStyle, importeStyle);
		columnCorretagem.setPattern("R$ #,##0.00");

		AbstractColumn columnEmolumentos = reportLayout.createColumn(
				ReportPropertyType.EMOLUMENTOS.toString(), "Emolu.", 50,
				Double.class.getName(), headerStyle, importeStyle);
		columnEmolumentos.setPattern("R$ #,##0.00");
		
		AbstractColumn columnTaxaLiquidacao = reportLayout.createColumn(
				ReportPropertyType.TAXA_DE_LIQUIDACAO.toString(),
				"Taxa de Liq.", 50, Double.class.getName(), headerStyle,
				importeStyle);
		columnTaxaLiquidacao.setPattern("R$ #,##0.00");

		AbstractColumn columnISS = reportLayout.createColumn(
				ReportPropertyType.ISS.toString(), "ISS", 50, Double.class
						.getName(), headerStyle, importeStyle);
		columnISS.setPattern("R$ #,##0.00");

		GroupBuilder gb1 = new GroupBuilder();

		Style glabelStyle = new StyleBuilder(false).setFont(Font.ARIAL_SMALL)
				.setHorizontalAlign(HorizontalAlign.RIGHT).setBorderTop(
						Border.THIN).setStretchWithOverflow(false).build();
		DJGroupLabel glabel1 = new DJGroupLabel("Total", glabelStyle,
				LabelPosition.TOP);
		DJGroupLabel glabel2 = new DJGroupLabel("Total", glabelStyle,
				LabelPosition.TOP);
		DJGroupLabel glabel3 = new DJGroupLabel("Total", glabelStyle,
				LabelPosition.TOP);
		DJGroupLabel glabel4 = new DJGroupLabel("Total", glabelStyle,
				LabelPosition.TOP);

		// define the criteria column to group by (columnState)
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnAno)
				.addFooterVariable(columnCorretagem, DJCalculation.SUM,
						headerSum, null, glabel1) // tell the group place a
													// variable footer of the
													// column "columnAmount"
													// with the SUM of allvalues
													// of the columnAmount in
													// this group.
				.addFooterVariable(columnEmolumentos, DJCalculation.SUM,
						headerSum, null, glabel2) // idem for the
													// columnaQuantity column
				.addFooterVariable(columnTaxaLiquidacao, DJCalculation.SUM,
						headerSum, null, glabel3) // idem for the
													// columnaQuantity column
				.addFooterVariable(columnISS, DJCalculation.SUM, headerSum,
						null, glabel4) // idem for the columnaQuantity column

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
		DJGroup g2 = gb2.setCriteriaColumn((PropertyColumn) columnData) // and
																		// we
																		// add
																		// the
																		// same
																		// operations
																		// for
																		// the
																		// columnAmount
																		// and
				.addFooterVariable(columnCorretagem, DJCalculation.SUM,
						headerSum, null, glabel1) // tell the group place a
													// variable footer of the
													// column "columnAmount"
													// with the SUM of allvalues
													// of the columnAmount in
													// this group.
				.addFooterVariable(columnEmolumentos, DJCalculation.SUM,
						headerSum, null, glabel2) // idem for the
													// columnaQuantity column
				.addFooterVariable(columnTaxaLiquidacao, DJCalculation.SUM,
						headerSum, null, glabel3) // idem for the
													// columnaQuantity column
				.addFooterVariable(columnISS, DJCalculation.SUM, headerSum,
						null, glabel4) // idem for the columnaQuantity column

				.build();

		reportLayout.addColumn(columnAno);
		reportLayout.addColumn(columnData);
		reportLayout.addColumn(columnHora);
		reportLayout.addColumn(columnAtivo);
		reportLayout.addColumn(columnTipoOperacao);
		reportLayout.addColumn(columnQuant);
		reportLayout.addColumn(createColumn3);
		// reportLayout.addColumn(createColumn4);
		reportLayout.addColumn(createColumn5);
		reportLayout.addColumn(columnCorretagem);
		reportLayout.addColumn(columnEmolumentos);
		reportLayout.addColumn(columnTaxaLiquidacao);
		reportLayout.addColumn(columnISS);

		reportLayout.addGroup(g1); // add group g1
		reportLayout.addGroup(g2); // add group g2

		return reportLayout.build();
	}

	@Override
	public void buildHeader(Composite parentComposite) {
		setHeader(new HeaderReportFilterByDatesComposite(getHeaderComposite()));
		getHeader().setReport(this);
	}
}
