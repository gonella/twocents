package com.twocents.dao;

import java.util.List;

import javax.persistence.Query;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Broker;
import com.twocents.model.StockBroker;

public class BrokerHibernateDAO extends BaseDAOImpl<Broker> implements
		BrokerDAO {

	public Broker findBrokerByName(String name, StockBroker stockBroker) throws CoreException {

		Query q = getEntityManager().createNamedQuery(
				Broker.FIND_BROKER_BY_NAME);
		q.setParameter("name", name);
		q.setParameter("stockBrokerId", stockBroker.getId());
		return (Broker) q.getSingleResult();
	}

	public List<Broker> findBrokerByStockBoker(StockBroker stockBroker)
			throws CoreException {

		Query q = getEntityManager().createNamedQuery(
				Broker.FIND_BROKER_BY_STOCK_BROKER);
		q.setParameter("stockBrokerId", stockBroker.getId());
		return (List<Broker>) q.getResultList();
	}

}
