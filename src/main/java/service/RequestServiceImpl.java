package service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import beans.Department;
import beans.EventType;
import beans.Request;
import beans.Status;
import database.DepartmentDAO;
import database.RequestDAO;
import database.EventTypeDAO;
import database.StatusDAO;
import database.postgre.DepartmentPostgre;
import database.postgre.RequestPostgre;
import database.postgre.EventTypePostgre;
import database.postgre.StatusPostgre;
import exception.RequestSubmittedUnsuccessfully;

public class RequestServiceImpl implements RequestService{
	private DepartmentDAO departmentDAO = new DepartmentPostgre();
	private RequestDAO requestDAO = new RequestPostgre();
	private EventTypeDAO eventTypeDAO = new EventTypePostgre();
	private StatusDAO statusDAO = new StatusPostgre();
	@Override
	public int submitRequest(Request newRequest) throws RequestSubmittedUnsuccessfully {
		//When new request comes in, the status/eventType id should already be set.
		int requestID = requestDAO.create(newRequest);	
		if(requestID == -1) {		
			throw new RequestSubmittedUnsuccessfully();
		}
		return requestID;
	}
	@Override
	public Set<Request> getRequestsByEmployeeID(int employeeID) {
		Set<Request> requests = new HashSet<Request>();
		requests = requestDAO.getAllRequestsBySubmitterID(employeeID);
		return requests;
	}
	@Override
	public Request editRequestByRequestID(Request newRequest) {
		requestDAO.update(newRequest);
		return requestDAO.getById(newRequest.getRequestID());
	}
	@Override
	public Set<Request> getRequestsByManagerID(int managerID) {
		return requestDAO.getAllRequestsByManagerID(managerID);
	}
	@Override
	public Set<Request> getRequestsByDeptID(int depthID) {
		return requestDAO.getAllRequestsByDeptID(depthID);
	}
	@Override
	public Set<EventType> getAllEventType() {
		return eventTypeDAO.getAll();
	}
	@Override
	public Set<Status> getAllStatuses() {
		return statusDAO.getAll();
	}
	@Override
	public Set<Department> getAllDepartment() {
		return departmentDAO.getAll();
	}
}
