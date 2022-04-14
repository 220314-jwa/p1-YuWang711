package service;

import main.Department;
import main.Employee;
import database.postgre.*;
import exception.IncorrectCredentialExcception;
import exception.UsernameAlreadyExistsException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;

import database.DepartmentDAO;
import database.EmployeeDAO;

import main.PasswordSalt;

//class where DAO is declared and communicate with Database happens
//Employee service should contain
// register, login, sessions,



public class EmployeeService implements UserService<Employee> {
	private EmployeeDAO employee = new EmployeePostgre();
	private DepartmentDAO department = new DepartmentPostgre();
	@Override
	public int register(Employee userType) throws UsernameAlreadyExistsException {
		//Need to check if account/user exist.
		//Call read before create.
		//Generate salt for password
		//provide a userID in database
		try {
			String[] saltAndPassword = PasswordSalt.saltPassword(userType.getPassword());
			userType.setSalt(saltAndPassword[0]);
			userType.setPassword(saltAndPassword[1]);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		int newID = employee.create(userType);

		if(newID > 0) {
			userType.setEmployeeID(newID);
			return newID;
		} else if(newID == -1) {
			throw new UsernameAlreadyExistsException();
		}		
		return -1;
	}

	@Override
	public Employee logIn(Employee userType) throws IncorrectCredentialExcception{
		
		String[] saltAndHash = employee.getSaltAndHashByUserName(userType.getUsername());
		
		try {
			if(saltAndHash == null) {
				throw new IncorrectCredentialExcception();
			}
			boolean validate = PasswordSalt.validatePassword(userType.getPassword(), saltAndHash[0], saltAndHash[1]);	
			if(validate) {
				int employeeID = employee.getIdByUsername(userType.getUsername());
				Employee user = employee.getById(employeeID);
				return user;
			} else {
				throw new IncorrectCredentialExcception();
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Employee getEmployeeByID(int id) {
		return employee.getById(id);
	}

	@Override
	public Set<Department> getDepartments() {
		return department.getAll();
	}
	

}
