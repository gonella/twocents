package com.twocents.model;

public enum MonthType {
	JAN("0"),
	FEB("1"),
	MAR("2"),
	ABR("3"),
	MAY("4"),
	JUN("5"),
	JUL("6"),
	AGO("7"),
	SET("8"),
	OUT("9"),	
	NOV("10"),
	DEZ("11")	
	;
	
	private String month;
	
	private MonthType(String month) {
		this.month = month;
	}
	
	public String toString(){
		return this.month;
	}

}
