package org.edu.aub282.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.edu.aub282.model.EmailBatch;
import org.edu.aub282.model.NotificationEmailLog;
import org.edu.aub282.util.DBConnection;

/**
 * 
 * @author ambika_b
 *
 */
public class NotificationEmailLogRepository {

	Connection connection = DBConnection.getConnection();

	public ArrayList<NotificationEmailLog> getNotifySubscriptions(EmailBatch currentBatch) {
		ArrayList<NotificationEmailLog> results = null;
		String getNotifySubscriptions = "select logId, userEmail, productId, productName, styleId, imageUrl from notification_log nl , subscriptions subs, user u, product p ";
		String whereClause = " where nl.subscriptionId = subs.subscriptionId and subs.userId = u.userId "
				+ " and p.itemId = subs.itemId "
				+ " and nl.batchId = ? ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(getNotifySubscriptions + whereClause);
			preparedStatement.setLong(1, currentBatch.getBatchId());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				if (results == null) results = new ArrayList<NotificationEmailLog>();
				String userEmail = resultSet.getString("userEmail");
				long productId = resultSet.getLong("productId");
				long logId = resultSet.getLong("logId");
				String productName = resultSet.getString("productName");
				String imageUrl = resultSet.getString("imageUrl");
				long styleId = resultSet.getLong("styleId");
				results.add(new NotificationEmailLog(userEmail, productId, styleId, productName, imageUrl, false, logId));
			}
		} catch (SQLException sqlException) {

		}
		return results;
	}

}