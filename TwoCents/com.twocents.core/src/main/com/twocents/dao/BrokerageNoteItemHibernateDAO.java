package com.twocents.dao;

import javax.persistence.Query;

import com.twocents.model.BrokerageNoteItem;
import com.twocents.model.Operation;


public class BrokerageNoteItemHibernateDAO extends
		BaseDAOImpl<BrokerageNoteItem> implements BrokerageNoteItemDAO {

	public BrokerageNoteItem findBrokerageNoteItemByOperation(
			Operation operation) {
		Query q = getEntityManager().createNamedQuery(
				BrokerageNoteItem.FIND_BY_OPERATION);
		q.setParameter("operationId", operation.getId());
		return (BrokerageNoteItem) q.getSingleResult();
	}

}
