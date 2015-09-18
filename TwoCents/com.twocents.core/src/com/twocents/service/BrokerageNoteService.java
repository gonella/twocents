package com.twocents.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.BrokerageNote;


@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Throwable.class, CoreException.class})
public interface BrokerageNoteService  extends BaseService<BrokerageNote> {
	
	String BEAN_NAME = "com.twocents.service.BrokerageNoteService";
	
	public BrokerageNote findBrokerageNoteByDate(Account account,Timestamp min,Timestamp max) throws CoreException;

	public List<BrokerageNote> findAllBrokerageNoteByAccount(Account account)throws CoreException;

	public List<BrokerageNote> findBrokerageNoteByDateWithDateOrderedAsc(Account account,Timestamp min,Timestamp max) throws CoreException;

	public List<Map<String, Object>> findBrokerageNoteForPeriodAndGroupByYearAndMonth(Account account, Timestamp min, Timestamp max);
	
}
