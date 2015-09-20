package com.twocents.ui.client.components.export.action;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TableItem;
import org.springframework.util.CollectionUtils;

import com.swtdesigner.SWTResourceManager;
import com.twocents.core.util.StringCheck;
import com.twocents.data.excel.ImportProcess;
import com.twocents.exceptions.CoreException;
import com.twocents.ui.client.common.action.UIDialogAction;
import com.twocents.ui.client.components.TwMessageBox;
import com.twocents.ui.client.components.export.ImportComposite;
import com.twocents.ui.client.session.ContextHolderUI;
import com.twocents.ui.handler.progress.threadprogress.ThreadProgressImportProcess;
import com.twocents.ui.resources.UIImages;

public class ImportCompositeAction extends UIDialogAction {

	Image imageOk = SWTResourceManager.getImage(this.getClass(), UIImages.ICON_OK_16);
	Image imageError = SWTResourceManager.getImage(this.getClass(), UIImages.ICON_ERROR_16);


	private final ImportComposite importComposite;
	
	public ImportCompositeAction(ImportComposite importComposite) {
		this.importComposite = importComposite;
	}

	@Override
	public void pressOkButton() {
		getImportComposite().getShell().close();
	}

	@Override
	public void pressCancelButton() {
		getImportComposite().getShell().close();
	}

	public ImportComposite getImportComposite() {
		return importComposite;
	}

	public void importFile(final String fileNameSelected) {
		
		
		try {
			
			if(StringCheck.isEmpty(fileNameSelected)){
				TwMessageBox.messageWarning(getImportComposite().getShell(), "Não arquivo selecionado ["+fileNameSelected+"]");
				return;
			}
			
			ThreadProgressImportProcess threadProgressImportProcess=new ThreadProgressImportProcess(
					getImportComposite().getShell()
					,getImportComposite().getShell()
					,fileNameSelected
					,ContextHolderUI.getStockBrokerLogged()) {
				@Override
				public void execute() {
					ImportProcess importProcess=null;
					try {
				
						importProcess = new ImportProcess(fileNameSelected);
						setItemsProcessed(importProcess.process(importProcess.read(), getStockBroker()));
						
					} catch (CoreException e) {
						logger.error(e.getClass().getName(),e);						
					}
				}
			};
			List<String> itemsProcessed = threadProgressImportProcess.getItemsProcessed();
			
			
			/*ImportProcess importProcess = new ImportProcess(fileNameSelected);
			List<String[]> read = importProcess.read();
			List<String> itemsProcessed = importProcess.process(read,ContextHolderUI.getStockBrokerLogged());
			*/
			if(CollectionUtils.isEmpty(itemsProcessed)){
				TwMessageBox.messageWarning(getImportComposite().getShell(), "Não foi possível processar o arquivo");
				return;
			}
			
			TableItem item;
			int id=0;
			String status="";
			for (String string : itemsProcessed) {
				item = new TableItem(getImportComposite().getTable(), SWT.NONE);
				
				if(ImportProcess.OK.equals(string)){
					item.setText(new String[] {id+"",ImportProcess.OK,""});
					item.setImage(1,imageOk);
				}
				else{
					item.setText(new String[] {id+"",ImportProcess.ERROR,string});
					item.setImage(1,imageError);
				}
				
				id++;
			}
			
		} catch (CoreException e) {
			TwMessageBox.messageWarning(getImportComposite().getShell(), e.getMessage());
		}
		
	}

	@Override
	public void init() {
		try {
			getImportComposite().getAccountSelected().setText(ContextHolderUI.getAccountSelected().getUser().getName());
		} catch (CoreException e) {
			logger.error(e);
		}

	}
	public void checkAccount() throws CoreException{
		try {
			ContextHolderUI.getAccountSelected();
		} catch (CoreException e) {
			String msg = "É necessário selecionar uma conta para importar.";
			TwMessageBox.messageWarning(getImportComposite().getShell(), msg);
			throw new CoreException(msg);
		}
	}

}
