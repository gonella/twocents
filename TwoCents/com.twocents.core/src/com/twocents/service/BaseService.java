package com.twocents.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Throwable.class, CoreException.class})
public interface BaseService<T> {

	public void persist(T transientInstance);

	public void attachClean(T instance);

	public void delete(T persistentInstance);
	
	public void deleteById(java.lang.Long id);

	public T merge(T detachedInstance);

	public T findById( java.lang.Long id);

	public List<T> findAll();

}
