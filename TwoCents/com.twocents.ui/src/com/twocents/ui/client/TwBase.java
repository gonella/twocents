package com.twocents.ui.client;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import swing2swt.layout.BorderLayout;

import com.swtdesigner.SWTResourceManager;
import com.twocents.ui.TwoCentsUI;
import com.twocents.ui.client.common.action.PanelCompositeAction;
import com.twocents.ui.client.common.windows.DefaultDialog;
import com.twocents.ui.client.components.AboutComposite;
import com.twocents.ui.resources.UIImages;

public abstract class TwBase extends PanelCompositeAction{
	
	private Logger logger = Logger.getLogger(TwBase.class);


	// TODO: Colocar os setters e inicializar as variaveis no init

	// Barra principal da tab. Aqui fica o nome da tab e os icones
	final Composite compositeTop = new Composite(this, SWT.NONE);
	// Apresenta o nome da Tab no topo
	//final CLabel labelTopText = new CLabel(compositeTop, SWT.NONE);
	
	// Apresenta icones no canto superior da tela responsáveis para dar suporte,
	// pesquisa...
	final Composite compositeTool = new Composite(compositeTop, SWT.NONE);

	final CLabel helpIcon = new CLabel(compositeTool, SWT.NONE);
	
	// Neste painel devemos colocar as telas do sistema.
	final Composite compositeCenter = new Composite(this, SWT.NONE);
	// Painel localizado no canto da página.
	final Composite compositeLeft = new Composite(compositeCenter, SWT.NONE);
	// Exibe a data por extenso
	final CLabel labelDate = new CLabel(compositeLeft, SWT.NONE);

	public Composite getCompositeCenter() {
		return compositeCenter;
	}

	public Composite getCompositeLeft() {
		return compositeLeft;
	}

	public TwBase(Composite arg0, int arg1, String labelText) {
		super(arg0, arg1);
		init(labelText);
		
	}
	public TwBase(Composite arg0, int arg1){
		super(arg0,arg1);
	}
	
	private void init(String labelText) {

		this.setLayout(new BorderLayout(0, 0));
		this
				.setBackground(Display.getCurrent().getSystemColor(
						SWT.COLOR_WHITE));

		//Header em cima do panel
		
		/*compositeTop.setLayout(new BorderLayout(0, 0));
		compositeTop.setBackground(SWTResourceManager.getColor(3, 16, 48));
		compositeTop.setLayoutData(BorderLayout.NORTH);
	
		labelTopText.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		labelTopText.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NONE));
		labelTopText.setBackground(SWTResourceManager.getColor(3, 16, 48));
		labelTopText.setText(labelText);

		compositeTool.setLayout(new FillLayout());
		compositeTool.setLayoutData(BorderLayout.EAST);

		helpIcon.setText("    ");
		helpIcon.setToolTipText("Ajuda");
		helpIcon.setBackground(SWTResourceManager.getColor(3, 16, 48));
		helpIcon.setImage(SWTResourceManager.getImage(TwoCentsUI.class,UIImages.HELP_ICON_16));

		helpIcon.addMouseListener(new MouseAdapter() {

			public void mouseDown(
					MouseEvent arg0) {

				displayAbout();
			}

		});
*/		
		compositeCenter.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		compositeCenter.setLayout(new BorderLayout(0, 0));
		compositeCenter.setLayoutData(BorderLayout.CENTER);
		

	}


	protected void displayAbout() {
		DefaultDialog defaultDialog = new DefaultDialog(getShell(),
				"Sobre o TwoCents", 700, 550,true);
		AboutComposite aboutComposite=new AboutComposite(defaultDialog.getCompositeBody(), SWT.NONE);
		
		defaultDialog.open();
	}

	public Composite getScreenCenter() {
		return compositeCenter;
	}

	public Composite getLeftScreen() {
		return compositeLeft;
	}

	public Rectangle getLeftScreenBounds() {
		return labelDate.getBounds();
	}

}
