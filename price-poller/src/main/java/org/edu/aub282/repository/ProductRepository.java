/**
 * 
 */
package org.edu.aub282.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.edu.aub282.dto.ProductDTO;
import org.edu.aub282.model.Product;
import org.edu.aub282.util.DBConnection;

/**
 * @author ambika_b
 *
 */
public class ProductRepository {

	Connection connection = DBConnection.getConnection();

	public ArrayList<Product> fetchProducts(Long productId) {
		ArrayList<Product> results = null;
		String fetchProducts = "select * from product where productId = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(fetchProducts);
			preparedStatement.setLong(1, productId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				if (results == null) results = new ArrayList<Product>();
				Long itemId = resultSet.getLong("itemId"), styleId = resultSet.getLong("styleId"), categoryId = resultSet.getLong("categoryId");
				double originalPrice = resultSet.getDouble("originalPrice"), currentPrice = resultSet.getDouble("price");
				int percentOff = resultSet.getInt("percentOff");
				String productName = resultSet.getString("productName"), imageUrl = resultSet.getString("imageUrl");
				results.add(new Product(categoryId, itemId, productId, styleId, originalPrice, percentOff, currentPrice, imageUrl, productName));
			}
		}catch (SQLException sqlException) {
		}
		return results;
	}

	public ArrayList<Long> getDistinctProducts() {
		String getDistinctProducts = "select distinct(productId) from product";
		ArrayList<Long> products = null;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(getDistinctProducts);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				if (products == null) products = new ArrayList<Long>();
				products.add(resultSet.getLong("productId"));
			}
			System.out.println("Number of products = " + products.size());
			return products;
		} catch(SQLException sqlException) {
			return products;
		}
	}

	public void batchInsert(ArrayList<ProductDTO> products) {
		String batchInsert = "insert into product(productId, styleId, percentOff, originalPrice, price, categoryId, imageUrl, productName) values(?, ?, ?, ?, ?, 1, ?, ?)";
		try {
			for (ProductDTO product : products) {
				PreparedStatement preparedStatement = connection.prepareStatement(batchInsert);
				preparedStatement.setLong(1, product.getProductId());
				preparedStatement.setLong(2, product.getStyleId());
				preparedStatement.setInt(3, Integer.parseInt(product.getPercentOff()));
				preparedStatement.setDouble(4, Double.valueOf(product.getOriginalPrice()));
				preparedStatement.setDouble(5,Double.valueOf(product.getPrice()));
				preparedStatement.setString(6, product.getThumbnailImageUrl());
				preparedStatement.setString(7, product.getProductName());
				preparedStatement.executeUpdate();
			}
		} catch (SQLException sqlException ) {
			System.out.println(sqlException.getMessage());
		}
	}

	/**
	 * Batch update of the product price is done.
	 * @param products
	 */
	public void batchUpdate(ArrayList<ProductDTO> products) {
		String batchUpdate = "update product set percentOff = ? , price = ? where productId = ? and styleId = ? ";
		try {
			connection.setAutoCommit(false);
			PreparedStatement batchPreparedStatement = connection.prepareStatement(batchUpdate);
			for (ProductDTO productDto : products) {
				batchPreparedStatement.setInt(1, Integer.parseInt(productDto.getPercentOff()));
				batchPreparedStatement.setDouble(2, Double.valueOf(productDto.getPrice()));
				batchPreparedStatement.setLong(3, productDto.getProductId());
				batchPreparedStatement.setLong(4, productDto.getStyleId());
				batchPreparedStatement.addBatch();
			} 
			batchPreparedStatement.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException sqlException) {
			System.out.println("exception ocuured");
			sqlException.printStackTrace();
		}
	}
}