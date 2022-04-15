package database.postgre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import beans.Department;
import beans.EventType;
import database.EventTypeDAO;
//Work on the SQL first, then come and add onto these.
import database.util.DatabaseUtil;

public class EventTypePostgre implements EventTypeDAO{
	// create table query, CREATE TABLE RequestType (ID SERIAL PRIMARY KEY NOT NULL, RequestType String NOT NULL);
	private static DatabaseUtil dbUtil = DatabaseUtil.getConnectionUtil();

	@Override
	public int create(EventType dataToCreate) {
		// TODO Auto-generated method stub
		int generatedID = 0;
		String sql_query = "insert into eventtype (ID, EventTypeName) values (default, ?);";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			conn.setAutoCommit(false);

			pStatement.setString(1, dataToCreate.getEventType());

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
	public EventType getById(int data) {
		EventType eventType = new EventType();
		String sql_query = "SELECT * FROM EventType WHERE EventType.ID=?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			pStatement.setInt(1,  data);
			ResultSet resultSet = pStatement.executeQuery();
			if(resultSet.next()) {
				eventType.setEventType(resultSet.getString("EventTypeName"));
				eventType.setEventTypeID(resultSet.getInt("ID"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return eventType;
	}

	@Override
	public Set<EventType> getAll() {
		Set<EventType> eventTypes = new HashSet<EventType>();

		String sql_query = "SELECT * FROM EventType;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			ResultSet resultSet = pStatement.executeQuery();
			while(resultSet.next()) {
				EventType eventType = new EventType(resultSet.getInt("ID"),resultSet.getString("EventTypeName"));
				eventTypes.add(eventType);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return eventTypes;
	}

	@Override
	public void update(EventType dataToUpdate) {
		String sql_query = "UPDATE EventType SET EventTypeName = ? WHERE ID = ?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			
			pStatement.setString(1, dataToUpdate.getEventType());
			pStatement.setInt(2, dataToUpdate.getEventTypeID());
			pStatement.executeQuery();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(EventType dataToDelete) {
		String sql_query = "Delete FROM EventType WHERE ID = ?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			
			pStatement.setInt(1, dataToDelete.getEventTypeID());

			pStatement.executeQuery();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
