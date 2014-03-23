/**
 * 
 */
package org.edu.aub282.model;

/**
 * @author ambika_b
 *
 */
public class Subscription {

	User user;
	
	Product product;
	
	boolean status;
	
	public Subscription(User user, Product product, boolean status) {
		super();
		this.user = user;
		this.product = product;
		this.status = status;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
