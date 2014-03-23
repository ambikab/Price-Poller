/**
 * 
 */
package org.edu.aub282.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.edu.aub282.model.Category;
import org.edu.aub282.util.DBConnection;

/**
 * @author ambika_b
 *
 */
public class CategoryRepository {

	Connection connection = DBConnection.getConnection();

	public ArrayList<Category> getAllCategory() {
		ArrayList<Category> categories = null;
		String getAllCategory = "select * from category";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(getAllCategory);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				if (categories == null) categories = new ArrayList<Category>();
				categories.add(new Category(resultSet.getLong("categoryId"), resultSet.getString("categoryName")));
			}
		} catch (SQLException sqlException){
			//TOOD: log exception.
		}
		return categories;
	}
}
