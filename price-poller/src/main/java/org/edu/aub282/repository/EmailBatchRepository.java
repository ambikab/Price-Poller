package org.edu.aub282.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.edu.aub282.enums.Progress;
import org.edu.aub282.model.EmailBatch;
import org.edu.aub282.util.DBConnection;

/**
 * 
 * @author ambika_a
 *
 */
public class EmailBatchRepository {

	Connection connection = DBConnection.getConnection();

	public boolean isAllPreviousComplete() {
		String isComplete = "select * from email_batch where status in (?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(isComplete);
			preparedStatement.setString(1, Progress.START.getProgressValue());
			preparedStatement.setString(2, Progress.INPROGRESS.getProgressValue());
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				return false;
			}
		} catch (SQLException sqlException) {
			return false;
		}
		return true;		
	}

	public boolean updateStatus(EmailBatch emailBatch) {
		String updateStatus = "update email_batch set status = ? , lastUpdatedOn = ? where batchId = ? ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(updateStatus);
			preparedStatement.setString(1, emailBatch.getStatus());
			preparedStatement.setTimestamp(2, emailBatch.getDate());
			preparedStatement.setLong(3, emailBatch.getBatchId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public EmailBatch startNewBatch() {
		EmailBatch emailBatch = null;
		String startNewBatch = "insert into email_batch(lastUpdatedOn, status) values(?,?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(startNewBatch);
			preparedStatement.setTimestamp(1, new Timestamp(new java.util.Date().getTime()));
			preparedStatement.setString(2, Progress.START.getProgressValue());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			return emailBatch;
		}
		String getBatchId = "select * from email_batch where status = ? ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(getBatchId);
			preparedStatement.setString(1, Progress.START.getProgressValue());
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			emailBatch = new EmailBatch(resultSet.getLong("batchId"), resultSet.getTimestamp("lastUpdatedOn"), resultSet.getString("status"));
		} catch (SQLException sqlException) {
			return emailBatch;
		}
		return emailBatch;
	}	
}