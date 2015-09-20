package com.twocents.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.model.Darf;

@Transactional(propagation=Propagation.REQUIRED)
public interface DarfService extends BaseService<Darf> {

	String BEAN_NAME = "com.twocents.service.DarfService";


}

