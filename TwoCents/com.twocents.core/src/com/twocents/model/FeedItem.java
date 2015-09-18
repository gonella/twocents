package com.twocents.model;

import javax.persistence.Entity;


@Entity
public class FeedItem extends Persistent{
	
	private static final long serialVersionUID = -4018343719776195488L;

	private String news;
	private String date;
	
	public String getNews() {
		return news;
	}
	public void setNews(String news) {
		this.news = news;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public FeedItem(){
		super();
	}
	
}
