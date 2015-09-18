package com.twocents.ui.client.components;

import org.apache.log4j.Logger;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.nebula.widgets.pshelf.RedmondShelfRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.swtdesigner.SWTResourceManager;
import com.twocents.ui.client.TwReportUI;
import com.twocents.ui.client.common.action.PanelCompositeAction;
import com.twocents.ui.client.components.action.ReportTableCompositeAction;
import com.twocents.ui.client.report.Report;

public class ReportTableComposite extends PanelCompositeAction {
	
	private static Logger logger = Logger.getLogger(ReportTableComposite.class);

	private Table table;

	final Color GREEN_LIGHT=SWTResourceManager.getColor(128, 255, 128);

	private final TwReportUI twReportUI;

	private ReportTableCompositeAction action;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @param twReportUI 
	 */
	public ReportTableComposite(Composite parent, int style,TwReportUI twReportUI) {
		super(parent, style);
		this.twReportUI = twReportUI;
		setLayout(new FillLayout(SWT.HORIZONTAL));
		//setSize(sizeX,sizeY);
		
		PShelf shelf = new PShelf(this, SWT.NONE);
		shelf.setRenderer(new RedmondShelfRenderer());	

		PShelfItem shelfItem = new PShelfItem(shelf, SWT.NONE);
		shelfItem.setText("Relat\u00F3rios Personalizados");
		shelfItem.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(shelfItem.getBody(), SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		setTable(new Table(composite, SWT.BORDER));
		

		getTable().addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				Point pt = new Point(event.x, event.y);
				TableItem item = getTable().getItem(pt);
				
				//logger.info(" Clicked ");
				
				if (item == null)
					return;
				for (int i = 0; i < getTable().getColumnCount(); i++) {
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt)) {
						int index = getTable().indexOf(item);
						
						Object data = item.getData();			
						getTwReportUI().getAction().displayReportSelected((Report)data);

						break;
					}
				}
			}
		});
		
		getTable().addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(Event event) {
			        event.detail &= ~SWT.HOT;
			        if ((event.detail & SWT.SELECTED) == 0) return; /// item not selected

			        Table table =(Table)event.widget;
			        TableItem item =(TableItem)event.item;
			        int clientWidth = table.getClientArea().width;

			        GC gc = event.gc;                               
			        Color oldForeground = gc.getForeground();
			        Color oldBackground = gc.getBackground();

			        //gc.setBackground(red);
			        //gc.setForeground(yellow);
			        
			        gc.setBackground(GREEN_LIGHT);
			        gc.setForeground(GREEN_LIGHT);                              
			        
			        //gc.fillRectangle(0, event.y, clientWidth, event.height);
			        gc.fillGradientRectangle(0, event.y, clientWidth, event.height, false);

			        gc.setForeground(oldForeground);
			        gc.setBackground(oldBackground);
			        event.detail &= ~SWT.SELECTED;
			}
			});
		
		TableColumn col1 = new TableColumn(getTable(),SWT.FILL);
		col1.setText("Relatórios");
		col1.setWidth(200);
		
		//setBounds(new Rectangle(0, 0, sizeX,sizeY));
		
	}
	
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}


	public void setTable(Table table) {
		this.table = table;
	}

	public Table getTable() {
		return table;
	}

	public TwReportUI getTwReportUI() {
		return twReportUI;
	}

	@Override
	public ReportTableCompositeAction getAction() {
		
		if(action==null){
			action = new ReportTableCompositeAction(this);
		}
		
		return action;
	}
}
