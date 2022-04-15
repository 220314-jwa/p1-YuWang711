package database.postgre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import beans.Employee;
import database.EmployeeDAO;
import database.util.DatabaseUtil;


public class EmployeePostgre implements EmployeeDAO{
	private static DatabaseUtil dbUtil = DatabaseUtil.getConnectionUtil();
	@Override
	public int create(Employee dataToCreate) {
		int generatedID = 0;
		String sql_query = "INSERT INTO Employee (ID,First_Name,Last_Name,"
				+ "Manager_ID, Department_ID,Username,Salt,Password)"
				+ " VALUES (default,?,?,?,?,?,?,?);";
		String[] keys = { "id" };

		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query,keys);){
			conn.setAutoCommit(false);

			pStatement.setString(1, dataToCreate.getFirstName());
			pStatement.setString(2, dataToCreate.getLastName());
			pStatement.setInt(3, dataToCreate.getManagerID());
			pStatement.setInt(4, dataToCreate.getDeptID());
			pStatement.setString(5, dataToCreate.getUsername());
			pStatement.setString(6, dataToCreate.getSalt());
			pStatement.setString(7, dataToCreate.getPassword());
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
			generatedID = -1;
		}
		return generatedID;
	}

	@Override
	public Employee getById(int EmployeeID) {

		Employee employee = null;
		String sql_query = "SELECT Employee.ID,First_Name,Last_Name,Manager_ID,"
				+ "Department_ID FROM Employee WHERE Employee.ID=?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			pStatement.setInt(1,  EmployeeID);
			ResultSet resultSet = pStatement.executeQuery();
			
			if(resultSet.next()) {
				employee = new Employee();
				employee.setEmployeeID(EmployeeID);
				employee.setFirstName(resultSet.getString("First_Name"));
				employee.setLastName(resultSet.getString("Last_Name"));
				employee.setManagerID(resultSet.getInt("Manager_ID"));
				employee.setDeptID(resultSet.getInt("Department_ID"));

			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public Set<Employee> getAll() {
		Set<Employee> employees = new HashSet<Employee>();
		String sql_query = "SELECT Employee.ID,First_Name,Last_Name,Manager_ID,"
				+ "Department_ID,password,salt FROM Employee;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			ResultSet resultSet = pStatement.executeQuery();
			
			while(resultSet.next()) {
				Employee employee = new Employee();
				employee.setEmployeeID(resultSet.getInt("ID"));
				employee.setFirstName(resultSet.getString("First_Name"));
				employee.setLastName(resultSet.getString("Last_Name"));
				employee.setManagerID(resultSet.getInt("Manager_ID"));
				employee.setDeptID(resultSet.getInt("Department_ID"));
				employee.setPassword(resultSet.getString("password"));
				employee.setSalt(resultSet.getString("salt"));
				employees.add(employee);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	@Override
	public void update(Employee dataToUpdate) {
		//update
		String sql_query = "UPDATE EMPLOYEE "
				+ "SET First_Name = ?,Last_Name = ?,Manager_ID = ?,"
				+ "Department_ID = ?,Username = ?,Password = ?"
				+ ",Salt = ?"
				+ "WHERE ID = ?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			
			pStatement.setString(1, dataToUpdate.getFirstName());
			pStatement.setString(2, dataToUpdate.getLastName());
			pStatement.setInt(3, dataToUpdate.getManagerID());
			pStatement.setInt(4, dataToUpdate.getDeptID());
			pStatement.setString(5, dataToUpdate.getUsername());
			pStatement.setString(6, dataToUpdate.getPassword());
			pStatement.setString(7, dataToUpdate.getSalt());
			pStatement.setInt(8, dataToUpdate.getEmployeeID());
			pStatement.executeQuery();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Employee dataToDelete) {
		//delete
		String sql_query = "DELETE FROM EMPLOYEE"
				+ "WHERE ID = ?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			pStatement.setInt(1, dataToDelete.getEmployeeID());
			pStatement.executeQuery();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getIdByUsername(String user) {
		String sql_query = "SELECT ID FROM Employee WHERE Username=?;";
		try( Connection conn = dbUtil.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql_query);){
				pStatement.setString(1,  user);
				ResultSet resultSet = pStatement.executeQuery();
				int employee_ID;
				if(resultSet.next()) {
					employee_ID = resultSet.getInt("ID");
					return employee_ID;
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return 0;
	}

	@Override
	public String[] getSaltAndHashByUserName(String user) {
		//Write SQL query to get salt and hashedPassword by username
		String sql_query = "SELECT password,salt FROM Employee WHERE Username=?;";
		try( Connection conn = dbUtil.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql_query);){
				pStatement.setString(1,  user);
				ResultSet resultSet = pStatement.executeQuery();
				String[] SaltAndPassword = new String[2];
				if(resultSet.next()) {
					SaltAndPassword[1] = resultSet.getString("password");
					SaltAndPassword[0] = resultSet.getString("salt");
					return SaltAndPassword;
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return null;
	}

}
