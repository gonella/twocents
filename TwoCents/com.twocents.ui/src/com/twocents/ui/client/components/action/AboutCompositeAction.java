package com.twocents.ui.client.components.action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.twocents.bovespa.stock.LoadResource;
import com.twocents.ui.client.common.action.UIAction;
import com.twocents.ui.client.components.AboutComposite;

public class AboutCompositeAction extends UIAction {

	private static final String README_TXT = "README.txt";

	private Logger logger = Logger.getLogger(this.getClass());

	private final AboutComposite UI;

	public AboutCompositeAction(AboutComposite UI) {
		this.UI = UI;
	}


	@Override
	public void refresh() {

	}

	@Override
	public void init() {
		StringBuffer sb=new StringBuffer();
		try {
			logger.info("Apresentando arquivo :"+README_TXT);
			
			URL resource = LoadResource.getResource(README_TXT, this.getClass());
			
		    BufferedReader in = new BufferedReader(new FileReader(resource.getFile()));
		    String str;
		    
		    while ((str = in.readLine()) != null) {
		        sb.append(str+"\r\n");
		        
		    }
		    in.close();
		} catch (IOException e) {
			String string = "Não foi possível ler o arquivo : "+README_TXT;
			logger.error(string,e);			
			sb.append(string);
		}
		
		getUI().getStyledText().append(sb.toString());
		
	}

	public AboutComposite getUI() {
		return UI;
	}

}
