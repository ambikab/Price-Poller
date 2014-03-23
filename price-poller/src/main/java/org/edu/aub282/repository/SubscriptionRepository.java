/**
 * 
 */
package org.edu.aub282.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.edu.aub282.model.Product;
import org.edu.aub282.model.User;
import org.edu.aub282.util.DBConnection;

/**
 * @author ambika_b
 *
 */
public class SubscriptionRepository {

	UserRepository userRepository = new UserRepository();

	Connection connection = DBConnection.getConnection();

	public boolean addSubscriptions(User user, ArrayList<Product> products) {
		String addSubscription = "insert into subscriptions(userId, itemId, status) values(?,?,?)";
		for (Product product : products) {
			PreparedStatement preparedStatement;
			try {
				preparedStatement = connection.prepareStatement(addSubscription);
				preparedStatement.setLong(1, user.getUserId());
				preparedStatement.setLong(2, product.getItemId());
				preparedStatement.setBoolean(3, true);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public boolean isSubscribed(User user, ArrayList<Product> products) {
		StringBuffer isSubscribed = new StringBuffer("select count(1) from subscriptions where userId = ? and itemId in ( ? ");
		for (int  i = 1; i < products.size(); i++)
			isSubscribed.append(" , ? ");
		isSubscribed.append(" ) ");
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(isSubscribed.toString());
			preparedStatement.setLong(1, user.getUserId());
			int i = 2;
			for (Product product : products) 
				preparedStatement.setLong(i++, product.getItemId());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int count = resultSet.getInt(1);
				return count > 0;
			}
		} catch(SQLException sqlException) {
			
		}
		for (int i = 0; i < products.size(); i++) {
			
		}
		return false;
	}
}