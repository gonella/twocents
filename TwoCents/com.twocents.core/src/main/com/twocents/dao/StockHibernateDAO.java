package com.twocents.dao;

import java.util.List;

import javax.persistence.Query;

import com.twocents.model.Stock;

@SuppressWarnings("unchecked")
public class StockHibernateDAO extends BaseDAOImpl<Stock> implements StockDAO {

	public Stock findByCode(String code) {
		 Query q = getEntityManager().createNamedQuery(Stock.FIND_BY_CODE);
	     q.setParameter("code", code);
	     return (Stock)q.getSingleResult();
	}

	
	public List<Stock> findByCodePrefix(String codePrefix) {
		Query q = getEntityManager().createNamedQuery(Stock.FIND_BY_CODEPREFIX);
	    q.setParameter("codePrefix", codePrefix + "%");
	    return q.getResultList();
	}

}
