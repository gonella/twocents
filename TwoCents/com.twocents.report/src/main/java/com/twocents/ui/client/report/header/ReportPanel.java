package com.twocents.ui.client.report.header;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import swing2swt.layout.BorderLayout;

import com.jasperassistant.designer.viewer.DefaultHyperlinkHandler;
import com.jasperassistant.designer.viewer.ReportViewer;
import com.jasperassistant.designer.viewer.StatusBar;
import com.swtdesigner.SWTResourceManager;
import com.twocents.report.resources.ReportImages;

public class ReportPanel extends Composite {

	private Logger logger = Logger.getLogger(ReportPanel.class);


	private ReportViewer reportViewer;

	private Composite reportComposite;
	
	public ReportPanel(Composite parentComposite, JasperPrint jasperPrint) {

		super(parentComposite, SWT.NONE);
		setLayoutData(BorderLayout.CENTER);

		createReportContent();

		createJasperContent(getReportComposite(),jasperPrint);
	}

	public void createJasperContent(Composite reportComposite,JasperPrint jasperPrint) {

		reportViewer= new ReportViewer(SWT.BORDER);
		reportViewer.setDocument(jasperPrint);
		
		Control reportViewerControl = reportViewer.createControl(reportComposite);
		//reportViewerControl.setLayoutData(new GridData(GridData.FILL_BOTH));
		reportViewerControl.setLayoutData(BorderLayout.CENTER);

		StatusBar statusBar = new StatusBar();
		statusBar.setReportViewer(reportViewer);
		Control statusBarControl = statusBar.createControl(reportComposite);
		//statusBarControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		statusBarControl.setLayoutData(BorderLayout.SOUTH);

		reportViewer.addHyperlinkListener(new DefaultHyperlinkHandler());
		
		/*scrolledComposite = new ScrolledComposite(reportViewerControl.getParent(), SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		
		scrolledComposite.addListener(SWT.Activate, new Listener() {
		    public void handleEvent(Event e) {
		    	
		    	logger.info("SCROLL:"+scrolledComposite.getVerticalBar().getIncrement());
		    }
		});*/
	}
	
	public void createReportContent() {
		setLayout(new BorderLayout(0, 0));
		{
			{
				{
					setReportComposite(new Composite(this, SWT.NONE));
					getReportComposite().setLayoutData(BorderLayout.CENTER);
					getReportComposite().setLayout(new BorderLayout(0, 0));
								{
									Composite barComposite = new Composite(this, SWT.BORDER);
									barComposite.setLayoutData(BorderLayout.NORTH);
									barComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
									
												ToolBar toolBar = new ToolBar(barComposite, SWT.FLAT | SWT.RIGHT);
												{
													ToolItem toolItem_1 = new ToolItem(toolBar, SWT.NONE);
													toolItem_1.setImage(SWTResourceManager
															.getImage(ReportPanel.class,
																	ReportImages.COM_JASPERASSISTANT_DESIGNER_VIEWER_ACTIONS_IMAGES_FIRST_GIF));
													toolItem_1.setText("Voltar");

													toolItem_1.addSelectionListener(new SelectionAdapter() {
														public void widgetSelected(SelectionEvent event) {
															// ToolItem item = (ToolItem) event.widget;
															methodBack();

														}
													});
												}
												{
													ToolItem toolItem_1 = new ToolItem(toolBar, SWT.NONE);
													toolItem_1.setImage(SWTResourceManager
															.getImage(ReportPanel.class,
																	ReportImages.COM_JASPERASSISTANT_DESIGNER_VIEWER_ACTIONS_IMAGES_LAST_GIF));
													toolItem_1.setText("Avançar");
													toolItem_1.addSelectionListener(new SelectionAdapter() {
														public void widgetSelected(SelectionEvent event) {
															// ToolItem item = (ToolItem) event.widget;
															methodNext();

														}
													});
												}
												{
													ToolItem toolItem_1 = new ToolItem(toolBar, SWT.NONE);
													toolItem_1.setImage(SWTResourceManager
															.getImage(ReportPanel.class,
																	ReportImages.COM_JASPERASSISTANT_DESIGNER_VIEWER_ACTIONS_IMAGES_ZOOMMINUS_GIF));
													toolItem_1.setText("Zoom(-)");
													toolItem_1.addSelectionListener(new SelectionAdapter() {
														public void widgetSelected(SelectionEvent event) {
															// ToolItem item = (ToolItem) event.widget;
															methodZoomOut();

														}
													});
												}
												{
													ToolItem toolItem_1 = new ToolItem(toolBar, SWT.NONE);
													toolItem_1.setImage(SWTResourceManager
															.getImage(ReportPanel.class,
																	ReportImages.COM_JASPERASSISTANT_DESIGNER_VIEWER_ACTIONS_IMAGES_ZOOMPLUS_GIF));
													toolItem_1.setText("Zoom(+)");
													toolItem_1.addSelectionListener(new SelectionAdapter() {
														public void widgetSelected(SelectionEvent event) {
															// ToolItem item = (ToolItem) event.widget;
															methodZoomIn();

														}
													});
												}
												{
													ToolItem toolItem_1 = new ToolItem(toolBar, SWT.NONE);
													toolItem_1.setImage(SWTResourceManager
															.getImage(ReportPanel.class,
																	ReportImages.COM_JASPERASSISTANT_DESIGNER_VIEWER_ACTIONS_IMAGES_PRINT_GIF));
													toolItem_1.setText("Imprimir");
													toolItem_1.addSelectionListener(new SelectionAdapter() {
														public void widgetSelected(SelectionEvent event) {
															// ToolItem item = (ToolItem) event.widget;
															methodPrint();

														}
													});
												}
												ToolItem toolItem = new ToolItem(toolBar, SWT.NONE);
												toolItem.setImage(SWTResourceManager
														.getImage(ReportPanel.class,
																ReportImages.EXPORT_PDF_ICON_16));
												toolItem.setText("PDF");
												toolItem.addSelectionListener(new SelectionAdapter() {
													public void widgetSelected(SelectionEvent event) {
														// ToolItem item = (ToolItem) event.widget;
														methodSave();

													}
												});
												ToolItem toolItemXLS = new ToolItem(toolBar, SWT.NONE);
												toolItemXLS.setImage(SWTResourceManager
														.getImage(ReportPanel.class,
																ReportImages.EXPORT_XLS_ICON_16));
												toolItemXLS.setText("XLS");
												toolItemXLS.addSelectionListener(new SelectionAdapter() {
													public void widgetSelected(SelectionEvent event) {
														// ToolItem item = (ToolItem) event.widget;
														methodSave();

													}
												});
								}
				}
			}

		}
	}

	
	
	protected void methodZoomIn() {
		logger.info("ZoomIn");
		reportViewer.zoomIn();
	}

	protected void methodZoomOut() {
		reportViewer.zoomOut();

	}

	protected void methodSave() {
		logger.info("Salvar em arquivo");

	}

	protected void methodPrint() {

		final File TEMP_DIR = new File(System.getProperty("java.io.tmpdir"));
		final String PDF_EXTENSION = ".pdf";

		final Display display = Display.getCurrent();
		display.asyncExec(new Runnable() {
			public void run() {
				try {
					if ("carbon".equals(SWT.getPlatform())) {
						Random random = new Random();
						int integer = random.nextInt();
						final String reportName = getReportViewer()
								.getDocument().getName();
						final String fileName = reportName + integer
								+ PDF_EXTENSION;
						final File file = new File(TEMP_DIR, fileName);
						file.deleteOnExit();
						exportAsPDF(file);
						openPDF(file);
					} else {
						JasperPrintManager.printReport(getReportViewer()
								.getDocument(), true);
					}
				} catch (Throwable e) {
					e.printStackTrace();
					MessageDialog.openError(display.getActiveShell(),
							"Printing Error", "Failed to print the document:"
									+ e.getMessage());
				}
			}
		});

	}

	protected void methodNext() {
		reportViewer.gotoNextPage();
	}

	protected void methodBack() {
		reportViewer.gotoPreviousPage();
	}

	

	public ReportViewer getReportViewer() {
		return reportViewer;
	}

	/**
	 * Exports the current document to a PDF.
	 * 
	 * @param file
	 *            The file to export the PDF to.
	 * @throws JRException
	 *             if there are problems exporting the Report to a PDF.
	 * 
	 * @since 03.05.2005
	 */
	private void exportAsPDF(final File file) throws JRException {
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT,
				getReportViewer().getDocument());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, file);
		exporter.exportReport();
	}

	/**
	 * Opens the PDF with the default registered application.
	 * 
	 * @param file
	 *            The PDF to open.
	 * @throws IOException
	 *             If the PDF can not be found.
	 * 
	 * @since 03.05.2005
	 */
	private void openPDF(final File file) throws IOException {
		final Runtime runtime = Runtime.getRuntime();
		runtime.exec("open " + file);
	}

	public void setReportComposite(Composite reportComposite) {
		this.reportComposite = reportComposite;
	}

	public Composite getReportComposite() {
		return reportComposite;
	}

	/*public void mouseWheelMoved(MouseWheelEvent e) {
		try {
			int mouseWheelRotates = e.getWheelRotation();
			int incPixel = 0;
			if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
				incPixel = _viewerForm.getScrollIncrementUnit();
			}
			else {
				incPixel = _viewerForm.getScrollIncrementBlock();
			}
			// Strg pressed so just zoom in or out!
			boolean isCtrlPressed = ((e.getModifiers() & InputEvent.CTRL_MASK) != 0);
			if (isCtrlPressed) {
				// Zoomen anstatt scrollen
				if (mouseWheelRotates < 0) {
					zoomOut();
				} else {
					zoomIn();
				}
			}
			else {
				// just scroll down or up or go to next or prev page
				int scrollPos = _viewerForm.getScrollPos();
				if (mouseWheelRotates < 0) {
					// scroll up
					incPixel = incPixel * (-1 * e.getUnitsToScroll());
					if (scrollPos - incPixel < _viewerForm.getMinScrollPos()) {
						prevPage();
					}
					else {
						_viewerForm.scrollUpByPixel(incPixel);
					}
				}
				else {
					// scroll down
					incPixel = incPixel * e.getUnitsToScroll();
					if (scrollPos + incPixel > _viewerForm.getMaxScrollPos()) {
						nextPage();
					}
					else {
						_viewerForm.scrollDownByPixel(incPixel);
					}
				}
			}
		}
		catch (Exception ex) {
			StandardDialog.showErrorDialog(_windowGUI, ex);
		}

	}
*/
}
