/**
 * 
 */
package org.edu.aub282.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.edu.aub282.model.User;
import org.edu.aub282.util.DBConnection;

/**
 * @author ambika_b
 *
 */
public class UserRepository {

	Connection connection = DBConnection.getConnection();
	
	/**
	 * Returns null if a user does not exist.
	 * @param userMailId
	 * @return
	 */
	public User getUser(String userMailId) {
		User user = null;
		String findUser = "select * from user where userEmail = ? ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(findUser);
			preparedStatement.setString(1, userMailId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				user = new User();
				user.setUserEmail(resultSet.getString("userEmail"));
				user.setUserId(resultSet.getLong("userId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * Creates a new user in the system.
	 * @param userMailId
	 */
	public void createUser(String userMailId) {
		String createUser = "insert into user(userEmail) values(?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(createUser);
			preparedStatement.setString(1, userMailId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
