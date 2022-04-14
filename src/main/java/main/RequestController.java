package main;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import exception.RequestSubmittedUnsuccessfully;
import exception.UsernameAlreadyExistsException;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import service.RequestServiceImpl;

public class RequestController {
	private static RequestServiceImpl requestServiceImpl = new RequestServiceImpl();
	final static String requestID = "requestID";
	final static String submitterID = "submitterID";
	final static String eventTypeID = "eventTypeID";
	final static String eventDate = "eventDate";
	final static String statusID = "statusID";
	final static String costs = "costs";
	final static String description = "description";
	final static String location = "location";
	final static String submittedAt = "submittedAt";
	final static String deptHeadID = "deptHeadID";
	final static String managerID = "managerID";
	
	
	public static void submitRequest(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		Request request = new Request();

		request.setSubmitterId(Integer.parseInt(credentials.get(submitterID)));
		request.setEventTypeId(new EventType(Integer.parseInt(credentials.get(eventTypeID)),""));
		request.setStatusId(new Status(Integer.parseInt(credentials.get(statusID)),""));
		request.setEventDate(LocalDate.parse(credentials.get(eventDate)).toString());
		request.setCost(Double.parseDouble(credentials.get(costs)));
		request.setDescription(credentials.get(description));
		request.setLocation(credentials.get(location));
		request.setSubmittedAt(credentials.get(submittedAt));
		try {
			int requestID = requestServiceImpl.submitRequest(request);
			ctx.json(requestID);
			ctx.status(200);			
			//Not sure what to return but its fine.
		} catch (RequestSubmittedUnsuccessfully e) {
			//Need to write some homemade exception
			ctx.status(401);
			ctx.result(e.getMessage());
		} 
	}
	
	public static void getRequestsByEmployeeID(Context ctx){
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		
		int employeeID = Integer.parseInt(credentials.get("employeeID"));
		
		Set<Request> requests = requestServiceImpl.getRequestsByEmployeeID(employeeID);
		System.out.println("getrequests");
		ctx.json(requests);
		ctx.status(200);	
	}
	
	public static void editRequestByRequestID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		Request request = new Request();

		request.setRequestID(Integer.parseInt(credentials.get(requestID)));
		request.setSubmitterId(Integer.parseInt(credentials.get(submitterID)));
		request.setEventTypeId(new EventType(Integer.parseInt(credentials.get(eventTypeID)),""));
		request.setStatusId(new Status(Integer.parseInt(credentials.get(statusID)),""));
		request.setEventDate(LocalDate.parse(credentials.get(eventDate)).toString());
		request.setCost(Double.parseDouble(credentials.get(costs)));
		request.setDescription(credentials.get(description));
		request.setLocation(credentials.get(location));
		request.setSubmittedAt(credentials.get(submittedAt));
		//try {
			ctx.status(HttpCode.CREATED);
			requestServiceImpl.editRequestByRequestID(request);
			ctx.status(200);			
			//Not sure what to return but its fine.
		//} catch (RequestSubmittedUnsuccessfully e) {
		//	//Need to write some homemade exception
		//	ctx.status(401);
		//	ctx.result(e.getMessage());
		//} 
	}
	
	public static void getRequestsByManagerID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		
		int mID = Integer.parseInt(credentials.get(managerID));
		Set<Request> requests = requestServiceImpl.getRequestsByManagerID(mID);
		ctx.json(requests);
		ctx.status(200);	

	}
	public static void editRequestByManagerID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		Request request = new Request();

		request.setRequestID(Integer.parseInt(credentials.get(requestID)));
		request.setStatusId(new Status(Integer.parseInt(credentials.get(statusID)),""));

		//try {
			ctx.status(HttpCode.CREATED);
			requestServiceImpl.editRequestByRequestID(request);
			ctx.status(200);	
	}
	public static void getRequestsByDeptID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		
		int dID = Integer.parseInt(credentials.get(deptHeadID));
		Set<Request> requests = requestServiceImpl.getRequestsByDeptID(dID);
		ctx.json(requests);
		ctx.status(200);	

	}
	public static void editRequestByDeptID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		Request request = new Request();

		request.setRequestID(Integer.parseInt(credentials.get(requestID)));
		request.setSubmitterId(Integer.parseInt(credentials.get(submitterID)));
		request.setEventTypeId(new EventType(Integer.parseInt(credentials.get(eventTypeID)),""));
		request.setStatusId(new Status(Integer.parseInt(credentials.get(statusID)),""));
		request.setEventDate(LocalDate.parse(credentials.get(eventDate)).toString());
		request.setCost(Double.parseDouble(credentials.get(costs)));
		request.setDescription(credentials.get(description));
		request.setLocation(credentials.get(location));
		request.setSubmittedAt(credentials.get(submittedAt));
		//try {
			ctx.status(HttpCode.CREATED);
			requestServiceImpl.editRequestByRequestID(request);
			ctx.status(200);	
	}
	
	public static void getAllEventType(Context ctx) {
		Set<EventType> eventType = requestServiceImpl.getAllEventType();
		ctx.json(eventType);
		ctx.status(200);
	}
}
