package com.twocents.service;

import org.apache.log4j.Logger;

import com.twocents.dao.DarfDAO;
import com.twocents.model.Darf;

public class DarfServiceImpl extends BaseServiceImpl<Darf, DarfDAO> implements DarfService {

	private static final Logger log = Logger.getLogger(DarfServiceImpl.class);
	
	public void setDarfDAO(DarfDAO dao) {
		super.setDao(dao);
	}
	
	public DarfDAO getDarfDAO() {
		return super.getDao();
	}
	
	
}
