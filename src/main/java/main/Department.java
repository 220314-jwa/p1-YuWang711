package main;

public class Department {
	private int Department_id;
	private String Depardment_Name;
	private int dept_head_id;
	
	public Department() {
		this.setDepartment_id(1);

	}
	
	public Department(int Department_id, String Department_Name, int dept_head_id) {
		this.setDepardment_Name(Department_Name);
		this.setDepartment_id(Department_id);
		this.setDept_head_id(dept_head_id);
	}

	public int getDept_head_id() {
		return dept_head_id;
	}

	public void setDept_head_id(int dept_head_id) {
		this.dept_head_id = dept_head_id;
	}

	public String getDepardment_Name() {
		return Depardment_Name;
	}

	public void setDepardment_Name(String depardment_Name) {
		Depardment_Name = depardment_Name;
	}

	public int getDepartment_id() {
		return Department_id;
	}

	public void setDepartment_id(int department_id) {
		Department_id = department_id;
	}
	
	
}
