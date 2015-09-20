package com.twocents.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.twocents.dao.BrokerageNoteDAO;
import com.twocents.model.Account;
import com.twocents.model.BrokerageNote;
import com.twocents.resources.CoreMessages;

@SuppressWarnings("unchecked")
public class BrokerageNoteServiceImpl extends BaseServiceImpl<BrokerageNote, BrokerageNoteDAO> implements BrokerageNoteService {
	
	
	private static final Logger log = Logger.getLogger(BrokerageNoteServiceImpl.class);

	public void setBrokerageNoteDAO(BrokerageNoteDAO dao) {
		super.setDao(dao);
	}
	
	public BrokerageNoteDAO getBrokerageNoteDAO() {
		return super.getDao();
	}


	public BrokerageNote findBrokerageNoteByDate(Account account,Timestamp min,Timestamp max){
		try{
			return getBrokerageNoteDAO().findBrokerageNoteByDate(account,min,max);
		} catch (RuntimeException e) {
			log.debug(CoreMessages.getMessage(1009) +" - " + e.getMessage());
			return null;
		}
		
	}
	
	public List<BrokerageNote> findBrokerageNoteByDateWithDateOrderedAsc(Account account,Timestamp min,Timestamp max){
		try{
			return getBrokerageNoteDAO().findBrokerageNoteByDateWithDateOrderedAsc(account,min,max);
		} catch (RuntimeException e) {
			log.debug(CoreMessages.getMessage(1009) +" - " + e.getMessage());
			return null;
		}
		
	}

	public List<BrokerageNote> findAllBrokerageNoteByAccount(Account account){
		try{
			return getBrokerageNoteDAO().findAllBrokerageNoteByAccount(account);
		} catch (RuntimeException e) {
			log.debug(CoreMessages.getMessage(1009) +" - " + e.getMessage());
			return null;
		}
	}
	

	public List<Map<String, Object>> findBrokerageNoteForPeriodAndGroupByYearAndMonth(Account account,Timestamp min,Timestamp max){
		try{
			return getBrokerageNoteDAO().findBrokerageNoteForPeriodAndGroupByYearAndMonth(account,min,max);
		} catch (RuntimeException e) {
			log.debug(CoreMessages.getMessage(1009) +" - " + e.getMessage(),e);
			return null;
		}
		
	}
	
}
