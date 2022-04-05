package database.postgre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import database.RequestDAO;
import database.util.DatabaseUtil;
import main.Request;
import main.EventType;
/*
Select request FROM request r, employee e
WHERE r.submitter_ID = (SELECT employeeID from
Employee where managerID = currentemployeeID)
*/
import main.Status;

public class RequestPostgre implements RequestDAO{
	private static DatabaseUtil dbUtil = DatabaseUtil.getConnectionUtil();

	@Override
	public int create(Request dataToCreate) {
		int generatedID = 0;
		String sql_query = "insert into REQUESTS (ID,Submitter_id,Event_type_id,Status_id,"
				+ "Event_date, Costs,Description,Locations,Submitted_at)"
				+ "values (default,?,?,?,?,?,?,?,?);";
		String[] keys = { "id" };
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query,keys);){
			conn.setAutoCommit(false);

			pStatement.setInt(1, dataToCreate.getSubmitterId());
			pStatement.setInt(2, dataToCreate.getEventTypeId().getEventTypeID());
			pStatement.setInt(3, dataToCreate.getStatusId().getStatus());
			pStatement.setString(4, dataToCreate.getEventDate().toString());
			pStatement.setDouble(5, dataToCreate.getCost());
			pStatement.setString(6, dataToCreate.getDescription());
			pStatement.setString(7, dataToCreate.getLocation());
			pStatement.setString(8, dataToCreate.getSubmittedAt());
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
			return -1;
		}
		return generatedID;
	}

	@Override
	public Request getById(int requestID) {
		/*
		" (ID INT PRIMARY 	KEY    NOT NULL," +
		Submitter_id
		" Event_type_id     INT    NOT NULL," +
		" Status_id      	INT    NOT NULL," +
		" Event_date     	TEXT	NOT NULL," +
		" Cost	 			REAL	NOT NULL," +
		" Description   	TEXT 	NOT NULL," +
		" Location 	    	TEXT	NOT NULL," +
		" Submitted_at  
 * 
 */
		Request request = null;
		String sql_query = "SELECT REQ.ID, REQ.Submitter_id,REQ.Event_type_id,REQ.Status_id,REQ.Event_date,"
				+ "REQ.Costs,REQ.Description,REQ.Locations,"
				+ "REQ.Submitted_at,  S.StatusName, ET.EventTypeName FROM REQUESTS As REQ "
				+ "INNER JOIN Status as S ON  S.ID = REQ.Status_id "
				+ "INNER JOIN EventType as ET ON ET.ID = REQ.Event_type_id "
				+ "WHERE REQ.ID=?"
				+ ";";
		//Need to use join to get event type
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			pStatement.setInt(1,  requestID);
			ResultSet resultSet = pStatement.executeQuery();
				
			if(resultSet.next()) {
				request = new Request();
				LocalDate date = LocalDate.parse(resultSet.getString("Event_date"));
				EventType eventTypeID = new EventType(resultSet.getInt("Event_type_id"),resultSet.getString("EventTypeName"));
				Status statusID = new Status(resultSet.getInt("Status_id"),resultSet.getString("StatusName"));
				request.setRequestID(requestID);
				request.setEventTypeId(eventTypeID);
				request.setStatusId(statusID);
				request.setEventDate(date);
				request.setCost(resultSet.getDouble("Costs"));
				request.setDescription(resultSet.getString("Description"));
				request.setLocation(resultSet.getString("Locations"));
				request.setSubmittedAt(resultSet.getString("Submitted_at"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return request;
	}

	@Override
	public Set<Request> getAll() {
		// Possibly don't need get all, it is for testing purposes
		// TODO Auto-generated method stub
		Set<Request> requests = new HashSet<Request>();
		String sql_query = "SELECT REQ.ID, REQ.Submitter_id,REQ.Event_type_id,REQ.Status_id,REQ.Event_date,"
				+ "REQ.Costs,REQ.Description,REQ.Locations,"
				+ "REQ.Submitted_at,  S.StatusName, ET.EventTypeName FROM REQUESTS As REQ "
				+ "INNER JOIN Status as S ON  S.ID = REQ.Status_id "
				+ "INNER JOIN EventType as ET ON ET.ID = REQ.Event_type_id;"
				+ ";";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			ResultSet resultSet = pStatement.executeQuery();
			
			while(resultSet.next()) {
				LocalDate date = LocalDate.parse(resultSet.getString("Event_date"));
				Request request = new Request();
				EventType eventTypeID = new EventType(resultSet.getInt("Event_type_id"),resultSet.getString("EventTypeName"));
				Status statusID = new Status(resultSet.getInt("Status_id"),resultSet.getString("StatusName"));
				
				request.setEventTypeId(eventTypeID);
				request.setStatusId(statusID);
				request.setRequestID(resultSet.getInt("ID"));
				request.setEventDate(date);
				request.setCost(resultSet.getDouble("Costs"));
				request.setDescription(resultSet.getString("Description"));
				request.setLocation(resultSet.getString("Locations"));
				request.setSubmittedAt(resultSet.getString("Submitted_at"));
				requests.add(request);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return requests;	
	}

	@Override
	public void update(Request dataToUpdate) {
		// TODO Auto-generated method stub
		/*
		" (ID INT PRIMARY 	KEY    NOT NULL," +
		Submitter_id
		" Event_type_id     INT    NOT NULL," +
		" Status_id      	INT    NOT NULL," +
		" Event_date     	TEXT	NOT NULL," +
		" Cost	 			REAL	NOT NULL," +
		" Description   	TEXT 	NOT NULL," +
		" Location 	    	TEXT	NOT NULL," +
		" Submitted_at  
 * 
 */
		String sql_query = "UPDATE REQUESTS "
				+ "SET Event_date = ?,Costs = ?,Description = ?,"
				+ "Locations = ? "
				+ "WHERE ID = ?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			conn.setAutoCommit(false);
			
			pStatement.setString(1, dataToUpdate.getEventDate().toString());
			pStatement.setDouble(2, dataToUpdate.getCost());
			pStatement.setString(3, dataToUpdate.getDescription());
			pStatement.setString(4, dataToUpdate.getLocation());
			pStatement.setInt(5, dataToUpdate.getRequestID());
			int rowsAffected = pStatement.executeUpdate();
			
			boolean requestUpdated = true;

			//Only one request should be affected at once
			if (rowsAffected <= 1 && requestUpdated) {
				conn.commit();
			} else {
				conn.rollback();
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Request dataToDelete) {
		//Delete might never ever be called?
		String sql_query = "DELETE FROM REQUESTS"
				+ "WHERE ID = ?";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			pStatement.setInt(1, dataToDelete.getRequestID());
			pStatement.executeQuery();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
/*
	@Override
	public void createTable() {
		String sql_query = "IF (SELECT count(*) "+ 
				"WHERE table_name = 'REQUESTS' " +
				"LIMIT 1;" + 
				") THEN"+
				"CREATE TABLE REQUESTS " + 
				" (ID SERIAL PRIMARY KEY    NOT NULL,"
				+ "Submitter_id		INT	   NOT NULL," +
				" Event_type_id     INT    FOREIGN KEY   NOT NULL," +
				" Status_id      	INT    FOREIGN KEY	 NOT NULL," +
				" Event_date     	TEXT	NOT NULL," +
				" Cost	 			REAL	NOT NULL," +
				" Description   	TEXT 	NOT NULL," +
				" Location 	    	TEXT	NOT NULL," +
				" Submitted_at  	TEXT	NOT NULL)"
				+ "END IF";
		try (Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query)){
			pStatement.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}*/

	@Override
	public Set<Request> getAllRequestsBySubmitterID(int SubmitterID) {
		Set<Request> requests = new HashSet<Request>();
		//fix the sql query
		//Need to rewrite the query
		String sql_query = "SELECT REQUESTS.ID,Event_type_id,Status_id,Event_date,"
				+ "Costs,Description,Locations,Submitted_at FROM REQUESTS WHERE Submitter_id = ?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			pStatement.setInt(1, SubmitterID);
			ResultSet resultSet = pStatement.executeQuery();
			
			while(resultSet.next()) {
				LocalDate date = LocalDate.parse(resultSet.getString("Event_date"));
				EventType eventTypeID = new EventType(resultSet.getInt("Event_type_id"),"");
				Status statusID = new Status(resultSet.getInt("Status_id"),"");
				Request request = new Request();
				
				request.setRequestID(resultSet.getInt("ID"));
				request.setEventTypeId(eventTypeID);
				request.setStatusId(statusID);
				request.setEventDate(date);
				request.setCost(resultSet.getDouble("Costs"));
				request.setDescription(resultSet.getString("Description"));
				request.setLocation(resultSet.getString("Locations"));
				request.setSubmittedAt(resultSet.getString("Submitted_at"));
				requests.add(request);

			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return requests;	
	}

	@Override
	public Set<Request> getAllRequestsByDeptID(int DeptID) {
		Set<Request> requests = new HashSet<Request>();
		//fix the sql query

		String sql_query = "SELECT REQUESTS.ID,Event_type_id,Status_id,Event_date,Costs,Description,Locations,Submitted_at FROM REQUESTS "
				+ "	inner join Employee on REQUESTS.submitter_id = Employee.id and Employee.DeptHeadID  = ?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			pStatement.setInt(1, DeptID);
			ResultSet resultSet = pStatement.executeQuery();
			
			while(resultSet.next()) {
				LocalDate date = LocalDate.parse(resultSet.getString("Event_date"));
				EventType eventTypeID = new EventType(resultSet.getInt("Event_type_id"),"");
				Status statusID = new Status(resultSet.getInt("Status_id"),"");
				Request request = new Request();
					
				request.setRequestID(resultSet.getInt("ID"));
				request.setEventTypeId(eventTypeID);
				request.setStatusId(statusID);
				request.setEventDate(date);
				request.setCost(resultSet.getDouble("Costs"));
				request.setDescription(resultSet.getString("Description"));
				request.setLocation(resultSet.getString("Locations"));
				request.setSubmittedAt(resultSet.getString("Submitted_at"));
				requests.add(request);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return requests;	
	}

	@Override
	public Set<Request> getAllRequestsByManagerID(int ManagerID) {
		Set<Request> requests = new HashSet<Request>();
		//fix the sql query
		String sql_query = "SELECT REQUESTS.ID,Event_type_id,Status_id,Event_date,Costs,Description,Locations,Submitted_at FROM REQUESTS "
				+ "	inner join Employee on REQUESTS.submitter_id = Employee.id and Employee.manager_id  = ?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			pStatement.setInt(1, ManagerID);
			ResultSet resultSet = pStatement.executeQuery();
			
			while(resultSet.next()) {
				LocalDate date = LocalDate.parse(resultSet.getString("Event_date"));
				EventType eventTypeID = new EventType(resultSet.getInt("Event_type_id"),"");
				Status statusID = new Status(resultSet.getInt("Status_id"),"");
				Request request = new Request();
				
	
				request.setRequestID(resultSet.getInt("ID"));
				request.setEventTypeId(eventTypeID);
				request.setStatusId(statusID);
				request.setEventDate(date);
				request.setCost(resultSet.getDouble("Costs"));
				request.setDescription(resultSet.getString("Description"));
				request.setLocation(resultSet.getString("Locations"));
				request.setSubmittedAt(resultSet.getString("Submitted_at"));
				requests.add(request);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return requests;	
	}

}
