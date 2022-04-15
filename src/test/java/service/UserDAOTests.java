package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import beans.Employee;
import database.EmployeeDAO;
import database.postgre.EmployeePostgre;
import exception.UsernameAlreadyExistsException;

public class UserDAOTests {
	private EmployeeDAO employeeDAO = new EmployeePostgre();
	private static int testID;
	private static String FirstName = "test_fist";
	private static String LastName = "test_last";
	private static int ManagerID = 200;
	private static int DeptID = 1;
	private static String Username = "test_username";
	private static String Password = "test_password";
	private static String Salt = "test_salt";
	private static Employee employee;

	@BeforeAll
	public static void init(){
		//Make sure table to created.
		//Create mock data for login
		employee = new Employee();
		employee.setFirstName(FirstName);
		employee.setLastName(LastName);
		employee.setManagerID(ManagerID);
		employee.setDeptID(DeptID);
		employee.setUsername(Username);
		employee.setPassword(Password);
		employee.setSalt(Salt);
		//Mock user session
		//Mock user request
	}	
	@Test
    @Order(1)
	void createUser() {
		testID = employeeDAO.create(employee);
		assertNotEquals(0,testID);
	}
	@Test
    @Order(2)
	void createExistingUser() throws SQLException { //catch sql exception
		assertThrows(SQLException.class ,()->{
			employeeDAO.create(employee);
		});
	}
	@Test
    @Order(3)
	void getById() {
		Employee employeeFromGet = employeeDAO.getById(testID);
		assertEquals(employee, employeeFromGet);
	}
	@Test
	void getAll() {
		Set<Employee> employees = employeeDAO.getAll();
		assertNotEquals(null, employees);
	}
	@Test
	void update() {
		//Not needed because it is not in the requirement
	}
	@Test
	void delete() {
		//Don't use delete for anything
	}
	@Test
    @Order(4)
	void getIdByUsername() {
		//setting up passwords
		int employeeID = employeeDAO.getIdByUsername(Username);
		int id = testID;
		assertEquals(id, employeeID);
	}
	@Test
    @Order(5)
	void getSaltAndHashByUserName() {
		String[] saltandhash = employeeDAO.getSaltAndHashByUserName(Username);
		assertEquals(saltandhash[0], Salt);
		assertEquals(saltandhash[1], Password);
	}
}
