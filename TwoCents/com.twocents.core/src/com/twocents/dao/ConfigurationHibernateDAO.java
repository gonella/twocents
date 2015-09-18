package com.twocents.dao;

import java.util.List;

import javax.persistence.Query;

import com.twocents.model.Configuration;

@SuppressWarnings("unchecked")
public class ConfigurationHibernateDAO extends BaseDAOImpl<Configuration> implements ConfigurationDAO {
	
	public List<? extends Configuration> findByType(Class<?> classe) {
		Query q = getEntityManager().createQuery("select o from Configuration o where o.class = "+classe.getName());
	    return q.getResultList();
	}
	
}
