package com.twocents.service;

import org.apache.log4j.Logger;

import com.twocents.dao.DocumentDAO;
import com.twocents.model.Document;

public class DocumentServiceImpl extends BaseServiceImpl<Document, DocumentDAO> implements DocumentService {

	private static final Logger log = Logger.getLogger(DocumentServiceImpl.class);
	
	public void setDocumentDAO(DocumentDAO dao) {
		super.setDao(dao);
	}
	
	public DocumentDAO getDocumentDAO() {
		return super.getDao();
	}
	
	
}
