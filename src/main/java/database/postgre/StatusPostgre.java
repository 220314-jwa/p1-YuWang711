package database.postgre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import beans.Department;
import beans.EventType;
import beans.Request;
import beans.Status;
import database.StatusDAO;
import database.util.DatabaseUtil;

public class StatusPostgre implements StatusDAO{
	// create table query, CREATE TABLE Status (ID SERIAL PRIMARY KEY NOT NULL, StatusType String NOT NULL);
	private static DatabaseUtil dbUtil = DatabaseUtil.getConnectionUtil();

	@Override
	public int create(Status dataToCreate) {
		int generatedID = 0;
		String sql_query = "insert into status (ID, StatusName) values (default, ?);";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			conn.setAutoCommit(false);

			pStatement.setString(1, dataToCreate.getStatusType());

			pStatement.executeUpdate();
			ResultSet resultSet = pStatement.getGeneratedKeys();

			//Get the EmployeeID Generated when creating new data
			if (resultSet.next()) { 
				generatedID = resultSet.getInt("ID");
				conn.commit(); 
			} else {
				conn.rollback();
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return generatedID;
	}

	@Override
	public Status getById(int data) {
		Status status = new Status();
		String sql_query = "SELECT * FROM Status WHERE Status.ID=?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			pStatement.setInt(1,  data);
			ResultSet resultSet = pStatement.executeQuery();
			if(resultSet.next()) {
				status = new Status(resultSet.getInt("ID"),resultSet.getString("StatusName"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Set<Status> getAll() {
		Set<Status> statuses = new HashSet<Status>();
		String sql_query = "SELECT * FROM Status;";
		try( Connection conn = dbUtil.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql_query);){
				ResultSet resultSet = pStatement.executeQuery();
				
				while(resultSet.next()) {
					Status status = new Status(resultSet.getInt("ID"),resultSet.getString("StatusName"));
					statuses.add(status);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return statuses;
	}

	@Override
	public void update(Status dataToUpdate) {
		String sql_query = "UPDATE Status SET StatusName = ? WHERE ID = ?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			
			pStatement.setString(1, dataToUpdate.getStatusType().toString());
			pStatement.setInt(2, dataToUpdate.getStatus());
			pStatement.executeQuery();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Status dataToDelete) {
		String sql_query = "Delete FROM Status WHERE ID = ?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			
			pStatement.setInt(1, dataToDelete.getStatus());

			pStatement.executeQuery();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
