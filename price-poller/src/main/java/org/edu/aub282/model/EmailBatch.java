package org.edu.aub282.model;

import java.sql.Timestamp;

/**
 * 
 * @author ambika_b
 *
 */
public class EmailBatch {

	long batchId;
	
	Timestamp date;
	
	String status;

	public EmailBatch(long batchId, Timestamp date, String status) {
		super();
		this.batchId = batchId;
		this.date = date;
		this.status = status;
	}

	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}