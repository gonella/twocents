package com.twocents.service;

import org.apache.log4j.Logger;

import com.twocents.dao.TwUserDAO;
import com.twocents.exceptions.CoreException;
import com.twocents.model.TwUser;
import com.twocents.resources.CoreMessages;

public class TwUserServiceImpl extends BaseServiceImpl<TwUser, TwUserDAO> implements TwUserService {
	
	private static final Logger log = Logger.getLogger(TwUserServiceImpl.class);
	
	public void setTwUserDAO(TwUserDAO dao) {
		super.setDao(dao);
	}
	
	public TwUserDAO getTwUserDAO() {
		return super.getDao();
	}

	public TwUser findUserByName(String name) throws CoreException {
		try {			
			return getTwUserDAO().findUserByName(name);
		} catch (RuntimeException e) {
			log.debug(CoreMessages.getMessage(1002) +" - " + e.getMessage());
			return null;
		}
	}

	public void addUser(TwUser user) throws CoreException {
		getDao().persist(user);
	}

	public void removeUser(TwUser user) throws CoreException {
		getDao().delete(user);
	}
	
}
