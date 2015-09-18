package com.twocents.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.model.Document;

@Transactional(propagation=Propagation.REQUIRED)
public interface DocumentService extends BaseService<Document> {

	String BEAN_NAME = "com.twocents.service.DocumentService";


}

