package com.twocents.ui.client.components.export.action;

import org.springframework.util.StringUtils;

import com.twocents.core.util.StringCheck;
import com.twocents.data.excel.ExportProcess;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.ui.client.common.action.UIDialogAction;
import com.twocents.ui.client.components.TwMessageBox;
import com.twocents.ui.client.components.export.ExportComposite;
import com.twocents.ui.client.session.ContextHolderUI;

public class ExportCompositeAction extends UIDialogAction {

	private final ExportComposite exportComposite;

	public ExportCompositeAction(ExportComposite exportComposite) {
		this.exportComposite = exportComposite;
	}
	
	@Override
	public void pressOkButton() {
		getExportComposite().getShell().close();
		
	}

	@Override
	public void pressCancelButton() {
		getExportComposite().getShell().close();
		
	}

	public ExportComposite getExportComposite() {
		return exportComposite;
	}
	
	public void exportFile(String fileNameSelected){
			ExportProcess process=null;
			
			try {
				if(StringCheck.isEmpty(fileNameSelected)){
					TwMessageBox.messageWarning(getExportComposite().getShell(), "Não existe arquivo selecionado ["+fileNameSelected+"]");
					return;
				}
				
				Account accountSelected = ContextHolderUI.getAccountSelected();
				
				boolean checkXls=false;
				if(getExportComposite().getBtnXls().getSelection()){
					checkXls=true;
				}else{
					checkXls=false;
				}
				
				process=new ExportProcess(checkXls,fileNameSelected);
				process.write(accountSelected);
				
				TwMessageBox.messageInformation(getExportComposite().getShell(), "Operações exportadas com sucesso.");
				
			} catch (CoreException e) {
				TwMessageBox.messageWarning(getExportComposite().getShell(), e);			
			}	
		
	}

	@Override
	public void init() {
		
		
		
	}
	
	public void checkAccount() throws CoreException{
		try {
			getExportComposite().getAccountSelected().setText(ContextHolderUI.getAccountSelected().getUser().getName());
		} catch (CoreException e) {
			String msg = "É necessário selecionar uma conta para exportar.";
			TwMessageBox.messageWarning(getExportComposite().getShell(), msg);
			throw new CoreException(msg);
		}
	}

}
