package com.twocents.dao;

import com.twocents.model.BrokerageNoteItem;
import com.twocents.model.Operation;

public interface BrokerageNoteItemDAO extends BaseDAO<BrokerageNoteItem> {

	public BrokerageNoteItem findBrokerageNoteItemByOperation(
			Operation operation);

}
