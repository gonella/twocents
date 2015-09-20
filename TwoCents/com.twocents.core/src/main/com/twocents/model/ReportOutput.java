package com.twocents.model;

public class ReportOutput {

	private OutputType type;
	private String fileName;
	
	public ReportOutput(OutputType type){
		this.type=type;
	}
	public ReportOutput(OutputType type,String fileName){
		this.type=type;
		this.fileName=fileName;
	}
	public OutputType getType() {
		return type;
	}
	public String getFileName() {
		return fileName;
	}
}
