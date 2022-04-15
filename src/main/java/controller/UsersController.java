package controller;
import java.sql.Connection;

import java.sql.DriverManager;
import java.util.Map;
import java.util.Set;

import beans.Department;
import beans.Employee;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import service.EmployeeService;
import exception.IncorrectCredentialExcception;
import exception.UserNotExistException;
import exception.UsernameAlreadyExistsException;

public class UsersController {
	//Need to connect to SQL
	// Need SQL query that fulfill the following:
	// SQL needs database that has username, password, employeID
	// 2nd Database employeeID and info
	// 3rd Database reimbursement requests
	// add/update/remove query for all three databases
	// Works but need to santisized the input in form
	//Need to send 
	
	// UserController, communicate with web browser.
	private static EmployeeService employeeServ = new EmployeeService();
	public static void register(Context ctx){
		//User already exist exception
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		String firstName = credentials.get("firstName");
		String lastName = credentials.get("lastName");
		String username = credentials.get("username");
		String password = credentials.get("password"); 
		int deptID = Integer.parseInt(credentials.get("departmentId"));
		int managerID = Integer.parseInt(credentials.get("managerId"));

		Employee newEmployee = new Employee();
		newEmployee.setFirstName(firstName);
		newEmployee.setLastName(lastName);
		newEmployee.setUsername(username);		
		newEmployee.setPassword(password);
		newEmployee.setDeptID(deptID);
		newEmployee.setManagerID(managerID);
		
		try {
			int id = employeeServ.register(newEmployee);
			ctx.status(200);
			ctx.status(HttpCode.CREATED);
			System.out.println("POST: 200");
			//Not sure what to return but its fine.
		} catch (UsernameAlreadyExistsException e) {
			//Need to write some homemade exception
			ctx.status(401);
			ctx.result(e.getMessage());
		}

	}
	
	public static void login(Context ctx) {
		//Incorrect Credential Exception
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		String username = credentials.get("username");
		String password = credentials.get("password");
		Employee newEmployee = new Employee();
		newEmployee.setUsername(username);
		newEmployee.setPassword(password);
		System.out.println("requestCenter.html");
		try {
			Employee employee = employeeServ.logIn(newEmployee);
			ctx.json(employee.getEmployeeID());
			ctx.status(200);
		} catch (UserNotExistException | IncorrectCredentialExcception e) {
			if (e instanceof UserNotExistException) {
				ctx.status(401);
			} else if (e instanceof IncorrectCredentialExcception) {
				ctx.status(402);
			}
			ctx.result(e.getMessage());
		}
	}
	
	public static void getUserById(Context ctx) {
		String pathParam = ctx.pathParam("id");
		if (pathParam != null && !pathParam.equals("undefined") && !pathParam.equals("null")) {
			int userId = Integer.parseInt(pathParam);
			
			Employee employee = employeeServ.getEmployeeByID(userId);
			if (employee != null) {
				ctx.json(employee);
			}
			else {
				ctx.status(HttpCode.NOT_FOUND); // 404 not found
			}
		} else {
			ctx.status(HttpCode.BAD_REQUEST); // 400 bad request
		}

	}
	
	public static void getDepartments(Context ctx) {
		Set<Department> departments = employeeServ.getDepartments();
		ctx.json(departments);
		ctx.status(200);
	}
	

}
