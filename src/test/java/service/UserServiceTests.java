package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import beans.Employee;
import database.EmployeeDAO;
import exception.IncorrectCredentialExcception;
import exception.UserNotExistException;
import exception.UsernameAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
	/*
	 * Service layer test
	 * 	-register
	 * 		-account doesn't exist, test for exception
	 * 		-account exist
	 * 		-invalid input			//exception not written
	 * 		-missing credentials	//exception not written
	 * 	-login
	 * 		-success
	 * 		-account doesn't exist
	 * 		-incorrect credential
	 * 		-invalid input
	 * 	-submit request
	 * 		-success
	 * 		-submit consecutive requests
	 * 		-incorrect input
	 *  -view request
	 *  	-success for all users
	 *  	-multiple view requests
	 *  -edit request
	 *  	-success for all users
	 *  	-incorrect input
	 *  -approve/reject request
	 *  	-success for all user
	 *  -more approve/reject request
	 *  	-success for all user
	 */
	@InjectMocks
	public static EmployeeService employeeServ = new EmployeeService();
	
	@Mock
	private EmployeeDAO employeeDAO;
	@Mock
	private static Employee mockerUser_1;
	@BeforeAll
	public static void init() throws UsernameAlreadyExistsException {
		//Make sure table to created.
		//Create mock data for login
		mockerUser_1 = new Employee();
		mockerUser_1.setFirstName("first name");
		mockerUser_1.setLastName("last name");
		mockerUser_1.setUsername("mockUser_1");
		mockerUser_1.setPassword("mockUser_password");
		mockerUser_1.setDeptID(1);
		mockerUser_1.setManagerID(1);
		int mockUserID = employeeServ.register(mockerUser_1);
		mockerUser_1.setEmployeeID(mockUserID);
	}	
	@AfterAll
	public static void fin() {
		//need to truncate/ delete table?
	}
	
	@Test
	void sampleTest() {
		
	}
	//Possibly need a init
	@Test
	public void registerSuccess() throws UsernameAlreadyExistsException {
		System.out.println("register success:");

		Employee employee = new Employee();
		employee.setUsername("test_user_01");
		employee.setPassword("test_user_password");
		
		when(employeeDAO.create(employee)).thenReturn(2);
		
		int id = employeeServ.register(employee);
		assertTrue(0 < id);
		//Call delete employee afterward.
	}
	@Test
	void registerAccountExisted() throws UsernameAlreadyExistsException {
		System.out.println("register account exists");
		Employee employee = new Employee();
		employee.setUsername("test_user_01");
		employee.setPassword("test_user_password");
		
		when(employeeDAO.create(employee)).thenReturn(-1);		
		
		assertThrows(UsernameAlreadyExistsException.class ,()->{
			employeeServ.register(employee);
		});
	}
	@Test
	void registerInvalidInput() {
		//Do I need this?
	}
	@Test
	void registerMissingCredential() {
		//Potentially not needed to test this
	}
	@Test 
	void loginSuccess() throws IncorrectCredentialExcception, UserNotExistException {
		Employee mockUser = new Employee();
		mockUser.setUsername("mockUser_1");
		mockUser.setPassword("mockUser_password");
		employeeServ.logIn(mockUser);
		
		assertEquals(mockUser.getUsername(),mockerUser_1.getUsername());
	}
	@Test
	void loginAccountDoesNotExist() {
		//Might just be the same with incrroect credential
	}
	@Test
	void loginIncorrectCredential() {
		Employee mockUser = new Employee();
		mockUser.setUsername("test_user_02");
		mockUser.setPassword("test_user_password");

		assertThrows(IncorrectCredentialExcception.class ,()->{
			employeeServ.logIn(mockUser);
		});
	}

}
