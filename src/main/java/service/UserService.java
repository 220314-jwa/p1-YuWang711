package service;

import java.util.Set;

import beans.Department;
import beans.Employee;
import exception.IncorrectCredentialExcception;
import exception.UserNotExistException;
import exception.UsernameAlreadyExistsException;

public interface UserService<UserType> {
	public int register(UserType userType) throws UsernameAlreadyExistsException;
	public UserType logIn(UserType userType) throws IncorrectCredentialExcception, UserNotExistException;
	public Employee getEmployeeByID(int id);
	public Set<Department> getDepartments();
}
