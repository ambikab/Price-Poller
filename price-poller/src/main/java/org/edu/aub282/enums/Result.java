package org.edu.aub282.enums;

/**
 * 
 * @author ambika_b
 *
 */
public enum Result {

	SUCCESS("SUCCESS"), FAILURE("FAILURE");

	private String statusValue;

	private Result(String statusValue) {
		this.statusValue = statusValue;
	}

	public String statusValue() { 
		return this.statusValue; 
	}
}
