package com.twocents.dao;

import com.twocents.model.TwUser;


public interface TwUserDAO extends BaseDAO<TwUser> {

	public TwUser findUserByName(String name);

}
