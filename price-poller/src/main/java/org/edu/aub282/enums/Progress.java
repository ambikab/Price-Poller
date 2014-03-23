package org.edu.aub282.enums;

public enum Progress {

	START("START"), COMPLETE("COMPLETE"), INPROGRESS("INPROGRESS"), ABORT("ABORT");
	
	private String progressValue;
	
	private Progress(String progressValue) {
		this.progressValue = progressValue;
	}
	
	public String getProgressValue() {
		return this.progressValue;
	}
	
}
