package com.twocents.dao;

import javax.persistence.Query;

import com.twocents.model.TwUser;


public class TwUserHibernateDAO extends BaseDAOImpl<TwUser> implements TwUserDAO {
	
	public TwUser findUserByName(String name) {
		Query q = getEntityManager().createNamedQuery(TwUser.FIND_BY_NAME);
	    q.setParameter("name", name);	   
	    return (TwUser)q.getSingleResult();
	}

	
}
