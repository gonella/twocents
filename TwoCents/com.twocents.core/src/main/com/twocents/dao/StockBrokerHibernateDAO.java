package com.twocents.dao;

import javax.persistence.Query;

import com.twocents.model.StockBroker;


public class StockBrokerHibernateDAO extends BaseDAOImpl<StockBroker> implements StockBrokerDAO {

    //@Override
    public StockBroker findStockBrokerByName(String name) {
        Query q = getEntityManager().createNamedQuery(StockBroker.FIND_BY_NAME);
        q.setParameter("name", name);
        return (StockBroker)q.getSingleResult();

    }


    public StockBroker findStockBrokerByUsernameAndPassword(String username,
            String password) {
        Query q = getEntityManager().createNamedQuery(StockBroker.FIND_BY_USERNAME_AND_PASSWORD);
        q.setParameter("username", username);
        q.setParameter("password", password);

        return (StockBroker)q.getSingleResult();
    }


    public StockBroker findStockBrokerByUsername(String username) {
        Query q = getEntityManager().createNamedQuery(StockBroker.FIND_BY_USERNAME);
        q.setParameter("username", username);
        return (StockBroker)q.getSingleResult();
    }

}
