package com.twocents.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.twocents.model.Account;
import com.twocents.model.BrokerageNote;


public interface BrokerageNoteDAO extends BaseDAO<BrokerageNote> {
	
	public BrokerageNote findBrokerageNoteByDate(Account account,Timestamp min,Timestamp max);

	public List<BrokerageNote> findAllBrokerageNoteByAccount(Account account);

	public List<BrokerageNote> findBrokerageNoteByDateWithDateOrderedAsc(Account account,Timestamp min,Timestamp max);

	public List<Map<String, Object>> findBrokerageNoteForPeriodAndGroupByYearAndMonth(Account account, Timestamp min, Timestamp max);

}
