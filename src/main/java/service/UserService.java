package service;

import java.util.Set;

import exception.IncorrectCredentialExcception;
import exception.UsernameAlreadyExistsException;
import main.Department;
import main.Employee;

public interface UserService<UserType> {
	public int register(UserType userType) throws UsernameAlreadyExistsException;
	public UserType logIn(UserType userType) throws IncorrectCredentialExcception;
	public Employee getEmployeeByID(int id);
	public Set<Department> getDepartments();
}
