package com.twocents.service;

import java.util.List;

import com.twocents.dao.BaseDAO;
import com.twocents.model.Persistent;


public abstract class BaseServiceImpl<T extends Persistent, DAO extends BaseDAO<T>> 
	implements BaseService<T> {
	
	private DAO dao;

	protected DAO getDao() {
		return dao;
	}

	protected void setDao(DAO dao) {
		this.dao = dao;
	}

	public void attachClean(T instance) {
		dao.attachClean(instance);
		
	}

	public void delete(T persistentInstance) {
		dao.delete(persistentInstance);
		
	}

	public List<T> findAll() {
		return dao.findAll();
	}

	public T findById(Long id) {
		return dao.findById(id);
	}

	public T merge(T detachedInstance) {
		return dao.merge(detachedInstance);
	}

	public void persist(T transientInstance) {
		if(transientInstance.getId() != null) {
			dao.merge(transientInstance);
		} else {
			dao.persist(transientInstance);
		}	
	}

	public void deleteById(Long id) {
		dao.deleteById(id);
	}
	
	

}
