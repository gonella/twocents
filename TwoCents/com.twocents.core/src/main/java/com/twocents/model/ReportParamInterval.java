package com.twocents.model;

import java.sql.Timestamp;

public class ReportParamInterval extends ReportParam{

	private Timestamp dateStart=new Timestamp(0);
	private Timestamp dateEnd=new Timestamp(0);
	
	public ReportParamInterval(String title,ReportGroupType type){
		super(title,type);
	}

	public Timestamp getDateStart() {
		return dateStart;
	}

	public void setDateStart(Timestamp dateStart) {
		this.dateStart = dateStart;
	}

	public Timestamp getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Timestamp dateEnd) {
		this.dateEnd = dateEnd;
	}
	
}
