package main;

public class Employee {
	private int employee_id;
	private String first_name;
	private String last_name;
	private String username;
	private int manager_id;
	private int dept_id;
	private String password;
	private String salt;
	protected int test;
	
	//Getter and Setter method for private
	//variables
	
	
	public int getEmployeeID() {
		return employee_id;
	}
	
	public String getFirstName() {
		return first_name;
	}
	
	public String getLastName() {
		return last_name;
	}
	
	public int getManagerID() {
		return manager_id;
	}
	
	public int getDeptID() {
		return dept_id;
	}
	
	public String getSalt() {
		return salt;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

	public boolean setEmployeeID(int EmployeeID) {
		//Validation before setting
		this.employee_id = EmployeeID;
		return true;
	}
	public boolean setFirstName(String FirstName) {
		//Validation before setting
		this.first_name = FirstName;
		return true;
	}
	public boolean setLastName(String LastName) {
		//Validation before setting
		this.last_name = LastName;
		return true;
	}
	public boolean setManagerID(int ManagerID) {
		//Validation before setting
		this.manager_id = ManagerID;
		return true;
	}
	public boolean setDeptID(int DeptID) {
		//Validation before setting
		this.dept_id= DeptID;
		return true;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
