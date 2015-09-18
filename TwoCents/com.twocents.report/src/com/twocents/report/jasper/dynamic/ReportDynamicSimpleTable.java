package com.twocents.report.jasper.dynamic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import com.twocents.exceptions.CoreException;

public class ReportDynamicSimpleTable extends DynamicJasperReportLayout {

	private final String title;
	private final String subTitle;

	public ReportDynamicSimpleTable(String title,String subTitle,
					List<Map<String,Object>> data
	){
		super(data);
		this.title = title;
		this.subTitle = subTitle;
	}
	
	public DynamicReport buildReport() throws CoreException {
	
		DynamicReportBuilder drb = createHeader(getTitle(),getSubTitle());
		
		AbstractColumn columnVector[]=getColumnVectors();
		                       
		List<Map<String, Object>> column = getColumnData();
		
		int i=0;
		
		for (Iterator iterator = column.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			
			Boolean group=(Boolean)map.get(COLUMN_GROUP);
			if(group){
				GroupBuilder gb1 = new GroupBuilder();
				DJGroup g1 = gb1.setCriteriaColumn(
						(PropertyColumn) columnVector[i]) // define the criteria column to group by (columnState)
						//.addFooterVariable(columnAmount, DJCalculation.SUM) // tell the group place a variable in the footer of the column "columnAmount" with the SUM of all values of the columnAmount in this group.
						//.addFooterVariable(columnaQuantity, DJCalculation.SUM) // idem for the columnaQuantity column
						//setGroupLayout(GroupLayout.VALUE_IN_HEADER) // tells the group how to be shown, there are many posibilities, see the GroupLayout for more.
						.build();
				
				drb.addGroup(g1); 
			}			
			i++;
			
		}	
		
		for (AbstractColumn abstractColumn : columnVector) {
			drb.addColumn(abstractColumn);
		}
		
		drb.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();
		
		return dr;
	}
	
	public static List<Map<String,Object>> dataStructure(){
		
		Style groupOne = getStyle().getGroupOne();
		Style groupTwo = getStyle().getGroupTwo();
		
		Style defaultContent = getStyle().getDefaultContent();
		
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			
		//Once defined, does not change the order, its important to define the group filter.
		list.add(addRegistry("state","State",85,String.class.getName(),groupOne,true,GroupLayout.VALUE_IN_HEADER));
		list.add(addRegistry("branch","Branch",85,String.class.getName(),groupTwo,true,GroupLayout.VALUE_IN_HEADER));
		list.add(addRegistry("productLine","Product Line",85,String.class.getName(),defaultContent));
		list.add(addRegistry("item","Item",85,String.class.getName(),defaultContent));
		
		list.add(addRegistry("id","ID",40,Long.class.getName(),defaultContent));
		/*
		list.add(addRegistry("quantity","Quantity",80,Long.class.getName(),importeStyle));
		list.add(addRegistry("test1","test1",40,String.class.getName(),importeStyle));
		list.add(addRegistry("test2","test2",40,String.class.getName(),importeStyle));
		list.add(addRegistry("test3","test3",40,String.class.getName(),importeStyle));
	
		list.add(addRegistry("amount","Amount",90,Float.class.getName(),importeStyle));
			*/	
		return list;
	}
	
	public static void main(String[] args) throws Exception {
		
		String title="November 2006 sales report";
		String subTitle="The items in this report correspond to the main products: DVDs, Books, Foods and Magazines";
		
		List<Map<String, Object>> data = dataStructure();
		
		ReportDynamicSimpleTable test = new ReportDynamicSimpleTable(title,subTitle,data);
		
		DynamicReport buildReport = test.buildReport();
		 		
		//JRDataSource ds = new JRBeanCollectionDataSource(TestRepositoryProducts.getDummyCollection());   
		//JasperPrint jp = DynamicJasperHelper.generateJasperPrint(buildReport, new ClassicLayoutManager(), ds);
		//JasperViewer.viewReport(jp);    //finally display the report report
		
	}

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}
	
}
