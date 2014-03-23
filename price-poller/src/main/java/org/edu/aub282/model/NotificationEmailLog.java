package org.edu.aub282.model;

/**
 * 
 * @author ambika_b
 *
 */
public class NotificationEmailLog {

	String userEmail;
	
	long productId;
	
	long styleId;
	
	String productName;
	
	String productUrl;
	
	boolean status;
	
	long logId;
	
	public NotificationEmailLog(String userEmail, long productId, long styleId,
			String productName, String productUrl, boolean status, long logId) {
		super();
		this.userEmail = userEmail;
		this.productId = productId;
		this.styleId = styleId;
		this.productName = productName;
		this.productUrl = productUrl;
		this.status = status;
		this.logId = logId;
	}

	public long getStyleId() {
		return styleId;
	}

	public void setStyleId(long styleId) {
		this.styleId = styleId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}
	
}