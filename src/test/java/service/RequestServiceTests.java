package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import database.DepartmentDAO;
import database.EmployeeDAO;
import database.EventTypeDAO;
import database.RequestDAO;
import database.StatusDAO;
import database.postgre.DepartmentPostgre;
import database.postgre.EventTypePostgre;
import database.postgre.RequestPostgre;
import database.postgre.StatusPostgre;
import exception.IncorrectCredentialExcception;
import exception.RequestSubmittedUnsuccessfully;
import exception.UsernameAlreadyExistsException;
import main.Employee;
import main.Request;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTests {
	/* Overview
	 * Employee Side
	 * Submit request
	 * 		-success
	 * 		-multiple success
	 * Edit request
	 * 		-success
	 * 		-missing credential?
	 * View Request
	 * 		-success
	 * 		-view after edit
	 * Manager Side
	 * view request
	 * 		-success
	 * Accept/Reject request
	 * 		-success
	 * 		-concurrency check with employee side edit request
	 * comment on requeset -optional
	 * 
	 * Department Head Side
	 * view request
	 * 		-success
	 * 		accept/reject request
	 */
	@InjectMocks
	public static RequestServiceImpl requestServ = new RequestServiceImpl();
	
	@Mock
	private RequestDAO requestDAO = new RequestPostgre();
	@Mock
	private EventTypeDAO eventType = new EventTypePostgre();
	@Mock
	private StatusDAO status = new StatusPostgre();
	@Mock
	private DepartmentDAO department= new DepartmentPostgre();
	
	@Test
	void submitRequest() throws RequestSubmittedUnsuccessfully {
		Request request = new Request();
		
		when(requestDAO.create(request)).thenReturn(1);
		
		int requestId = requestServ.submitRequest(request);
		
		assertEquals(1, requestId);
		
	}
	@Test
	void getRequestsByEmployeeID() {
		Set<Request> requests = new HashSet<Request>();
		
		Request requestOne = new Request();
		requestOne.setSubmitterId(2);
		Request requestTwo = new Request();
		requestTwo.setSubmitterId(2);
		
		requests.add(requestOne);
		requests.add(requestTwo);
		
		when(requestDAO.getAllRequestsBySubmitterID(1)).thenReturn(requests);
		Set<Request> actualRequests = requestServ.getRequestsByEmployeeID(1);

		assertEquals(2, actualRequests.size());
	}
	@Test
	void editRequestByRequestID() {		
		Request editRequest = new Request();
		editRequest.setSubmitterId(3);
		editRequest.setDescription("test");
		editRequest.setRequestID(1);
		
		doNothing().when(requestDAO).update(editRequest);
		when(requestDAO.getById(1)).thenReturn(editRequest);
		
		Request actualRequest = requestServ.editRequestByRequestID(editRequest);
		
		assertEquals(editRequest,actualRequest);
	}
	@Test
	void getRequestsByManagerID() {
		Set<Request> requests = new HashSet<Request>();
		
		Request requestTest = new Request();
		requestTest.setSubmitterId(3);
		requestTest.setDescription("test");
		requestTest.setRequestID(1);
		
		requests.add(requestTest);
		when(requestDAO.getAllRequestsByManagerID(1)).thenReturn(requests);
		Set<Request> actualRequests = requestServ.getRequestsByManagerID(1);
		
		assertEquals(1, actualRequests.size());
	}
	@Test
	void getRequestsByDeptID() {
		Set<Request> requests = new HashSet<Request>();
		
		Request requestOne = new Request();
		requestOne.setSubmitterId(2);
		Request requestTwo = new Request();
		requestTwo.setSubmitterId(2);
		
		requests.add(requestOne);
		requests.add(requestTwo);
		
		when(requestDAO.getAllRequestsByDeptID(1)).thenReturn(requests);
		Set<Request> actualRequests = requestServ.getRequestsByDeptID(1);

		assertEquals(2, actualRequests.size());
	}

}
