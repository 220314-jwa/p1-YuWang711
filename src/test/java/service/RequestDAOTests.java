package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import beans.Employee;
import beans.EventType;
import beans.Request;
import beans.Status;
import database.DepartmentDAO;
import database.EmployeeDAO;
import database.EventTypeDAO;
import database.RequestDAO;
import database.StatusDAO;
import database.postgre.DepartmentPostgre;
import database.postgre.EmployeePostgre;
import database.postgre.EventTypePostgre;
import database.postgre.RequestPostgre;
import database.postgre.StatusPostgre;

@ExtendWith(MockitoExtension.class)
public class RequestDAOTests {
	private RequestDAO requestDAO = new RequestPostgre();
	private EventTypeDAO eventTypeDAO = new EventTypePostgre();
	private StatusDAO statusDAO = new StatusPostgre();
	private DepartmentDAO departmentDAO= new DepartmentPostgre();

	private static int requestId;
	private static int submitter_id = 1;
	private static EventType event_type_id = new EventType(1,"test_eventType");
	private static Status status = new Status(1,"test_status");
	private static String event_date = LocalDate.now().toString();
	private static double cost = 1.0;
	private static String description = "test_description";
	private static String location = "test_location";
	private static String submitted_at = "test_submmited_at";
	
	private static Request testRequest;
	
	@BeforeAll
	public static void init(){
		//Make sure table to created.
		//Create mock data for login
		testRequest = new Request();
		testRequest.setSubmitterId(submitter_id);
		testRequest.setEventTypeId(event_type_id);
		testRequest.setStatusId(status);
		testRequest.setLocation(location);
		testRequest.setCost(cost);
		testRequest.setDescription(description);
		testRequest.setEventDate(event_date);
		testRequest.setSubmittedAt(submitted_at);
		//Mock user session
		//Mock user request
	}
	@AfterAll
	public static void fin() {
		
	}
	//one test for each CRUD functions
	@Test
    @Order(1)
	void createRequest() {
		System.out.println("Create Request:");
		requestId = requestDAO.create(testRequest);
		assertNotEquals(-1,requestId);
	}
	@Test    
	@Order(2)
	void getRequestById() {
		System.out.println("Get Request by ID:");
		Request request = requestDAO.getById(requestId);
		assertEquals(request.getRequestID(), requestId);
	}
	@Test
	@Order(3)
	void getAllRequests() {
		System.out.println("Get All Requests:");
		Set<Request> requests = requestDAO.getAll();
		assertNotEquals(null, requests);
	}
	@Test
	@Order(4)
	void updateRequest() {
		System.out.println("Update Request:");

		Request requestUpdate = requestDAO.getById(requestId);
		System.out.println(requestId);
		requestUpdate.setDescription("test_description_two");
		requestDAO.update(requestUpdate);
		assertEquals("test_description_two",requestDAO.getById(requestId).getDescription());	
	}
	@Test
	@Order(5)
	void getAllRequestsBySubmitterID() {
		System.out.println("Get All Requests by SubmittedID:");

		Set<Request> requests = requestDAO.getAllRequestsBySubmitterID(submitter_id);
		assertNotEquals(null, requests);
	}
	@Test
	@Order(6)
	void getAllRequestsByDeptID() {
		System.out.println("Get All Requests by DeptID:");

		Set<Request> requests = requestDAO.getAllRequestsByDeptID(1);
		assertNotEquals(null, requests);
	}
	@Test
	@Order(7)
	void getAllRequestsByManagerID() {
		System.out.println("Get All Requests by ManagerID:");

		
		Set<Request> requests = requestDAO.getAllRequestsByManagerID(1);
		assertNotEquals(null, requests);

	}

}
