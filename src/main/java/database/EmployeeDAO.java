package database;

import main.Employee; // Need to Change this later

public interface EmployeeDAO extends GenericDAO<Employee> {
	public int getIdByUsername(String user);
	public String[] getSaltAndHashByUserName(String user);
}
