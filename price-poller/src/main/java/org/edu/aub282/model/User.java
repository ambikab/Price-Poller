/**
 * 
 */
package org.edu.aub282.model;

/**
 * @author ambika_b
 *
 */
public class User {

	long userId;
	
	String userEmail;
	
	public User() {}
	
	public User(long userId, String userEmail) {
		super();
		this.userId = userId;
		this.userEmail = userEmail;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
