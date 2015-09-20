package com.twocents.service;

import org.apache.log4j.Logger;

import com.twocents.dao.BrokerageNoteItemDAO;
import com.twocents.exceptions.CoreException;
import com.twocents.model.BrokerageNoteItem;
import com.twocents.model.Operation;

public class BrokerageNoteItemServiceImpl extends BaseServiceImpl<BrokerageNoteItem, BrokerageNoteItemDAO> implements BrokerageNoteItemService {


    private static final Logger logger = Logger.getLogger(BrokerageNoteItemServiceImpl.class);

    public void setBrokerageNoteItemDAO(BrokerageNoteItemDAO dao) {
        super.setDao(dao);
    }

    public BrokerageNoteItemDAO getBrokerageNoteItemDAO() {
        return super.getDao();
    }


    public BrokerageNoteItem saveItem(BrokerageNoteItem item) throws CoreException{

        logger.debug("Salvando item da nota de corretagem : "+item.toString());
        getBrokerageNoteItemDAO().persist(item);

        return item;
    }


    public BrokerageNoteItem findBrokerageNoteItemByOperation(
            Operation operation) {
        return getDao().findBrokerageNoteItemByOperation(operation);
    }





}
