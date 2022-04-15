package database;

import beans.Employee;

public interface EmployeeDAO extends GenericDAO<Employee> {
	public int getIdByUsername(String user);
	public String[] getSaltAndHashByUserName(String user);
}
