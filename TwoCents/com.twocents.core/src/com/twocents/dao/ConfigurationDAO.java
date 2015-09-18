package com.twocents.dao;

import java.util.List;

import com.twocents.model.Configuration;


public interface ConfigurationDAO extends BaseDAO<Configuration> {
	
	
	public List<? extends Configuration> findByType(Class<?> classe);
	
}
