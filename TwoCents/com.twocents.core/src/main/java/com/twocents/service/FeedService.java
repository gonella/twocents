package com.twocents.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Feed;


@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Throwable.class, CoreException.class})
public interface FeedService extends BaseService<Feed> {
	
	String BEAN_NAME = "com.twocents.service.FeedService";

	public void addFeed(Feed feed) throws CoreException;

	public void removeFeed(Feed feed) throws CoreException;
}
