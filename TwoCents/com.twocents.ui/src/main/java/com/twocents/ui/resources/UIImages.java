package com.twocents.ui.resources;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.widgets.Display;

public class UIImages {

	private static Logger logger = Logger.getLogger(UIImages.class);

	public static final String COM_TWOCENTS_UI_RESOURCES_SPLASH_SCREEN = "/com/twocents/ui/resources/images/TC4.png";

	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_BOTAO_EDITAR01_PNG = "/com/twocents/ui/resources/images/BotaoEditar01.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_BOTAO_CANCELAR01_PNG = "/com/twocents/ui/resources/images/BotaoCancelar01.png";

	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_BOTAO_CONECTAR01_PNG = "/com/twocents/ui/resources/images/BotaoConectar01.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_BOTAO_COMPRAR01_PNG = "/com/twocents/ui/resources/images/BotaoComprar01.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_BOTAO_VENDER01_PNG = "/com/twocents/ui/resources/images/BotaoVender01.png";

	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_IMPORT04_48X48_PNG = "/com/twocents/ui/resources/images/Import04_48x48.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_MENU_FOND_PNG = "/com/twocents/ui/resources/images/menu-fond.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_UPDATE01_48X48_PNG = "/com/twocents/ui/resources/images/Update01_48x48.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_UPDATE03_48X48_PNG = "/com/twocents/ui/resources/images/Update03_48x48.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_EXIT01_48X48_PNG = "/com/twocents/ui/resources/images/Exit01_48x48.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_EXPORT_DATA03_48X48_PNG = "/com/twocents/ui/resources/images/ExportData03_48x48.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_CONFIGURATION01_48X48_PNG = "/com/twocents/ui/resources/images/Configuration01_48x48.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_USERS_48X48_PNG = "/com/twocents/ui/resources/images/users-48x48.png";

	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_ABOUT01_48X48_PNG = "/com/twocents/ui/resources/images/About01_48x48.png";

	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_REPORT01_48X48_PNG = "/com/twocents/ui/resources/images/ReportAba01_48x48.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_REPORT02_63X48_PNG = "/com/twocents/ui/resources/images/Report02_63x48.png";
	
	
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_BANIERE_FOND_PNG = "/com/twocents/ui/resources/images/baniere-fond.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_STOCK02_48X48_PNG = "/com/twocents/ui/resources/images/stock02_48x48.png";

	public static final String CHECKBOX_EMPTY	= "/com/twocents/ui/resources/images/checkbox_empty_32x32.png";
	public static final String CHECKBOX_CHECKED_GREEN	= "/com/twocents/ui/resources/images/checkbox_checked_green_32x32.png";
	public static final String CHECKBOX_CHECKED_RED	= "/com/twocents/ui/resources/images/checkbox_checked_red_32x32.png";

	public static final String MAIN_01_ICON_48		= "/com/twocents/ui/resources/images/Tc_logo_01.ico";
	
	public static final String BULLET_ICON_16		= "/com/twocents/ui/resources/images/bullet1_16x16.png";
	public static final String BULLET_12_ICON_16		= "/com/twocents/ui/resources/images/bullet2_16x16.png";
	
	public static final String BULLET_REPORT1_ICON_24		= "/com/twocents/ui/resources/images/report1_24x24.png";
	public static final String BULLET_REPORT4_ICON_24		= "/com/twocents/ui/resources/images/report4_24x24.png";
	public static final String BULLET_REPORT6_ICON_24		= "/com/twocents/ui/resources/images/report6_24x24.png";

	public static final String BULLET_ACCOUNT1_ICON_32		= "/com/twocents/ui/resources/images/AccountIcon01_32x32.png";
	public static final String BULLET_ACCOUNT2_ICON_32		= "/com/twocents/ui/resources/images/AccountIcon02_32x32.png";
	
	
	public static final String EDIT_OPERATION_ICON_16x16_1		= "/com/twocents/ui/resources/images/editOperation_16x16_01.png";
	public static final String EDIT_OPERATION_ICON_16x16_2		= "/com/twocents/ui/resources/images/editOperation_16x16_02.png";

	public static final String DELETE_ICON_16		= "/com/twocents/ui/resources/images/Stop 2.png";
	public static final String OPERATION_ICON_32	= "/com/twocents/ui/resources/images/Column-Chart-32x32.png";
	public static final String REPORT_ICON_32		= "/com/twocents/ui/resources/images/Printer-32x32.png";
	public static final String SEARCH_ICON_16		= "/com/twocents/ui/resources/images/search2-16x16.png";
	public static final String HELP_ICON_16			= "/com/twocents/ui/resources/images/help-16x16.png";
	public static final String DOWN_ARROW_ICON_16	= "/com/twocents/ui/resources/images/Downarrow3_red_16x10.png";
	public static final String UP_ARROW_ICON_16		= "/com/twocents/ui/resources/images/Downarrow3_green_16x10.png";
	public static final String REPORT_ITEMS_ICON_32	= "/com/twocents/ui/resources/images/ms-office-outlook-32x32.png";
	public static final String EXPORT_PDF_ICON_48	= "/com/twocents/ui/resources/images/Oficina-PDF-48x48.png";	
	public static final String EXPORT_XLS_ICON_48	= "/com/twocents/ui/resources/images/Oficina-XLS-48x48.png";
	
	public static final String FEED_ICON_38	= "/com/twocents/ui/resources/images/Oficina-XLS-48x48.png";
	public static final String TWOCENTS_GMAIL_REPOSITORY_ICON_48	= "/com/twocents/ui/resources/images/TwoCents-GmailRepository_Icon_48x48.png";
	public static final String TWOCENTS_GMAIL_CONNECTED_INTERNET_ICON_48	= "/com/twocents/ui/resources/images/TwoCents-StayConnected_Station_Icon_48x48.png";
	
	
	public static final String TWOCENTS_ADD_OPERATION	= "/com/twocents/ui/resources/images/GraphUp.png";
	public static final String TWOCENTS_CUSTODY	= "/com/twocents/ui/resources/images/GraphPie.png";
	
	public static final String TWOCENTS_FEED_TAB	= "/com/twocents/ui/resources/images/TwoCents-Feed_Icon_16x16.png";
	public static final String PROGRESSBAR_FOR_PROCESS01 = "/com/twocents/ui/resources/images/progressBar01.png";
	public static final String PROGRESSBAR_FOR_PROCESS02 = "/com/twocents/ui/resources/images/progressbar02.gif";
	public static final String PROGRESSBAR_FOR_PROCESS03 = "/com/twocents/ui/resources/images/progressbar03.gif";
	public static final String PROGRESSBAR_FOR_PROCESS04 = "/com/twocents/ui/resources/images/progressbar04.gif";
	public static final String PROGRESSBAR_FOR_PROCESS05 = "/com/twocents/ui/resources/images/progressBar05.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_ADVERTISING_REPORT_PNG = "/com/twocents/ui/resources/images/AdvertisingReport.png";
	public static final String COM_TWOCENTS_UI_RESOURCES_IMAGES_ADVERTISING_OPERATION_PNG = "/com/twocents/ui/resources/images/AdvertisingOperation.png";
	
	public static final String ICON_ERROR_16	= "/com/twocents/ui/resources/images/icon_error_16x16.png";
	public static final String ICON_OK_16		= "/com/twocents/ui/resources/images/icon_ok_16x16.png";

	

	
	public static Image makeSWTImage(Display display, java.awt.Image ai) {
		int width = ai.getWidth(null);
		int height = ai.getHeight(null);
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(ai, 0, 0, null);
		g2d.dispose();
		int[] data = ((DataBufferInt) bufferedImage.getData().getDataBuffer())
				.getData();
		ImageData imageData = new ImageData(width, height, 24, new PaletteData(
				0xFF0000, 0x00FF00, 0x0000FF));
		imageData.setPixels(0, 0, data.length, data, 0);
		Image swtImage = new Image(display, imageData);
		return swtImage;
	}
	
	
	public static Image loadImageResource(Display d, String name) {
		try {

			Image ret = null;
			InputStream is = ClassLoader.getSystemResourceAsStream(name);
			if (is != null) {
				ret = new Image(d, is);
				is.close();
			}
			if (ret == null)
				logger.info("Não foi possível carregar a imagem");
			
			return ret;
		} catch (Exception e1) {
			logger.error("Não foi possível carregar a imagem",e1);
			return null;
		}
	}
}
