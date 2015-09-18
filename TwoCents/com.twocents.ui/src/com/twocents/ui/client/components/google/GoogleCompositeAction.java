package com.twocents.ui.client.components.google;

import org.apache.log4j.Logger;

import com.google.gdata.util.AuthenticationException;
import com.twocents.repository.google.DocumentListException;
import com.twocents.repository.google.GoogleDocsRepository;
import com.twocents.ui.client.common.action.UIAction;
import com.twocents.ui.util.DialogUtil;

public class GoogleCompositeAction extends UIAction {

	private  Logger logger = Logger.getLogger(GoogleCompositeAction.class);
	private final GoogleComposite googleComposite;
	private boolean logged=false;
	
	public GoogleCompositeAction(GoogleComposite googleComposite) {
		this.googleComposite = googleComposite;
	}


	@Override
	public void refresh() {
		
	}

	@Override
	public void init() {
		
	}

	public void connect(String username, String password) {
		
		if(isLogged()){
			logger.info("*** Usuário desconectado");
			logoffDisableButtons();
			return;
		}
		
		logger.info("Conectando no Google Docs");
		try {
			GoogleDocsRepository google = GoogleDocsRepository.getInstance();
			
			logger.info("Logando no Google Docs");
			google.login(username, password);
			logger.info("*** Usuário conectado - "+username);
			
			loggedEnableButtons();
			
		} catch (DocumentListException e) {
			logger.error(e.getClass().getName(),e);
			DialogUtil.errorMessage(getGoogleComposite(), "Aviso", "Não foi possivel conexão com o Google");
			
		} catch (AuthenticationException e) {
			logger.error(e.getClass().getName(),e);
			DialogUtil.errorMessage(getGoogleComposite(), "Aviso", "As credenciais informadas são invalidas");
		}
		
	}

	public void loggedEnableButtons(){
		getGoogleComposite().getSaveData().setVisible(true);
		getGoogleComposite().getRecoverData().setVisible(true);
		
		setLogged(true);
		getGoogleComposite().getConnect().setText(GoogleComposite.DESCONECTAR);
		
	}
	public void logoffDisableButtons(){
		getGoogleComposite().getSaveData().setVisible(false);
		getGoogleComposite().getRecoverData().setVisible(false);
		
		setLogged(false);
		getGoogleComposite().getConnect().setText(GoogleComposite.CONECTAR);
	}
	
	public GoogleComposite getGoogleComposite() {
		return googleComposite;
	}

	public void saveData() {
		
		logger.info("Salvando dados do TwoCents na conta do Google");
		
		try {
			GoogleDocsRepository google = GoogleDocsRepository.getInstance();
			
			
		} catch (DocumentListException e) {
			logger.error(e.getClass().getName(),e);
			DialogUtil.errorMessage(getGoogleComposite(), "Aviso", "Não foi possivel conexão com o Google");
		}
		
		
		
	}

	public void recoverData() {
		
		logger.info("Recuperando dados do TwoCents da conta do Google");
		
		try {
			GoogleDocsRepository google = GoogleDocsRepository.getInstance();
			
			
		} catch (DocumentListException e) {
			logger.error(e.getClass().getName(),e);
			DialogUtil.errorMessage(getGoogleComposite(), "Aviso", "Não foi possivel conexão com o Google");
		}
		
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public boolean isLogged() {
		return logged;
	}

}
