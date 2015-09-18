package com.twocents.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Feed extends Persistent{
	
	private static final long serialVersionUID = 329614312124360336L;
	
	private String title;
	
	private String link;
	
	private String description;
	
	private String type;
	
	private Date date;
		
	private List<FeedItem> feedItems;
	
	private FeedUpdateType feedUpdateType;
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public FeedUpdateType getFeedUpdateType() {
		return feedUpdateType;
	}

	public void setFeedUpdateType(FeedUpdateType feedUpdateType) {
		this.feedUpdateType = feedUpdateType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	/*
	public List<SyndFeedItem> getFeedItems() {
		return feedItems;
	}

	public void setFeedItems(List<SyndFeedItem> feedItems) {
		this.feedItems = feedItems;
	}
*/
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public Feed(){
		super();
	}
	
	
}
