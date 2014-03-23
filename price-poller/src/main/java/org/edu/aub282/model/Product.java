/**
 * 
 */
package org.edu.aub282.model;

import org.edu.aub282.dto.ProductDTO;

/**
 * @author ambika_b
 *
 */
public class Product {
	
	Long categoryId;
	
	long itemId;
	
	long productId;
	
	long styleId;
	
	double originalPrice;
	
	int percentOff;
	
	double currentPrice;
	
	String url;
	
	String productName;
	
	public Product(Long category, long itemId, long productId,
			long styleId, double originalPrice, int percentOff,
			double currentPrice, String url, String productName) {
		super();
		this.categoryId = category;
		this.itemId = itemId;
		this.productId = productId;
		this.styleId = styleId;
		this.originalPrice = originalPrice;
		this.percentOff = percentOff;
		this.currentPrice = currentPrice;
		this.url = url;
		this.productName = productName;
	}

	public Product(ProductDTO productDto, Category category) {
		this.categoryId = category.getCategoryId();
		this.productId = productDto.getProductId();
		this.itemId = productDto.getStyleId();
		this.originalPrice = Double.valueOf(productDto.getOriginalPrice());
		this.percentOff = Integer.parseInt(productDto.getPercentOff());
		this.currentPrice = Double.valueOf(productDto.getPrice());
	}
	
	public Long getCategory() {
		return categoryId;
	}

	public void setCategory(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getStyleId() {
		return styleId;
	}

	public void setStyleId(long styleId) {
		this.styleId = styleId;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public int getPercentOff() {
		return percentOff;
	}

	public void setPercentOff(int percentOff) {
		this.percentOff = percentOff;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	
}