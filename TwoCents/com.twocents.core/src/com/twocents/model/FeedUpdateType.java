package com.twocents.model;

public enum FeedUpdateType {

	UPDATE_1MIN("1 min"),
	UPDATE_3MIN("3 min"),
	UPDATE_5MIN("5 min"),
	UPDATE_10MIN("10 min"),
	UPDATE_15MIN("15 min"),
	UPDATE_30MIN("30 min"),
	UPDATE_1H("1h");
	
	
	private String Type;
	
	private FeedUpdateType(String Type) {
		this.Type = Type;
	}
	
	public String toString(){
		return this.Type;
	}
	
	public int toTime(FeedUpdateType type){
		
		if(UPDATE_1MIN.equals(type)){
			return 1;
		}
		else if(UPDATE_5MIN.equals(type)){
			return 5;
		}
		else if(UPDATE_10MIN.equals(type)){
			return 10;
		}
		else if(UPDATE_30MIN.equals(type)){
			return 1;
		}		
		else if(UPDATE_1H.equals(type)){
			return 60;
		}
		else {
			return 15;
		}
		
	}
}
