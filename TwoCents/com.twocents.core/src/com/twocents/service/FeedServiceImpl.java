package com.twocents.service;


import org.apache.log4j.Logger;

import com.twocents.dao.FeedDAO;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Feed;

public class FeedServiceImpl extends BaseServiceImpl<Feed, FeedDAO> implements FeedService {
	
	private static final Logger logger = Logger.getLogger(FeedServiceImpl.class);
	
	public void setFeedDAO(FeedDAO dao) {
		super.setDao(dao);
	}
	
	public FeedDAO getFeedDAO() {
		return super.getDao();
	}
	
	public void addFeed(Feed feed) throws CoreException {
		getDao().persist(feed);
	}

	public void removeFeed(Feed feed) throws CoreException {
		getDao().delete(feed);
		
	}
	
}
