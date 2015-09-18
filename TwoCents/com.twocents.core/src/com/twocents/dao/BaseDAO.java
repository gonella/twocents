package com.twocents.dao;

import java.util.List;

import com.twocents.model.Persistent;

public interface BaseDAO<T extends Persistent> {


	    
	    public void persist(T transientInstance);
	    
	    public void attachClean(T instance);
	    
	    public void delete(T persistentInstance);
	    
	    public void deleteById(java.lang.Long id) ;
	    
	    public T merge(T detachedInstance) ;
	    
	    public T findById( java.lang.Long id) ;
	    
	    public List<T> findAll();

	
}
