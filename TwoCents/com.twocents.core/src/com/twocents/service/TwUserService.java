package com.twocents.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;
import com.twocents.model.TwUser;


@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Throwable.class, CoreException.class})
public interface TwUserService extends BaseService<TwUser> {
	
	String BEAN_NAME = "com.twocents.service.TwUserService";

	public void addUser(TwUser user) throws CoreException;
	
	public void removeUser(TwUser user) throws CoreException;

	public TwUser findUserByName(String name) throws CoreException;

	
}
