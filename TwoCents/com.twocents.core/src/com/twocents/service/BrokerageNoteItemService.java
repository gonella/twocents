package com.twocents.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;
import com.twocents.model.BrokerageNoteItem;
import com.twocents.model.Operation;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
		Throwable.class, CoreException.class })
public interface BrokerageNoteItemService extends
		BaseService<BrokerageNoteItem> {

	String BEAN_NAME = "com.twocents.service.BrokerageNoteItemService";

	public BrokerageNoteItem saveItem(BrokerageNoteItem item)
			throws CoreException;

	public BrokerageNoteItem findBrokerageNoteItemByOperation(
			Operation operation);

}
