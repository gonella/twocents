package com.twocents.repository.google;

import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gdata.data.Link;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.twocents.environment.Environment;

public class GoogleDocsRepository {

	private static GoogleDocsRepository googleDocsRepository=null;
	
	public static GoogleDocsRepository getInstance() throws DocumentListException{
		
		if(googleDocsRepository==null){
			googleDocsRepository=new GoogleDocsRepository(APPLICATION_NAME,HOST);
		}
		
		return googleDocsRepository;
	}
	
	private static final String HOST = "docs.google.com";

	private static Logger logger = Logger.getLogger(GoogleDocsRepository.class);

	private DocumentList documentList;
	
	private static final String APPLICATION_NAME = "JavaGDataClientSampleAppV3.0";

	/**
	 * Constructor
	 * 
	 * @param outputStream
	 *            Stream to print output to.
	 * @throws DocumentListException
	 */
	public GoogleDocsRepository(String appName,String host) throws DocumentListException {
		setDocumentList(new DocumentList(appName, host));
	}

	/**
	 * Authenticates the client using ClientLogin
	 * 
	 * @param username
	 *            User's email address
	 * @param password
	 *            User's password
	 * @throws DocumentListException
	 * @throws AuthenticationException
	 */
	public void login(String username, String password)
			throws AuthenticationException, DocumentListException {
		getDocumentList().login(username, password);
	}

	/**
	 * Authenticates the client using AuthSub
	 * 
	 * @param authSubToken
	 *            authsub authorization token.
	 * @throws DocumentListException
	 * @throws AuthenticationException
	 */
	public void login(String authSubToken) throws AuthenticationException,
			DocumentListException {
		getDocumentList().loginWithAuthSubToken(authSubToken);
	}

	public static void viewFeed(DocumentListFeed feed) {
		for (DocumentListEntry entry : feed.getEntries()) {
			StringBuffer output = new StringBuffer();

			output.append(" -- " + entry.getTitle().getPlainText() + " ");
			if (!entry.getParentLinks().isEmpty()) {
				for (Link link : entry.getParentLinks()) {
					output.append("[" + link.getTitle() + "] ");
				}
			}

			output.append(" -> " + entry.getResourceId());

			System.out.println(output);
		}
	}

	public void setDocumentList(DocumentList documentList) {
		this.documentList = documentList;
	}

	public DocumentList getDocumentList() {
		return documentList;
	}

	public void download(DocumentListEntry entry) {
		try {
			String resourceId = entry.getResourceId();
			String docId = resourceId
					.substring(resourceId.lastIndexOf(":") + 1);

			String fileName = entry.getTitle().getPlainText();
			logger.info("Google - Downloading...:" + fileName);
			
			getDocumentList().downloadDocument(docId, fileName, "txt");

			logger.info("**Downloaded");
			
		} catch (Exception e) {
			logger.error("Não foi possível fazer o download do arquivo",e);
		}
	}

	public void upload(String fileNamePath, String linkName) {
		try {
			logger.info("Google - Uploading...:"+linkName);

			getDocumentList().uploadFile(fileNamePath, linkName);
			logger.info("Google - Arquivo carregado com sucesso");
		} catch (IOException e) {
			logger.error(e.getClass().getName(), e);

		} catch (ServiceException e) {
			logger.error(e.getClass().getName(), e);

		} catch (DocumentListException e) {
			logger.error(e.getClass().getName(), e);

		}
	}

	public DocumentListFeed findResource(String fileName) {
		DocumentListFeed feed = null;

		try {
			Map<String, String> searchParameters = new HashMap<String, String>();
			searchParameters.put("title", fileName);

			feed = getDocumentList().search(searchParameters, "documents");
		} catch (MalformedURLException e) {
			logger.error(e.getClass().getName(), e);
		} catch (IOException e) {
			logger.error(e.getClass().getName(), e);
		} catch (ServiceException e) {
			logger.error(e.getClass().getName(), e);
		} catch (DocumentListException e) {
			logger.error(e.getClass().getName(), e);
		}
		return feed;
	}

	public static void main(String[] args) throws DocumentListException,
			IOException, ServiceException, InterruptedException {
		String user = "gonella";
		String password = "@";

		GoogleDocsRepository demo = new GoogleDocsRepository(APPLICATION_NAME, HOST);
		demo.login(user, password);

		String file1 = Environment.DATABASE_FILENAME_SCRIPT + ".txt";
		String file2 = Environment.DATABASE_FILENAME_PROPERTIES + ".txt";

		String filepathScript = "C:/Users/Adriano Gonella/Downloads/" + file1;
		String filepathProperties = "C:/Users/Adriano Gonella/Downloads/" + file2;

		//demo.upload(filepathScript, file1);		
		//demo.upload(filepathProperties, file2);
		//System.exit(0);
		
		DocumentListFeed findResource1 = demo.findResource(file1);
		DocumentListFeed findResource2 = demo.findResource(file2);

		//Sempre baixar SOMENTE o primeiro da lista
		for (DocumentListEntry entry : findResource1.getEntries()) {
			demo.download(entry);
			break;
		}
		for (DocumentListEntry entry : findResource2.getEntries()) {
			demo.download(entry);
			break;
		}
	}
		
	
	
}
