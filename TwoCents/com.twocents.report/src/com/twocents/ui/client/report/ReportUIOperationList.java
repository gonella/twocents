package com.twocents.ui.client.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;

import com.twocents.core.util.CoreUtil;
import com.twocents.core.util.DateUtil;
import com.twocents.core.util.FormatUtil;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.ReportGroupType;
import com.twocents.model.ReportParam;
import com.twocents.model.ReportParamInterval;
import com.twocents.model.ReportPropertyType;
import com.twocents.report.jasper.JasperReport;
import com.twocents.report.jasper.dynamic.DynamicJasperReportLayout;
import com.twocents.report.jasper.dynamic.ReportDynamicSimpleTable;
import com.twocents.report.jasper.dynamic.ReportStyle;
import com.twocents.report.resources.ReportMessages;
import com.twocents.ui.client.report.header.HeaderReportCompositeEmpty;

public class ReportUIOperationList extends Report {

	private final Logger logger = Logger.getLogger(ReportUIOperationList.class);
	
	public ReportUIOperationList(Composite parent) {
		super(parent,ReportMessages.getMessage("REPORT_OPERATION_LIST"),ReportGroupType.OPERATION
				,ReportMessages.getMessage("REPORT_OPERATION_LIST_DESCRIPTION")
				
		);
	}

	@Override
	public void buildHeader(Composite showReportFields) {
		setHeader(new HeaderReportCompositeEmpty(this));
		getHeader().setReport(this);

	}	
	
	@Override
	public JasperPrint generateData(Account account,
			HashMap mapParam)
			throws CoreException {
		
		ReportParamInterval param=(ReportParamInterval)mapParam.get(REPORT_PARAM_KEY);
		if(param==null){throw new CoreException(7001);}
		
		List<Map<String, Object>> list = getOperationService()
				.findStockOperationByAccountAndInterval(account,
						param.getDateStart(), param.getDateEnd());
	
		ArrayList<HashMap> array = new ArrayList<HashMap>();
		HashMap<String, String> map;
		for (Map<String, Object> op : list) {
			map = new HashMap<String, String>();

			String stock = op.get("stock") != null ? op.get("stock").toString()
					: "";
			Double price = op.get("price") != null ? (Double) op.get("price")
					: null;
			Integer amount = op.get("amount") != null ? (Integer) op
					.get("amount") : 0;
			String description = op.get("description").toString();
			String tipo = CoreUtil.getOperationType(op.get("type").toString());
			String data = op.get("operationDate").toString();
			
			map.put(ReportPropertyType.ATIVO.toString(), stock);
			map.put(ReportPropertyType.QUANTIDADE.toString(), amount.toString());
			map.put(ReportPropertyType.PRECO.toString(), price.toString());
			map.put(ReportPropertyType.DESCRICAO.toString(), description);
			map.put(ReportPropertyType.TIPO_OPERACAO.toString(), tipo);
			map.put(ReportPropertyType.DATA.toString(), data);
		

			array.add(map);
		}
		return JasperReport.generateDynamicReport(getReportForm(),array, getName());

	}
	
	public DynamicReport getReportForm() throws CoreException{
		
		ReportStyle style=DynamicJasperReportLayout.getStyle();
		
		Style detailStyle = style.getDetailStyle();
		Style headerStyle = style.getHeaderStyle();
		Style titleStyle = style.getTitleStyle();
		Style importeStyle = style.getImporteStyle();
		Style oddRowStyle = style.getOddRowStyle();
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			
		//Once defined, does not change the order, its important to define the group filter.
		list.add(DynamicJasperReportLayout.addRegistry(ReportPropertyType.TIPO_OPERACAO.toString(),"Tipo Operação",50,String.class.getName(),titleStyle));
		list.add(DynamicJasperReportLayout.addRegistry(ReportPropertyType.ATIVO.toString(),"Ativo",50,String.class.getName(),detailStyle));
		list.add(DynamicJasperReportLayout.addRegistry(ReportPropertyType.QUANTIDADE.toString(),"Quantidade",50,String.class.getName(),detailStyle));
		list.add(DynamicJasperReportLayout.addRegistry(ReportPropertyType.PRECO.toString(),"Preço",50,String.class.getName(),detailStyle));
		list.add(DynamicJasperReportLayout.addRegistry(ReportPropertyType.DATA.toString(),"Data",50,String.class.getName(),importeStyle));
		
		ReportDynamicSimpleTable reportForm = new ReportDynamicSimpleTable(getName(),getDescription(),list);	
		
		return reportForm.buildReport();
	}

	
	public HashMap buildParameters() {
		ReportParamInterval param = getReportParam().get(REPORT_PARAM_KEY)!=null?(ReportParamInterval)getReportParam().get(REPORT_PARAM_KEY):null;
		
		if(param==null){
			//Setting the DEFAULT VALUE
			param=new ReportParamInterval(getName(),getGroupType());
			param.setDateStart(FormatUtil.getMinTimestamp(DateUtil.buildDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.JANUARY)));
			param.setDateEnd(FormatUtil.getMaxTimestamp(new Date()));
			
			HashMap map=new HashMap<String,ReportParam>();
			map.put(REPORT_PARAM_KEY, param);
			setReportParam(map);
		}
		
		return getReportParam();
	}

	

}
