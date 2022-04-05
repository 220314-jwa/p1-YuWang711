package service;

import java.util.Map;
import java.util.Set;

import database.DepartmentDAO;
import database.postgre.DepartmentPostgre;
import exception.RequestSubmittedUnsuccessfully;
import main.Department;
import main.EventType;
import main.Request;
import main.Status;

public interface RequestService {
	//Requests
	public int submitRequest(Request newRequest) throws RequestSubmittedUnsuccessfully;
	public Set<Request> getRequestsByEmployeeID(int employeeID);
	public Request editRequestByRequestID(Request newRequest);
	public Set<Request> getRequestsByManagerID(int employeeID);	
	public Set<Request> getRequestsByDeptID(int employeeID);
	
	//Forms
	public Set<EventType> getAllEventType();
	public Set<Status> getAllStatuses();
	public Set<Department> getAllDepartment();

}
