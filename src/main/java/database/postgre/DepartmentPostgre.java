package database.postgre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import beans.Department;
import beans.Employee;
import database.DepartmentDAO;
import database.util.DatabaseUtil;

public class DepartmentPostgre implements DepartmentDAO{
	private static DatabaseUtil dbUtil = DatabaseUtil.getConnectionUtil();

	@Override
	public int create(Department dataToCreate) {
		int generatedID = 0;
		String sql_query = "insert into Department (ID,DeptName,DeptHeadID) values (default,?, ?);";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			conn.setAutoCommit(false);

			pStatement.setString(1, dataToCreate.getDepardment_Name());
			pStatement.setInt(2, dataToCreate.getDept_head_id());

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
	public Department getById(int data) {
		Department department = new Department();
		String sql_query = "SELECT * FROM Department WHERE Department.ID=?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			pStatement.setInt(1,  data);
			ResultSet resultSet = pStatement.executeQuery();
			if(resultSet.next()) {
				department.setDepardment_Name(resultSet.getString("DeptName"));
				department.setDepartment_id(resultSet.getInt("ID"));
				department.setDept_head_id(resultSet.getInt("DeptHeadID"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return department;
	}

	@Override
	public Set<Department> getAll() {
		Set<Department> departments = new HashSet<Department>();
		String sql_query = "SELECT * FROM Department;";
		try( Connection conn = dbUtil.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql_query);){
				ResultSet resultSet = pStatement.executeQuery();
				
				while(resultSet.next()) {
					Department department = new Department(resultSet.getInt("ID"),
							resultSet.getString("DeptName"),
							resultSet.getInt("DeptHeadID"));

					departments.add(department);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return departments;
	}

	@Override
	public void update(Department dataToUpdate) {
		// TODO Auto-generated method stub
		String sql_query = "UPDATE Department SET DeptName = ?, DeptHeadID = ? WHERE ID = ?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			
			pStatement.setString(1, dataToUpdate.getDepardment_Name());
			pStatement.setInt(2, dataToUpdate.getDept_head_id());
			pStatement.setInt(3, dataToUpdate.getDepartment_id());

			pStatement.executeQuery();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Department dataToDelete) {
		// TODO Auto-generated method stub
		String sql_query = "Delete FROM Department WHERE ID = ?;";
		try( Connection conn = dbUtil.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql_query);){
			
			pStatement.setInt(1, dataToDelete.getDepartment_id());

			pStatement.executeQuery();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}


}
