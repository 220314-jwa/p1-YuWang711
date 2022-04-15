package database;

import java.util.Set;

import beans.Request;

public interface RequestDAO extends GenericDAO<Request> {
	public Set<Request> getAllRequestsBySubmitterID(int SubmitterID);
	public Set<Request> getAllRequestsByDeptID(int DeptID);
	public Set<Request> getAllRequestsByManagerID(int ManagerID);
}
