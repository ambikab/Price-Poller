package org.edu.aub282.dto;

/**
 * 
 * @author ambika_b
 *
 */
public class ProductDTO {

	long styleId, productId;
	
	String productName , percentOff, brandName, price, originalPrice, productUrl, thumbnailImageUrl;
	
	int colorId;

	public long getStyleId() {
		return styleId;
	}

	public void setStyleId(long styleId) {
		this.styleId = styleId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPercentOff() {
		return percentOff.replace("%", "");
	}

	public void setPercentOff(String percentOff) {
		this.percentOff = percentOff;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getPrice() {
		return price.replace("$", "");
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOriginalPrice() {
		return originalPrice.replace("$", "");
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getThumbnailImageUrl() {
		return thumbnailImageUrl;
	}

	public void setThumbnailImageUrl(String thumbnailImageUrl) {
		this.thumbnailImageUrl = thumbnailImageUrl;
	}

	public int getColorId() {
		return colorId;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
	}

	@Override
	public String toString() {
		return "ProductDTO [styleId=" + styleId + ", productId=" + productId
				+ ", productName=" + productName + ", percentOff=" + getPercentOff()
				+ ", brandName=" + brandName + ", price=" + getPrice()
				+ ", originalPrice=" + getOriginalPrice() + ", productUrl="
				+ productUrl + ", thumbnailImageUrl=" + thumbnailImageUrl
				+ ", colorId=" + colorId + "]";
	}
	
}
